import request from '@/request'

export function upload(formdata) {
  return request({
    headers: {'Content-Type': 'multipart/form-data'},
    url: '/api/upload',
    method: 'post',
    data: formdata
  })

}
