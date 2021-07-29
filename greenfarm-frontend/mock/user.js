export default [
  {
    url: '/user/login',
    type: 'post',
    response: config => {
      return {
        "data": {
          "carbonCredit": 0,
          "carbonMedals": [
            "string"
          ],
          "createTime": "2021-06-24T06:48:55.907Z",
          "description": "string",
          "email": "string",
          "fullname": "string",
          "iconUrl": "string",
          "location": "string",
          "modifyTime": "2021-06-24T06:48:55.907Z",
          "password": "string",
          "phone": "string",
          "remark": "string",
          "rolename": "string",
          "sex": "string",
          "username": "string"
        },
        "message": "string",
        "resultCode": "OK"
      }

    }
  },
  {
    url: '/user/queryInfo',
    type: 'get',
    response: config => {
      return {
        "data": {
          "carbonCredit": 23333,
          "carbonMedals": [
            "string"
          ],
          "createTime": "2021-06-22T08:37:09.405Z",
          "description": "string",
          "email": "string",
          "fullname": "string",
          "iconUrl": "string",
          "location": "string",
          "modifyTime": "2021-06-22T08:37:09.405Z",
          "password": "string",
          "phone": "string",
          "remark": "string",
          "rolename": "string",
          "sex": "string",
          "username": "Jack"
        },
        "message": "string",
        "resultCode": "OK"
      }
    }
  },

]


