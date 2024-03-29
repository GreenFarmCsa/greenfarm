
import UIKit
import SceneKit
import ARKit
import Photos
import AudioToolbox
import VideoToolbox

class RulerARProViewController: UIViewController {
    
    var cameraNode: SCNNode!
    
    enum MeasurementMode {
        case length
        case area
        func toAttrStr() -> NSAttributedString {
            let str = self == .area ? Localization.startArea.toString() : Localization.startLength.toString()
            return NSAttributedString(string: str, attributes: [NSAttributedStringKey.font : UIFont.boldSystemFont(ofSize: 20),
                                                                 NSAttributedStringKey.foregroundColor: UIColor.white])
        }
    }
    struct Image {
        struct Menu {
            static let area = #imageLiteral(resourceName: "gf_menu_area")
            static let length = #imageLiteral(resourceName: "menu_length")
            static let reset = #imageLiteral(resourceName: "menu_reset")
            static let setting = #imageLiteral(resourceName: "menu_setting")
            static let save = #imageLiteral(resourceName: "menu_save")
        }
        struct More {
            static let close = #imageLiteral(resourceName: "more_off")
            static let open = UIImage.init(named: "gf_greenbk")
        }
        struct Place {
            static let area = #imageLiteral(resourceName: "gf_menu_area")
            static let length = #imageLiteral(resourceName: "place_length")
            static let done = #imageLiteral(resourceName: "gf_place_done")
        }
        struct Close {
            static let delete = #imageLiteral(resourceName: "gf_cancle_delete")
            static let cancle = #imageLiteral(resourceName: "gf_cancle_delete")
        }
        struct Indicator {
            static let enable = #imageLiteral(resourceName: "gf_img_indicator_enable")
            static let disable = #imageLiteral(resourceName: "gf_img_indicator_disable")
        }
        struct Result {
            static let copy = #imageLiteral(resourceName: "result_copy")
            static let back = #imageLiteral(resourceName: "gf_cancle_back")
        }
    }
    
    struct Sound {
        static var soundID: SystemSoundID = 0
        static func install() {
            guard let path = Bundle.main.path(forResource: "SetPoint", ofType: "wav") else { return  }
            let url = URL(fileURLWithPath: path)
            AudioServicesCreateSystemSoundID(url as CFURL, &soundID)
        }
        static func play() {
            guard soundID != 0 else { return }
            AudioServicesPlaySystemSound(soundID)
        }
        static func dispose() {
            guard soundID != 0 else { return }
            AudioServicesDisposeSystemSoundID(soundID)
        }

    }
    private let sceneView: ARSCNView =  ARSCNView(frame: UIScreen.main.bounds)
    private let indicator = UIImageView()
    private let resultLabel = UILabel().then({
        $0.textAlignment = .center
        $0.textColor = UIColor.white
        $0.numberOfLines = 0
        $0.font = UIFont.systemFont(ofSize: 10, weight: UIFont.Weight.heavy)
    })

    
    private var line: LineNode?
    private var lineSet: LineSetNode?
    
    
    private var lines: [LineNode] = []
    private var lineSets: [LineSetNode] = []
    private var planes = [ARPlaneAnchor: Plane]()
    private var focusSquare: FocusSquare?
    
    
    
    private var mode = MeasurementMode.area
    private var finishButtonState = false
    private var lastState: ARCamera.TrackingState = .notAvailable {
        didSet {
            switch lastState {
            case .notAvailable:
                guard HUG.isVisible else { return }
                HUG.show(title: Localization.arNotAvailable.toString())
            case .limited(let reason):
                switch reason {
                case .initializing:
                    HUG.show(title: Localization.arInitializing.toString(), message: Localization.arInitializingMessage.toString(), inSource: self, autoDismissDuration: 5)
                case .insufficientFeatures:
                    HUG.show(title: Localization.arExcessiveMotion.toString(), message: Localization.arInitializingMessage.toString(), inSource: self, autoDismissDuration: 5)
                case .excessiveMotion:
                    HUG.show(title: Localization.arExcessiveMotion.toString(), message: Localization.arExcessiveMotionMessage.toString(), inSource: self, autoDismissDuration: 5)
                case .relocalizing:
                    HUG.show(title: Localization.arRelocalizing.toString(), message: Localization.arRelocalizing.toString(), inSource: self, autoDismissDuration: 5)
                }
            case .normal:
                HUG.dismiss()
            }
        }
    }
    private var measureUnit = ApplicationSetting.Status.defaultUnit {
        didSet {
            let v = measureValue
            measureValue = v
        }
    }
    private var measureValue: MeasurementUnit? {
        didSet {
            if let m = measureValue {
                resultLabel.text = nil
                resultLabel.attributedText = m.attributeString(type: measureUnit)
            } else {
                resultLabel.attributedText = mode.toAttrStr()
            }
        }
    }
    
    
    
