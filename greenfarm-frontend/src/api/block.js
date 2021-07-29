import request from '@/utils/request'
export function queryBlockById(data) {
  return request({
    url: '/block/query',
    method: 'get',
    params: data
  })
}