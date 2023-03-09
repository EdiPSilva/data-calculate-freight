create table company (
	id			serial,
	name		varchar(125),
	document	varchar(14),
	postal_code	varchar(14),
	active		boolean default false,
	date_create	timestamp default current_date,
	date_update	timestamp,
	constraint company_id_pk primary key (id)
);

create table shipping_company (
	id			serial,
	name		varchar(125),
	document	varchar(14),
	active		boolean default false,
	date_create timestamp default current_date,
	date_update timestamp,
	constraint shipping_company_id_pk primary key (id)
);

create table type_delivery (
	id			serial,
	type		varchar(125),
	active		boolean default false,
	date_create timestamp default current_date,
	date_update timestamp,
	constraint type_delivery_id_pk primary key (id)
);

create table region (
	id			serial,
	state		varchar(2),
	date_create	timestamp default current_date,
	date_update	timestamp,
	constraint region_id_pk primary key (id)
);

create table range_shipping (
	id					serial,
	shipping_company_id	integer,
	type_delivery_id	integer,
	start_value			numeric(5,2) not null default 0.0,
	end_value			numeric(5,2) not null default 0.0,
	shipping_value		numeric(5,2) not null default 0.0,
	surplus_value		numeric(5,2) not null default 0.0,
	active				boolean default false,
	date_create			timestamp default current_date,
	date_update			timestamp,
	constraint range_shipping_id_pk primary key (id),
	constraint shipping_company_id_in_range_shipping_fk foreign key (shipping_company_id) references shipping_company (id)
);

create table shipping_rage (
	id					serial,
	range_shipping_id	integer,
	region_id			integer,
	start_postal_code	varchar(8),
	end_postal_code		varchar(8),
	delivery_days		integer not null default 0,
	devolution_days		integer not null default 0,
	active				boolean default false,
	date_create			timestamp default current_date,
	date_update			timestamp,
	constraint shipping_rage_id_pk primary key (id),
	constraint range_shipping_id_in_shipping_rage_fk foreign key (range_shipping_id) references range_shipping (id),
	constraint region_id_in_shipping_rage_fk foreign key (region_id) references region (id)
);

create table calculation_type_range_shipping (
	id					serial,
	calculation_type	varchar(20),
	range_shipping_id	integer,
	shipping_rage_id	integer,
	date_create			timestamp default current_date,
	constraint calculation_type_range_shipping_id_pk primary key (id),
	constraint range_shipping_id_in_calculation_type_range_shipping_fk foreign key (range_shipping_id) references range_shipping (id),
	constraint shipping_rage_id_in_calculation_type_range_shipping_fk foreign key (shipping_rage_id) references shipping_rage (id)
);

create table calculation_shipping (
	id					serial,
	shipping_company_id	integer,
	sender_postal_code	varchar(8),
	destiny_postal_code	varchar(8),
	width				numeric(5,4) not null default 0.0,
	height				numeric(5,4) not null default 0.0,
	length				numeric(5,4) not null default 0.0,
	cubage				numeric(5,4) not null default 0.0,
	weight				numeric(5,4) not null default 0.0,
	shipping_value		numeric(5,2) not null default 0.0,
	date_create			timestamp default current_date,
	date_update			timestamp,
	constraint calculation_shipping_id_pk primary key (id),
	constraint shipping_company_id_in_calculation_shipping_fk foreign key (shipping_company_id) references shipping_company (id)
);

create table orders (
	id						serial,
	company_id				integer,
	calculation_shipping_id integer,
	number					varchar(20) not null,
	date_create				timestamp default current_date,
	date_update				timestamp,
	constraint orders_id_pk primary key (id),
	constraint company_id_in_orders_fk foreign key (company_id) references company (id),
	constraint calculation_shipping_id_in_orders_fk foreign key (calculation_shipping_id) references calculation_shipping (id)
);