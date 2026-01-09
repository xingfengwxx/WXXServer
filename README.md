# WXXServer 项目文档

一句话简介：基于 Kotlin 与 Spring Boot 的模块化后端服务骨架，支持 Spring Modulith、GraalVM Native，内置 DGS GraphQL 客户端代码生成。

## 架构总览
- 类型：单体应用（Single Module），Kotlin/JVM，Gradle Kotlin DSL 构建。
- 框架：Spring Boot（Gradle 插件 4.0.1）、Spring Modulith（2.0.1，用于模块化边界与事件驱动）。
- 通信：已启用 Spring MVC（spring-boot-starter-web）；提供基础 REST 接口；DGS Codegen 作为 GraphQL 客户端（用于调用远端 GraphQL 服务），未启用 GraphQL 服务端。
- 数据存储：未接入数据库或 Redis（无相关依赖与配置）。
- 可执行形态：可执行 Jar；可选原生镜像（GraalVM Native Build Tools 0.11.3）。
- 测试：JUnit 5、Spring Boot Test、Modulith Test（测试依赖已加入）。

当前状态清单：
- 已启用：Kotlin 2.2.21、Java 17、Spring Boot 插件、Spring MVC Web、Spring Validation、统一返回 Result、全局异常处理、Spring Modulith（BOM 管理与测试依赖）、DGS GraphQL 客户端代码生成、DevTools、配置处理器、原生构建工具。
- 未启用：数据库（JPA/MyBatis-Plus）、Redis 缓存、认证鉴权、安全审计、领域模块边界（仅依赖在，未落地设计）、异步与定时任务。

## 技术栈与版本
- Java 17（Gradle Toolchain）
- Kotlin 2.2.21（kotlin-jvm、kotlin-spring 插件）
- Spring Boot 4.0.1（Gradle 插件）
- Spring MVC + Validation（统一校验/返回）
- Spring Modulith 2.0.1（BOM 管理：core、test）
- Netflix DGS Codegen 8.3.0（GraphQL 客户端代码生成）
- GraalVM Native Build Tools 0.11.3（原生镜像/原生测试）
- 测试：spring-boot-starter-test、kotlin-test-junit5、spring-modulith-starter-test

## 目录结构
```
.
├─ build.gradle.kts
├─ settings.gradle.kts
├─ compose.yaml                         # 存在但未定义服务
├─ src
│  ├─ main
│  │  ├─ kotlin/com/wangxingxing/wxxserver/wxxserver/
│  │  │  └─ WxxServerApplication.kt     # 启动类
│  │  └─ resources
│  │     ├─ application.properties      # 基础配置
│  │     └─ graphql-client/             # DGS 客户端 schema（可选）
│  └─ test
│     └─ kotlin/ ...                    # 测试目录（当前为空）
├─ gradle/wrapper/*                     # Gradle Wrapper
└─ README.md
```
包规范建议（后续落地）：
- controller：对外接口（REST/GraphQL），不写业务逻辑。
- service / service.impl：业务逻辑接口与实现，支持 @Transactional。
- repository / mapper：数据访问（JPA/MyBatis-Plus），与 DB 解耦。
- domain / entity：领域模型、实体、值对象。
- dto / vo：入参与出参模型。
- config / infrastructure：配置与基础设施适配层（缓存/消息/安全）。

## 架构设计
### 分层与模块
- 分层：Controller（I/O 边界）→ Service（业务编排）→ Repository（数据访问）→ Infrastructure（外部依赖）
- 模块化：已引入 Spring Modulith 依赖；建议按领域划分组件模块（当前尚未创建组件边界与事件）。

### 统一响应与异常（已启用）
- 统一响应 Result<T> 字段：code(int)、message(String)、data(T)、traceId(String)。
- 全局异常：`@RestControllerAdvice` 捕获 BusinessException、校验异常与兜底异常并映射错误码。
- 错误码分层：1xxx 参数校验；2xxx 业务冲突；5xxx 系统异常。

### 并发/缓存/安全（预案，未启用）
- 并发：@Async/CompletableFuture，线程池参数可在配置中外化；禁止阻塞主线程。
- 缓存：Redis + 统一 TTL 策略，防穿透/击穿/雪崩；所有操作必须设置过期时间。
- 安全：Spring Security（认证/授权/审计），密码加密（BCrypt），外部输入严格校验。

## 主要依赖与用途
- spring-boot-starter-web：启用 Spring MVC 与内嵌服务器。
- spring-boot-starter-validation：Bean Validation 支持。
- com.fasterxml.jackson.module:jackson-module-kotlin：Kotlin 模型友好序列化。
- org.springframework.modulith:spring-modulith-starter-core：模块化支持。
- com.netflix.dgs.codegen：从 schema 生成 GraphQL 客户端类型与 DSL（路径：`src/main/resources/graphql-client`）。
- org.graalvm.buildtools.native：GraalVM 原生构建支持与原生测试。
- 测试：spring-boot-starter-test、kotlin-test-junit5、spring-modulith-starter-test。

## 配置说明
文件：`src/main/resources/application.properties`
- spring.application.name=WXXServer
- spring.docker.compose.enabled=false
- 可选：server.port=8080

## GraphQL 客户端代码生成（DGS Codegen）
```powershell
.\gradlew generateJava
```

## REST 接口示例
- 健康检查：GET /api/health/ping
- 期望响应：
```json
{"code":0,"message":"OK","data":"pong","traceId":null}
```
- cURL 调用：
```powershell
curl -s http://localhost:8080/api/health/ping
```

## 本地运行（Windows PowerShell）
```powershell
.\gradlew clean build
.\gradlew bootRun
```

## 测试
```powershell
.\gradlew test
```

## 打包与部署
```powershell
.\gradlew bootJar
java -jar .\build\libs\WXXServer-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 原生镜像（GraalVM Native）
```powershell
.\gradlew nativeCompile
.\build\native\nativeCompile\WXXServer.exe --spring.profiles.active=prod
```

## API 约定
- 统一响应：成功 code=0，失败返回对应错误码；字段：code/message/data/traceId
- 参数校验：`@Valid`/`@Validated` + 全局异常映射
- 错误码规范：1xxx（参数）/2xxx（业务）/5xxx（系统）

## 贡献与规范
- 提交信息（中文祈使句）：`feat: ...`、`fix: ...`
- 控制器不写业务；Service 必须有接口；禁止在 Controller 直接操作 Mapper
- 不吞异常，抛出 BusinessException；全局异常由 `@RestControllerAdvice` 处理

## FAQ（节选）
- 启动后 404：确认已启用 web 依赖且端口正确；访问 /api/health/ping 验证
- 原生编译失败：安装 GraalVM 25+ 并使用一致 JAVA_HOME；参考 Native Build Tools 文档
