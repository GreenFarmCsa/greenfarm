
import UIKit
import SceneKit
import ARKit

class LineSetNode: NSObject {
    private(set) var lines = [LineNode]()
    var currentNode: LineNode
    var closeNode: LineNode?
    let sceneView: ARSCNView
    let textNode: SCNNode

    let cameraNode1: SCNNode
    var textWrapNode: SCNNode!
    var scaleLine:CGFloat
    var scaleFont:CGFloat
    var scaleArea:CGFloat
    
    
    init(cameraNode:SCNNode,startPos: SCNVector3, sceneV: ARSCNView) {
        cameraNode1 = cameraNode
        sceneView = sceneV
               let  cameraPos = cameraNode1.position
                let cc = cameraPos.distanceFromPos(pos: startPos)
        var color:UIColor = UIColor.white
        if cc>=0.5&&cc<1.0 {
              scaleFont = 1/200.0
              scaleLine = 0.002
              scaleArea = 1/300.0
              color = UIColor.gray
       
        }else if cc>=1.0 && cc < 1.5 {
               scaleFont = 1/130.0
               scaleLine = 0.004
               scaleArea = 1/250.0
               color = UIColor.red
           }else if cc>=1.5 && cc < 2 {
                    scaleFont = 1/100.0
                    scaleLine = 0.005
                    scaleArea = 1/150.0
                    color = UIColor.orange
                }else if cc>=2 {
                    scaleFont = 1/80.0
                    scaleLine = 0.008
                    scaleArea = 1/100.0
                    color = UIColor.yellow
                } else {
                    scaleFont = 1/500.0
                    scaleLine = 0.0005
                    scaleArea = 1/600.0
                    color = UIColor.white
                }
                
//        color = UIColor.init(red: 143/255.0, green: 137/255.0, blue: 239/255.0, alpha: 1)
//        color = UIColor.init(red: 255/255.0, green: 180/255.0, blue: 0/255.0, alpha: 1)
        color = UIColor.white
        let line = LineNode(cameraNode: cameraNode,startPos: startPos,
                            sceneV: sceneV,
                            color: (UIColor.white, UIColor.white),
                            font: UIFont.systemFont(ofSize: 6),
                            scaleFont: scaleFont,
                            scaleLine: scaleLine,
                            order:1
                            )
        currentNode = line
        lines.append(line)
        
        let text = SCNText (string: "--", extrusionDepth: 0.1)
        text.font = UIFont.boldSystemFont(ofSize: 10)
        text.firstMaterial?.diffuse.contents = color
        text.alignmentMode  = kCAAlignmentCenter
        text.truncationMode = kCATruncationMiddle
        text.firstMaterial?.isDoubleSided = true
        textNode = SCNNode(geometry: text)
        textNode.scale = SCNVector3(scaleArea, scaleArea,scaleArea)
        textNode.eulerAngles = SCNVector3Make(0, .pi, 0)
        textNode.renderingOrder = -2
     
        textWrapNode = SCNNode()
        textWrapNode.addChildNode(textNode)
        let constraint = SCNLookAtConstraint(target: cameraNode)
        constraint.isGimbalLockEnabled = true
        textWrapNode.constraints = [constraint]
        
        super.init()
    }
    
    func addLine(cameraNode:SCNNode) {
        currentNode = LineNode(cameraNode: cameraNode,startPos: currentNode.endNode.position,
                               sceneV: sceneView,
                               color: (UIColor.white, UIColor.white),
                               font: UIFont.systemFont(ofSize: 6),
                               scaleFont: scaleFont,
                               scaleLine: scaleLine
                               )
        lines.append(currentNode)
        resetCloseLine(cameraNode:cameraNode)
    }
    
    func removeLine(cameraNode:SCNNode) -> Bool {
        guard let n = lines.popLast(), lines.count >= 1 else {
            resetCloseLine(cameraNode:cameraNode)
            return false
        }
        n.removeFromParent()
        currentNode = lines.last!
        resetCloseLine(cameraNode:cameraNode)
        return true
    }
    
    func removeFromParent() {
        lines.forEach({ $0.removeFromParent() })
        textWrapNode.removeFromParentNode()
    }
    
    private func resetCloseLine(cameraNode:SCNNode) {
        closeNode?.removeFromParent()
        closeNode = nil
        if lines.count > 1 {
            let closeNodeTemp = LineNode(cameraNode: cameraNode,startPos: lines[0].startNode.position,
                                         sceneV: sceneView,
                                         color: (UIColor.white, UIColor.white),
                                         font: UIFont.systemFont(ofSize: 6),
                                         scaleFont: scaleFont,
                                         scaleLine: scaleLine
                                         )
            closeNode = closeNodeTemp
        }
    }
    
    public func updatePosition(pos: SCNVector3, camera: ARCamera?, unit: MeasurementUnit.Unit = MeasurementUnit.Unit.centimeter) -> Float {
        _ = closeNode?.updatePosition(pos: pos, camera: camera, unit: unit)
        _ = currentNode.updatePosition(pos: pos, camera: camera, unit: unit)
        guard lines.count >= 2 else {
            textWrapNode.isHidden = true
            return 0
        }
        let posEnd = updateTransform(for: pos, camera: camera)
        
        var points = lines.map({ $0.endNode.position })
        points.append(lines[0].startNode.position)
        
        var center = points.average ?? points[0]
        center.y += 0.002
        let text = textNode.geometry as! SCNText
        let area = computePolygonArea(points: points)
        text.string = MeasurementUnit(meterUnitValue: area, isArea: true).string(type: unit)
        textNode.setPivotArea()
        textWrapNode.position = center
        textWrapNode.isHidden = false
        if textWrapNode.parent == nil {
            sceneView.scene.rootNode.addChildNode(textWrapNode)
        }
        
        return area
    }
    
    private func computePolygonArea(points: [SCNVector3]) -> Float {
        return abs(area3DPolygonFormPointCloud(points: points))
    }
    
    private func updateTransform(for position: SCNVector3, camera: ARCamera?) -> SCNVector3 {
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





