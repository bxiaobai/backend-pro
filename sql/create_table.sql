# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(9024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(9024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';


-- 字典表
create table if not exists sys_dict_data
(
    id         bigint auto_increment comment 'id' primary key,
    dictName   varchar(256)                       null comment '字典名称',
    dictType   varchar(255)                       null comment '字典类型',
    status     tinyint  default 0                 null comment '字典状态',
    userId     bigint                             not null comment '创建用户 id',
    editTime   datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_title (dictName)
) comment '字典表' collate = utf8mb4_unicode_ci;

-- 字典值表
create table if not exists sys_dict_type
(
    id         bigint auto_increment comment 'id' primary key,
    label      varchar(256)                       null comment '标签',
    value      varchar(255)                       null comment '键值',
    status     tinyint  default 0                 null comment '状态',
    dictType   varchar(255)                       not null comment '字典类型',
    userId     bigint                             not null comment '创建用户 id',
    editTime   datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_title (label)
) comment '字典值表' collate = utf8mb4_unicode_ci;


-- 科室表
create table if not exists sys_dept
(
    id         bigint auto_increment comment 'id' primary key,
    deptName   varchar(256)                       not null comment '科室名称',
    parentId   bigint                             not null comment '父级id',
    deptDesc   varchar(9024)                      null comment '科室描述',
    status     tinyint  default 0                 null comment '状态',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_title (deptName)
) comment '科室表' collate = utf8mb4_unicode_ci;


-- 输液室表
create table if not exists ir_room
(
    id            bigint auto_increment comment 'id' primary key,
    irName        varchar(256)                       not null comment '输液室名称',
    irDesc        varchar(9024)                      null comment '描述',
    status        tinyint  default 0                 not null comment '状态',
    #输液室发送短信模板
    smsTemplateId varchar(256)                       null comment '短信模板id',
    #输液室电话
    phone         varchar(256)                       null comment '电话',
    #取号点
    numberPoint   varchar(256)                       null comment '取号点',
    #输液地点
    irPlace       varchar(256)                       null comment '输液地点',
    #可接待患者类型
    patType       tinyint  default 0                 not null comment '可接待患者类型',
    #修改人id
    editUserId    bigint                             null comment '修改人id',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除',
    index idx_title (irName)
) comment '输液室表' collate = utf8mb4_unicode_ci;


-- 输液室座位表
create table if not exists ir_seat
(
    id         bigint auto_increment comment 'id' primary key,
    irId       bigint                             not null comment '输液室id',
    seatNumber varchar(256)                       not null comment '座位号',
    patType    tinyint  default 0                 not null comment '可接待患者类型',
    status     tinyint  default 0                 not null comment '座位状态',
    area       varchar(90)                        not null comment '区域',
    seatRow    tinyint                            not null comment '行号',
    seatCol    tinyint                            not null comment '列号',
    flag       varchar(90)                        not null comment '标签(vip(vip),common(普通),bed(床为))',
    editUserId bigint                             null comment '修改人id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_number (seatNumber),
    index idx_patType (patType),
    index idx_area (area)
) comment '输液室表' collate = utf8mb4_unicode_ci;

