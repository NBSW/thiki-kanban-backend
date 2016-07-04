-- ----------------------------
-- Table structure for kb_user_profile
-- ----------------------------
DROP TABLE IF EXISTS kb_user_profile;
CREATE TABLE kb_user_profile
(
  id                VARCHAR(40) NOT NULL
  COMMENT '编号',
  email             VARCHAR(200) COMMENT '邮箱',
  name              VARCHAR(40),
  nick              VARCHAR(40),
  phone            VARCHAR(40),
  delete_status     INT(2)   DEFAULT 0,
  creation_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS kb_user_registration;
CREATE TABLE kb_user_registration
(
  id VARCHAR(40) NOT NULL,
  user_id VARCHAR(40) NOT NULL UNIQUE COMMENT "foreign key kb_user_profile.id",
  password varchar(63) NOT NULL,
  recovery_email varchar(127) NOT NULL,
  recovery_phone varchar(63) NOT NULL,
  status int default 0,
  delete_status int DEFAULT 0,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_board
-- ----------------------------
DROP TABLE IF EXISTS kb_board;
CREATE TABLE kb_board (
  id                VARCHAR(40) NOT NULL,
  name              TEXT,
  reporter          INT(2)      NOT NULL,
  delete_status     INT(2)               DEFAULT 0,
  order_number      INT(2)      NOT NULL DEFAULT 0,
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_entry
-- ----------------------------
DROP TABLE IF EXISTS kb_entry;
CREATE TABLE kb_entry (
  id                VARCHAR(40) NOT NULL,
  title             VARCHAR(50) NOT NULL,
  reporter          VARCHAR(40) NOT NULL,
  delete_status     INT                  DEFAULT 0,
  order_number      INT(2)      NOT NULL DEFAULT 0,
  board_id          VARCHAR(40)          DEFAULT NULL,
  creation_time     DATETIME             DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_task
-- ----------------------------
DROP TABLE IF EXISTS kb_task;
CREATE TABLE kb_task (
  id                VARCHAR(40)   NOT NULL,
  summary           VARCHAR(1023) NOT NULL,
  content           TEXT,
  order_number      INT(2)        NOT NULL  DEFAULT 0,
  reporter          VARCHAR(40)             DEFAULT NULL,
  entry_id          VARCHAR(40)             DEFAULT NULL,
  creation_time     DATETIME                DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)                  DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Table structure for kb_task_assignment
-- ----------------------------
DROP TABLE IF EXISTS kb_task_assignment;

CREATE TABLE kb_task_assignment (
  id                VARCHAR(40) NOT NULL,
  task_id           VARCHAR(40) NOT NULL,
  assigner          VARCHAR(40) NOT NULL,
  assignee          VARCHAR(40) NOT NULL,
  reporter          VARCHAR(40) DEFAULT NULL,
  creation_time     DATETIME    DEFAULT CURRENT_TIMESTAMP,
  modification_time DATETIME ON UPDATE CURRENT_TIMESTAMP,
  delete_status     INT(2)      DEFAULT 0,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
  
ALTER TABLE kb_user_registration
  ADD COLUMN name VARCHAR(40) DEFAULT NULL AFTER modification_time,
  ADD COLUMN salt VARCHAR(255) DEFAULT NULL COMMENT '加密密码的盐' AFTER name;  
