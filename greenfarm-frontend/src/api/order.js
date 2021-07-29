import request from '@/utils/request'

export function myPurchasedProduct(data) {
  return request({
    url: '/order/queryPurchased',
    method: 'get',
    params: data
  })
}

export function mySoldProduct(data) {
  return request({
    url: '/order/querySold',
    method: 'get',
    params: data
  })
}

export function addComment(data) {
  return request({
    url: '/product/addComment',
    method: 'post',
    data
  })
}

export function queryPlant(data) {
  return request({
    url: '/product/queryPlant',
    method: 'get',
    params: data
  })
}

 