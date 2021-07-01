CREATE TABLE SIEDU_ENTIDAD
  (
    enti_enti       	NUMBER NOT NULL ,
    enti_nombre     	VARCHAR2 (100) NOT NULL ,
    enti_dom_tipo   	NUMBER (4) NOT NULL ,
    enti_luggeo     	NUMBER (6) NOT NULL ,
    enti_dom_sector 	NUMBER (4) NOT NULL ,
    enti_direccion  	VARCHAR2 (100) ,
    enti_telefono   	NUMBER (20) ,
    enti_page_web   	VARCHAR2 (50) ,
    enti_email      	VARCHAR2 (50),
    enti_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    enti_fecha_crea     DATE 		    NOT NULL ,
	enti_maquina_crea   VARCHAR2 (100)  NOT NULL,
	enti_ip_crea    	VARCHAR2 (100)	NOT NULL,
    enti_usu_mod        VARCHAR2 (30),	
    enti_fecha_mod      DATE,
    enti_maquina_mod    VARCHAR2 (100),
	enti_ip_mod    		VARCHAR2 (100)		
  );
  
COMMENT ON TABLE SIEDU_ENTIDAD
IS
  'Tabla de Entidades' ;
  
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_enti
IS
  'Primary Key de la tabla entidades.' ;
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_nombre
IS
  'Nombre de la entidad.' ;
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_dom_tipo
IS
  'Si es empresa, entidad educativa, otros. (DOMINIO TIPO_ENTIDAD)' ;
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_luggeo
IS
  'Donde se encuentra ubicada la entidad geograficamente.' ;
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_dom_sector
IS
  'Si la entidad es oficial, privado, mixto. (DOMINIO SECTOR_ENTIDAD)' ;
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_direccion
IS
  'Dirección de la Entidad' ;
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_telefono
IS
  'Teléfono de la Entidad' ;
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_page_web
IS
  'Sitio web de la entidad' ;
COMMENT ON COLUMN SIEDU_ENTIDAD.enti_email
IS
  'Email corporativo de la entidad' ;

ALTER TABLE SIEDU_ENTIDAD ADD CONSTRAINT ENTIDADES_PK PRIMARY KEY ( enti_enti ) USING INDEX;
ALTER TABLE SIEDU_ENTIDAD ADD CONSTRAINT ENTIDAD_UK UNIQUE ( enti_nombre , enti_dom_tipo , enti_dom_sector ) USING INDEX;

create sequence siedu_entidad_seq;
