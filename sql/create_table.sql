# 数据库初始化
#
#

-- 创建库
create database if not exists library;

-- 切换库
use library;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment                  comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '姓名',
    birthday 	   date                                   null comment '生日',
    address      varchar(512)                           null comment '地址',
    phoneNumber  varchar(20)                            null comment '电话',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '用户' collate = utf8mb4_unicode_ci;

-- 图书表
create table if not exists book
(
    id             bigint auto_increment    comment 'id' PRIMARY KEY,
    title          varchar(256)             not null comment '书名',
    author         varchar(256)             null comment '作者',
    publisher      varchar(256)             null comment '出版社',
    isbn           varchar(20)              null comment 'ISBN编号',
    publishDate    date                     null comment '出版日期',
    price          decimal(10, 2)           null comment '价格',
    quantity       int default 0            not null comment '库存数量',
    category       varchar(256)             null comment '分类',
    isDelete       tinyint default 0        not null comment '是否删除'
) comment '图书' collate = utf8mb4_unicode_ci;

-- 借阅表
create table if not exists borrow
(
    id           bigint auto_increment                comment 'id' PRIMARY KEY,
    userId       bigint                               not null comment '读者ID',
    bookId       bigint                               not null comment '图书ID',
    borrowDate   datetime default CURRENT_TIMESTAMP   not null comment '借出日期',
    returnDate   datetime                             null comment '归还日期',
    isReturned   tinyint default 0                    not null comment '是否归还',
    isDelete     tinyint default 0                    not null comment '是否删除',
    index idx_userId (userId)
) comment '借阅记录' collate = utf8mb4_unicode_ci;

