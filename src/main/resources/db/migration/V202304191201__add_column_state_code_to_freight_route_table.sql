alter table freight_route add column state_code varchar(2);
comment on column freight_route.state_code is 'Recebe o uf do estado, exemplo SP';