CREATE TABLE SIEDU_CONSOLIDA_PAE
  (
    cpae_cpae 			NUMBER (6) NOT NULL ,
    cpae_nece 			NUMBER (6) NOT NULL ,
    cpae_capa 			NUMBER (6) ,
    cpae_form 			NUMBER (6),
    cpae_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    cpae_fecha_crea     DATE 		    NOT NULL ,
	cpae_maquina_crea   VARCHAR2 (100)  NOT NULL,	
	cpae_ip_crea  		VARCHAR2 (100)	NOT NULL,
    cpae_usu_mod        VARCHAR2 (30),	
    cpae_fecha_mod      DATE,
    cpae_maquina_mod    VARCHAR2 (100),
	cpae_ip_mod    		VARCHAR2 (100)		
  );

ALTER TABLE SIEDU_CONSOLIDA_PAE ADD CONSTRAINT SIEDU_CONSOLIDA_PAE_PK PRIMARY KEY ( cpae_cpae ) ;
ALTER TABLE SIEDU_CONSOLIDA_PAE ADD CONSTRAINT SIEDU_CONSOLIDA_NECESIDAD_FK FOREIGN KEY ( cpae_nece ) REFERENCES SIEDU_NECESIDAD ( nece_nece ) ;
ALTER TABLE SIEDU_CONSOLIDA_PAE ADD CONSTRAINT SIEDU_CONSOLIDA_CAPACITA_FK FOREIGN KEY ( cpae_capa ) REFERENCES SIEDU_PAE_CAPACITACION ( capa_capa ) ;
ALTER TABLE SIEDU_CONSOLIDA_PAE ADD CONSTRAINT SIEDU_CONSOLIDA_FORMACION_FK FOREIGN KEY ( cpae_form ) REFERENCES SIEDU_PAE_FORMACION ( form_form ) ;
  
ALTER TABLE SIEDU_CONSOLIDA_PAE ADD CONSTRAINT SIEDU_CONSOLIDA_PAE_TIPO_CK CHECK (( cpae_capa IS NULL AND cpae_form IS NOT NULL ) OR ( cpae_capa is not NULL AND cpae_form IS NULL )) ;

create sequence siedu_consolida_pae_seq nocache;  

COMMENT ON TABLE SIEDU_CONSOLIDA_PAE
IS
  'Almacena la relación de consolidación entre registros de necesidad en registros de Capacitación o Formación' ;
COMMENT ON COLUMN SIEDU_CONSOLIDA_PAE.cpae_cpae
IS
  'Secuencial identificador del registro' ;
COMMENT ON COLUMN SIEDU_CONSOLIDA_PAE.cpae_nece
IS
  'Identificador del registro de necesidad que participa en la consolidación' ;
COMMENT ON COLUMN SIEDU_CONSOLIDA_PAE.cpae_capa
IS
  'Identificador del registro consolidado de Capacitación' ;
COMMENT ON COLUMN SIEDU_CONSOLIDA_PAE.cpae_form
IS
  'Identificador del registro consolidado de Formación' ;
