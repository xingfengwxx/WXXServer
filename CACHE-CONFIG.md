# 缓存配置说明

## 问题
应用启动时出现 Redis 连接失败错误：`Failed to connect to 127.0.0.1:6379`

## 解决方案

### 当前配置（开发环境）
- 默认使用 **简单内存缓存**（`spring.cache.type=simple`）
- 不依赖 Redis，可直接启动应用
- 缓存数据存储在 JVM 内存中，应用重启后失效

### 启用 Redis（生产环境推荐）

#### 1. 安装 Docker Desktop（首次使用需要）

**Windows 系统安装步骤：**

1. **下载 Docker Desktop**
   - 官方下载地址：https://www.docker.com/products/docker-desktop/
   - 或直接下载：https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe

2. **系统要求**
   - Windows 10 64位：Pro、Enterprise 或 Education（Build 19041 或更高版本）
   - Windows 11 64位：Home 或 Pro 版本（Build 22000 或更高版本）
   - 启用 WSL 2（Windows Subsystem for Linux 2）

3. **安装前准备 - 启用 WSL 2**
   
   以管理员身份打开 PowerShell，运行以下命令：
   ```powershell
   # 启用 WSL
   dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
   
   # 启用虚拟机平台
   dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
   
   # 重启电脑
   ```

4. **安装 WSL 2 更新包**
   - 下载地址：https://wslstorestorage.blob.core.windows.net/wslblob/wsl_update_x64.msi
   - 双击安装

5. **设置 WSL 2 为默认版本**
   ```powershell
   wsl --set-default-version 2
   ```

6. **安装 Docker Desktop**
   - 双击下载的 `Docker Desktop Installer.exe`
   - 安装过程中确保勾选 "Use WSL 2 instead of Hyper-V"
   - 安装完成后重启电脑

7. **验证安装**
   
   打开 PowerShell 或命令提示符，运行：
   ```powershell
   docker --version
   docker compose version
   ```
   
   如果显示版本号，说明安装成功。

#### 2. 启动 Redis 服务
```bash
# 使用 Docker Compose
docker compose up -d redis

# 查看运行状态
docker compose ps

# 查看日志
docker compose logs redis
```

#### 3. 修改配置
编辑 `src/main/resources/application.properties`：
```properties
# 注释掉这行
# spring.cache.type=simple

# 取消注释这行
spring.cache.type=redis
```

#### 4. 重启应用

## 缓存类型对比

| 缓存类型 | 优点 | 缺点 | 适用场景 |
|---------|-----|-----|---------|
| simple  | 无需外部依赖，启动快 | 不支持分布式，重启失效 | 开发/测试环境 |
| redis   | 支持分布式，持久化 | 需要独立 Redis 服务 | 生产环境 |

## 常见问题排查

### 问题 1: `unable to get image 'redis:7-alpine': unexpected end of JSON input`

**原因**: Docker 镜像下载不完整或缓存损坏

**解决步骤**:

1. **确保 Docker Desktop 已启动**
   - 打开 Docker Desktop 应用
   - 等待右下角图标显示 "Docker is running"

2. **清理损坏的镜像**
   ```powershell
   # 查看现有镜像
   docker images redis
   
   # 删除损坏的镜像（如果存在）
   docker rmi redis:7-alpine -f
   
   # 清理 Docker 缓存
   docker system prune -f
   ```

3. **重新拉取镜像**
   ```powershell
   # 方式 1: 直接拉取镜像
   docker pull redis:7-alpine
   
   # 方式 2: 使用 Docker Compose 拉取
   docker compose pull redis
   ```

4. **启动 Redis 服务**
   ```powershell
   docker compose up -d redis
   ```

**如果仍然失败，尝试使用国内镜像源**:

编辑 Docker Desktop 设置:
- 打开 Docker Desktop
- 点击右上角齿轮图标 → Docker Engine
- 在 JSON 配置中添加:
  ```json
  {
    "registry-mirrors": [
      "https://docker.mirrors.ustc.edu.cn",
      "https://hub-mirror.c.163.com"
    ]
  }
  ```
- 点击 "Apply & Restart"
- 重新执行 `docker compose up -d redis`

### 问题 2: Docker Desktop 启动失败

**解决方法**:
1. 确保 WSL 2 已正确安装（参见上面安装步骤）
2. 以管理员身份运行 PowerShell:
   ```powershell
   wsl --update
   wsl --set-default-version 2
   ```
3. 重启 Docker Desktop

## 技术细节

### CacheConfig 配置类
- `redisCacheManager`: 当 `spring.cache.type=redis` 时启用
- `simpleCacheManager`: 当 `spring.cache.type=simple` 时启用
- 使用 `@ConditionalOnProperty` 实现动态切换

### Redis 连接配置
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
# spring.data.redis.password=  # 如果 Redis 设置了密码
```

### 缓存 TTL 配置
```properties
wxx.cache.default-ttl-seconds=600  # 仅对 Redis 生效，默认 10 分钟
```

