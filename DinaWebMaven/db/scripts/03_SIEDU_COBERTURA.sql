CREATE TABLE SIEDU_COBERTURA
  (
    cobe_cobe             NUMBER (8) 	  NOT NULL ,
    cobe_pae              NUMBER (6) 	  NOT NULL ,
    cobe_ude_fuerza_ufisi NUMBER (1) 	  NOT NULL ,
    cobe_ude_ufisi        NUMBER 	 	  NOT NULL ,
    cobe_dom_estra        NUMBER (4) 	  NOT NULL ,
    cobe_ude_fuerza_escu  NUMBER (1) 	  NOT NULL ,
    cobe_ude_escu         NUMBER 	 	  NOT NULL ,
    cobe_usu_crea    	  VARCHAR2 (30)   NOT NULL ,	
    cobe_fecha_crea       DATE 		      NOT NULL ,
	cobe_maquina_crea     VARCHAR2 (100)  NOT NULL,
	cobe_ip_crea    	  VARCHAR2 (100)  NOT NULL,
    cobe_usu_mod          VARCHAR2 (30),	
    cobe_fecha_mod        DATE,
    cobe_maquina_mod      VARCHAR2 (100), 
	cobe_ip_mod    		  VARCHAR2 (100)
  ) ;
  
ALTER TABLE SIEDU_COBERTURA ADD CONSTRAINT SIEDU_COBERTURA_PK PRIMARY KEY ( cobe_cobe ) ;
ALTER TABLE SIEDU_COBERTURA ADD CONSTRAINT SIEDU_COBERTURA_UK UNIQUE ( cobe_pae , cobe_ude_ufisi , cobe_ude_fuerza_ufisi , cobe_dom_estra ) ;
ALTER TABLE SIEDU_COBERTURA ADD CONSTRAINT SIEDU_COBER_UDE_ESCU_FK FOREIGN KEY ( cobe_ude_fuerza_escu, cobe_ude_escu ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_COBERTURA ADD CONSTRAINT SIEDU_COBER_UDE_UFISI_FK FOREIGN KEY ( cobe_ude_fuerza_ufisi, cobe_ude_ufisi ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;

create sequence siedu_cobertura_seq nocache;

COMMENT ON TABLE SIEDU_COBERTURA IS  'Tabla que indica las Unidades físicas y escuelas disponibles para la vigencia' ;
COMMENT ON COLUMN SIEDU_COBERTURA.cobe_cobe IS  'Secuencial identificador único del registro de cobertura' ;
COMMENT ON COLUMN SIEDU_COBERTURA.cobe_pae IS  'Secuencial identificador del PAE alque corresponde el registro de cobertura.' ;
COMMENT ON COLUMN SIEDU_COBERTURA.cobe_ude_fuerza_escu IS  'Identificador de Fuerza de la Escuela' ;
COMMENT ON COLUMN SIEDU_COBERTURA.cobe_ude_escu IS  'Identificador de Escuela' ;
COMMENT ON COLUMN SIEDU_COBERTURA.cobe_dom_estra IS  'Estrategia ( Dominio ESTRATEGIA)' ;
COMMENT ON COLUMN SIEDU_COBERTURA.cobe_ude_fuerza_ufisi IS 'identificador de Fuerza para la  Unidad Física' ;
COMMENT ON COLUMN SIEDU_COBERTURA.cobe_ude_ufisi IS  'Identificador de la Unidad Física' ;
