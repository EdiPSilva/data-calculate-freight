create table country_states (
	id			serial,
	state		varchar(20),
	state_code  varchar(2),
	date_create	timestamp default current_date,
	date_update	timestamp,
	constraint country_states_id_pk primary key (id)
);