# 信用支撑型高校闲置技能与时间零工服务微校园平台

## 项目简介

本项目是一个面向高校的轻量级零工交易微平台，首期聚焦**校园跑腿代办**这一高频刚需场景。核心创新在于将"学籍绑定+熟人担保"引入交易链路——接单人必须绑定已认证的同学作为担保人，违约则担保人连带扣分，以此替代传统押金模式，降低交易门槛。

## 技术栈

| 层级 | 技术选型 |
|------|----------|
| 前端 | uni-app (Vue 3) |
| 后端 | Spring Boot 2.x + MyBatis-Plus |
| 数据库 | MySQL 8.0 + Redis |
| 开发工具 | IntelliJ IDEA / VS Code / Git (Gitee) / ApiFox |

## 项目结构

```
campus-zero-work/
├── backend/                     # 后端 Spring Boot 项目
│   ├── pom.xml
│   └── src/main/java/com/platform/
│       ├── CampusZeroWorkApplication.java  # 启动类
│       ├── common/              # 公共枚举与响应封装
│       │   ├── Result.java             # 统一响应格式
│       │   ├── TaskStatus.java         # 任务状态枚举
│       │   ├── CreditChangeReason.java # 信用分变动原因枚举
│       │   └── DisputeLevel.java       # 争议级别枚举
│       ├── config/              # 配置
│       │   ├── GlobalExceptionHandler.java  # 全局异常处理
│       │   ├── WebConfig.java             # 跨域配置
│       │   ├── RedisConfig.java           # Redis 配置
│       │   └── WebSocketConfig.java       # WebSocket 配置
│       ├── entity/              # 实体类
│       │   ├── User.java
│       │   ├── Task.java
│       │   ├── OrderPlatform.java
│       │   ├── Guarantor.java
│       │   ├── CreditLog.java
│       │   └── Message.java
│       ├── mapper/              # MyBatis-Plus Mapper
│       ├── service/             # 服务接口
│       │   ├── UserService.java
│       │   ├── TaskService.java
│       │   ├── GuarantorService.java
│       │   ├── CreditService.java
│       │   └── DisputeService.java
│       ├── service/impl/        # 服务实现
│       ├── controller/          # 控制器
│       │   ├── UserController.java      # 用户注册/登录/信用分
│       │   ├── TaskController.java      # 任务发布/接单/完成
│       │   ├── GuarantorController.java # 担保关系管理
│       │   ├── CreditController.java    # 信用分流水
│       │   └── DisputeController.java   # 纠纷上报/仲裁
│       ├── dto/                 # DTO
│       └── util/                # 工具类
│           ├── JwtUtil.java             # JWT 鉴权
│           └── ChatWebSocketHandler.java # WebSocket 聊天
├── frontend/                    # 前端 uni-app 项目
│   ├── package.json
│   ├── src/
│   │   ├── App.vue
│   │   ├── pages.json           # 页面路由 & tabBar 配置
│   │   ├── api/index.js         # API 封装
│   │   ├── utils/request.js     # 网络请求封装
│   │   └── pages/
│   │       ├── index/index.vue          # 首页（信用分+任务流）
│   │       ├── task/list.vue            # 任务列表
│   │       ├── task/publish.vue         # 发布任务
│   │       ├── task/detail.vue          # 任务详情 & 接单/完成
│   │       ├── user/login.vue           # 学籍认证登录
│   │       ├── user/profile.vue         # 个人主页
│   │       ├── guarantor/list.vue       # 担保人管理
│   │       └── message/chat.vue         # 聊天
├── database/
│   └── init.sql                 # 数据库初始化脚本
└── README.md
```

## 核心功能

### 1. 用户系统
- **学籍绑定**：学号+姓名+身份证后六位验证（A方案：教务API / B方案：人工审核）
- **信用分展示**：个人主页展示分数与等级（青铜小白→白银跑腿→黄金学长→钻石担保人）

### 2. 交易模块
- **任务发布**：标题、描述、报酬（必填），品类、信用分门槛（选填）
- **担保人自动判定**：门槛≤719需担保人，≥720免担保（路径B）
- **一键接单**：按信用分自动执行三条校验路径（路径A/B/C）
- **智能推荐**：同宿舍楼/同学院/好友的好友/高分优先

### 3. 担保模块
- 每人最多3个担保人，每人最多为5人担保
- 担保连带责任：违约按等级扣被担保人+担保人信用分
- 连续恶意违约导致担保人取消资格

### 4. 信用体系
- 履约质量 55%（贝叶斯平均修正）
- 担保人质量 20%（中位数，互保打八折）
- 近期活跃度 15%（不活跃不扣分）
- 社交贡献 10%
- 实时更新 + 每日增量 + 每两周全量

### 5. 风控模块
- 纠纷上报+两人裁审（指导老师+项目组）
- 三档违约标准：轻微/一般/恶意
- 黑名单机制

### 6. 增长模块
- 新手引导任务（绑定学籍+5、添加担保人+10、发布任务+5等）
- 邀请奖励+任务悬赏
- 三阶段冷启动（30名种子用户→100单→40%自然配对率）

## 快速开始

### 1. 数据库

```bash
# 创建数据库
mysql -u root -p < database/init.sql
```

### 2. 后端

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 3. 前端

```bash
cd frontend
npm install
# HBuilderX 中导入项目，运行到微信小程序模拟器
```

### 4. 访问

- 后端 API: http://localhost:8080
- 前端页面: 微信小程序开发工具预览

## API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/user/register | 用户注册 |
| POST | /api/user/login | 用户登录 |
| GET | /api/user/{id} | 获取用户信息 |
| GET | /api/user/{id}/credit | 获取信用分 |
| POST | /api/task/publish | 发布任务 |
| POST | /api/task/{id}/accept | 接单 |
| POST | /api/task/{id}/complete | 完成任务 |
| GET | /api/task/list | 任务列表 |
| GET | /api/task/{id} | 任务详情 |
| GET | /api/task/my | 我的任务 |
| POST | /api/guarantor/invite | 邀请担保人 |
| POST | /api/guarantor/remove | 解除担保 |
| GET | /api/guarantor/list | 担保人列表 |
| GET | /api/credit/log | 信用分流水 |
| POST | /api/dispute/report | 纠纷上报 |
| POST | /api/dispute/arbitrate | 裁审 |
| WS | /ws/chat | WebSocket聊天 |

## 信用分等级

| 分数范围 | 等级 | 权益 |
|----------|------|------|
| 600-649 | 青铜小白 | 基础接单 |
| 650-699 | 白银跑腿 | 正常接单 |
| 700-749 | 黄金学长 | 可为他人担保 |
| 750+ | 钻石担保人 | 免担保接单+为多人担保 |

## 冷启动策略

| 阶段 | 时间 | 目标 | 手段 |
|------|------|------|------|
| 种子用户筛选 | 第3个月 | 30名"校园跑腿大使" | 辅导员推荐+社团招募，信用分直授680 |
| 供需启动 | 第4个月 | 累计100单 | 种子用户发单+项目组接单，扫码注册送体验券 |
| 自运转验证 | 第5-6个月 | 自然配对率>40% | 撤出人工干预，分析瓶颈针对性调整 |

## 项目进度

| 时间 | 任务 |
|------|------|
| 2026.05 - 2026.07 | 需求调研、架构设计、UI设计 |
| 2026.08 - 2026.10 | 用户认证、任务、担保模块开发 |
| 2026.11 - 2027.01 | 聊天/风控/测试/种子用户招募 |
| 2027.02 - 2027.03 | Beta试运营/数据优化/结题报告 |
