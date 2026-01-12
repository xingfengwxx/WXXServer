```sql
-- ============================================
-- 第一步：创建数据库
-- ============================================
CREATE DATABASE IF NOT EXISTS `wxxserver`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

-- 验证数据库是否创建成功
SHOW DATABASES LIKE 'wxxserver';

-- ============================================
-- 第二步：创建用户并授权
-- ============================================

-- 删除已存在的用户（如果存在）
DROP USER IF EXISTS 'wxx'@'localhost';

-- 创建新用户（密码：admin）
CREATE USER 'wxx'@'localhost' IDENTIFIED BY 'admin';

-- 授予该用户对 wxxserver 数据库的所有权限
GRANT ALL PRIVILEGES ON `wxxserver`.* TO 'wxx'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;

-- 验证用户创建成功
SELECT user, host FROM mysql.user WHERE user = 'wxx';

-- ============================================
-- 第三步：切换到新数据库
-- ============================================
USE `wxxserver`;

-- 查看当前使用的数据库
SELECT DATABASE();

-- ============================================
-- 第四步：创建数据表
-- ============================================

-- 创建 user 表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 验证表是否创建成功
SHOW TABLES;

-- 查看表结构
DESC `user`;

-- ============================================
-- 第五步：插入测试数据（100条）
-- ============================================

INSERT INTO `user` (`username`, `email`) VALUES
('user001', 'user001@example.com'),
('user002', 'user002@example.com'),
('user003', 'user003@example.com'),
('user004', 'user004@example.com'),
('user005', 'user005@example.com'),
('user006', 'user006@example.com'),
('user007', 'user007@example.com'),
('user008', 'user008@example.com'),
('user009', 'user009@example.com'),
('user010', 'user010@example.com'),
('user011', 'user011@example.com'),
('user012', 'user012@example.com'),
('user013', 'user013@example.com'),
('user014', 'user014@example.com'),
('user015', 'user015@example.com'),
('user016', 'user016@example.com'),
('user017', 'user017@example.com'),
('user018', 'user018@example.com'),
('user019', 'user019@example.com'),
('user020', 'user020@example.com'),
('user021', 'user021@example.com'),
('user022', 'user022@example.com'),
('user023', 'user023@example.com'),
('user024', 'user024@example.com'),
('user025', 'user025@example.com'),
('user026', 'user026@example.com'),
('user027', 'user027@example.com'),
('user028', 'user028@example.com'),
('user029', 'user029@example.com'),
('user030', 'user030@example.com'),
('user031', 'user031@example.com'),
('user032', 'user032@example.com'),
('user033', 'user033@example.com'),
('user034', 'user034@example.com'),
('user035', 'user035@example.com'),
('user036', 'user036@example.com'),
('user037', 'user037@example.com'),
('user038', 'user038@example.com'),
('user039', 'user039@example.com'),
('user040', 'user040@example.com'),
('user041', 'user041@example.com'),
('user042', 'user042@example.com'),
('user043', 'user043@example.com'),
('user044', 'user044@example.com'),
('user045', 'user045@example.com'),
('user046', 'user046@example.com'),
('user047', 'user047@example.com'),
('user048', 'user048@example.com'),
('user049', 'user049@example.com'),
('user050', 'user050@example.com'),
('user051', 'user051@example.com'),
('user052', 'user052@example.com'),
('user053', 'user053@example.com'),
('user054', 'user054@example.com'),
('user055', 'user055@example.com'),
('user056', 'user056@example.com'),
('user057', 'user057@example.com'),
('user058', 'user058@example.com'),
('user059', 'user059@example.com'),
('user060', 'user060@example.com'),
('user061', 'user061@example.com'),
('user062', 'user062@example.com'),
('user063', 'user063@example.com'),
('user064', 'user064@example.com'),
('user065', 'user065@example.com'),
('user066', 'user066@example.com'),
('user067', 'user067@example.com'),
('user068', 'user068@example.com'),
('user069', 'user069@example.com'),
('user070', 'user070@example.com'),
('user071', 'user071@example.com'),
('user072', 'user072@example.com'),
('user073', 'user073@example.com'),
('user074', 'user074@example.com'),
('user075', 'user075@example.com'),
('user076', 'user076@example.com'),
('user077', 'user077@example.com'),
('user078', 'user078@example.com'),
('user079', 'user079@example.com'),
('user080', 'user080@example.com'),
('user081', 'user081@example.com'),
('user082', 'user082@example.com'),
('user083', 'user083@example.com'),
('user084', 'user084@example.com'),
('user085', 'user085@example.com'),
('user086', 'user086@example.com'),
('user087', 'user087@example.com'),
('user088', 'user088@example.com'),
('user089', 'user089@example.com'),
('user090', 'user090@example.com'),
('user091', 'user091@example.com'),
('user092', 'user092@example.com'),
('user093', 'user093@example.com'),
('user094', 'user094@example.com'),
('user095', 'user095@example.com'),
('user096', 'user096@example.com'),
('user097', 'user097@example.com'),
('user098', 'user098@example.com'),
('user099', 'user099@example.com'),
('user100', 'user100@example.com');

-- 验证插入结果
SELECT COUNT(*) AS total_users FROM `user`;

-- 查看前10条数据
SELECT * FROM `user` LIMIT 10;


```

