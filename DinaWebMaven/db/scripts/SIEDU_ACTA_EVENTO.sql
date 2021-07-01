
create table siedu_acta_evento 
   (acta_acta               number(6)     not null, 
	acta_nro_acta           varchar2(10)    not null, 
	acta_fecha              date            not null, 
	acta_nro_libro          varchar2(10)    not null, 
	acta_nro_folio          varchar2(10)    not null, 
	acta_fecha_inicio       date            not null, 
	acta_fecha_fin          date            not null, 
	acta_fecha_grado        date            not null, 
	acta_evee               number(6)     not null, 
	acta_usu_crea           varchar2(30)    not null, 
	acta_fecha_crea         date            not null, 
	acta_maquina_crea       varchar2(100)   not null, 
	acta_ip_crea            varchar2(100)   not null, 
	acta_usu_mod            varchar2(30), 
	acta_fecha_mod          date, 
	acta_maquina_mod        varchar2(100), 
	acta_ip_mod             varchar2(100)
    );
    
    
 alter table siedu_acta_evento add constraint siedu_acta_evento_pk primary key (acta_acta) using index;
 alter table siedu_acta_evento add constraint siedu_acta_uk unique (acta_evee, acta_nro_acta, acta_fecha) using index;
 alter table siedu_acta_evento add constraint siedu_act_even_evee_fk foreign key (acta_evee) references siedu_evento_escuela (evee_evee);


comment on column siedu_acta_evento.acta_acta is            'Secuencial identificador del acta';
comment on column siedu_acta_evento.acta_nro_acta is        'Número del acta';
comment on column siedu_acta_evento.acta_fecha is           'Fecha del acta';
comment on column siedu_acta_evento.acta_nro_libro is       'Libro donde se encuentra radicada el acta';
comment on column siedu_acta_evento.acta_nro_folio is       'Número del folio donde se encuentra radicada el acta';
comment on column siedu_acta_evento.acta_fecha_inicio is    'Fecha de inicio de la capacitación que se certifica';
comment on column siedu_acta_evento.acta_fecha_fin is       'Fecha de finalización de la capacitación que se certifica';
comment on column siedu_acta_evento.acta_fecha_grado is     'Fecha de grado del evento certificado';
comment on column siedu_acta_evento.acta_evee is            'Evento al que pertenece el acta';
   
create sequence siedu_acta_evento_seq nocache;
   
