create table if not exists developers
(
    id       bigint generated always as identity primary key,
    name     varchar(30) not null,
    username varchar(30) unique constraint valid_username
        check (translate(username, 'abcdefghijklmnopqrstuvwxyz_', '') = '' ),
    salary   int
);

create table if not exists skills
(
    id       bigint generated always as identity primary key,
    language varchar(30) not null,
    level    varchar(30) not null
);

create table if not exists projects
(
    id      bigint generated always as identity primary key,
    name    varchar(100) not null,
    git_url varchar(100) unique not null,
    cost    int,
    date    bigint not null
);

create table if not exists companies
(
    id      bigint generated always as identity primary key,
    name    varchar(50) unique not null,
    country varchar(30) not null
);

create table if not exists customers
(
    id    bigint generated always as identity primary key,
    name  varchar(30) not null,
    email varchar(30) unique not null
);

create table if not exists project_developer_relation
(
    id bigint generated always as identity primary key,
    project_id   bigint references projects(id),
    developer_id bigint references developers(id),
    unique (project_id, developer_id)
);

create table if not exists developer_skill_relation
(
    id bigint generated always as identity primary key,
    developer_id bigint references developers(id),
    skill_id     bigint references skills(id),
    unique (developer_id, skill_id)
);