    private lazy var menuButtonSet: PopButton = PopButton(buttons: menuButton.measurement,
                                                          menuButton.save,
                                                          menuButton.reset,
                                                          menuButton.setting,
                                                          menuButton.more)
    
    private let placeButton = UIButton(size: CGSize(width: 80, height: 80), image: Image.Place.area)
    private let cancleButton = UIButton(size: CGSize(width: 60, height: 60), image: Image.Close.delete)
    private let finishButton = UIButton(size: CGSize(width: 60, height: 60), image: Image.Place.done)
    private let doneButton = UIButton(size: CGSize(width: 70, height: 70), image: nil)
    private let menuButton = (measurement: UIButton(size: CGSize(width: 50, height: 50), image: Image.Menu.area),
                         save: UIButton(size: CGSize(width: 50, height: 50), image: Image.Menu.save),
                        reset: UIButton(size: CGSize(width: 50, height: 50), image: Image.Menu.reset),
                        setting: UIButton(size: CGSize(width: 50, height: 50), image: Image.Menu.setting),
                        more: UIButton(size: CGSize(width: 60, height: 60), image: Image.More.close))
    
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        layoutViewController()
       // setupFocusSquare()
        Sound.install()
        cameraNode = SCNNode()
        sceneView.scene.rootNode.addChildNode(cameraNode)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        restartSceneView()
    }
    
    
    override var prefersStatusBarHidden: Bool {
        return true
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        sceneView.session.pause()
    }
    
    private func layoutViewController() {
        let width = view.bounds.width
        let height = view.bounds.height
        view.backgroundColor = UIColor.black
        
        
        do {
            view.addSubview(sceneView)
            sceneView.frame = view.bounds
            sceneView.delegate = self
        }
        do {
            

            let resultLabelBg = UIView()

            let copy = UIButton(size: CGSize(width: 30, height: 30), image: Image.Result.copy)
            copy.addTarget(self, action: #selector(RulerARProViewController.copyAction(_:)), for: .touchUpInside)
            let backBtn = UIButton(size: CGSize(width: 30, height: 30), image: Image.Result.back)
            backBtn.addTarget(self, action: #selector(RulerARProViewController.backBtnAction(_:)), for: .touchUpInside)
         
            
            let tap = UITapGestureRecognizer(target: self, action: #selector(RulerARProViewController.changeMeasureUnitAction(_:)))
            resultLabel.addGestureRecognizer(tap)
            resultLabel.isUserInteractionEnabled = true
            
            resultLabelBg.frame = CGRect(x: 70, y: 40, width: width - 70*2, height: 35)
            let bkImage = UIImageView(image: UIImage.init(named: "black"))
            bkImage.frame = CGRect(x: 0, y: 0, width:resultLabelBg.frame.size.width , height: resultLabelBg.frame.size.height)
            resultLabelBg.addSubview(bkImage)
            
            copy.frame = CGRect(x: resultLabelBg.frame.maxX - 10 - 30,
                                y: resultLabelBg.frame.minY + (resultLabelBg.frame.height - 30)/2,
                                width: 30, height: 30)
            backBtn.frame = CGRect(x: 20, y: 0, width: 30, height: 30)
            backBtn.center.y = resultLabelBg.center.y
            resultLabel.frame = resultLabelBg.frame.insetBy(dx: 10, dy: 0)
            resultLabel.attributedText = mode.toAttrStr()
       
            
            view.addSubview(resultLabelBg)
            view.addSubview(resultLabel)
            self.view.addSubview(backBtn)
        }
        
        do {
            indicator.image = Image.Indicator.disable
            view.addSubview(indicator)
            indicator.frame = CGRect(x: (width - 60)/2, y: (height - 60)/2, width: 60, height: 60)
        }
        do {
            view.addSubview(finishButton)
            view.addSubview(doneButton)
            view.addSubview(placeButton)
            finishButton.addTarget(self, action: #selector(RulerARProViewController.finishAreaAction(_:)), for: .touchUpInside)
            doneButton.addTarget(self, action: #selector(RulerARProViewController.doneClicked(_:)), for: .touchUpInside)
            doneButton.setTitle("Done", for: UIControl.State.normal)
            doneButton.setTitleColor(UIColor.white, for: UIControl.State.normal)
            doneButton.setBackgroundImage(Image.More.open, for: UIControl.State.normal)
            
            placeButton.addTarget(self, action: #selector(RulerARProViewController.placeAction(_:)), for: .touchUpInside)
            placeButton.frame = CGRect(x: (width - 80)/2, y: (height - 20 - 80), width: 80, height: 80)
            finishButton.center = placeButton.center
            doneButton.frame = CGRect(x: (width - 40 - 60), y: placeButton.frame.origin.y + 10, width: 60, height: 60)
        }
        do {
            view.addSubview(cancleButton)
            cancleButton.addTarget(self, action: #selector(RulerARProViewController.deleteAction(_:)), for: .touchUpInside)
            cancleButton.frame = CGRect(x: 40, y: placeButton.frame.origin.y + 10, width: 60, height: 60)
        }
        do {
            menuButton.more.addTarget(self, action: #selector(RulerARProViewController.showMenuAction(_:)), for: .touchUpInside)
            menuButton.setting.addTarget(self, action: #selector(RulerARProViewController.moreAction(_:)), for: .touchUpInside)
            menuButton.reset.addTarget(self, action: #selector(RulerARProViewController.restartAction(_:)), for: .touchUpInside)
            menuButton.measurement.addTarget(self, action: #selector(RulerARProViewController.changeMeasureMode(_:)), for: .touchUpInside)
            menuButton.save.addTarget(self, action: #selector(RulerARProViewController.saveImage(_:)), for: .touchUpInside)
            menuButtonSet.frame = CGRect(x: (width - 40 - 60), y: placeButton.frame.origin.y + 10, width: 60, height: 60)
            
        }
    }
    
    private func configureObserver() {
        func cleanLine() {
            line?.removeFromParent()
            line = nil
            for node in lines {
                node.removeFromParent()
            }
            
        }
        NotificationCenter.default.addObserver(forName: NSNotification.Name.UIApplicationDidEnterBackground, object: nil, queue: OperationQueue.main) { _ in
            cleanLine()
        }
    }
    
    deinit {
        Sound.dispose()
        NotificationCenter.default.removeObserver(self)
    }
}


// MARK: - Target Action
@objc private extension RulerARProViewController {
    // 保存测量结果
    func saveImage(_ sender: UIButton) {
        func saveImage(image: UIImage) {
            PHPhotoLibrary.shared().performChanges({
                PHAssetChangeRequest.creationRequestForAsset(from: image)
            }) { (isSuccess: Bool, error: Error?) in
                if let e = error {
                    HUG.show(title: Localization.saveFail.toString(), message: e.localizedDescription)
                } else{
                    HUG.show(title: Localization.saveSuccess.toString())
                }
            }
        }
        
        let image = sceneView.snapshot()
        switch PHPhotoLibrary.authorizationStatus() {
        case .authorized:
            saveImage(image: image)
        default:
            PHPhotoLibrary.requestAuthorization { (status) in
                switch status {
                case .authorized:
                    saveImage(image: image)
                default:
                    HUG.show(title: Localization.saveFail.toString(), message: Localization.saveNeedPermission.toString())
                }
            }
        }
    }
    
    
    // 放置测量点
    func placeAction(_ sender: UIButton) {
        UIView.animate(withDuration: 0.2, delay: 0, usingSpringWithDamping: 1, initialSpringVelocity: 0, options: [.allowUserInteraction,.curveEaseOut], animations: {
            sender.transform = CGAffineTransform(scaleX: 1.2, y: 1.2)
        }) { (value) in
            UIView.animate(withDuration: 0.2, delay: 0, usingSpringWithDamping: 1, initialSpringVelocity: 0, options: [.allowUserInteraction,.curveEaseIn], animations: {
                sender.transform = CGAffineTransform.identity
            }) { (value) in
            }
        }
        Sound.play()
        switch mode {
        case .length:
            if let l = line {
                lines.append(l)
                line = nil
            } else  {
                let startPos = sceneView.worldPositionFromScreenPosition(indicator.center, objectPos: nil)
                if let p = startPos.position {
                    line = LineNode(cameraNode: cameraNode,startPos: p, sceneV: sceneView)
                }
            }
        case .area:
            if let l = lineSet {
                l.addLine(cameraNode: cameraNode)
            } else {
                let startPos = sceneView.worldPositionFromScreenPosition(indicator.center, objectPos: nil)
                if let p = startPos.position {
                    lineSet = LineSetNode(cameraNode: cameraNode,startPos: p, sceneV: sceneView)
                }
            }
        }
    }
    
    // 重置视图
    func restartAction(_ sender: UIButton) {
        showMenuAction(sender)
        line?.removeFromParent()
        line = nil
        for node in lines {
            node.removeFromParent()
        }
        
        lineSet?.removeFromParent()
        lineSet = nil
        for node in lineSets {
            node.removeFromParent()
        }
        restartSceneView()
        measureValue = nil
    }
    
    // 删除上一操作
    func deleteAction(_ sender: UIButton) {
        switch mode {
        case .length:
            if line != nil {
                line?.removeFromParent()
                line = nil
            } else if let lineLast = lines.popLast() {
                lineLast.removeFromParent()
            } else {
                lineSets.popLast()?.removeFromParent()
            }
        case .area:
            if let ls = lineSet {
                if !ls.removeLine(cameraNode: cameraNode) {
                    lineSet = nil
                }
            } else if let lineSetLast = lineSets.popLast() {
                lineSetLast.removeFromParent()
            } else {
                lines.popLast()?.removeFromParent()
            }
        }
        cancleButton.normalImage = Image.Close.delete
        measureValue = nil
    }
    
    
    // 复制测量结果
    func copyAction(_ sender: UIButton) {
        UIPasteboard.general.string = resultLabel.text
        HUG.show(title: "已复制到剪贴版")
    }
    
    // 返回上一页
    func backBtnAction(_ sender: UIButton) {
        self.dismiss(animated: true) {
           
        };
    }
    
    // 跳转设置
    func moreAction(_ sender: UIButton) {
        guard let vc = UIStoryboard(name: "SettingViewController", bundle: nil).instantiateInitialViewController() else {
            return
        }
        showMenuAction(sender)
        present(vc, animated: true, completion: nil)
    }
    
    
    // 显示菜单
    func showMenuAction(_ sender: UIButton) {
        if menuButtonSet.isOn {
            menuButtonSet.dismiss()
            menuButton.more.normalImage = Image.More.close
        } else {
            menuButtonSet.show()
            menuButton.more.normalImage = Image.More.open
        }
    }
    
    // 完成面积测量
    func finishAreaAction(_ sender: UIButton) {
        guard mode == .area,
            let line = lineSet,
            line.lines.count >= 2 else {
                lineSet = nil
                return
        }
        lineSets.append(line)
        lineSet = nil
        changeFinishState(state: false)
    }
    
    func doneClicked(_ sender:UIButton) {
        print("done")
        NotificationCenter.default.post(name: NSNotification.Name(rawValue:"areaValueNotify"), object: resultLabel.text!)
           self.dismiss(animated: true) {
              
           };

    }
    
    
    
    // 变换面积测量完成按钮状态
    func changeFinishState(state: Bool) {
        guard finishButtonState != state else { return }
        finishButtonState = state
        var center = placeButton.center
        if state {
            center.y -= 100
        }
        UIView.animate(withDuration: 0.3) {
            self.finishButton.center = center
        }
    }
    
    // 变换测量单位
    func changeMeasureUnitAction(_ sender: UITapGestureRecognizer) {
        measureUnit = measureUnit.next()
    }
    
    
    func changeMeasureMode(_ sender: UIButton) {
        showMenuAction(sender)
        lineSet = nil
        line = nil
        switch mode {
        case .area:
            changeFinishState(state: false)
            menuButton.measurement.normalImage = Image.Menu.area
            placeButton.normalImage  = Image.Place.length
            placeButton.disabledImage = Image.Place.length

            mode = .length
        case .length:
            menuButton.measurement.normalImage = Image.Menu.length
            placeButton.normalImage  = Image.Place.area
            placeButton.disabledImage = Image.Place.area
            mode = .area
        }
        resultLabel.attributedText = mode.toAttrStr()
    }
    
    
}


// MARK: - UI
fileprivate extension RulerARProViewController {
    
    func restartSceneView() {
        let configuration = ARWorldTrackingConfiguration()
        configuration.planeDetection = .horizontal
        sceneView.session.run(configuration, options: [.resetTracking, .removeExistingAnchors])
        sceneView.debugOptions = [ARSCNDebugOptions.showFeaturePoints]
        measureUnit = ApplicationSetting.Status.defaultUnit
        resultLabel.attributedText = mode.toAttrStr()
        updateFocusSquare()
    }
    
    func updateLine() -> Void {
        let startPos = sceneView.worldPositionFromScreenPosition(self.indicator.center, objectPos: nil)
        if let p = startPos.position {
            let camera = self.sceneView.session.currentFrame?.camera
            let cameraPos = SCNVector3.positionFromTransform(camera!.transform)
            if cameraPos.distanceFromPos(pos: p) < 0.05 {
                if line == nil {
                    placeButton.isEnabled = false
                    indicator.image = Image.Indicator.disable
                }
                return;
            }
            placeButton.isEnabled = true
            indicator.image = Image.Indicator.enable
        
            cameraNode.position = cameraPos//zll
            
            switch mode {
            case .length:
                guard let currentLine = line else {
                    cancleButton.normalImage = Image.Close.delete
                    return
                }
                let length = currentLine.updatePosition(pos: p, camera: self.sceneView.session.currentFrame?.camera, unit: measureUnit)
                measureValue =  MeasurementUnit(meterUnitValue: length, isArea: false)
                cancleButton.normalImage = Image.Close.cancle
            case .area:
                guard let set = lineSet else {
                    changeFinishState(state: false)
                    cancleButton.normalImage = Image.Close.delete
                    return
                }
                let area = set.updatePosition(pos: p, camera: self.sceneView.session.currentFrame?.camera, unit: measureUnit)
                measureValue =  MeasurementUnit(meterUnitValue: area, isArea: true)
                changeFinishState(state: set.lines.count >= 2)
                cancleButton.normalImage = Image.Close.cancle
            }
        }
    }
}

// MARK: - Plane
fileprivate extension RulerARProViewController {
    func addPlane(node: SCNNode, anchor: ARPlaneAnchor) {
        
        let plane = Plane(anchor, false)
        planes[anchor] = plane
        node.addChildNode(plane)
        indicator.image = Image.Indicator.enable
    }
    
    func updatePlane(anchor: ARPlaneAnchor) {
        if let plane = planes[anchor] {
            plane.update(anchor)
        }
    }
    
    func removePlane(anchor: ARPlaneAnchor) {
        if let plane = planes.removeValue(forKey: anchor) {
            plane.removeFromParentNode()
        }
    }
}

// MARK: - FocusSquare
fileprivate extension RulerARProViewController {
    
    func setupFocusSquare() {
        focusSquare?.isHidden = true
        focusSquare?.removeFromParentNode()
        focusSquare = FocusSquare()
        sceneView.scene.rootNode.addChildNode(focusSquare!)
    }
    
    func updateFocusSquare() {
        if ApplicationSetting.Status.displayFocus {
            focusSquare?.unhide()
        } else {
            focusSquare?.hide()
        }
        let (worldPos, planeAnchor, _) = sceneView.worldPositionFromScreenPosition(sceneView.bounds.mid, objectPos: focusSquare?.position)
        if let worldPos = worldPos {
            focusSquare?.update(for: worldPos, planeAnchor: planeAnchor, camera: sceneView.session.currentFrame?.camera)
        }
    }
}


// MARK: - ARSCNViewDelegate
extension RulerARProViewController: ARSCNViewDelegate {
    
    func session(_ session: ARSession, didFailWithError error: Error) {
        DispatchQueue.main.async {
            HUG.show(title: (error as NSError).localizedDescription)
        }
    }
    
    func renderer(_ renderer: SCNSceneRenderer, updateAtTime time: TimeInterval) {
        DispatchQueue.main.async {
            self.updateFocusSquare()
            self.updateLine()
        }
    }
    
    func renderer(_ renderer: SCNSceneRenderer, didAdd node: SCNNode, for anchor: ARAnchor) {
        DispatchQueue.main.async {
            if let planeAnchor = anchor as? ARPlaneAnchor {
                self.addPlane(node: node, anchor: planeAnchor)
            }
        }
    }
    
    func renderer(_ renderer: SCNSceneRenderer, didUpdate node: SCNNode, for anchor: ARAnchor) {
        DispatchQueue.main.async {
            if let planeAnchor = anchor as? ARPlaneAnchor {
                self.updatePlane(anchor: planeAnchor)
            }
        }
    }
    
    func renderer(_ renderer: SCNSceneRenderer, didRemove node: SCNNode, for anchor: ARAnchor) {
        DispatchQueue.main.async {
            if let planeAnchor = anchor as? ARPlaneAnchor {
                self.removePlane(anchor: planeAnchor)
            }
        }
    }
    
    func session(_ session: ARSession, cameraDidChangeTrackingState camera: ARCamera) {
        let state = camera.trackingState
        DispatchQueue.main.async {
            self.lastState = state
        }
    }
}



