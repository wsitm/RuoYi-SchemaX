

-- dim_jdbc_info definition

CREATE TABLE `dim_jdbc_info`
(
    `jdbc_id`      int(11) NOT NULL AUTO_INCREMENT COMMENT '驱动ID',
    `jdbc_name`    varchar(256) NOT NULL COMMENT '驱动名称',
    `jdbc_file`    varchar(512) NOT NULL COMMENT '驱动文件',
    `driver_class` varchar(256) NOT NULL COMMENT '驱动类名称',
    `create_by`    varchar(100) DEFAULT NULL COMMENT '创建用户',
    `create_time`  timestamp NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`jdbc_id`),
    UNIQUE KEY `dim_jdbc_info_unique` (`driver_class`)
) ENGINE=InnoDB COMMENT='驱动管理表';


-- dim_connect_info definition

CREATE TABLE `dim_connect_info`
(
    `connect_id`   int(11) NOT NULL AUTO_INCREMENT COMMENT '连接ID',
    `connect_name` varchar(200)  NOT NULL COMMENT '连接名称',
    `jdbc_id`      int(11) NOT NULL COMMENT '驱动ID',
    `jdbc_url`     varchar(2048) NOT NULL COMMENT 'JDBC URL',
    `username`     varchar(256)  NOT NULL COMMENT '用户',
    `password`     varchar(256)  NOT NULL COMMENT '密码',
    `create_by`    varchar(100) DEFAULT NULL COMMENT '创建用户',
    `create_time`  timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`    varchar(100) DEFAULT NULL COMMENT '修改用户',
    `update_time`  timestamp NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`connect_id`)
) ENGINE=InnoDB COMMENT='连接配置表';


INSERT INTO dim_jdbc_info
(jdbc_id, jdbc_name, jdbc_file, driver_class, create_by, create_time)
VALUES(1, 'oracle-jdbc', 'ojdbc8-23.6.0.24.10.jar', 'oracle.jdbc.OracleDriver', 'admin', '2025-01-11 20:21:04');
INSERT INTO dim_jdbc_info
(jdbc_id, jdbc_name, jdbc_file, driver_class, create_by, create_time)
VALUES(2, 'dameng-jdbc', 'DmJdbcDriver18-8.1.1.193.jar', 'dm.jdbc.driver.DmDriver', 'admin', '2025-01-12 14:41:15');
INSERT INTO dim_jdbc_info
(jdbc_id, jdbc_name, jdbc_file, driver_class, create_by, create_time)
VALUES(3, 'mysql8-jdbc', 'mysql-connector-j-8.2.0.jar', 'com.mysql.cj.jdbc.Driver', 'admin', '2025-01-19 15:58:01');
INSERT INTO dim_jdbc_info
(jdbc_id, jdbc_name, jdbc_file, driver_class, create_by, create_time)
VALUES(4, 'opengauss-jdbc', 'opengauss-jdbc-5.0.0-og.jar', 'org.opengauss.Driver', 'admin', '2025-02-05 09:53:11');
INSERT INTO dim_jdbc_info
(jdbc_id, jdbc_name, jdbc_file, driver_class, create_by, create_time)
VALUES(5, 'postgres-jdbc', 'postgresql-42.7.4.jar', 'org.postgresql.Driver', 'admin', '2025-02-05 09:56:14');
INSERT INTO dim_jdbc_info
(jdbc_id, jdbc_name, jdbc_file, driver_class, create_by, create_time)
VALUES(6, 'hive2-jdbc', 'hive-jdbc-uber-2.6.5.0-292.jar', 'org.apache.hive.jdbc.HiveDriver', 'admin', '2025-03-02 00:56:14');



INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2000, '驱动管理', 2006, 1, 'jdbc', 'rdbms/jdbc/index', NULL, '', 1, 0, 'C', '0', '0', 'rdbms:jdbc:list', 'dict', 'admin', '2025-01-11 12:22:36', 'admin', '2025-01-11 13:31:49', '驱动管理菜单');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2002, '驱动管理新增', 2000, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'rdbms:jdbc:add', '#', 'admin', '2025-01-11 12:22:37', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2003, '驱动管理修改', 2000, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'rdbms:jdbc:edit', '#', 'admin', '2025-01-11 12:22:37', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2004, '驱动管理删除', 2000, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'rdbms:jdbc:remove', '#', 'admin', '2025-01-11 12:22:37', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2005, '驱动管理导出', 2000, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'rdbms:jdbc:export', '#', 'admin', '2025-01-11 12:22:37', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2006, '库表工具', 0, 0, 'rdbms', NULL, NULL, '', 1, 0, 'M', '0', '0', '', 'table', 'admin', '2025-01-11 13:31:15', 'admin', '2025-02-08 22:58:56', '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2007, '连接配置', 2006, 1, 'connect', 'rdbms/connect/index', NULL, '', 1, 0, 'C', '0', '0', 'rdbms:connect:list', 'swagger', 'admin', '2025-01-11 20:31:34', 'admin', '2025-01-28 22:13:43', '连接配置菜单');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2009, '连接配置新增', 2007, 2, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'rdbms:connect:add', '#', 'admin', '2025-01-11 20:31:34', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2010, '连接配置修改', 2007, 3, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'rdbms:connect:edit', '#', 'admin', '2025-01-11 20:31:34', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2011, '连接配置删除', 2007, 4, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'rdbms:connect:remove', '#', 'admin', '2025-01-11 20:31:34', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2012, '连接配置导出', 2007, 5, '#', '', NULL, '', 1, 0, 'F', '0', '0', 'rdbms:connect:export', '#', 'admin', '2025-01-11 20:31:34', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2013, 'DDL转换', 2006, 3, 'convert', 'rdbms/convert/index', NULL, '', 1, 0, 'C', '0', '0', 'rdbms:convert:list', 'row', 'admin', '2025-01-28 14:16:29', 'admin', '2025-01-28 14:17:03', '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2014, '驱动管理装卸', 2000, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'rdbms:jdbc:load', '#', 'admin', '2025-02-09 14:32:04', '', NULL, '');
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, `path`, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES(2015, '连接配置表结构导出', 2007, 6, '', NULL, NULL, '', 1, 0, 'F', '0', '0', 'rdbms:connect:info-export', '#', 'admin', '2025-02-09 14:33:51', 'admin', '2025-02-09 14:38:18', '');

