<template>
  <div id="app">
    <router-view v-if="isRouterAlive"/>
<!--     全局颗粒特效-->
      <vue-canvas-nest :config="config" :el="'#app'"></vue-canvas-nest>
    <go-top></go-top>
  </div>
</template>

<script>
import GoTop from '@/components/gotop/GoTop'
import vueCanvasNest from 'vue-canvas-nest'
export default {
  name: 'App',
  components: { GoTop,vueCanvasNest},
  provide(){
    return{
      reload:this.reload
    }
  },

  data(){
    return{
      //配置属性
      config: {
        color: '0,0,0',
        opacity: 0.7,
        zIndex: -2,
        count: 120,
      },//配置炫酷效果
      isRouterAlive:true
    }
  },

  methods:{
    reload(){
      this.isRouterAlive = false;
      this.$nextTick(function () {
        this.isRouterAlive = true;
      });
    }
  }
}
</script>

<style>
* { margin: 0; padding: 0; }

body {
	background-color: #f5f5f5;
	font-weight: 400;
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
	line-height: 1.5;
}
a {
    cursor: pointer;
    text-decoration: none;
    transition: none 86ms ease-out;
}
a:hover {
	color: #5FB878;
}
.me-area{
	background-color: #fff;
}
html{height:100%;}
body{min-height:100%;}
body{position:relative;}

.me-pull-right {
	float: right;
}
</style>
