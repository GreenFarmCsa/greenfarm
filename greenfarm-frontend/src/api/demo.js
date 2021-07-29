import request from '@/utils/request'

export function getFontSize() {
  return request({
    url: '/getfontsize/info',
    method: 'get'
  })
}
export function saveFontSize(data) {
  return request({
    url: '/savefontsize/info',
    method: 'post',
    data
  })
}
export function saveBgColor(data) {
  return request({
    url: '/savebgcolor/data',
    method: 'post',
    data
  })
}
export function getBgColor() {
  return request({
    url: '/getbgcolor/data',
    method: 'get'
  })
}
export function getIndexNotifyData() {
  return request({
    url: '/getIndexNotify/data',
    method: 'get'
  })
}