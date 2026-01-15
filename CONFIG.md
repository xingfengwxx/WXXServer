# WXXServer 配置信息

## 数据库配置（MySQL）

### Docker 数据库（默认）

| 配置项 | 值 |
|--------|-----|
| 数据库名称 | wxxserver |
| 用户名 | root |
| 密码 | admin |
| 端口 | 3307 |
| 连接URL | jdbc:mysql://localhost:3307/wxxserver |
| 字符编码 | UTF-8 |
| 时区 | UTC |

#### 连接字符串
```
jdbc:mysql://localhost:3307/wxxserver?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

### 本地数据库

| 配置项 | 值 |
|--------|-----|
| 数据库名称 | wxxserver |
| 用户名 | root |
| 密码 | admin |
| 端口 | 3306 |
| 连接URL | jdbc:mysql://localhost:3306/wxxserver |
| 字符编码 | UTF-8 |
| 时区 | UTC |

#### 连接字符串
```
jdbc:mysql://localhost:3306/wxxserver?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

### MyBatis-Plus 配置
- ID类型：自动递增（AUTO）
- 驼峰命名转换：开启

---

## Redis 配置

| 配置项 | 值 |
|--------|-----|
| 主机地址 | localhost |
| 端口 | 6379 |
| 用户名 | *(空)* |
| 密码 | admin |

### 连接信息
```bash
# Redis CLI 连接命令
redis-cli -h localhost -p 6379 -a admin
```

### 缓存配置
- 缓存类型：Redis
- 默认TTL：600秒（10分钟）

---

## Docker Compose 启动

### 启动 Redis
```bash
docker compose up -d redis
```

### 启动所有服务
```bash
docker compose up -d
```

---

## 注意事项

1. **数据库选择**：
   - Docker 数据库：端口 3307（默认配置）
   - 本地数据库：端口 3306
   - 切换方式：修改 `application.properties` 中的 `spring.datasource.url` 端口号
2. **开发环境**：Spring Boot Docker Compose 自动集成已禁用，需手动启动服务
3. **Redis 密码**：生产环境请修改为强密码
4. **MySQL 端口**：Docker 使用非标准端口 3307，避免与本地 MySQL 冲突
5. **缓存 TTL**：可通过 `wxx.cache.default-ttl-seconds` 配置项调整

---

*最后更新：2026-01-15*

