/*
VERSION NUEVA CREADA EL 07/04/2016 DONDE SE AGREGAN LOS CAMPOS: conv_nombre, conve_dom_conv, conve_enti, conv_vigente
SE ACTUALIZA LA UK CON LOS SIGUIENTES CAMPOS: (CONV_NOMBRE,CONVE_DOM_CONV,CONVE_ENTI,conv_fecha_ini)
SE CREA FK CONVENIO_ENTI foreign key (CONVE_ENTI) references SIEDU_ENTIDAD (ENTI_ENTI)
CAMBIO REALIZADO POR ANDRÉS ZAMORA. 
*/

CREATE TABLE SIEDU_CONVENIO
  (
    conv_conv          NUMBER (4) NOT NULL ,
    conv_nombre                 VARCHAR2(100) not null,    
    conv_descri        VARCHAR2 (500) NOT NULL ,
    conve_dom_conv NUMBER(4) not null,
    conve_enti          NUMBER not null,
    conv_fecha_ini     DATE ,
    conv_fecha_fin     DATE,
    conv_vigente             VARCHAR2(2) not null,
    conv_usu_crea      VARCHAR2 (30)   NOT NULL ,  
    conv_fecha_crea     DATE         NOT NULL ,
    conv_maquina_crea   VARCHAR2 (100)  NOT NULL,
    conv_ip_crea      VARCHAR2 (100)  NOT NULL,
    conv_usu_mod        VARCHAR2 (30),  
    conv_fecha_mod      DATE,
    conv_maquina_mod    VARCHAR2 (100),
    conv_ip_mod        VARCHAR2 (100)
  );
  
COMMENT ON COLUMN SIEDU_CONVENIO.conv_conv
IS
  'Secuencial Identificador del Convenio' ;
COMMENT ON COLUMN SIEDU_CONVENIO.conv_nombre
IS
  'Nombre del Convenio' ;
COMMENT ON COLUMN SIEDU_CONVENIO.conv_descri
IS
  'DescripciÃ³n del Convenio' ;
COMMENT ON COLUMN SIEDU_CONVENIO.conve_dom_conv
IS
  'Dominio del tipo del convenio' ;
COMMENT ON COLUMN SIEDU_CONVENIO.conve_enti
IS
  'Entidad con la cual esta suscrito el convenio' ;
COMMENT ON COLUMN SIEDU_CONVENIO.conv_fecha_ini
IS
  'Fecha de Inicio del Convenio' ;
COMMENT ON COLUMN SIEDU_CONVENIO.conv_fecha_fin
IS
  'Fecha Fin del Convenio' ;

ALTER TABLE SIEDU_CONVENIO ADD CONSTRAINT CONVENIO_PK PRIMARY KEY ( conv_conv ) USING INDEX;
ALTER TABLE SIEDU_CONVENIO ADD CONSTRAINT CONVENIO_UK UNIQUE (CONV_NOMBRE, CONVE_DOM_CONV, CONVE_ENTI, conv_fecha_ini ) USING INDEX;
alter table SIEDU_CONVENIO add constraint CONVENIO_ENTI foreign key (CONVE_ENTI) references SIEDU_ENTIDAD (ENTI_ENTI);

create sequence siedu_convenio_seq;
