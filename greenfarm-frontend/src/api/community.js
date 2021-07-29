import request from '@/utils/request'

//join a community
export function joinCommunity(data) {
    return request({
        url: '/community/join',
        method: 'post',
        data
    })
}
//query community by farmId
export function queryCommunityByFarmId(data) {
    return request({
        url: '/community/queryByFarmId',
        method: 'get',
        params: data
    })
}

//query community by userName
export function queryCommunityByUserName(data) {
    return request({
        url: '/community/queryByUserName',
        method: 'get',
        params: data
    })
}