import request from '@/utils/request'

export function querySteps(data) {
    return request({
      url: '/plant-task/querySteps',
      method: 'get',
      params: data
    })
  }

  export function queryTaskDetail(data) {
    return request({
      url: '/plant-task/queryTaskDetail',
      method: 'get',
      params: data
    })
  }
  export function taskRevise(data) {
    return request({
      url: '/plant-task/revise',
      method: 'put',
      data: data
    })
  }