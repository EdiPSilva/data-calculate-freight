drop table calculation_type_range_freight;
drop table freight_rage;

create table freight_route (
	id					serial,
	range_freight_id	integer,
	region_id			integer,
	start_postal_code	varchar(8),
	end_postal_code		varchar(8),
	delivery_days		integer not null default 0,
	devolution_days		integer not null default 0,
	active				boolean default false,
	date_create			timestamp default current_date,
	date_update			timestamp,
	constraint freight_route_id_pk primary key (id),
	constraint range_freight_id_in_freight_route_fk foreign key (range_freight_id) references range_freight (id),
	constraint region_id_in_freight_route_fk foreign key (region_id) references region (id)
);
comment on column freight_route.id is 'freight_route_id_pk';
comment on column freight_route.range_freight_id is 'range_freight_id_in_freight_route_fk';
comment on column freight_route.region_id is 'region_id_in_freight_route_fk';
comment on column freight_route.start_postal_code is 'cep inicial';
comment on column freight_route.end_postal_code is 'cep final';
comment on column freight_route.delivery_days is 'quantidade de dias de entrega';
comment on column freight_route.devolution_days is 'quantidade de dias de devolucao';
comment on column freight_route.active is 'status';
comment on column freight_route.date_create is 'data de cadastro';
comment on column freight_route.date_update is 'data de atualizacao';

create table calculation_type_range_freight (
	id					serial,
	calculation_type	varchar(20),
	range_freight_id	integer,
	freight_route_id	integer,
	type_delivery_id	integer,
	date_create			timestamp default current_date,
	constraint calculation_type_range_freight_id_pk primary key (id),
	constraint range_freight_id_in_calculation_type_range_freight_fk foreign key (range_freight_id) references range_freight (id),
	constraint freight_route_id_in_calculation_type_range_freight_fk foreign key (freight_route_id) references freight_route (id),
	constraint type_delivery_id_in_calculation_type_range_freight_fk foreign key (type_delivery_id) references type_delivery (id)
);
comment on column calculation_type_range_freight.id is 'calculation_type_range_freight_id_pk';
comment on column calculation_type_range_freight.calculation_type is 'tipo do calculo (peso/cubagem)';
comment on column calculation_type_range_freight.range_freight_id is 'range_freight_id_in_calculation_type_range_freight_fk';
comment on column calculation_type_range_freight.freight_route_id is 'freight_route_id_in_calculation_type_range_freight_fk';
comment on column calculation_type_range_freight.freight_route_id is 'type_delivery_id_in_calculation_type_range_freight_fk';
comment on column calculation_type_range_freight.date_create is 'data de cadastro';