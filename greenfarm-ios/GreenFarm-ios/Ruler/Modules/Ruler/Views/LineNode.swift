
import UIKit
import SceneKit
import ARKit

class LineNode: NSObject {
    let startNode: SCNNode
    let endNode: SCNNode
    var lineNode: SCNNode?
    let textNode: SCNNode
    let pNode: SCNNode!
    let sceneView: ARSCNView?
    var textWrapNode: SCNNode!
    let cameraNode1:SCNNode!
    var lineWidth:CGFloat!
    var finishArea:Bool!
    var umColor:UIColor!
    let lineOrder:Int!
    private var recentFocusSquarePositions = [SCNVector3]()

    init(cameraNode:SCNNode,
         startPos: SCNVector3,
         sceneV: ARSCNView,
         color: (start: UIColor, end: UIColor) = (UIColor.white, UIColor.white),
         font: UIFont = UIFont.boldSystemFont(ofSize: 8),
         scaleFont:CGFloat = 1/500.0,
         scaleLine:CGFloat = 0.001,
         order:Int = 0
         
    ) {
        lineOrder = order
        sceneView = sceneV
        cameraNode1 = cameraNode
        
        let scale = 1/400.0
        let scaleVector = SCNVector3(scale, scale, scale)

        func buildSCNSphere(color: UIColor) -> SCNSphere {
            let dot = SCNSphere(radius:1)
            dot.firstMaterial?.diffuse.contents = color
            dot.firstMaterial?.lightingModel = .constant
            dot.firstMaterial?.isDoubleSided = true
            return dot
        }
//        umColor = UIColor.init(red: 249/255.0, green: 168/255.0, blue: 15/255.0, alpha: 1)
//        umColor = UIColor.init(red: 255/255.0, green: 180/255.0, blue: 0/255.0, alpha: 1)
        umColor = UIColor.white
        startNode = SCNNode(geometry: buildSCNSphere(color: umColor))
        startNode.scale = scaleVector
        startNode.position = startPos
        sceneView?.scene.rootNode.addChildNode(startNode)
        
        endNode = SCNNode(geometry: buildSCNSphere(color: umColor))
        endNode.scale = scaleVector
        
        lineNode = nil
        
        let text = SCNText (string: "--", extrusionDepth: 0.1)
        text.font = font
        if scaleLine > 0.002 {
            text.firstMaterial?.diffuse.contents = UIColor.red
        }else if scaleLine > 0.03 {
            text.firstMaterial?.diffuse.contents = UIColor.green
        } else {
            text.firstMaterial?.diffuse.contents = UIColor.white
        }
      
        text.firstMaterial?.diffuse.contents = umColor
        text.alignmentMode  = kCAAlignmentCenter
        text.truncationMode = kCATruncationMiddle
        text.firstMaterial?.isDoubleSided = true
     
        textNode = SCNNode(geometry: text)
        textNode.scale = SCNVector3(scaleFont, scaleFont, scaleFont)
        textNode.eulerAngles = SCNVector3Make(0, .pi, 0)
        textNode.renderingOrder = -2
        
        let plane = SCNPlane(width: 20, height: 8)
        plane.materials = [SCNMaterial.material(withDiffuse: UIColor.white)]
        plane.cornerRadius = 6
        let planNode = SCNNode(geometry:plane)
        planNode.scale =  SCNVector3(scaleFont, scaleFont, scaleFont)
        planNode.eulerAngles = SCNVector3Make(0, .pi, 0)
        pNode = planNode
     
        textWrapNode = SCNNode()

        planNode.addChildNode(textNode)
        textWrapNode.addChildNode(textNode)

     
        let constraint = SCNLookAtConstraint(target: cameraNode)
        constraint.isGimbalLockEnabled = true
        textWrapNode.constraints = [constraint]
        planNode.constraints = [constraint]

        lineWidth = scaleLine
        super.init()
    }
    
   
    public func finishClick(){
        finishArea = true
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    deinit {
        removeFromParent()
    }
    
    public func updatePosition(pos: SCNVector3, camera: ARCamera?, unit: MeasurementUnit.Unit = MeasurementUnit.Unit.centimeter) -> Float {
        let posEnd = updateTransform(for: pos, camera: camera)

        if endNode.parent == nil {
            sceneView?.scene.rootNode.addChildNode(endNode)
        }
        endNode.position = posEnd

        let posStart = startNode.position
        let middle = SCNVector3((posStart.x+0.005+posEnd.x+0.005)/2.0, (posStart.y+posEnd.y)/2.0+0.002, (posStart.z+posEnd.z)/2.0)

        let text = textNode.geometry as! SCNText
        let length = posEnd.distanceFromPos(pos: startNode.position)
        text.string = MeasurementUnit(meterUnitValue: length).string(type: unit)
        if lineOrder == 1 {
            textNode.setPivot()
        }
    

        lineNode?.removeFromParentNode()
       
        lineNode = lineBetweenNodeA(nodeA: startNode, nodeB: endNode)
        lineNode?.renderingOrder = 2
        sceneView?.scene.rootNode.addChildNode(lineNode!)
    
        textWrapNode.position = middle
        if textWrapNode.parent == nil {
            sceneView?.scene.rootNode.addChildNode(textWrapNode)
        }
        
        return length
    }
    
    func removeFromParent() -> Void {
        startNode.removeFromParentNode()
        endNode.removeFromParentNode()
        lineNode?.removeFromParentNode()
        textWrapNode.removeFromParentNode()
    }
    
    // MARK: - Private
    
    private func lineBetweenNodeA(nodeA: SCNNode, nodeB: SCNNode) -> SCNNode {
      
        return CylinderLine(parent: sceneView!.scene.rootNode,
                            v1: nodeA.position,
                            v2: nodeB.position,
                            radius: lineWidth,
                            radSegmentCount: 16,
                            color: umColor)

    }
    
    private func updateTransform(for position: SCNVector3, camera: ARCamera?) -> SCNVector3 {
        recentFocusSquarePositions.append(position)
        recentFocusSquarePositions.keepLast(8)
        if let camera = camera {
            let tilt = abs(camera.eulerAngles.x)
            let threshold1: Float = Float.pi / 2 * 0.65
            let threshold2: Float = Float.pi / 2 * 0.75
            let yaw = atan2f(camera.transform.columns.0.x, camera.transform.columns.1.x)
            var angle: Float = 0
            
            switch tilt {
            case 0..<threshold1:
                angle = camera.eulerAngles.y
            case threshold1..<threshold2:
                let relativeInRange = abs((tilt - threshold1) / (threshold2 - threshold1))
                let normalizedY = normalize(camera.eulerAngles.y, forMinimalRotationTo: yaw)
                angle = normalizedY * (1 - relativeInRange) + yaw * relativeInRange
            default:
                angle = yaw
            }
        }
        
        if let average = recentFocusSquarePositions.average {
            return average
        }
        
        return SCNVector3Zero
    }
    
    private func normalize(_ angle: Float, forMinimalRotationTo ref: Float) -> Float {

        var normalized = angle
        while abs(normalized - ref) > Float.pi / 4 {
            if angle > ref {
                normalized -= Float.pi / 2
            } else {
                normalized += Float.pi / 2
            }
        }
        return normalized
    }
}