-- 号源配置表
create table if not exists appts_template
(
    id         bigint auto_increment comment 'id' primary key,
    irId       bigint                             not null comment '输液室id',
    #开始时间
    startTime  time                               not null comment '开始时间',
    #结束时间
    endTime    time                               not null comment '结束时间',
    #患者类型
    patType    tinyint  default 0                 not null comment '可接待患者类型',
    #正常号源总量
    normalNum  tinyint  default 0                 not null comment '正常号源总量',
    #临时号源总量
    tempNum    tinyint  default 0                 not null comment '临时号源总量',
    editUserId bigint                             null comment '修改人id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '号源配置表' collate = utf8mb4_unicode_ci;


-- 号源库
-- 日期--开始时间--结束时间--正常号源总量(可约数)--临时号源数量--已约数--输液室--患者类型（字段待定）
create table if not exists appts_source
(
    id         bigint auto_increment comment 'id' primary key,
    irId       bigint                             not null comment '输液室id',
    date       date                               not null comment '日期',
    startTime  time                               not null comment '开始时间',
    endTime    time                               not null comment '结束时间',
    #正常号源总量
    normalNum  tinyint  default 0                 not null comment '正常号源总量',
    #临时号源总量
    tempNum    tinyint  default 0                 not null comment '临时号源总量',
    #已约数
    appointNum tinyint  default 0                 not null comment '已约数',
    #患者类型
    type       tinyint  default 0                 not null comment '号源类型',
    editUserId bigint                             null comment '修改人id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '号源库' collate = utf8mb4_unicode_ci;


-- 预约表
-- 患者姓名、卡号、医嘱开具时间、预约日期、预约时间、座位类型、座位号、预约人、所用号源id、号源类型、状态（未分配座位、已分配座位、已完成、进行中）、操作
create table if not exists appts_details
(
    id         bigint auto_increment comment 'id' primary key,
    #患者姓名
    patName    varchar(10)                        not null comment '患者姓名',
    #卡号
    card       varchar(10)                        not null comment '卡号',
    irId       bigint                             not null comment '输液室id',
    date       date                               not null comment '日期',
    startTime  time                               not null comment '开始时间',
    endTime    time                               not null comment '结束时间',
    #座位类型
    seatType   varchar(10)                        not null comment '座位类型(VIP,common,Bed)',
    #座位号
    seatNum    varchar(10)                        null comment '座位号',
    #所用号源id
    sourceId   varchar(255)                       null comment '号源id',
    #号源类型
    type       tinyint  default 0                 not null comment '号源类型(0正常号源,1临时号源)',
    #状态
    status     tinyint  default 0                 not null comment '状态(0未分配座位,1已分配座位,2已完成,3进行中)',
    editUserId bigint                             null comment '修改人id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '号源库' collate = utf8mb4_unicode_ci;


-- 预约药品表
create table if not exists appts_drug
(
    id         bigint auto_increment comment 'id' primary key,
    detailsId  bigint                             not null comment '预约id',
    registerId varchar(255)                       null comment '流水号',
    irNos      text                               null comment '输液单号拼接',
    drugTime   varchar(255)                       null comment '药品创建时间',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '预约药品表' collate = utf8mb4_unicode_ci;

-- 药品子表
create table if not exists appts_drug_children
(
    id         bigint auto_increment comment 'id' primary key,
    Sjly       varchar(50)                            null,
    Cfsbh      varchar(255)                           null,
    Ds         text                                   null,
    Gg         varchar(255)                           null,
    Jl         varchar(100)                           null,
    Pd         varchar(20)                            null,
    Psxx       tinyint      default 0                 null,
    Ypmc       varchar(255)                           null,
    Z_id       int                                    null,
    cfls       varchar(50)                            null,
    kkcbz      tinyint      default 0                 null,
    fysycs     tinyint      default 0                 null,
    Dsybz      tinyint      default 0                 null,
    Sl         int                                    null,
    Psjg       int          default 0                 null,
    yf         tinyint                                null,
    ypid       varchar(255)                           null,
    ypcs       tinyint                                null,
    sydh       varchar(50)                            null,
    sycs       tinyint      default 0                 null,
    bzjl       varchar(255) default ''                null,
    yfms       varchar(50)                            null,
    kssbz      tinyint      default 0                 null,
    ylts       varchar(255) default ''                null,
    pcdm       tinyint                                null,
    yytj       varchar(20)                            null,
    kbbz       varchar(2)                             null,
    jpid       int          default 0                 null,
    ps         varchar(20)                            null,
    bz         varchar(50)  default ''                null,
    zby        tinyint(1)   default 0                 null,
    sfzt       tinyint      default 1                 null,
    Dose       varchar(50)                            null,
    Unit       varchar(50)                            null,
    sysc       int                                    null,
    drug_id    varchar(255)                           null comment '主表id',
    userId     bigint                                 not null comment '创建用户 id',
    createTime datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint      default 0                 not null comment '是否删除'
) comment '预约药品子表' collate = utf8mb4_unicode_ci;



CREATE TABLE med_pat
(
    id                       VARCHAR(255) PRIMARY KEY comment '主键',
    birthday                 DATE comment '出生日期',
    companyAddress           VARCHAR(255) comment '公司地址',
    companyName              VARCHAR(255) comment '公司名称',
    contactAddress           VARCHAR(255) comment '通讯地址',
    card                     varchar(255) comment '患者卡号',
    height                   INT comment '身高',
    marriageCode             INT comment '婚姻代码',
    name                     VARCHAR(255) comment '姓名',
    nameSpell                VARCHAR(255) comment '姓名拼音',
    registedResidenceAddress VARCHAR(255) comment '户籍所在地',
    sexCode                  INT comment '性别代码',
    weight                   INT comment '体重',
    displayAge               VARCHAR(255) comment '年龄',
    userId                   bigint                             not null comment '创建用户 id',
    createTime               datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime               datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete                 tinyint  default 0                 not null comment '是否删除'
);


