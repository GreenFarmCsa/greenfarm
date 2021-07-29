package main

import (
	"fmt"
	
    "encoding/json"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
  return shim.Success([]byte("init success"))
}

func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("ex02 Invoke")
	function, args := stub.GetFunctionAndParameters()
	if function == "insertOrUpdate" {
		// Make payment of X units from A to B
		return t.insertOrUpdate(stub, args)
	} else if function == "select" {
		// Deletes an entity from its state
		return t.selectt(stub, args)
	} 

	return shim.Error("Invalid invoke function name. Expecting \"insertOrUpdate\" \"select\"")
}

// insertOrUpdate data
func (t *SimpleChaincode) insertOrUpdate(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var content string 
	var err error
	if len(args) != 1 {
      return shim.Error("args num is not 1")
	}
	
	content = args[0]
	
	var pojo Pojo
	json.Unmarshal([]byte(content), &pojo)
	
	if "" == pojo.ProductId {
      return shim.Error("productId cannot be null")
	}
	if "" == pojo.PlantStepName {
      return shim.Error("plantStepName cannot be null")
	}
	if "" == pojo.Time {
      return shim.Error("time cannot be null")
	}
	
	key, _ := stub.CreateCompositeKey("productId~plantStepName~time", []string{pojo.ProductId, pojo.PlantStepName, pojo.Time})

	err = stub.PutState(key, []byte(content))

	if err != nil {
      return shim.Error("ledger error")
	}
	
	result := make(map[string]interface{}, 4)
	result["txId"] = stub.GetTxID()
	bresult, _ := json.Marshal(result)
	return shim.Success(bresult)
}

// select data
func (t *SimpleChaincode) selectt(stub shim.ChaincodeStubInterface, args []string) pb.Response {
	var productId string 

	if len(args) != 1 {
      return shim.Error("args num is not 1")
	}
	
	productId = args[0]
	queryResult, _, _ := stub.GetStateByPartialCompositeKeyWithPagination("productId~plantStepName~time", []string{productId}, 10, "")
	
  	list := make([]Pojo, 0)
	for queryResult.HasNext() {
		item, _ := queryResult.Next()
		var pojo Pojo
		json.Unmarshal([]byte(string(item.Value)), &pojo)
		list = append(list, pojo)
	}

  bresult, _ := json.Marshal(list)
	return shim.Success(bresult)
}
type Pojo struct{
	ProductId		string 	`json:"productId"`
	UserName 		string 	`json:"userName"`
	PlantStepName 	string	`json:"plantStepName"`
	Time 			string	`json:"time"`
	FarmName 		string	`json:"farmName"`
	FarmLocation 	string	`json:"farmLocation"`
	LandId 			int		`json:"landId"`
	LandArea 		int		`json:"landArea"`
	VideoHash 		string	`json:"videoHash"`
	ImageHash 		string	`json:"imageHash"`
}

func main() {
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}