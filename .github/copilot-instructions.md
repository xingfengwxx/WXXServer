# GitHub Copilot Instructions - Java Backend

## 技术栈
- Java 17
- Spring Boot 3.x
- Spring Cloud 2023.x
- MyBatis-Plus
- Redis / MySQL

## 编码规范
- 使用 Lombok
- Controller 不写业务逻辑
- Service 必须有接口
- 禁止在 Controller 中直接操作 Mapper
- 所有接口返回统一 Result<T>

## 包结构规范
- controller
- service / service.impl
- mapper
- domain / entity
- dto / vo
- config
- infrastructure

## 异常处理
- 禁止 try-catch 吞异常
- 使用 BusinessException
- 全局异常由 @RestControllerAdvice 处理

## 并发 & 性能
- 禁止阻塞主线程
- 使用 CompletableFuture / @Async
- Redis 操作必须设置过期时间

## 安全
- 不生成硬编码密钥
- 密码必须加密（BCrypt）
- 所有外部输入必须校验

## 注释
- 关键逻辑必须有 JavaDoc
- 复杂 SQL 必须注释用途

## 测试
- 生成单元测试（JUnit5 + Mockito）