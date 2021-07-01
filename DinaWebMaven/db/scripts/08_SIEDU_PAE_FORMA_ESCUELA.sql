CREATE TABLE SIEDU_PAE_FORMA_ESCUELA
  (
    frme_form           NUMBER (6)      NOT NULL ,
    frme_ude_fuerza     NUMBER (1)      NOT NULL ,
    frme_ude_escu       NUMBER          NOT NULL,
    frms_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    frms_fecha_crea     DATE 		    NOT NULL ,
	frms_maquina_crea   VARCHAR2 (100)  NOT NULL,	
	frms_ip_crea  		VARCHAR2 (100)	NOT NULL,
    frms_usu_mod        VARCHAR2 (30),	
    frms_fecha_mod      DATE,
    frms_maquina_mod    VARCHAR2 (100),
	frms_ip_mod    		VARCHAR2 (100)
  ) ;
  
ALTER TABLE SIEDU_PAE_FORMA_ESCUELA ADD CONSTRAINT SIEDU_PAE_FORMACION_ESCUELA_PK PRIMARY KEY ( frme_form, frme_ude_fuerza, frme_ude_escu ) ;
ALTER TABLE SIEDU_PAE_FORMA_ESCUELA ADD CONSTRAINT SIEDU_FORM_ESCLA_UNID_DEPEN_FK FOREIGN KEY ( frme_ude_fuerza, frme_ude_escu ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_PAE_FORMA_ESCUELA ADD CONSTRAINT SIEDU_FORM_ESCUELAS_FORM_FK FOREIGN KEY ( frme_form ) REFERENCES SIEDU_PAE_FORMACION ( form_form ) ;
  
COMMENT ON TABLE SIEDU_PAE_FORMA_ESCUELA
IS
  'Tabla de registro de definición de Escuelas asociadas al registro de PAE Formación' ;
COMMENT ON COLUMN SIEDU_PAE_FORMA_ESCUELA.frme_form
IS
  'Identificador del PAE Formación al cual se asocia la Escuela' ;
COMMENT ON COLUMN SIEDU_PAE_FORMA_ESCUELA.frme_ude_fuerza
IS
  'Identificador de Fuerza' ;
COMMENT ON COLUMN SIEDU_PAE_FORMA_ESCUELA.frme_ude_escu
IS
  'Identificador de la Escuela. Unidades_Dependencia (ESCUELA)' ;

