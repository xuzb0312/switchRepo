-- ===============================================
-- 游戏分享平台 数据库初始化脚本
-- ===============================================

USE game_share;

-- ===============================================
-- 用户表（微信小程序用户）
-- ===============================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `openid` VARCHAR(100) NOT NULL UNIQUE COMMENT '微信openid',
  `nickname` VARCHAR(100) DEFAULT '匿名用户' COMMENT '昵称',
  `avatar` VARCHAR(500) DEFAULT '' COMMENT '头像URL',
  `role` TINYINT DEFAULT 1 COMMENT '角色: 1普通用户 99管理员',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ===============================================
-- 游戏表
-- ===============================================
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL COMMENT '游戏名称',
  `platform` VARCHAR(50) NOT NULL COMMENT '平台: switch/ps5/ps4/xbox/pc/tool',
  `cover_image` VARCHAR(500) DEFAULT '' COMMENT '封面图URL',
  `description` TEXT COMMENT '游戏简介',
  `type` VARCHAR(50) DEFAULT '' COMMENT '类型: NSZ/pkg/XBOX等',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0待审核 1已发布',
  `view_count` INT DEFAULT 0 COMMENT '浏览次数',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_platform` (`platform`),
  INDEX `idx_name` (`name`),
  INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏表';

-- ===============================================
-- 下载链接表（网盘链接）
-- ===============================================
DROP TABLE IF EXISTS `download_link`;
CREATE TABLE `download_link` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `game_id` BIGINT NOT NULL COMMENT '关联游戏ID',
  `type` VARCHAR(50) NOT NULL COMMENT '链接类型: aliyun/baidu/onedrive/magnet等',
  `name` VARCHAR(255) NOT NULL COMMENT '链接名称/备注',
  `url` VARCHAR(1000) NOT NULL COMMENT '下载链接',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_game_id` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='下载链接表';

-- ===============================================
-- 游戏截图表
-- ===============================================
DROP TABLE IF EXISTS `game_image`;
CREATE TABLE `game_image` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `game_id` BIGINT NOT NULL COMMENT '关联游戏ID',
  `url` VARCHAR(500) NOT NULL COMMENT '图片URL',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_game_id` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏截图表';

-- ===============================================
-- Banner轮播图表
-- ===============================================
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(255) NOT NULL COMMENT '标题',
  `subtitle` VARCHAR(255) DEFAULT '' COMMENT '副标题',
  `image` VARCHAR(500) NOT NULL COMMENT '图片URL',
  `link_type` VARCHAR(20) DEFAULT 'game' COMMENT '跳转类型: game/none',
  `link_value` VARCHAR(255) DEFAULT '' COMMENT '跳转值: 游戏ID或URL',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0禁用 1启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_status_sort` (`status`, `sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Banner表';

-- ===============================================
-- 用户解锁记录表（广告解锁）
-- ===============================================
DROP TABLE IF EXISTS `unlock_record`;
CREATE TABLE `unlock_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `game_id` BIGINT NOT NULL COMMENT '游戏ID',
  `ad_type` VARCHAR(20) DEFAULT 'rewarded' COMMENT '广告类型: rewarded/interstitial',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_user_game` (`user_id`, `game_id`),
  UNIQUE KEY `uk_user_game` (`user_id`, `game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='解锁记录表';

-- ===============================================
-- 管理员表
-- ===============================================
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(100) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码(加密)',
  `nickname` VARCHAR(100) DEFAULT '' COMMENT '昵称',
  `role` VARCHAR(20) DEFAULT 'admin' COMMENT '角色: admin/superadmin',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- ===============================================
-- 初始管理员账号: admin / admin123
-- ===============================================
INSERT INTO `admin` (`username`, `password`, `nickname`, `role`) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', 'superadmin');

-- ===============================================
-- 插入测试Banner
-- ===============================================
INSERT INTO `banner` (`title`, `subtitle`, `image`, `link_type`, `link_value`, `sort`) VALUES
('宝可梦 朱', 'Switch中文版', 'https://picsum.photos/750/400?random=1', 'game', '1', 1),
('塞尔达传说：王国之泪', 'Switch官方中文', 'https://picsum.photos/750/400?random=2', 'game', '2', 2),
('霍格沃茨之遗', 'PS5中文版 含DLC', 'https://picsum.photos/750/400?random=3', 'game', '3', 3);

-- ===============================================
-- 插入测试游戏数据
-- ===============================================
INSERT INTO `game` (`name`, `platform`, `cover_image`, `description`, `type`, `status`) VALUES
('宝可梦 朱', 'Switch', 'https://picsum.photos/400/400?random=10', '宝可梦 朱 中文版 包含1.0.1补丁', 'NSZ', 1),
('塞尔达传说：王国之泪', 'Switch', 'https://picsum.photos/400/400?random=11', '官方中文 NSZ版 包含全部DLC', 'NSZ', 1),
('霍格沃茨之遗', 'PS5', 'https://picsum.photos/400/400?random=12', 'PS5中文版 含全部DLC 整合版', 'pkg', 1),
('生化危机4 重制版', 'PS5', 'https://picsum.photos/400/400?random=13', 'PS5 高清重制版 中文', 'pkg', 1),
('铁拳8', 'PS5', 'https://picsum.photos/400/400?random=14', 'PS5 格斗游戏 中文', 'pkg', 1),
('最终幻想16', 'PS5', 'https://picsum.photos/400/400?random=15', 'PS5 中文版', 'pkg', 1),
('星露谷物语', 'Switch', 'https://picsum.photos/400/400?random=16', '温馨农场经营游戏 中文', 'NSZ', 1),
('奥日与精灵意志', 'Xbox', 'https://picsum.photos/400/400?random=17', 'Xbox中文版', 'XBOX', 1);

-- ===============================================
-- 为测试游戏添加下载链接
-- ===============================================
INSERT INTO `download_link` (`game_id`, `type`, `name`, `url`, `sort`) VALUES
(1, '阿里云盘', '高速下载', 'https://www.aliyundrive.com/s/xxx1', 1),
(1, '磁力链', '教育网盘', 'magnet:?xt=urn:btih:xxx1', 2),
(2, '阿里云盘', '百度教育分流', 'https://www.aliyundrive.com/s/xxx2', 1),
(3, '百度网盘', 'PS5资源站', 'https://pan.baidu.com/s/xxx3', 1),
(3, '阿里云盘', '备用分流', 'https://www.aliyundrive.com/s/xxx3b', 2),
(4, '阿里云盘', '高速下载', 'https://www.aliyundrive.com/s/xxx4', 1);

-- ===============================================
-- 为测试游戏添加截图
-- ===============================================
INSERT INTO `game_image` (`game_id`, `url`, `sort`) VALUES
(1, 'https://picsum.photos/600/400?random=20', 1),
(1, 'https://picsum.photos/600/400?random=21', 2),
(2, 'https://picsum.photos/600/400?random=22', 1),
(2, 'https://picsum.photos/600/400?random=23', 2),
(2, 'https://picsum.photos/600/400?random=24', 3);
