export default [
    {
        url: '/22farm/queryAll',
        type: 'get',
        response: config => {
            return {
                "data": [
                    {
                        "createTime": "2021-06-25T01:45:29.507Z",
                        "farmId": 1546526312321,
                        "farmName": "Hot Spring Planting Center",
                        "iconUrl": "string",
                        "idleArea": 0,
                        "imageUrl": "string",
                        "introduction": "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                        "location": "Beijing,China",
                        "modifyTime": "2021-06-25T01:45:29.508Z",
                        "remark": "string",
                        "rentPeriod": "string",
                        "suitedCrops": "string",
                        "totalArea": 0,
                        "username": "string",
                        "vrUrl": "string"
                    },
                    {
                        "createTime": "2021-06-25T01:45:29.507Z",
                        "farmId": 10546526312321,
                        "farmName": "Hot Spring Planting Center123",
                        "iconUrl": "string",
                        "idleArea": 0,
                        "imageUrl": "string",
                        "introduction": "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                        "location": "Beijing,China",
                        "modifyTime": "2021-06-25T01:45:29.508Z",
                        "remark": "string",
                        "rentPeriod": "string",
                        "suitedCrops": "string",
                        "totalArea": 0,
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
        url: '/farm/queryByLocation',
        type: 'get',
        response: config => {
            return {
                "data": [
                    {
                        "createTime": "2021-06-25T01:45:29.507Z",
                        "farmId": 1546526312321,
                        "farmName": "Hot Spring Planting Center",
                        "iconUrl": "string",
                        "idleArea": 0,
                        "imageUrl": "string",
                        "introduction": "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                        "location": "Beijing,China",
                        "modifyTime": "2021-06-25T01:45:29.508Z",
                        "remark": "string",
                        "rentPeriod": "string",
                        "suitedCrops": "string",
                        "totalArea": 0,
                        "username": "string",
                        "vrUrl": "string"
                    },
                    {
                        "createTime": "2021-06-25T01:45:29.507Z",
                        "farmId": 10546526312321,
                        "farmName": "Hot Spring Planting Center123",
                        "iconUrl": "string",
                        "idleArea": 0,
                        "imageUrl": "string",
                        "introduction": "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                        "location": "Beijing,China",
                        "modifyTime": "2021-06-25T01:45:29.508Z",
                        "remark": "string",
                        "rentPeriod": "string",
                        "suitedCrops": "string",
                        "totalArea": 0,
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
        url: '/farm/queryByProduct',
        type: 'get',
        response: config => {
            return {
                "data": [
                    {
                        "createTime": "2021-06-25T01:45:29.507Z",
                        "farmId": 1546526312321,
                        "farmName": "cropHot Spring Planting Center",
                        "iconUrl": "string",
                        "idleArea": 0,
                        "imageUrl": "string",
                        "introduction": "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                        "location": "Beijing,China",
                        "modifyTime": "2021-06-25T01:45:29.508Z",
                        "remark": "string",
                        "rentPeriod": "string",
                        "suitedCrops": "string",
                        "totalArea": 0,
                        "username": "string",
                        "vrUrl": "string"
                    },
                    {
                        "createTime": "2021-06-25T01:45:29.507Z",
                        "farmId": 10546526312321,
                        "farmName": "crHot Spring Planting Center123",
                        "iconUrl": "string",
                        "idleArea": 0,
                        "imageUrl": "string",
                        "introduction": "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                        "location": "Beijing,China",
                        "modifyTime": "2021-06-25T01:45:29.508Z",
                        "remark": "string",
                        "rentPeriod": "string",
                        "suitedCrops": "string",
                        "totalArea": 0,
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
        url: '/farm/queryById',
        type: 'get',
        response: config => {
            return {
                "data":
                {
                    "createTime": "2021-06-25T01:45:29.507Z",
                    "farmId": 1546526312321,
                    "farmName": "cropHot Spring Planting Center",
                    "iconUrl": "string",
                    "idleArea": 0,
                    "imageUrl":  require("@/assets/images/farm-3.png"),
                    "introduction": "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
                    "location": "Beijing,China",
                    "modifyTime": "2021-06-25T01:45:29.508Z",
                    "remark": "string",
                    "rentPeriod": "string",
                    "suitedCrops": "string",
                    "totalArea": 0,
                    "username": "string",
                    "vrUrl": [
                        require("@/assets/images/farm-5.png"),
                        require("@/assets/images/farm-6.png"),
                        require("@/assets/images/farm-7.png")
                    ]
                },


                "message": "string",
                "resultCode": "OK"
            }
        }
    },
    {
        url: '/seed/query',
        type: 'get',
        response: config => {
            return {
                "data": [
                    {
                        "fertilizerAmount": "string",
                        "fertilizerName": "string",
                        "fertilizerPrice": 0,
                        "maturePeriod": 0,
                        "productId": 0,
                        "production": "1",
                        "seedId": 0,
                        "seedIntroduction": "string",
                        "seedName": "potatos",
                        "seedPrice": 0,
                        "wateringQuantity": "string",
                        "seedImageUrl": require("@/assets/images/tomatos-2.png")
                    },
                    {
                        "fertilizerAmount": "string",
                        "fertilizerName": "string",
                        "fertilizerPrice": 0,
                        "maturePeriod": 0,
                        "productId": 0,
                        "production": "2",
                        "seedId": 0,
                        "seedIntroduction": "string",
                        "seedName": "chili",
                        "seedPrice": 0,
                        "wateringQuantity": "string",
                        "seedImageUrl": require("@/assets/images/hot-pepper.png"),
                    },

                    {
                        "fertilizerAmount": "string",
                        "fertilizerName": "string",
                        "fertilizerPrice": 0,
                        "maturePeriod": 0,
                        "productId": 0,
                        "production": "3",
                        "seedId": 0,
                        "seedIntroduction": "string",
                        "seedName": "tomatos",
                        "seedPrice": 0,
                        "wateringQuantity": "string",
                        "seedImageUrl": require("@/assets/images/tomatos-2.png"),
                    },

                    {
                        "fertilizerAmount": "string",
                        "fertilizerName": "string",
                        "fertilizerPrice": 0,
                        "maturePeriod": 0,
                        "productId": 0,
                        "production": "4",
                        "seedId": 0,
                        "seedIntroduction": "string",
                        "seedName": "zuccini",
                        "seedPrice": 0,
                        "wateringQuantity": "string",
                        "seedImageUrl": require("@/assets/images/zuccini.png"),
                    },


                ],
                "message": "string",
                "resultCode": "OK"
            }
        }
    },
]


