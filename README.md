# WXXServer 项目文档

一句话简介：基于 Kotlin 与 Spring Boot 的模块化后端服务骨架，支持 Spring Modulith、GraalVM Native，内置 DGS GraphQL 客户端代码生成。

## 架构总览
- 类型：单体应用（Single Module），Kotlin/JVM，Gradle Kotlin DSL 构建。
- 框架：Spring Boot（Gradle 插件 4.0.1）、Spring Modulith（2.0.1，用于模块化边界与事件驱动，当前未启用业务模块拆分）。
- 通信：当前未启用 Web 层（未引入 spring-boot-starter-web/webflux）。配置了 DGS Codegen 作为 GraphQL 客户端（用于调用远端 GraphQL 服务），未启用 GraphQL 服务端。
- 数据存储：未接入数据库或 Redis（无相关依赖与配置）。
- 可执行形态：可执行 Jar；可选原生镜像（GraalVM Native Build Tools 0.11.3）。
- 测试：JUnit 5、Spring Boot Test、Modulith Test（测试依赖已加入）。

当前状态清单：
- 已启用：Kotlin 2.2.21、Java 17、Spring Boot 插件、Spring Modulith（BOM 管理与测试依赖）、DGS GraphQL 客户端代码生成、DevTools、配置处理器、原生构建工具。
- 未启用：REST API/WebFlux、数据库（JPA/MyBatis-Plus）、Redis 缓存、统一响应 Result、全局异常处理、认证鉴权、安全审计、领域模块边界（仅依赖在，未落地设计）、异步与定时任务。

## 技术栈与版本
- Java 17（Gradle Toolchain）
- Kotlin 2.2.21（kotlin-jvm、kotlin-spring 插件）
- Spring Boot 4.0.1（Gradle 插件）
- Spring Modulith 2.0.1（BOM 管理：core、test）
- Netflix DGS Codegen 8.3.0（GraphQL 客户端代码生成）
- GraalVM Native Build Tools 0.11.3（原生镜像/原生测试）
- 测试：spring-boot-starter-test、kotlin-test-junit5、spring-modulith-starter-test
- 辅助：Lombok（compileOnly+annotationProcessor，可选）、DevTools、Docker Compose 开发支持（默认禁用）

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

### 业务数据流（当前空骨架）
- 入站适配器：未启用（无 Web/GraphQL 服务端）。
- 应用服务：尚未建立 service 层；可在引入 Web/API 后落地。
- 出站适配器：DGS 客户端可对接外部 GraphQL 服务；数据库/Redis 尚未接入。

### 统一响应与异常（建议方案，未启用）
- 统一响应 Result<T> 字段建议：code(int)、message(String)、data(T)、traceId(String)。
- 全局异常：使用 @RestControllerAdvice 捕获并映射错误码；禁止吞异常。
- 错误码分层：1xxx 参数校验；2xxx 业务冲突；5xxx 系统异常。

### 并发/缓存/安全（建议方案，未启用）
- 并发：@Async/CompletableFuture，线程池参数可在配置中外化；禁止阻塞主线程。
- 缓存：Redis + 统一 TTL 策略，防穿透/击穿/雪崩；所有操作必须设置过期时间。
- 安全：Spring Security（认证/授权/审计），密码加密（BCrypt），外部输入严格校验。

## 主要依赖与用途
- org.springframework.modulith:spring-modulith-starter-core：模块化支持（事件、结构约束）。
- com.netflix.dgs.codegen：从 schema 生成 GraphQL 客户端类型与 DSL（路径：`src/main/resources/graphql-client`）。
- org.springframework.boot:spring-boot-devtools：本地热重载（开发依赖）。
- org.springframework.boot:spring-boot-configuration-processor：配置提示元数据生成。
- org.springframework.boot:spring-boot-docker-compose：开发期 Docker Compose 集成（在本项目中默认禁用）。
- org.graalvm.buildtools.native：GraalVM 原生构建支持与原生测试。
- 测试：spring-boot-starter-test、kotlin-test-junit5、spring-modulith-starter-test。
- Kotlin：kotlin-reflect；Kotlin 编译器参数（`-Xjsr305=strict` 等）。

> 注意：目前未包含 Web、数据库、Redis 相关依赖，后续按需添加。

