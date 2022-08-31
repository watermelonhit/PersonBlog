import request from '@/request'

export function getAllCategorys() {
  return request({
    url: '/api/categorys',
    method: 'get',
  })
}

export function getAllCategorysDetail() {
  return request({
    url: '/api/categorys/detail',
    method: 'get',
  })
}

export function getCategory(id) {
  return request({
    url: `/api/categorys/${id}`,
    method: 'get',
  })
}

export function getCategoryDetail(id) {
  return request({
    url: `/api/categorys/detail/${id}`,
    method: 'get',
  })
}
