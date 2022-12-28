DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS article;

CREATE TABLE member
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    account       VARCHAR(255) UNIQUE   NULL,
    password      VARCHAR(255)          NULL,
    nickname      VARCHAR(255)          NULL,
    name          VARCHAR(255)          NULL,
    email         VARCHAR(255)          NULL,
    refresh_token VARCHAR(100)          NULL,
    enabled       TINYINT(1)            NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
);

CREATE TABLE authority
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(255)          NULL,
    member_id BIGINT                NULL,
    CONSTRAINT pk_authority PRIMARY KEY (id)
);

CREATE TABLE board
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    create_date   datetime              NULL,
    update_date   datetime              NULL,
    creator_id    BIGINT                NULL,
    modifier_id   BIGINT                NULL,
    enabled       TINYINT(1)            NOT NULL,
    CONSTRAINT pk_board PRIMARY KEY (id)
);

CREATE TABLE article
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    hits          INT                   NULL,
    board_id      BIGINT                NULL,
    create_date   datetime              NULL,
    update_date   datetime              NULL,
    creator_id    BIGINT                NULL,
    modifier_id   BIGINT                NULL,
    enabled       TINYINT(1)            NOT NULL,
    CONSTRAINT pk_article PRIMARY KEY (id)
);