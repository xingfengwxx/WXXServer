# IDEA 推荐插件清单

> 适用于 WXXServer 项目（Spring Boot 3.x + Kotlin + MyBatis-Plus）

## 🔥 必装插件（TOP 5）

### 1. Lombok
- **用途**：注解处理器支持
- **原因**：项目已使用 Lombok，避免编译错误
- **安装**：Settings → Plugins → 搜索 "Lombok"
- **注意**：安装后需启用注解处理（Settings → Build → Compiler → Annotation Processors → Enable annotation processing）

### 2. MyBatisX
- **用途**：MyBatis/MyBatis-Plus 增强
- **功能**：
  - Mapper 接口与 XML 互相跳转（Ctrl+Alt+B）
  - 自动生成 CRUD 代码
  - SQL 语句提示与检查
  - 参数自动补全
- **原因**：项目使用 MyBatis-Plus 3.5.7

### 3. Spring Boot Assistant
- **用途**：Spring Boot 配置文件增强
- **功能**：
  - application.properties 智能提示
  - 配置项文档说明
  - 快速跳转到配置项定义
  - 依赖快速添加
- **原因**：提升 Spring Boot 开发效率

### 4. SonarLint
- **用途**：实时代码质量检查
- **功能**：
  - 发现潜在 Bug 和安全漏洞
  - 代码异味检测
  - 支持 Kotlin 和 Java
  - 实时提示修复建议
- **原因**：保证代码质量，符合编码规范要求

### 5. GitToolBox
- **用途**：Git 工作流增强
- **功能**：
  - 显示每行代码的 Git Blame 信息
  - 自动 Fetch 远程更新
  - Commit 模板支持
  - 分支状态显示
- **原因**：提升 Git 使用效率

---

## 📦 核心开发插件

### Kotlin（IDEA 自带）
- **状态**：必须启用
- **用途**：Kotlin 语法支持、重构、调试
- **版本**：确保与项目 Kotlin 2.0.21 兼容

### Database Tools（IDEA Ultimate 自带）
- **用途**：MySQL 数据库管理
- **功能**：
  - 连接 MySQL 数据库
  - 可视化查询与编辑
  - SQL 代码补全
  - 数据导入导出

### Docker（JetBrains 官方）
- **用途**：Docker 容器管理
- **原因**：项目有 `compose.yaml` 文件
- **功能**：
  - 管理 Docker 容器
  - 查看日志
  - 快速启动/停止服务

---

## 🎨 代码质量与规范插件

### CheckStyle-IDEA
- **用途**：代码风格检查
- **功能**：实时检查代码规范违规

### Alibaba Java Coding Guidelines
- **用途**：阿里巴巴 Java 开发规范
- **功能**：
  - P3C 规范检查
  - 代码规约扫描
  - 适合国内团队

### Rainbow Brackets
- **用途**：彩虹括号匹配
- **功能**：不同层级括号用不同颜色标识
- **原因**：提高嵌套代码可读性（Kotlin/JSON）

---

## ⚡ 效率工具插件

### String Manipulation
- **用途**：字符串快捷转换
- **功能**：
  - 驼峰 ↔ 下划线 ↔ 大写
  - 编码/解码（Base64/URL/HTML）
  - 快捷键：Alt+M
- **原因**：写 DTO、Entity、SQL 时非常有用

### Translation（翻译插件）
- **用途**：快速翻译
- **功能**：
  - 选中文本快速翻译
  - 支持有道、Google、百度翻译
  - 快捷键：Ctrl+Shift+Y
- **原因**：变量命名时快速查单词

### GenerateAllSetter
- **用途**：快速生成对象的所有 setter 调用
- **功能**：
  - 右键 → Generate → Generate All Setter
  - 测试数据构造
- **原因**：单元测试时快速构造对象

### Key Promoter X
- **用途**：快捷键学习助手
- **功能**：点击菜单时提示对应快捷键
- **原因**：提升 IDE 使用效率

---

## 🌐 API 开发插件

