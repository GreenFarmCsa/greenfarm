import request from '@/utils/request'

//query plant-task by userName
export function plantTask(data) {
  return request({
    url: '/plant-task/query',
    method: 'get',
    params: data
  })
}
//query carbon-footprint by userName dashboard
export function echartsFootprint(data) {
  return request({
    url: '/carbon-footprint/queryFromDashboard',
    method: 'get',
    params: data
  })
}
//query carbon-footprint by userName
export function carbonFootprint(data) {
  return request({
    url: '/carbon-footprint/query',
    method: 'get',
    params: data
  })
}
//query recommendation farms
export function recommendFarms(data) {
  return request({
    url: '/recommendation/queryFarms',
    method: 'get',
    params: data
  })
}
//query recommendation products
export function recommendProducts(data) {
  return request({
    url: '/recommendation/queryProducts',
    method: 'get',
    params: data
  })
}