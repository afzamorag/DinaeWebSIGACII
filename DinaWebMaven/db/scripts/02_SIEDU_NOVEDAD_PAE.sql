CREATE TABLE SIEDU_NOVEDAD_PAE
  (
    nove_nove           NUMBER (4)      NOT NULL ,
    nove_pae            NUMBER (6)      NOT NULL ,
    nove_fecha          DATE            NOT NULL ,
    nove_procedi        VARCHAR2 (10)   NOT NULL ,
    nove_tpo_doc        VARCHAR2 (100)  NOT NULL ,
    nove_nro_doc        VARCHAR2 (30)   NOT NULL ,
    nove_fecha_doc      DATE NOT NULL ,
    nove_observa        VARCHAR2 (1000) ,
    nove_anexo_pdf 		NUMBER (8) , 
    nove_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    nove_fecha_crea     DATE 		    NOT NULL ,
	nove_maquina_crea   VARCHAR2 (100)  NOT NULL,	
	nove_ip_crea    	VARCHAR2 (100)	NOT NULL,
    nove_usu_mod        VARCHAR2 (30),	
    nove_fecha_mod      DATE,
    nove_maquina_mod    VARCHAR2 (100),	 
	nove_ip_mod    		VARCHAR2 (100)	
  ) ;
  
ALTER TABLE SIEDU_NOVEDAD_PAE ADD CONSTRAINT SIEDU_NOVEDAD_PAE_PK PRIMARY KEY ( nove_nove ) ;
ALTER TABLE SIEDU_NOVEDAD_PAE ADD CONSTRAINT SIEDU_NOVEDAD_PAE_FK FOREIGN KEY ( nove_pae ) REFERENCES SIEDU_PAE ( pae_pae ) ;
ALTER TABLE SIEDU_NOVEDAD_PAE ADD CONSTRAINT SIEDU_NOVEDAD_ARCHIVO_FK FOREIGN KEY ( nove_anexo_pdf ) REFERENCES SIEDU_ARCHIVO ( arch_id ) ;

create sequence siedu_novedad_seq;
  
COMMENT ON TABLE SIEDU_NOVEDAD_PAE
IS
  'Tabla de registro de operaciones del PAE' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_nove
IS
  'Identificador único del registro' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_pae
IS
  'Identificador del PAE al que corresponde la novedad' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_fecha
IS
  'Fecha de registro de la novedad' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_procedi
IS
  'Procedimiento asociado a la novedad' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_tpo_doc
IS
  'Tipo de documento asociado a la novedad' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_nro_doc
IS
  'Número de documento asociado a la novedad' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_fecha_doc
IS
  'Fecha del documento asociado a la novedad' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_observa
IS
  'Observaciones asociadas a la novedad' ;
COMMENT ON COLUMN SIEDU_NOVEDAD_PAE.nove_anexo_pdf
IS  'Archivo relacionado con la novedad' ;
