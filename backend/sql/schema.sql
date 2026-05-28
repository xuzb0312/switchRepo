-- 游戏分享平台数据库初始化脚本

CREATE DATABASE IF NOT EXISTS game_share DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE game_share;

-- 游戏表
CREATE TABLE IF NOT EXISTS `game` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL COMMENT '游戏名称',
  `platform` VARCHAR(50) NOT NULL COMMENT '平台: switch/ps5/xbox/pc',
  `cover_image` VARCHAR(500) COMMENT '封面图URL',
  `description` TEXT COMMENT '游戏简介',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0待审核 1已发布',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_platform` (`platform`),
  INDEX `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏表';

-- 下载链接表
CREATE TABLE IF NOT EXISTS `download_link` (
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

-- 游戏截图表
CREATE TABLE IF NOT EXISTS `game_image` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `game_id` BIGINT NOT NULL,
  `url` VARCHAR(500) NOT NULL,
  `sort` INT DEFAULT 0,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_game_id` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏截图表';

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `openid` VARCHAR(100) NOT NULL UNIQUE COMMENT '微信openid',
  `nickname` VARCHAR(100) COMMENT '昵称',
  `avatar` VARCHAR(500) COMMENT '头像URL',
  `role` TINYINT DEFAULT 1 COMMENT '角色: 1普通用户 99管理员',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `deleted` TINYINT DEFAULT 0,
  INDEX `idx_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
