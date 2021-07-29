
import Foundation

enum Localization: String {

    case arNotAvailable = "ARNotAvailable"
    case arInitializing = "ARInitializing"
    case arInsufficientFeatures = "ARInsufficientFeatures"
    case arExcessiveMotion = "ARExcessiveMotion"
    case arInitializingMessage = "ARInitializingMessage"
    case arInsufficientFeaturesMessage = "ARInsufficientFeaturesMessage"
    case arExcessiveMotionMessage = "ARExcessiveMotionMessage"
    case arRelocalizing = "ARRelocalizing"
    
    case saveSuccess = "SaveSuccess"
    case saveFail = "SaveFail"
    case saveNeedPermission = "SaveNeedPermission"
    case didCopy = "DidCopy"
    case setting = "Setting"
    case startArea = "StartArea"
    case startLength = "StartLength"
    
    
    func  toString() -> String {
        return NSLocalizedString(self.rawValue, tableName: nil, bundle: Bundle.main, value: "", comment: "")
    }
}
