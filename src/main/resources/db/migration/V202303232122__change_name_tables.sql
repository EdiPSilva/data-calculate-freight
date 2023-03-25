drop table orders;
drop table calculation_shipping;
drop table calculation_type_range_shipping;
drop table shipping_rage;
drop table range_shipping;

create table range_freight (
	id					serial,
	shipping_company_id	integer,
	type_delivery_id	integer,
	start_value			numeric(5,2) not null default 0.0,
	end_value			numeric(5,2) not null default 0.0,
	freight_value		numeric(5,2) not null default 0.0,
	surplus_value		numeric(5,2) not null default 0.0,
	active				boolean default false,
	date_create			timestamp default current_date,
	date_update			timestamp,
	constraint range_freight_id_pk primary key (id),
	constraint shipping_company_id_in_range_freight_fk foreign key (shipping_company_id) references shipping_company (id)
);
comment on column range_freight.id is 'range_freight_id_pk';
comment on column range_freight.shipping_company_id is 'shipping_company_id_in_range_freight_fk';
comment on column range_freight.start_value is 'valor incial do range';
comment on column range_freight.end_value is 'valor final do range';
comment on column range_freight.freight_value is 'valor do frete';
comment on column range_freight.surplus_value is 'valor excedente';
comment on column range_freight.active is 'status';
comment on column range_freight.date_create is 'data de cadastro';
comment on column range_freight.date_update is 'data de atualizacao';

create table freight_rage (
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
	constraint freight_rage_id_pk primary key (id),
	constraint range_freight_id_in_freight_rage_fk foreign key (range_freight_id) references range_freight (id),
	constraint region_id_in_freight_rage_fk foreign key (region_id) references region (id)
);
comment on column freight_rage.id is 'freight_rage_id_pk';
comment on column freight_rage.range_freight_id is 'range_freight_id_in_freight_rage_fk';
comment on column freight_rage.region_id is 'region_id_in_freight_rage_fk';
comment on column freight_rage.start_postal_code is 'cep inicial';
comment on column freight_rage.end_postal_code is 'cep final';
comment on column freight_rage.delivery_days is 'quantidade de dias de entrega';
comment on column freight_rage.devolution_days is 'quantidade de dias de devolucao';
comment on column freight_rage.active is 'status';
comment on column freight_rage.date_create is 'data de cadastro';
comment on column freight_rage.date_update is 'data de atualizacao';

create table calculation_type_range_freight (
	id					serial,
	calculation_type	varchar(20),
	range_freight_id	integer,
	freight_rage_id	    integer,
	type_delivery_id	integer,
	date_create			timestamp default current_date,
	constraint calculation_type_range_freight_id_pk primary key (id),
	constraint range_freight_id_in_calculation_type_range_freight_fk foreign key (range_freight_id) references range_freight (id),
	constraint freight_rage_id_in_calculation_type_range_freight_fk foreign key (freight_rage_id) references freight_rage (id),
	constraint type_delivery_id_in_calculation_type_range_freight_fk foreign key (type_delivery_id) references type_delivery (id)
);
comment on column calculation_type_range_freight.id is 'calculation_type_range_freight_id_pk';
comment on column calculation_type_range_freight.calculation_type is 'tipo do calculo (peso/cubagem)';
comment on column calculation_type_range_freight.range_freight_id is 'range_freight_id_in_calculation_type_range_freight_fk';
comment on column calculation_type_range_freight.freight_rage_id is 'freight_rage_id_in_calculation_type_range_freight_fk';
comment on column calculation_type_range_freight.freight_rage_id is 'type_delivery_id_in_calculation_type_range_freight_fk';
comment on column calculation_type_range_freight.date_create is 'data de cadastro';

create table calculation_freight (
	id					serial,
	shipping_company_id	integer,
	sender_postal_code	varchar(8),
	destiny_postal_code	varchar(8),
	width				numeric(5,4) not null default 0.0,
	height				numeric(5,4) not null default 0.0,
	length				numeric(5,4) not null default 0.0,
	cubage				numeric(5,4) not null default 0.0,
	weight				numeric(5,4) not null default 0.0,
	freight_value		numeric(5,2) not null default 0.0,
	date_create			timestamp default current_date,
	date_update			timestamp,
	constraint calculation_freight_id_pk primary key (id),
	constraint shipping_company_id_in_calculation_shipping_fk foreign key (shipping_company_id) references shipping_company (id)
);
comment on column calculation_freight.id is 'calculation_freight_id_pk';
comment on column calculation_freight.shipping_company_id is 'shipping_company_id_in_calculation_shipping_fk';
comment on column calculation_freight.sender_postal_code is 'cep remetente';
comment on column calculation_freight.destiny_postal_code is 'cep destino';
comment on column calculation_freight.width is 'lagura';
comment on column calculation_freight.height is 'altura';
comment on column calculation_freight.length is 'comprimento';
comment on column calculation_freight.cubage is 'cubagem';
comment on column calculation_freight.weight is 'peso';
comment on column calculation_freight.freight_value is 'valor frete calculado';
comment on column calculation_freight.date_create is 'data de cadastro';
comment on column calculation_freight.date_update is 'data de atualizacao';

create table orders (
	id						serial,
	company_id				integer,
	calculation_freight_id integer,
	number					varchar(20) not null,
	date_create				timestamp default current_date,
	date_update				timestamp,
	constraint orders_id_pk primary key (id),
	constraint company_id_in_orders_fk foreign key (company_id) references company (id),
	constraint calculation_freight_id_in_orders_fk foreign key (calculation_freight_id) references calculation_freight (id)
);
comment on column orders.id is 'orders_id_pk';
comment on column orders.company_id is 'company_id_in_orders_fk';
comment on column orders.calculation_freight_id is 'calculation_freight_id_in_orders_fk';
comment on column orders.number is 'pedido';
comment on column orders.date_create is 'data de cadastro';
comment on column orders.date_update is 'data de atualizacao';