import request from '@/utils/request'

export function queryFinanceProductById(data) {
  return request({
    url: '/finance-product/queryById',
    method: 'get',
    params: data
  })
}

export function queryFinanceProductByUserName(data) {
  return request({
    url: '/finance-product/queryByUserName',
    method: 'get',
    params: data
  })
}

export function addUserFinance(data) {
  return request({
    url: '/finance-product/addUserFinance',
    method: 'post',
    params: data
  })
}


export function queryFinanceProductWithSignByUserName(data) {
  return request({
    url: '/finance-product/queryAllWithSignStatus',
    method: 'get',
    params: data
  })
}

export function applyFinanceProduct(data) {
  return request({
    url: '/finance-product/apply',
    method: 'post',
    data
  })
}

export function breakFinanceProduct(data) {
  return request({
    url: '/finance-product/break',
    method: 'post',
    data
  })
}