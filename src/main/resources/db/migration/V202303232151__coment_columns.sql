comment on column company.id is 'company_id_pk';
comment on column company.name is 'nome da empresa';
comment on column company.document is 'cnpj';
comment on column company.postal_code is 'cep';
comment on column company.active is 'status';
comment on column company.date_create is 'data de cadastro';
comment on column company.date_update is 'data de atualizacao';

comment on column shipping_company.id is 'shipping_company_id_pk';
comment on column shipping_company.name is 'nome da transportadora';
comment on column shipping_company.document is 'cnpj';
comment on column shipping_company.active is 'status';
comment on column shipping_company.date_create is 'data de cadastro';
comment on column shipping_company.date_update is 'data de atualizacao';

comment on column type_delivery.id is 'type_delivery_id_pk';
comment on column type_delivery.type is 'tipo de entrega';
comment on column type_delivery.active is 'status';
comment on column type_delivery.date_create is 'data de cadastro';
comment on column type_delivery.date_update is 'data de atualizacao';

comment on column region.id is 'region_id_pk';
comment on column region.state is 'uf';
comment on column region.date_create is 'data de cadastro';
comment on column region.date_update is 'data de atualizacao';