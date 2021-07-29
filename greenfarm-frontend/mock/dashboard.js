export default [
    {
        url:'/plant-task/query',                              
        type:'get', 
        response: config => {
            return {
                "data": [
                  {
                    "createTime": "2021-06-21T02:51:22.973Z",
                    "modifyTime": "2021-06-21T02:51:22.973Z",
                    "remark": "string",
                    "seedId": 0,
                    "seedName": "Potatoes",
                    "status": "SOW",
                    "taskId": 0,
                    "username": "string"
                  },
                  {
                    "createTime": "2021-06-21T02:51:22.973Z",
                    "modifyTime": "2021-06-21T02:51:22.973Z",
                    "remark": "string",
                    "seedId": 0,
                    "seedName": "Corn",
                    "status": "IRRIGATE",
                    "taskId": 0,
                    "username": "string"
                  },
                ],
                "message": "string",
                "resultCode": "OK"
              }
        }
    },
    {
        url:'/carbon-footprint/query',                               
        type:'get', 
        response: config => {
            return {
                "data": [
                  {
                    "carbonCredit": 20,
                    "carbonReduction": 0,
                    "createTime": "2021-06-21T06:09:07.096Z",
                    "farmId": 0,
                    "footprintCategory": "string",
                    "footprintId": 0,
                    "footprintName": "Buy Green Products",
                    "location": "string",
                    "modifyTime": "2021-06-21T06:09:07.096Z",
                    "remark": "string",
                    "username": "string"
                  },
                  {
                    "carbonCredit": 30,
                    "carbonReduction": 0,
                    "createTime": "2021-06-21T06:09:07.096Z",
                    "farmId": 0,
                    "footprintCategory": "string",
                    "footprintId": 0,
                    "footprintName": "Buy Green Seeds",
                    "location": "string",
                    "modifyTime": "2021-06-18T06:09:07.096Z",
                    "remark": "string",
                    "username": "string"
                  },
                ],
                "message": "string",
                "resultCode": "OK"
              }
        }
    },
    {
        url:'/recommendation/queryFarms',                              
        type:'get', 
        response: config => {
            return {
                "data": [
                  {
                    "createTime": "2021-06-21T06:16:24.936Z",
                    "farmId": 0,
                    "farmName": "Hot Spring Planting Center",
                    "iconUrl": "string",
                    "idleArea": 0,
                    "imageUrl": "string",
                    "introduction": "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                    "location": "Beiging.China",
                    "modifyTime": "2021-06-21T06:16:24.936Z",
                    "remark": "string",
                    "rentPeriod": "string",
                    "suitedCrops": "string",
                    "totalArea": 100,
                    "username": "string",
                    "vrUrl": "string"
                  },
                  {
                    "createTime": "2021-06-21T06:16:24.936Z",
                    "farmId": 0,
                    "farmName": "Hot Spring Planting Center2",
                    "iconUrl": "string",
                    "idleArea": 0,
                    "imageUrl": "string",
                    "introduction": "The farm2 supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                    "location": "ShangHai.China",
                    "modifyTime": "2021-06-21T06:16:24.936Z",
                    "remark": "string",
                    "rentPeriod": "string",
                    "suitedCrops": "string",
                    "totalArea": 80,
                    "username": "string",
                    "vrUrl": "string"
                  },
                ],
                "message": "string",
                "resultCode": "OK"
              }
        }
    },
    {
        url:'/recommendation/queryProducts',                          
        type:'get', 
        response: config => {
            return {
                "data": [
                  {
                    "carbonCredit": 0,
                    "carbonEmission": 0,
                    "category": "string",
                    "createTime": "2021-06-21T06:20:34.825Z",
                    "donateAmount": 0,
                    "farmId": 0,
                    "identifications": "string",
                    "imageUrl": "string",
                    "introduction": "string",
                    "landId": 0,
                    "likeNumber": 200,
                    "modifyTime": "2021-06-21T06:20:34.826Z",
                    "number": 100,
                    "price": 2.99,
                    "productId": 0,
                    "productName": "Onions",
                    "remark": "string",
                    "saleNumber": 0,
                    "username": "string",
                    "vedioUrl": "string"
                  },
                  {
                    "carbonCredit": 0,
                    "carbonEmission": 0,
                    "category": "string",
                    "createTime": "2021-06-21T06:20:34.825Z",
                    "donateAmount": 0,
                    "farmId": 0,
                    "identifications": "string",
                    "imageUrl": "string",
                    "introduction": "string",
                    "landId": 0,
                    "likeNumber": 291,
                    "modifyTime": "2021-06-21T06:20:34.826Z",
                    "number": 150,
                    "price": 3.99,
                    "productId": 0,
                    "productName": "Carrots",
                    "remark": "string",
                    "saleNumber": 0,
                    "username": "string",
                    "vedioUrl": "string"
                  },
                ],
                "message": "string",
                "resultCode": "OK"
              }
        }
    },

]