### RestfulTool / RestfulToolkit
- **用途**：REST API 快速测试
- **功能**：
  - 显示所有 Controller 端点（Ctrl+\）
  - 快速发送 HTTP 请求
  - 可替代 Postman 做简单测试
- **原因**：项目使用 Spring MVC

### HTTP Client（IDEA 自带）
- **用途**：`.http` 文件支持
- **功能**：使用 `.http` 文件测试 API
- **建议**：创建 `api-test.http` 文件管理测试用例

---

## 🗄️ 数据库与缓存插件

### Redis（JetBrains 官方）
- **用途**：Redis 客户端
- **功能**：
  - 连接 Redis 查看数据
  - 执行 Redis 命令
  - 可视化 Key 管理
- **原因**：项目使用 Spring Data Redis

---

## 📝 日志与调试插件

### Grep Console
- **用途**：控制台日志着色
- **功能**：
  - ERROR/WARN/INFO 不同颜色
  - 过滤日志输出
  - 快速定位错误
- **原因**：Spring Boot 日志输出多

---

## 🚀 Git 相关插件

### Conventional Commit
- **用途**：规范化 Commit Message
- **功能**：
  - 符合约定式提交规范
  - 自动生成格式：`类型: 简要说明`
  - 支持类型：feat/fix/docs/style/refactor/test/chore
- **原因**：符合项目 Git 提交规范要求

### .ignore
- **用途**：.gitignore 文件支持
- **功能**：
  - 语法高亮
  - 模板生成
  - 快速添加忽略规则

---

## 🔧 Gradle 相关插件

### Gradle（IDEA 自带）
- **状态**：已启用
- **用途**：Gradle 构建支持
- **功能**：
  - 依赖管理
  - 任务执行
  - 项目刷新

---

## 📋 安装步骤

### 方式一：通过 IDEA 界面安装
1. 打开 **Settings/Preferences** (Ctrl+Alt+S)
2. 选择 **Plugins**
3. 搜索插件名称
4. 点击 **Install**
5. 重启 IDEA

### 方式二：批量安装（推荐）
在 IDEA 中打开 Terminal，执行以下命令（需要配置 IDEA 命令行工具）：

```bash
# 注：需要先配置 idea 命令行工具
idea installPlugins Lombook MyBatisX "Spring Boot Assistant" SonarLint GitToolBox
```

---

## ✅ 安装后配置

### Lombok 配置
1. Settings → Build, Execution, Deployment → Compiler → Annotation Processors
2. 勾选 **Enable annotation processing**
3. 重启 IDEA

### MyBatisX 配置
1. Settings → Tools → MyBatisX
2. 配置 Mapper XML 路径（默认 `resources/mapper/*.xml`）

### SonarLint 配置
1. Settings → Tools → SonarLint
2. 绑定 SonarQube/SonarCloud（可选）
3. 配置规则集

---

## 📌 插件优先级建议

### 第一批（立即安装）
- ✅ Lombok
- ✅ MyBatisX
- ✅ Spring Boot Assistant
- ✅ SonarLint
- ✅ GitToolBox

### 第二批（按需安装）
- Rainbow Brackets
- String Manipulation
- Translation
- RestfulTool
- Grep Console

### 第三批（可选）
- Conventional Commit
- GenerateAllSetter
- Key Promoter X
- Redis

---

## ⚠️ 注意事项

1. **性能影响**：插件过多会影响 IDEA 启动速度，建议只安装必要插件
2. **版本兼容**：确保插件支持 IDEA 版本（检查插件页面说明）
3. **定期更新**：定期更新插件到最新版本
4. **冲突检查**：某些插件功能可能重叠，安装前检查是否冲突

---

## 🔗 相关链接

- [JetBrains Plugins Marketplace](https://plugins.jetbrains.com/)
- [MyBatis-Plus 官方文档](https://baomidou.com/)
- [Kotlin 官方文档](https://kotlinlang.org/docs/home.html)
- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)

---

**最后更新**：2026-01-09  
**适用 IDEA 版本**：2023.3+  
**项目版本**：WXXServer 0.0.1-SNAPSHOT

