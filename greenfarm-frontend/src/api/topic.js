import request from '@/utils/request'
export function addTopic(data) {
    return request({
        url: '/topic/add',
        method: 'post',
        data: data
    })
}

export function addTopicComment(data) {
    return request({
        url: '/topic/addComment',
        method: 'post',
        data: data
    })
}

export function deleteTopicComment(data) {
    return request({
        url: '/topic/deleteComment',
        method: 'delete',
        params: data
    })
}

export function queryTopicComment(data) {
    return request({
        url: '/topic/queryComment',
        method: 'get',
        params: data
    })
}

export function queryTopic(data) {
    return request({
        url: '/topic/query',
        method: 'get',
        params: data
    })
}


export function queryTopicByCommunityId(data) {
    return request({
        url: '/topic/queryByCommunityId',
        method: 'get',
        params: data
    })
}

export function queryTopicByUserName(data) {
    return request({
        url: '/topic/queryByUserName',
        method: 'get',
        params: data
    })
}

export function reviseTopic(data) {
    return request({
        url: '/topic/revise',
        method: 'put',
        params: data
    })
}

export function queryTopicByByTopicId(data) {
    return request({
        url: '/topic/queryByTopicId',
        method: 'get',
        params: data
    })
}

export function topicLike(data) {
    return request({
        url: '/topic/like',
        method: 'put',
        params: data
    })
}