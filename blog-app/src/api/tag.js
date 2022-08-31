import request from '@/request'

export function getAllTags() {
  return request({
    url: '/api/tags',
    method: 'get',
  })
}

export function getAllTagsDetail() {
  return request({
    url: '/api/tags/detail',
    method: 'get',
  })
}

export function getHotTags() {
  return request({
    url: '/api/tags/hot',
    method: 'get',
  })
}

export function getTag(id) {
  return request({
    url: `/api/tags/${id}`,
    method: 'get',
  })
}

export function getTagDetail(id) {
  return request({
    url: `/api/tags/detail/${id}`,
    method: 'get',
  })
}
