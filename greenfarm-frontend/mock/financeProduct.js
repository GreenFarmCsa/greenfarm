export default [
   

    {
        url:'/finance-product/queryByUserName',                              
        type:'get', 
        response: config => {
            return {
                "data": [
                    {
                      "createTime": "2021-06-24T06:16:23.258Z",
                      "financeLimit": "100",
                      "financeProductCategory": "string",
                      "financeProductDesc": "string",
                      "financeProductId": "1",
                      "financeProductName": "XXX Bank Green Agricultural Benefit Loan",
                      "modifyTime": "2021-06-24T06:16:23.258Z",
                      "orgName": "0",
                      "remark": "string",
                      "validPeriod": "06/01/2021-06/07/2023",                  
                    },
                    {
                        "createTime": "2021-06-24T06:16:23.258Z",
                        "financeLimit": "100",
                        "financeProductCategory": "string",
                        "financeProductDesc": "string",
                        "financeProductId": "2",
                        "financeProductName": "XXX Bank Green Agricultural Benefit Loan2",
                        "modifyTime": "2021-06-24T06:16:23.258Z",
                        "orgName": "1",
                        "remark": "string",
                        "validPeriod": "06/01/2021-06/07/2023",                  
                      },
                      {
                        "createTime": "2021-06-24T06:16:23.258Z",
                        "financeLimit": "100",
                        "financeProductCategory": "string",
                        "financeProductDesc": "string",
                        "financeProductId": "3",
                        "financeProductName": "XXX Bank Green Agricultural Benefit Loan3",
                        "modifyTime": "2021-06-24T06:16:23.258Z",
                        "orgName": "2",
                        "remark": "string",
                        "validPeriod": "06/01/2021-06/07/2023",                  
                      }
                  ],
                  "message": "string",
                  "resultCode": "OK"
            }
        }
    },
    {
        url:'/finance-product/queryById',                              
        type:'get', 
        response: config => {
            return {
                "data": {
                    "createTime": "2021-06-24T06:51:33.880Z",
                    "financeLimit": "1000",
                    "financeProductCategory": "string",
                    "financeProductDesc": "armers who meet certain loan conditions can apply for loans through channels such as the branches of XXX Bank, and the system will automatically examine and approve the loans, and farmers can use their self-help letters, receive the loans quickly and recycle the loans.",
                    "financeProductId": "0",
                    "financeProductName": "eet certain loan c",
                    "modifyTime": "2021-06-24T06:51:33.880Z",
                    "orgName": "string",
                    "remark": "string",
                    "validPeriod": "06/01/2021-06/07/2023",          
                  },
                  "message": "string",
                  "resultCode": "OK"
            }
        }
    },
]


