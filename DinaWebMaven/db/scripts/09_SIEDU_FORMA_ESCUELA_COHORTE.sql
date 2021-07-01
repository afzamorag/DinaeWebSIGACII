CREATE TABLE SIEDU_FORMA_ESCUELA_COHORTE
  (
    freh_form         	NUMBER (6) 	NOT NULL ,
    freh_fuerza       	NUMBER (1) 	NOT NULL ,
    freh_escu         	NUMBER 		NOT NULL ,
    freh_coho         	NUMBER (2) 	NOT NULL ,
    freh_coho_consecu 	NUMBER (4) ,
    freh_fecha_ini    	DATE 		NOT NULL ,
    freh_fecha_fin    	DATE 		NOT NULL ,
    freh_nro_estu     	NUMBER (4) ,
    freh_dom_modal    	NUMBER (4),
    freh_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    freh_fecha_crea     DATE 		    NOT NULL ,
	freh_maquina_crea   VARCHAR2 (100)  NOT NULL,	
	freh_ip_crea  		VARCHAR2 (100)	NOT NULL,
    freh_usu_mod        VARCHAR2 (30),	
    freh_fecha_mod      DATE,
    freh_maquina_mod    VARCHAR2 (100),
	freh_ip_mod    		VARCHAR2 (100)	
  );
  
ALTER TABLE SIEDU_FORMA_ESCUELA_COHORTE ADD CONSTRAINT FORMACION_ESCUELA_COHORTE_PK PRIMARY KEY ( freh_coho, freh_fuerza, freh_escu, freh_form ) ;

ALTER TABLE SIEDU_FORMA_ESCUELA_COHORTE ADD CONSTRAINT ESCUELA_COHORTE_UK UNIQUE ( freh_form , freh_fuerza , freh_escu , freh_coho_consecu ) ;

ALTER TABLE SIEDU_FORMA_ESCUELA_COHORTE ADD CONSTRAINT FRM_ESCLA_COHRTE_FORM_ESCLA_FK FOREIGN KEY ( freh_form, freh_fuerza, freh_escu ) REFERENCES SIEDU_PAE_FORMA_ESCUELA ( frme_form, frme_ude_fuerza, frme_ude_escu ) ;

  
COMMENT ON TABLE SIEDU_FORMA_ESCUELA_COHORTE
IS
  'Definición de cohortes por Escuela para el registro de PAE Formación' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_form
IS
  'Identificador del registro de PAE Formación' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_fuerza
IS
  'Identificador de Fuerza' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_escu
IS
  'Identificador de la Escuela. Unidades_Dependencia (ESCUELA)' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_coho
IS
  'Secuencial identificador de la cohorte de la Escuela en el registro de PAE Formación.' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_coho_consecu
IS
  'Consecutivo para la cohorte en la escuela.  Único por Escuela.' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_fecha_ini
IS
  'Fecha de inicio de la cohorte' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_fecha_fin
IS
  'Fecha fin de la cohorte' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_nro_estu
IS
  'Número de participantes definido para la cohorte.' ;
COMMENT ON COLUMN SIEDU_FORMA_ESCUELA_COHORTE.freh_dom_modal
IS
  'Modalidad de la Cohorte de formación de la escuela. Modalidad ( DOMINIO METODOLOGIA )' ;
