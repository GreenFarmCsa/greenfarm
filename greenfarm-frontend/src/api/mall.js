import request from '@/utils/request'

export function queryHotProducts(data) {
  return request({
    url: '/product/queryTopN',
    method: 'get',
    params: data
  })
}

//category
export function queryProductsByCategory(data) {
  return request({
    url: '/product/queryByCategory',
    method: 'get',
    params: data
  })
}


//query-name
export function queryProducts(data) {
  return request({
    url: '/product/query',
    method: 'get',
    params: data
  })
}

//query-id
export function queryProductsById(data) {
  return request({
    url: '/product/queryById',
    method: 'get',
    params: data
  })
}

//comment
export function queryProductComment(data) {
  return request({
    url: '/product/queryComment',
    method: 'get',
    params: data
  })
}

export function productLike(data) {
  return request({
    url: '/product/like',
    method: 'put',
    params: data
  })
}
export function productAdd(data) {
  return request({
    url: '/product/add',
    method: 'post',
    data: data
  })
}
export function productBox(data) {
  return request({
    url: '/product/box',
    method: 'post',
    data: data
  })
}
//shopping cart
export function addCart(data) {
  return request({
    url: '/shopping-cart/add',
    method: 'post',
    params: data
  })
}

export function queryCart(data) {
  return request({
    url: '/shopping-cart/queryByUserName',
    method: 'post',
    params: data
  })
}

export function removeCart(data) {
  return request({
    url: '/shopping-cart/remove',
    method: 'post',
    params: data
  })
}

export function updateCart(data) {
  return request({
    url: '/shopping-cart/update',
    method: 'post',
    params: data
  })
}

export function createOrder(data) {
  return request({
    url: '/order/add',
    method: 'post',
    data: data
  })
}

export function queryProductByFarmId(data) {
  return request({
    url: '/product/queryByFarmId',
    method: 'get',
    params: data
  })
}


export function queryPlantTask(data) {
  return request({
    url: '/plant-task/queryPlantTaskByProductId',
    method: 'get',
    params: data
  })
}





