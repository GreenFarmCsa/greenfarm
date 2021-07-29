import request from '@/utils/request'
export function addLive(data) {
    return request({
        url: '/live/add',
        method: 'post',
        data
    })
}
export function queryLiveByFarmId(data) {
    return request({
        url: '/live/queryByFarmId',
        method: 'get',
        params: data
    })
}
export function reviseLive(data) {
    return request({
        url: '/live/revise',
        method: 'post',
        data
    })
}