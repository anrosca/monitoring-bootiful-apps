alter table if exists investment_records
    drop constraint if exists investment_records_fk_to_investment;
alter table if exists investments
    drop constraint if exists investments_fk_to_users;
alter table if exists users
    drop constraint if exists unique_user_name;
alter table if exists user_roles
    drop constraint if exists unique_user_role;
drop table if exists investment_records cascade;
drop table if exists investments cascade;
drop table if exists users cascade;

create table investment_records
(
    id              varchar(255)   not null,
    amount_bought   numeric(19, 8) not null,
    investment_date timestamp      not null,
    spent           numeric(19, 2) not null,
    symbol          varchar(10)    not null,
    unit_price      numeric(19, 8) not null,
    investment_id   varchar(255)   not null,
    created_at      timestamp      not null default (now() at time zone 'utc'),
    updated_at      timestamp      not null default (now() at time zone 'utc'),
    primary key (id)
);

create table investments
(
    id         varchar(255) not null,
    name       varchar(255) not null,
    user_id    varchar(255) not null,
    symbol     varchar(10)  not null,
    created_at timestamp    not null default (now() at time zone 'utc'),
    updated_at timestamp    not null default (now() at time zone 'utc'),
    primary key (id)
);

create table users
(
    id            varchar(255) not null,
    enabled       boolean      not null,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    password      varchar(255) not null,
    user_name     varchar(255) not null,
    email_address varchar(255) not null,
    created_at    timestamp    not null default (now() at time zone 'utc'),
    updated_at    timestamp    not null default (now() at time zone 'utc'),
    primary key (id)
);

create table user_roles
(
    id         varchar(255) not null,
    role_name  varchar(255) not null,
    user_id    varchar(255) not null,
    created_at timestamp    not null default (now() at time zone 'utc'),
    updated_at timestamp    not null default (now() at time zone 'utc'),
    primary key (id)
);

create unique index unique_user_roles on user_roles (id, role_name);

alter table if exists users
    add constraint unique_user_name unique (user_name);

alter table if exists users
    add constraint unique_user_email unique (email_address);

create index user_name_index on users (user_name);

alter table if exists investments
    add constraint unique_investment_name unique (name);

create index investment_user_id_index on investments (user_id);

alter table if exists investment_records
    add constraint investment_records_fk_to_investment
        foreign key (investment_id)
            references investments;

alter table if exists investments
    add constraint investments_fk_to_users
        foreign key (user_id)
            references users;
