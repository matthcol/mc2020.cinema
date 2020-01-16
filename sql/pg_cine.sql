DROP SCHEMA cinema cascade;
DROP USER if exists cinema;

create user cinema LOGIN password 'password';
create schema cinema authorization cinema;

set role cinema;

\i pg_cine_ddl.sql;
\i pg_cine_data.sql;
\i pg_cine_views.sql;
\i pg_cine_plsql.sql;
\i pg_cine_data2.sql;
\i pg_cine_data3.sql;
