import request from '@/request'


export function getArticles(query, page) {
  return request({
    url: '/api/articles',
    method: 'post',
    data: {
      page: page.pageNumber,
      pageSize: page.pageSize,
      name: page.name,
      sort: page.sort,
      year: query.year,
      month: query.month,
      tagId: query.tagId,
      categoryId: query.categoryId
    }
  })
}

export function getHotArtices() {
  return request({
    url: '/api/articles/hot',
    method: 'post'
  })
}

export function searchArticle(search) {
  return request({
    url: '/api/articles/search',
    method: 'post',
    data:{"search":search}
  })
}

export function getNewArtices() {
  return request({
    url: '/api/articles/new',
    method: 'post'
  })
}

export function viewArticle(id) {
  return request({
    url: `/api/articles/view/${id}`,
    method: 'post'
  })
}

export function deleteArticle(id) {
  return request({
    url: `/api/articles/delete/${id}`,
    method: 'post'
  })
}

export function getArticlesByCategory(id) {
  return request({
    url: `/api/articles/category/${id}`,
    method: 'post'
  })
}

export function getArticlesByTag(id) {
  return request({
    url: `/api/articles/tag/${id}`,
    method: 'post'
  })
}


export function publishArticle(article,token) {
  return request({
    headers: {'Authorization': token},
    url: '/api/articles/publish',
    method: 'post',
    data: article
  })
}
export function getPosters() {
  return request({
    url: '/api/poster/getAll',
    method: 'get',
  })
}

export function getAutoSummary(article,token) {
  return request({
    headers: {'Authorization': token},
    url: '/api/articles/autoSummary',
    method: 'post',
    data: article
  })
}


export function listArchives() {
  return request({
    url: '/api/articles/listArchives',
    method: 'post'
  })
}

export function getArticleById(id) {
  return request({
    url: `/api/articles/${id}`,
    method: 'post'
  })
}
