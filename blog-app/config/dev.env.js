'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
   // 后端访问路径，可以进行相应的修改
  BASE_API: '"http://localhost:9012/"'
})
