export default [
  {
    url: '/order/queryPurchased',
    type: 'get',
    response: config => {
      return {
        "data": [
          {
            "address": "beijing,china",
            "carbonCredit": 0,
            "createTime": "2021-06-22T05:57:02.835Z",
            "detail": [
              {
                "count": 1,
                "imageUrl": "string",
                "orderId": 0,
                "productId": 0,
                "productName": "apple pen"
              }
            ],
            "modifyTime": "2021-06-22T05:57:02.835Z",
            "money": 0,
            "orderId": 0,
            "remark": "tag1",
            "username": "pinglinyan"
          },
          {
            "address": "tianjin,china",
            "carbonCredit": 0,
            "createTime": "2021-06-22T05:57:02.835Z",
            "detail": [
              {
                "count": 2,
                "imageUrl": "string",
                "orderId": 0,
                "productId": 0,
                "productName": "pineapple pen"
              }
            ],
            "modifyTime": "2021-06-22T05:57:02.835Z",
            "money": 0,
            "orderId": 0,
            "remark": "tag2",
            "username": "zhangaofan"
          }
        ],
        "message": "string",
        "resultCode": "OK"
      }
    }
  },
  {
    url: '/order/querySold',
    type: 'get',
    response: config => {
      return {
        "data": [
          {
            "address": "string",
            "carbonCredit": 0,
            "createTime": "2021-06-22T05:59:36.724Z",
            "detail": [
              {
                "count": 0,
                "imageUrl": "string",
                "orderId": 0,
                "productId": 0,
                "productName": "string"
              }
            ],
            "modifyTime": "2021-06-22T05:59:36.724Z",
            "money": 0,
            "orderId": 0,
            "remark": "string",
            "username": "string"
          }
        ],
        "message": "string",
        "resultCode": "OK"
      }
    }
  },
]


