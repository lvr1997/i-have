# 『I have』校园二手交易平台

**基于2019年毕业设计科大二手工坊的V2.0版本，项目采用SpringBoot3 + Vue3构建，持续更新中...**

> 2024年9月3日，我再次回到这个项目，最重要的是积累和坚持✊
> 不能放弃！！

🙆‍♀️项目的灵感是来源于我的2019年毕业设计：[科大二手工坊](https://github.com/lvr1997/kd-second-hand-workshop)；科大二手工坊是基于Java语言编写由SSM框架构建，页面是基于jsp+layui编写的，项目相关技术还比较老旧

🙋‍♀️而如今，由于我个人职业发展因素的变更以及前端技术的不断更新迭代，我想就这个项目，完成一个新的尝试。也作为我毕业这么长时间以来，对前端方面学习上的一些总结吧。

ihave项目对我而言，我想实现在我职业技术生涯中的一项新的挑战。

## ⚒️技术栈升级

![images](https://img.shields.io/badge/SpringBoot-3.5.9.RELEASE-brightgreen) 
![images](https://img.shields.io/badge/Mybatis-3.3-yellowgreen)

![images](https://img.shields.io/badge/vue-3.4-green)
![Static Badge](https://img.shields.io/badge/ElementPlus-2.8.5-darkgreen)

## 📑项目介绍

> 项目前端分为两部分。一个是学生端前台，另一个是管理员端后台（前后端同库）

![ihave.png](./scanshoot/ihave.png)

### 环境准备：

- 项目开发环境: JDK21
- 项目开发工具： IDEA2023/VSCode
- 数据库：Mysql8.0、Redis

### 各模块说明

`ihave-common` 项目通用实体，工具类、自定义注解等
`ihave-business` 业务代码模块
`ihave-web` `web`启动模块

### 如何启动项目

1. 导入sql文件 在项目目录 `ihave-business\src\main\resources\ihave.sql` 文件夹下（该sql仅为结构）
2. 导入测试数据 在项目目录 `ihave-business\src\main\resources\init-data.sql` 文件夹下
3. `ihave-web` 模块为服务启动模块，运行`com.lvr.ihave.web.WebApplication`启动学生端`web`服务

管理员前端 项目目录 `ihave-web\src\main\resources\template\` 下

管理员端后台地址：`http://localhost:3000/admin/login` 账号密码：`admin/123456`

前台学生端（Vue项目）`ihave-ui`

本项目使用 `Bun` 作为前端项目的包管理工具，你需要先安装Bun 

```bash
powershell -c "irm bun.sh/install.ps1|iex"
```

然后在项目目录下运行 `cd ihave-ui` 进入前台学生端项目目录，运行 `bun install` 安装项目依赖。

5. 启动前台学生端项目，在项目目录下运行 `bun run dev` 启动项目，默认端口为 `http://localhost:8080`

## 💬项目更新记录

`V2.0`

- **2021-12-08** 科大二手工坊版本： `V2.0` 首次提交

## 🐞项目Bug与踩坑

1. 前端在配置axios请求代理时一定要加上请求前缀baseUrl，否则当前端路由地址刚好与后端接口地址一样时，你会发现惊喜🙃

## 👋感谢

> 最后感谢各位小伙伴们的支持，希望项目会对你们有所帮助
