CREATE TABLE SIEDU_CIERRE_PAE
  (
    rpae_rpae          	NUMBER (6) NOT NULL ,
    rpae_nove          	NUMBER (4) NOT NULL ,
    rpae_capa          	NUMBER (6) NOT NULL ,
    rpae_capa_nro_nece 	NUMBER (4) NOT NULL ,
    rpae_form          	NUMBER (6) NOT NULL ,
    rpae_form_nro_nece 	NUMBER (4) NOT NULL,
    rpae_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    rpae_fecha_crea     DATE 		    NOT NULL ,
	rpae_maquina_crea   VARCHAR2 (100)  NOT NULL,	
	rpae_ip_crea  		VARCHAR2 (100)	NOT NULL,
    rpae_usu_mod        VARCHAR2 (30),	
    rpae_fecha_mod      DATE,
    rpae_maquina_mod    VARCHAR2 (100),
	rpae_ip_mod    		VARCHAR2 (100)		
  ) ;
  
ALTER TABLE SIEDU_CIERRE_PAE ADD CONSTRAINT SIEDU_CIERRE_PAE_PK PRIMARY KEY ( rpae_rpae ) ;
ALTER TABLE SIEDU_CIERRE_PAE ADD CONSTRAINT SIEDU_CIERRE_CAPACITA_FK FOREIGN KEY ( rpae_capa ) REFERENCES SIEDU_PAE_CAPACITACION ( capa_capa ) ;
ALTER TABLE SIEDU_CIERRE_PAE ADD CONSTRAINT SIEDU_CIERRE_FORMACION_FK FOREIGN KEY ( rpae_form ) REFERENCES SIEDU_PAE_FORMACION ( form_form ) ;
ALTER TABLE SIEDU_CIERRE_PAE ADD CONSTRAINT SIEDU_CIERRE_NOVEDAD_PAE_FK FOREIGN KEY ( rpae_nove ) REFERENCES SIEDU_NOVEDAD_PAE ( nove_nove ) ;
  
create sequence siedu_cierre_pae_seq;

COMMENT ON TABLE SIEDU_CIERRE_PAE
IS
  'Almacena los registros de necesidad consolidada por cada registro de Capacitación o Formación en cada cierre de PAE.' ;
COMMENT ON COLUMN SIEDU_CIERRE_PAE.rpae_rpae
IS
  'Secuencial identificador del registro' ;
COMMENT ON COLUMN SIEDU_CIERRE_PAE.rpae_nove
IS
  'Identificador del registro de novedad de cierre' ;
COMMENT ON COLUMN SIEDU_CIERRE_PAE.rpae_capa
IS
  'Identifica el registro de Capacitación para el cual se toma el número de estudiantes necesarios consolidados al momento del cierre.' ;
COMMENT ON COLUMN SIEDU_CIERRE_PAE.rpae_capa_nro_nece
IS
  'Número de estudiantes necesarios consolidados al momento del cierre para el Identificador del registro de Capacitación' ;
COMMENT ON COLUMN SIEDU_CIERRE_PAE.rpae_form
IS
  'Identifica el registro de Formación para el cual se toma el número de estudiantes consolidados necesarios al momento del cierre.' ;
COMMENT ON COLUMN SIEDU_CIERRE_PAE.rpae_form_nro_nece
IS
  'Número de estudiantes necesarios consolidados al momento del cierre para el Identificador del registro de Formación' ;
