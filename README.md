# MoreMall

[![CI](https://github.com/Xenos-hxc/MoreMall/actions/workflows/ci.yml/badge.svg)](https://github.com/Xenos-hxc/MoreMall/actions/workflows/ci.yml)

基于 Vue 2 和 Spring Cloud 的商城管理系统，提供商品维护、会员管理、数据看板和管理员个人中心。

## 功能

- 商品新增、编辑、上下架、批量删除、分页查询与表格导出。
- 会员资料管理、状态切换、批量删除与 Excel 导出。
- 看板展示商品数量、会员数量、商品访问量和库存分布。
- 管理员登录、个人资料和头像维护。
- Eureka 服务注册与发现，Zuul 网关统一转发。

## 技术与目录

| 目录 | 内容 |
| --- | --- |
| `chatroom-master/` | Vue 2.7、Vue CLI 5、Element UI、ECharts 管理前端 |
| `Eureka_Demo/` | 服务注册中心，默认端口 `9099` |
| `Zuul_Demo/` | API 网关，默认端口 `10010` |
| `ServiceDemo/` | 商品、会员与管理员业务服务，默认端口 `9088` |
| `sql/` | MySQL 表结构和虚构 MongoDB 演示账号 |
| `scripts/smoke.py` | 登录、查询和网关接口检查 |

业务数据使用 MongoDB / Morphia，仓库同时保留原有 MySQL / MyBatis 模块的空表结构。前端目录沿用已有工程名称。

## 本地运行

需要 JDK 8、Maven 3.8+、Node.js 22、MySQL 8 和 MongoDB 4.4。可在仓库根目录执行 `docker compose up -d` 启动两个演示数据库；首次启动会导入表结构并创建虚构管理员。

```sh
mvn clean package
```

在三个终端中，均从仓库根目录启动服务：

```sh
java -jar Eureka_Demo/target/Eureka_Demo-1.0-SNAPSHOT.jar
java -jar ServiceDemo/target/ServiceDemo-1.0-SNAPSHOT.jar
java -jar Zuul_Demo/target/Zuul_Demo-1.0-SNAPSHOT.jar
```

启动前端：

```sh
cd chatroom-master
npm ci
npm run serve
```

访问 `http://localhost:8080`。管理员账号为 `admin / local-demo-change-me`，仅供本地演示。验证码由页面生成。

已有数据库时，创建 `moremall_demo` MySQL 数据库并导入 `sql/schema.sql`；MongoDB 中执行 `sql/mongo-demo.js` 初始化演示账号。该脚本不会覆盖同名已有管理员。

| 环境变量 | 用途 | 默认值 |
| --- | --- | --- |
| `DB_URL` | MySQL JDBC 连接地址 | 本地 `moremall_demo` |
| `DB_USERNAME` / `DB_PASSWORD` | MySQL 账号 | `demo` / `local-demo-change-me` |
| `MONGO_HOST` / `MONGO_PORT` | MongoDB 地址 | `localhost` / `27017` |
| `MONGO_DATABASE` | MongoDB 数据库名 | `moremall_demo` |
| `EXPORT_DIR` | 运行时 Excel 导出目录 | 仓库下的 `exports/` |
| `VUE_APP_API_TARGET` | 前端开发代理目标 | `http://localhost:9088` |

前端 `/api` 请求默认直连业务服务的开发代理。通过网关访问业务接口时使用 `/api/demoservice/`；如需让前端使用网关，可把 `VUE_APP_API_TARGET` 设为 `http://localhost:10010/api/demoservice`。生产构建执行 `npm run build`，部署时把静态站点的 `/api` 转发到业务服务并去掉前缀。

头像上传文件写入 `upload/images/`，此目录以及 Excel 导出目录均被 Git 忽略。默认头像访问地址使用本地业务端口，部署到其他地址时需调整 `StaffServiceImpl` 的公开文件地址。

## 验证与数据

`mvn clean package` 构建三个可执行服务并执行已有测试，`npm run build` 构建前端。数据库和后端启动后，在仓库根目录执行 `python scripts/smoke.py` 验证演示登录、空商品列表、会员分页、数据看板和网关转发。GitHub Actions 使用独立的 MySQL 与 MongoDB 数据库执行这些检查。

仓库不包含原数据库记录、历史上传文件、个人附件、机器路径或 Gitee 中的旧提交历史。演示账号为新建的虚构数据。当前认证与明文密码方式适用于本地功能演示，正式部署前需增加服务端访问控制、密码散列和 MongoDB 认证。
