import request from '@/utils/request'
export function queryLandByFarmId(data) {
    return request({
        url: '/land/queryByFarmId',
        method: 'get',
        params:data
    })
}
export function addLand(data) {
    return request({
        url: '/land/add',
        method: 'post',
        data
    })
}
export function editLand(data) {
    return request({
        url: '/land/revise',
        method: 'put',
        data
    })
} 