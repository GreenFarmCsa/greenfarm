export default [


    {
        url: '/topic/queryByUserName',
        type: 'get',
        response: config => {
            return {
                "data": [
                    {
                        "communityId": "0",
                        "communityImageUrl": require("@/assets/images/farm-1.png"),
                        "communityName": "string",
                        "createTime": "2021-06-24T07:31:47.155Z",
                        "farmId": "0",
                        "introduction": "string",
                        "modifyTime": "2021-06-24T07:31:47.155Z",
                        "remark": "string",
                        "likeSum": "0",
                        "topicId": 0
                    },
                    {
                        "communityId": "0",
                        "communityImageUrl": require("@/assets/images/farm-2.png"),
                        "communityName": "string",
                        "createTime": "2021-06-24T07:31:47.155Z",
                        "farmId": "0",
                        "introduction": "string",
                        "modifyTime": "2021-06-24T07:31:47.155Z",
                        "remark": "string",
                        "likeSum": "1",
                        "topicId": 6
                    },
                    {
                        "communityId": "0",
                        "communityImageUrl": require("@/assets/images/farm-3.png"),
                        "communityName": "string",
                        "createTime": "2021-06-24T07:31:47.155Z",
                        "farmId": "0",
                        "introduction": "string",
                        "modifyTime": "2021-06-24T07:31:47.155Z",
                        "remark": "string",
                        "likeSum": "2",
                        "topicId": 5
                    },
                    {
                        "communityId": "0",
                        "communityImageUrl": require("@/assets/images/farm-4.png"),
                        "communityName": "string",
                        "createTime": "2021-06-24T07:31:47.155Z",
                        "farmId": "0",
                        "introduction": "string",
                        "modifyTime": "2021-06-24T07:31:47.155Z",
                        "remark": "string",
                        "likeSum": "3",
                        "topicId": 4
                    },
                    {
                        "communityId": "0",
                        "communityImageUrl": require("@/assets/images/farm-2.png"),
                        "communityName": "string",
                        "createTime": "2021-06-24T07:31:47.155Z",
                        "farmId": "0",
                        "introduction": "string",
                        "modifyTime": "2021-06-24T07:31:47.155Z",
                        "remark": "string",
                        "likeSum": "4",
                        "topicId": 3
                    },
                    {
                        "communityId": "0",
                        "communityImageUrl": require("@/assets/images/farm-3.png"),
                        "communityName": "string",
                        "createTime": "2021-06-24T07:31:47.155Z",
                        "farmId": "0",
                        "introduction": "string",
                        "modifyTime": "2021-06-24T07:31:47.155Z",
                        "remark": "string",
                        "likeSum": "5",
                        "topicId": 2
                    },
                    {
                        "communityId": "0",
                        "communityImageUrl": require("@/assets/images/farm-4.png"),
                        "communityName": "string",
                        "createTime": "2021-06-24T07:31:47.155Z",
                        "farmId": "0",
                        "introduction": "string",
                        "modifyTime": "2021-06-24T07:31:47.155Z",
                        "remark": "string",
                        "likeSum": "6",
                        "topicId": 1
                    }
                ],
                "message": "string",
                "resultCode": "OK"
            }
        }
    },
    {
        url: '/topic/queryComment',
        type: 'get',
        response: config => {
            return {
                "data": [
                    {
                      "commentContent": "The harvest of sweet potatoes is good, we can communicate with each other about the …",
                      "commentId": 0,
                      "createTime": "2021-06-24T08:02:35.577Z",
                      "modifyTime": "2021-06-24T08:02:35.577Z",
                      "remark": "string",
                      "topicId": 0,
                      "username": "Lily"
                    },
                    {
                        "commentContent": "otatoes is good, we can communicate with each other about the …",
                        "commentId": 1,
                        "createTime": "2021-06-24T08:02:35.577Z",
                        "modifyTime": "2021-06-24T08:02:35.577Z",
                        "remark": "string",
                        "topicId": 0,
                        "username": "lucy"
                      },
                  ],
                  "message": "string",
                  "resultCode": "OK"
            }
        }
    },
]


