import request from '@/utils/request'
export function addFarm(data) {
    return request({
        url: '/farm/add',
        method: 'post',
        data
    })
}

export function queryFarm(data) {
    return request({
        url: '/farm/query',
        method: 'get',
        params: data
    })
}

export function queryAllFarm(data) {
    return request({
        url: '/farm/queryAll',
        method: 'get',
        params: data
    })
}

export function queryFarmByLocation(data) {
    return request({
        url: '/farm/queryByLocation',
        method: 'get',
        params: data
    })
}

export function queryFarmById(data) {
    return request({
        url: '/farm/queryById',
        method: 'get',
        params: data
    })
}


export function queryFarmByProduct(data) {
    return request({
        url: '/farm/queryByProductName',
        method: 'get',
        params: data
    })
}

export function queryFarmByUserName(data) {
    return request({
        url: '/farm/queryByUserName',
        method: 'get',
        params: data
    })
}

export function queryFarmByTotalArea(data) {
    return request({
        url: '/farm/queryByTotalArea',
        method: 'get',
        params: data
    })
}

export function reviseFarm(data) {
    return request({
        url: '/farm/revise',
        method: 'put',
        data
    })
}
export function querySeed(data) {
    return request({
        url: '/seed/query',
        method: 'get',
        params: data
    })
}
export function querySeedById(data) {
    return request({
        url: '/seed/queryById',
        method: 'get',
        params: data
    })
}