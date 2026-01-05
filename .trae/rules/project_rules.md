# Copilot Instructions for Rote Project

## Project Overview

i-have 是一个校园二手交易平台项目，采用前后端分离架构，项目分为前台和后台两个部分。
前台主要负责用户交互和展示，支持用户注册登录、发布商品、购买商品、评论商品等功能。
后台主要负责管理商品、用户、订单等数据，支持商品的发布、删除、修改、查询等操作，同时提供开放 API 接口，方便其他开发者集成。

## Technology Stack

- **运行时环境**: Bun (替代 Node.js，提供更快的性能)
- **后端技术**: Spring Boot + MyBatis ORM + MySQL
- **前端技术**: Vue + TypeScript + Vite + Unocss + Element Plus
- **数据存储**: MySQL (数据库) + AWS S3/R2 (文件存储)
- **部署方式**: 前端 Vue 应用部署在服务器上，使用 Nginx 反向代理，将请求转发到后端 Spring Boot 应用

## Code Standards & Guidelines

### 前端通用规范

- 运行时：使用 Bun 作为 JavaScript 运行时，获得更快的启动速度和执行性能
- 包管理：统一使用 `bun install` 安装依赖，`bun run` 执行脚本
- 代码质量：遵循 ESLint 配置规则和最佳实践
- **代码检查**：每次修改代码后必须执行 lint 检查（`bun run lint` 或 `npm run lint`），确保没有 lint 错误和 TypeScript 类型错误，所有问题必须修复后才能提交代码
- Vue 规范：优先使用 Vue Composition API
- 注释语言：代码注释使用中文，便于团队理解
- 命名规范：变量名、函数名使用英文驼峰命名法
- 代码组织：避免单个文件代码过长，保持每个文件在 200 行以内，适当拆分组件和函数
- 逻辑简化：实现功能时优先考虑 `简化逻辑，避免过度复杂化`
- 代码复用：多复用函数和组件，减少重复代码，提高代码维护性
- 测试策略：在没有明确说明的情况下不需要编写测试代码
- 文档风格：编写文档保持简洁明了，关注核心内容，避免使用 emoji，可以适当使用其他符号增强可读性

### 后端开发规范 (Spring Boot + MyBatis)

- 运行时环境：使用 Spring Boot 作为 Java 运行时，享受更快的执行性能
- 框架选择：使用 MyBatis 作为 ORM 框架
- 数据库操作：使用 MyBatis 进行数据库操作，统一操作 MySQL 数据库
- 路由组织：API 路由分为 v1 和 v2 版本，统一放置在 `route/` 目录
- 中间件管理：所有中间件文件放置在 `middleware/` 目录
- 工具函数：通用工具函数放置在 `utils/` 目录
- 类型定义：TypeScript 类型定义放置在 `types/` 目录
- 身份验证：使用 JWT 进行无状态认证
- 安全控制：实现 API 访问限流机制，防止滥用
- 文件存储：支持文件上传到 AWS S3/R2 云存储服务

### 数据库迁移规范 (MyBatis ORM)

SQL 为中心：MyBatis Migrations 是基于 SQL 的，这意味着数据库迁移脚本是用 SQL 语言编写的。这样的设计使得数据库管理员和开发人员可以直接使用熟悉的 SQL 语言来管理数据库模式的变更。

支持撤销操作：MyBatis Migrations 提供了撤销操作，这使得你可以方便地撤销最近的数据库模式变更。每个迁移脚本都包含两部分：一部分用于执行数据库变更，另一部分用于撤销这些变更。

版本控制：每次执行一个新的数据库迁移，MyBatis Migrations 都会在一个特殊的表（默认名为 changlog）中记录这次变更。

支持命令行和编程式 API：你可以通过命令行工具来使用 MyBatis Migrations

### 前端开发规范 (Vue + TypeScript)

- 构建工具：使用 Vite 作为前端构建工具，提供快速的开发体验
- UI 组件：使用 Element Plus + Unocss 构建现代化界面
- 组件组织：页面级组件放置在 `pages/` 目录，可复用组件放置在 `components/` 目录
- 状态管理：使用 `Pinia` 进行状态管理
- 网络请求：使用 `ihave-ui/api/request.ts` 封装好的方法，统一 API 请求
- 设计系统：使用 Element Plus 组件系统，保持界面一致性
- always use Unocss

### API 设计规范

- 设计原则：严格遵循 RESTful API 设计原则和最佳实践
- 错误处理：实现统一的错误处理机制和标准化响应格式
- 功能支持：全面支持分页查询、条件搜索、数据过滤等功能
- 文档维护：API 接口文档统一维护在 `doc/` 目录
- 认证机制：实现基于 API Key 的安全认证机制

## File Structure Conventions

### Backend Structure

```
ihave-bussiness
├── src/main/java/com/lvr/ihave/business/mapper/           # 数据库映射接口
├── src/main/java/com/lvr/ihave/business/service/          # 业务逻辑服务接口
├── src/main/java/com/lvr/ihave/business/service/impl/     # 业务逻辑服务实现类
├── src/main/java/com/lvr/ihave/business/utils/            # 工具函数
├── src/main/resources/mapper                              # 数据库映射文件
├── src/main/resources/application.yml                     # 应用配置文件
├── pom.xml                                                # Maven 项目配置文件
```

```
ihave-common
├── src/main/java/com/lvr/ihave/annotation/          # 自定义注解
├── src/main/java/com/lvr/ihave/common/              # 通用工具函数
├── src/main/java/com/lvr/ihave/constant/            # 常量定义
├── src/main/java/com/lvr/ihave/ex/                  # 自定义异常类
├── src/main/java/com/lvr/ihave/pojo/                # 数据传输对象 (DTO)
├── src/main/java/com/lvr/ihave/util/                # 通用工具函数
├── src/main/resources/application.yml               # 应用配置文件
├── pom.xml                                          # Maven 项目配置文件
```

```
ihave-web
├── src/main/java/com/lvr/ihave/web/config/                # 配置类
├── src/main/java/com/lvr/ihave/web/controller/            # 控制器类
├── src/main/java/com/lvr/ihave/web/interceptor/           # 拦截器类
├── src/main/java/com/lvr/ihave/web/util/                  # 通用工具函数
├── src/main/java/com/lvr/ihave/web/WebApplication.java    # 应用入口类
├── src/main/resources/i18n/                               # 国际化文件目录
├── src/main/resources/mybatis/                            # MyBatis 主配置文件
├── src/main/resources/static/                             # 静态资源目录
├── src/main/resources/templates/                          # Thymeleaf模板文件目录 (后台系统)
├── src/main/resources/application.yml                     # 应用配置文件
├── src/main/resources/logback.xml                         # 日志配置文件
├── pom.xml                                                # Maven 项目配置文件
```

```
logs/     # logback 输出日志
upload/   # 上传文件目录
```

### Frontend Structure

develop by Vue 3 + TypeScript + Pinia + Element Plus + Unocss

```
ihave-ui/
├── src/
│   ├── api/          # API 调用模块
│   ├── assets/       # 静态资源目录
│   ├── composables/  # 可组合函数
│   ├── components/   # 可复用组件
│   ├── pages/        # 页面组件
│   ├── utils/        # 工具函数
│   ├── store/        # 状态管理
│   ├── types/        # 类型定义
│   ├── router/       # 路由配置
│   ├── styles/       # 样式文件

```