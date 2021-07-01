CREATE TABLE SIEDU_PAE_FORMACION
  (
    form_form           NUMBER (6)      NOT NULL ,
    form_pae            NUMBER (6)      NOT NULL ,
    form_dom_proce      NUMBER (4) ,
    form_dom_estra      NUMBER (4) ,
    form_necesi         NUMBER (6) ,
    form_estado         VARCHAR2 (30)   NOT NULL,
    form_fuerza         NUMBER (1)      NOT NULL ,
    form_id_carrera     NUMBER          NOT NULL,
    form_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    form_fecha_crea     DATE 		    NOT NULL ,
	form_maquina_crea   VARCHAR2 (100)  NOT NULL,
	form_ip_crea    	VARCHAR2 (100)	NOT NULL,
    form_usu_mod        VARCHAR2 (30),	
    form_fecha_mod      DATE,
    form_maquina_mod    VARCHAR2 (100),	 
	form_ip_mod    		VARCHAR2 (100)
  ) ;
ALTER TABLE SIEDU_PAE_FORMACION ADD CONSTRAINT SIEDU_PAE_FORMACION_ESTADO_CK CHECK (form_estado IN ('PENDIENTE','APROBADO','NO APROBADO')) ;

ALTER TABLE SIEDU_PAE_FORMACION ADD CONSTRAINT SIEDU_PAE_FORMACION_PK PRIMARY KEY ( form_form ) ;
ALTER TABLE SIEDU_PAE_FORMACION ADD CONSTRAINT SIEDU_PAE_FORMACION_CARRERA_UK UNIQUE ( form_pae , form_fuerza , form_id_carrera, form_dom_proce, form_dom_estra ) ;
ALTER TABLE SIEDU_PAE_FORMACION ADD CONSTRAINT SIEDU_PAE_FORMACION_PAE_FK FOREIGN KEY ( form_pae ) REFERENCES SIEDU_PAE ( pae_pae ) ;
ALTER TABLE SIEDU_PAE_FORMACION ADD CONSTRAINT SIEDU_PAE_FORM_CARRERAS_FK FOREIGN KEY ( form_fuerza, form_id_carrera ) REFERENCES USR_EDUC.CARRERAS ( FUERZA, ID_CARRERA ) ;

create sequence siedu_formacion_seq;

COMMENT ON TABLE SIEDU_PAE_FORMACION
IS
  'Tabla de definición del PAE de Formación' ;
COMMENT ON COLUMN SIEDU_PAE_FORMACION.form_form
IS
  'Identificador del registro de PAE Formación' ;
COMMENT ON COLUMN SIEDU_PAE_FORMACION.form_pae
IS
  'Identificador del PAE al que corresponde el registro de Formación' ;
COMMENT ON COLUMN SIEDU_PAE_FORMACION.form_dom_proce
IS
  'Proceso de Formación - Dominio PROCESO' ;
COMMENT ON COLUMN SIEDU_PAE_FORMACION.form_dom_estra
IS
  'Estrategia de Formación ( Dominio ESTRATEGIA)' ;
COMMENT ON COLUMN SIEDU_PAE_FORMACION.form_necesi
IS
  'Número de cupos solicitados en la necesidad' ;
COMMENT ON COLUMN SIEDU_PAE_FORMACION.form_estado
IS  'Estado del registro de necesidad de formación. PENDIENTE, APROBADO, NO APROBADO' ;

COMMENT ON COLUMN SIEDU_PAE_FORMACION.form_fuerza
IS
  'Identificador de Fuerza' ;
COMMENT ON COLUMN SIEDU_PAE_FORMACION.form_id_carrera
IS
  'Identificador de la Carrera.' ;
  

