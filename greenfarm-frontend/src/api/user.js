import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    params: data
  })
}

export function logout(data) {
  return request({
    url: '/user/logout',
    method: 'get',
    params: data
  })
}

export function queryUserInfo(data) {
  return request({
    url: '/user/queryInfo',
    method: 'get',
    params: data
  })
}
