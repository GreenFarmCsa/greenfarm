export default [
    {
        url: '/plant-task/queryTaskDetail',
        type: 'get',
        response: config => {
            return {
                "data": [
                    {
                        "createTime": "2021-06-24T07:49:54.768Z",
                        "detailId": 0,
                        "location": "string",
                        "modifyTime": "2021-06-24T07:49:54.768Z",
                        "operRecord": "string",
                        "remark": "string",
                        "status": "string",
                        "stepId": 0,
                        "username": "string"
                    }
                ],
                "message": "string",
                "resultCode": "OK"
            }
        }
    },
    {
        url: '/plant-task/query',
        type: 'get',
        response: config => {
            return {
                "data": [
                    {
                        "createTime": "2021-06-24T09:45:13.859Z",
                        "landId": 1,
                        "modifyTime": "2021-06-24T09:45:13.859Z",
                        "remark": "string",
                        "seedId": 0,
                        "seedName": "Potatoes",
                        "status": "SOW",
                        "taskId": 0,
                        "username": "string",
                    },
                    {
                        "createTime": "2021-06-24T09:45:13.859Z",
                        "landId": 2,
                        "modifyTime": "2021-06-24T09:45:13.859Z",
                        "remark": "string",
                        "seedId": 0,
                        "seedName": "Corn",
                        "status": "IRRIGATE",
                        "taskId": 0,
                        "username": "string",
                    },
                    {
                        "createTime": "2021-06-24T09:45:13.859Z",
                        "landId": 3,
                        "modifyTime": "2021-06-24T09:45:13.859Z",
                        "remark": "string",
                        "seedId": 0,
                        "seedName": "Tomato",
                        "status": "IRRIGATE",
                        "taskId": 0,
                        "username": "string",
                    }
                ],
                "message": "string",
                "resultCode": "OK"
            }
        }
    },
]