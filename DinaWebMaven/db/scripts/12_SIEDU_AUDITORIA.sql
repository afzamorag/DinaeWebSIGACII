CREATE TABLE SIEDU_AUDITORIA
  (
    audi_audi  		NUMBER (10) 	NOT NULL ,
    audi_fecha 		DATE 			NOT NULL ,
    audi_tabla 		VARCHAR2 (30) 	NOT NULL ,
    audi_oper  		VARCHAR2 (1) 	NOT NULL ,
    audi_regantes 	CLOB ,
    audi_regdespues CLOB ,
    audi_usuario 	VARCHAR2 (30) 	NOT NULL ,
    audi_maquina 	VARCHAR2 (100) ,
    audi_ip      	VARCHAR2 (50) 	NOT NULL
  );

ALTER TABLE SIEDU_AUDITORIA ADD CONSTRAINT SIEDU_AUDITORIA_PK PRIMARY KEY ( audi_audi ) ;  

create sequence siedu_auditoria_seq;
  
COMMENT ON TABLE SIEDU_AUDITORIA
IS
  'Tabla que almacena los registros de Auditoría generados por las tablas.' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_audi
IS
  'Secuencial Identificador del registro' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_fecha
IS
  'Fecha de creación del registro de Auditoría' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_tabla
IS
  'Nombre de la tabla correspondiente al registro de Auditoría' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_oper
IS
  'Identifica la operación orígen del registro de Auditoría' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_regantes
IS
  'Detalle del contenido de los campos antes de la operación auditada' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_regdespues
IS
  'Detalle del contenido de los campos antes de la operación auditada' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_usuario
IS
  'Usuario que realizó la operación auditada' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_maquina
IS
  'Nombre de la máquina desde la que se realizó la operación auditada' ;
COMMENT ON COLUMN SIEDU_AUDITORIA.audi_ip
IS
  'Dirección IP desde donde se realizó la operación auditada' ;

