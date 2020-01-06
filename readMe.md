1. 需要添加数据库表：
```sql
-- auto-generated definition
create table t_role
(
    id        int auto_increment
        primary key,
    note      varchar(20) null,
    role_name varchar(20) null
);
```

2. URL : http://localhost:8080/my/getRole?id=1