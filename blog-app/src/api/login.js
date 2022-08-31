import request from '@/request'

export function login(account, password) {
  const data = {
    account,
    password
  }
  return request({
    url: '/api/login',
    method: 'post',
    data
  })
}

export function logout(token) {
  return request({
    headers: {'Authorization': token},
    url: '/api/logout',
    method: 'get'
  })
}

export function getUserInfo(token) {
  return request({
    headers: {'Authorization': token},
    url: '/api/users/currentUser',
    method: 'get'
  })
}

export function register(account, nickname, password) {
  const data = {
    account,
    nickname,
    password
  }
  return request({
    url: '/api/register',
    method: 'post',
    data
  })
}
  export function updateInfo(token,account,nickname, password) {
    const data = {
      account,
      nickname,
      password
    }
    return request({
      headers: {'Authorization': token},
      url: '/api/updateInfo',
      method: 'post',
      data
    })
 }


