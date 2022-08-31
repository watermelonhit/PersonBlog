import request from '@/request'


export default{
 add(board,token){
   return request({
     headers: {'Authorization': token},
       url: `/api/board/add`,
       method: 'post',
       data: board
   })
 },
   getAllBoards(){
    return request({
        url: '/api/board/getAll',
        method: 'get',
    })
}
}
