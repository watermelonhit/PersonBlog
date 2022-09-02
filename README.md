## 博客介绍

<p align=center>
  <a href="http://watermelonhit.cn/">
    <img src="http://wt.watermelonhit.cn/header.jpg" alt="watermelonhitの博客" style="border-radius: 50%">
  </a>
</p>

<p align=center>
   基于Springboot + Vue 开发的前后端分离博客
</p>

<p align="center">
   <a target="_blank" href="https://github.com/X1192176811/blog">
      <img src="https://img.shields.io/badge/JDK-1.8+-green.svg"/>
      <img src="https://img.shields.io/badge/springboot-2.5.0-green"/>
      <img src="https://img.shields.io/badge/maven-3.8.1-green"/>
      <img src="https://img.shields.io/badge/mysql-8.0.26-green"/>
      <img src="https://img.shields.io/badge/mybatis--plus-3.4.3-green"/>
      <img src="https://img.shields.io/badge/redis-6.2.6-green"/>
      <img src="https://img.shields.io/badge/rabbitmq-3.9.11-green"/>
      <img src="https://img.shields.io/badge/vue-2.6.12-green"/>
      <img src="https://img.shields.io/badge/element--ui-2.15.6"/>
-green"/>
   </a>
</p>

[在线地址](#在线地址) | [目录结构](#目录结构) | [项目特点](#项目特点) | [技术介绍](#技术介绍) | [运行环境](#运行环境) | [开发环境](#开发环境) | [项目截图](#项目截图) | [快速开始](#快速开始) | [注意事项](#注意事项) | [项目总结](#项目总结) 

## 在线地址

**项目链接：** [watermelonhitの博客](http://watermelonhit.cn:9014/#/)

**后台管理：** [https://github.com/watermelonhit/BlogManager](https://github.com/watermelonhit/BlogManager)

**Github地址：** [https://github.com/watermelonhit/PersonBlog](https://github.com/watermelonhit/PersonBlog)

欢迎大家投入到本项目的建设中，让它焕发出更加绚丽的光彩！

## 目录结构

前端项目位于blog-app下。

后端项目位于blog-po中的blog-api。

SQL文件位于根目录下的blogSql.sql。

可直接导入该项目于本地，修改后端配置文件中的数据库等连接信息，项目中使用到的关于百度云智能生成摘要功能和七牛云存储等需要自行开通。

当你克隆项目到本地后可先随意注册一个账号进行体验。

本地项目访问接口：[http://127.0.0.1:9014](http://127.0.0.1:9014)

**ps：请先运行后端项目，再启动前端项目，前端项目配置由后端动态加载。**

```
blog-springboot
├── common.aop    --  自定义注解（日志打印和缓存控制）
├── config        --  配置模块
├── controller    --  控制器模块
├── enums         --  自定义枚举（主要定义了存储在reids中的key）
├── handler       --  处理器（控制器异常处理器）
├── interceptor   --  拦截器（登录权限拦截）
├── mapper        --  操作数据库层
├── rabbitmq      --  rabbitmq相关业务
├── service       --  服务模块
├── util          --  工具类模块
└── vo            --  vo模块
```

## 项目特点
- 前台利用vue和element-ui，美观简洁，响应式体验好；
- 采用Markdown编辑器，写法简单，支持代码高亮，图片预览，用户体验良好；
- 具有标签、类别，归档等标志信息，小巧实用，满足日常使用；
- 标准留言板，随心畅言，首页轮播图，支持自定义设置，宣扬个性；
- 支持文章摘要自动生成（需开发者自行到百度云申请），释放双手；
- 代码遵循阿里巴巴开发规范，利于开发者学习；
- 后端采用主流框架，代码条理清晰，便于开发者自定义修改；
- 整体小巧，可部署在小型服务器上；
## 技术介绍

**前端：** vue + vuex + vue-router + axios + vuetify + element 

**后端：** SpringBoot + MyBatisPlus + Mysql + Redis + RabbitMQ 

**其他：** 七牛云，百度智能服务

## 运行环境

**服务器：** 阿里云1核2G CentOS7.6

**CDN：** 七牛云加速

**对象存储：** 七牛云OSS



## 开发环境

|开发工具|说明|
|-|-|
|IDEA|Java、VUE开发工具IDE|
|Navicat|MySQL远程连接工具|
|Another Redis Desktop Manager|Redis远程连接工具|
|X-shell|Linux远程连接工具|
|Xftp|Linux文件上传工具|

|开发环境|版本|
|-|-|
|JDK|1.8|
|MySQL|8.0.26|
|Redis|6.2.6|
|RabbitMQ|3.9.11|

## 项目截图

![博客首页页面](http://wt.watermelonhit.cn/project%2Fblog%2Fblog1.png)

![留言页面](http://wt.watermelonhit.cn/project%2Fblog%2Fblog2.png)

![文章编辑页面](http://wt.watermelonhit.cn/project%2Fblog%2Fblog3.png)

## 快速开始

### 项目环境安装
前端：主要得配置好vue环境
后端：主要得配置好mysql，redis，rabbitmq环境
以上配置，请自行配置好（版本不一定要严格跟本项目一致）
### 项目配置
请根据注释完成blog-api中的yml配置，其中第三方配置可以参考注意事项
### 项目本地运行

#### 前端：
1.先安装依赖
```
npm run install
```
2.运行
```
npm run start
```
3.访问
```
http:localhost:9014
```
#### 后端：
- 0.导入数据库数据，运行sql文件
- 1.安装依赖，在blog-api中的pom文件进行maven刷新一下
- 2.rebuild一下blog-api项目
- 3.运行本项目（可以直接运行BlogApp中的main方法）

## 注意事项
- 前端：blog-app中src/views/Index.vue中采用了静态加载轮播图，如果要动态加载（去掉注释）则要配合后台管理系统，
- 项目拉下来配置好后，前端记得npm run install一下；后端记得先到pom文件下进行maven刷新，再rebuild一下，确保项目可以正常运行
- 七牛云和百度云需要自行配置，可参考下文：
[1.七牛云上传图片环境配置](https://blog.csdn.net/qq_33924360/article/details/89153493?spm=1001.2101.3001.6650.18&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-18-89153493-blog-122061310.topnsimilarv1&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-18-89153493-blog-122061310.topnsimilarv1&utm_relevant_index=19)
[2.百度云智能摘要环境配置](https://cloud.baidu.com/doc/NLP/s/Gk6z52hu3)

## 项目总结

博客作为入门项目是十分不错的，不仅实用，可以满足日常的记录需求，还可锻炼自身编程能力。
<br/>此外，本项目所用的技术栈比较主流、覆盖面也比较广，适合初学者学习。
<br/>主要难点在于登录权限管理、数据缓存一致性问题这块。
<br/>由于本人实力有限，本项目中做的不好的地方请大家见谅。当然，如果大家有问题或者有好的建议都可以私聊联系我。






