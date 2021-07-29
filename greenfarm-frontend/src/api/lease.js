import request from '@/utils/request'
export function leaseFarmsJack(data) {
    return request({
      url: '/rent/queryRentLands',
      method: 'get',
      params: data
    })
  }

export function addRent(data) {
  return request({
    url: '/rent/add',
    method: 'post',
    data
  })
}

export function querySubscriber(data) {
  return request({
    url: '/rent/querySubscriber',
    method: 'get',
    params: data
  })
}