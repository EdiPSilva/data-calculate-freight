alter table calculation_freight add column company_id integer;
alter table calculation_freight add constraint company_id_in_calculation_freight_fk foreign key (company_id) references company (id);