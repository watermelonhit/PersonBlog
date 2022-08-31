<template>
  <div v-title data-title="watermelonhit">
    <el-container>
      <el-main class="me-articles">
        <el-carousel :interval="4000" type="card" height="200px">
          <el-carousel-item v-for="item in posters " :key="item.id">
            <img class="me-carousel" :src="item.fromUrl" @click="linkTo(item.toUrl)"  />
          </el-carousel-item>
        </el-carousel>
        <article-scroll-page></article-scroll-page>
      </el-main>

      <el-aside>

        <card-me class="me-area"></card-me>
        <card-tag :tags="hotTags"></card-tag>

        <card-article cardHeader="最热文章" :articles="hotArticles"></card-article>

        <card-archive cardHeader="文章归档" :archives="archives"></card-archive>

        <card-article cardHeader="最新文章" :articles="newArticles"></card-article>

      </el-aside>

    </el-container>
  </div>
</template>

<script>
  import CardMe from '@/components/card/CardMe'
  import CardArticle from '@/components/card/CardArticle'
  import CardArchive from '@/components/card/CardArchive'
  import CardTag from '@/components/card/CardTag'
  import ArticleScrollPage from '@/views/common/ArticleScrollPage'

  import {getArticles, getPosters,getHotArtices, getNewArtices} from '@/api/article'
  import {getHotTags} from '@/api/tag'
  import {listArchives} from '@/api/article'

  export default {
    name: 'Index',
    created() {
      this.getHotArtices()
      this.getNewArtices()
      this.getHotTags()
      this.listArchives()
      //this.getPosters() 静态加载，如果要开启动态加载则需配合后台管理系统实用
    },
    data() {
      return {
        hotTags: [],
        hotArticles: [],
        newArticles: [],
        archives: [],
        posters:[
          {fromUrl:"http://wt.watermelonhit.cn/posters/p1.png",
            toUrl:""},
          {fromUrl:"http://wt.watermelonhit.cn/posters/p2.jpg",
            toUrl:""},
          {fromUrl:"http://wt.watermelonhit.cn/posters/p3.jpg",
            toUrl:""},
          {fromUrl:"http://wt.watermelonhit.cn/posters/p4.jpg",
            toUrl:""},
          {fromUrl:"http://wt.watermelonhit.cn/posters/p5.jpg",
            toUrl:""}
        ]
      }
    },
    methods: {
      linkTo (url) {
        window.location.href = url;
      },
      getPosters(){
        let that = this
        getPosters().then(data => {
          that.posters = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '轮播图加载失败!', showClose: true})
          }
        })
      },
      getHotArtices() {
        let that = this
        getHotArtices().then(data => {
          that.hotArticles = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '最热文章加载失败!', showClose: true})
          }
        })
      },
      getNewArtices() {
        let that = this
        getNewArtices().then(data => {
          that.newArticles = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '最新文章加载失败!', showClose: true})
          }

        })

      },
      getHotTags() {
        let that = this
        getHotTags().then(data => {
          that.hotTags = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '最热标签加载失败!', showClose: true})
          }

        })
      },
      listArchives() {
        listArchives().then((data => {
          this.archives = data.data
        })).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章归档加载失败!', showClose: true})
          }
        })
      }

    },
    components: {
      'card-me': CardMe,
      'card-article': CardArticle,
      'card-tag': CardTag,
      ArticleScrollPage,
      CardArchive
    }
  }
</script>

<style scoped>

  .el-container {
    width: 960px;
  }

  .el-aside {
    margin-left: 20px;
    width: 260px;
  }

  .el-main {
    padding: 0px;
    line-height: 16px;
  }

  .el-card {
    border-radius: 0;
  }

  .el-card:not(:first-child) {
    margin-top: 20px;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  img{
    /*设置图片宽度和浏览器宽度一致*/
    width:100%;
    height:inherit;
  }
</style>