## 配置说明
文件：`src/main/resources/application.properties`
- spring.application.name=WXXServer
- spring.docker.compose.enabled=false（禁用 Spring Boot Dev Services 的 Docker Compose 集成，避免启动依赖本地 Docker）

建议的环境分离（待落地）：
- application-dev.properties / application-test.properties / application-prod.properties
- 数据源、Redis、线程池、安全策略等放入对应 profile。

## GraphQL 客户端代码生成（DGS Codegen）
- 将远端 GraphQL 服务的 schema 文件放入：`src/main/resources/graphql-client/`
- 生成客户端类型与 DSL：
```powershell
.\gradlew generateJava
```
- 生成代码默认包名示例：`com.wangxingxing.wxxserver.wxxserver.codegen`

## 本地运行（Windows PowerShell）
- 初始化与编译
```powershell
.\gradlew clean build
```
- 开发运行
```powershell
.\gradlew bootRun
```
- 指定 Profile
```powershell
.\gradlew bootRun --args="--spring.profiles.active=dev"
```

## 测试
- 运行全部测试
```powershell
.\gradlew test
```
- 按测试名过滤
```powershell
.\gradlew test --tests "com.wangxingxing.*"
```

## 打包与部署
### 可执行 Jar
```powershell
.\gradlew bootJar
java -jar .\build\libs\WXXServer-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 原生镜像（GraalVM Native）
- 要求：本地已安装并配置可用的 GraalVM，版本 25+。
- 构建与运行：
```powershell
.\gradlew nativeCompile
.\build\native\nativeCompile\WXXServer.exe --spring.profiles.active=prod
```
- 原生测试：
```powershell
.\gradlew nativeTest
```

### OCI 镜像（可选）
```powershell
.\gradlew bootBuildImage
# 运行镜像示例
# docker run --rm wxxserver:0.0.1-SNAPSHOT
```

## API 约定（预案，待启用 Web 层后生效）
- 统一响应：
  - 成功：`{"code":0,"message":"OK","data":{...},"traceId":"..."}`
  - 失败：`{"code":<int>,"message":"<string>","data":null,"traceId":"..."}`
- 参数校验：Bean Validation（`@Valid`/`@Validated`）+ 全局异常映射。
- 错误码规范：1xxx（参数）/2xxx（业务）/5xxx（系统）。

## 贡献与规范
- 提交信息（中文祈使句）：
  - 示例：`feat: 新增登录页面`、`fix: 修复列表闪退问题`
- 代码规范：
  - Controller 不写业务逻辑；禁止直接操作 Mapper（如后续接入 MyBatis-Plus）。
  - Service 必须有接口；异常不可吞，使用 BusinessException；全局异常由 `@RestControllerAdvice` 处理。
  - 并发建议使用 CompletableFuture/@Async；Redis 操作必须设置过期时间；外部输入必须校验；密码使用 BCrypt。
  - 关键逻辑需有 JavaDoc；复杂 SQL 需注释用途；单元测试使用 JUnit5 + Mockito。

## 常见问题（FAQ）
- 应用启动无 Web 端口：当前未引入 Web 依赖，属预期行为。如需 REST，请添加 `spring-boot-starter-web` 并创建 Controller。
- compose.yaml 存在但无服务：这是 Spring Initializr 的默认产物，需自行补充依赖服务（如 MySQL/Redis）或删除该文件。
- 原生构建失败：请确认安装 GraalVM 25+，并使用与 Gradle 进程一致的 Java 环境；参考 Native Build Tools 文档进行参数调优。
- 配置不生效：检查 `--spring.profiles.active` 是否正确传入，查看启动日志中的有效配置源。

## 路线图（建议）
- v0.1：接入 REST Web（或 WebFlux）与统一响应/异常；补齐基础监控与日志追踪（traceId）。
- v0.2：接入数据库（JPA 或 MyBatis-Plus）与 Redis；完善分层与 DTO/VO；引入全链路校验。
- v0.3：基于 Modulith 划分领域组件与事件；引入 @Async 与任务编排；安全（Spring Security）。
- v0.4：容器化与 CI/CD；原生镜像评估与优化；完善端到端集成测试。

## 版本与许可证
- 当前版本：0.0.1-SNAPSHOT
- 许可证：待定（可选添加 LICENSE 文件）
