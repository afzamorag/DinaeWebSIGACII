CREATE TABLE SIEDU_COMPETENCIA
  (
    comp_comp   		NUMBER (4) NOT NULL ,
    comp_descri 		VARCHAR2 (200) NOT NULL,
    comp_dom_tpcompe    NUMBER(4) NOT NULL,
    comp_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    comp_fecha_crea     DATE 		    NOT NULL ,
	comp_maquina_crea   VARCHAR2 (100)  NOT NULL,
	comp_ip_crea    	VARCHAR2 (100)	NOT NULL,
    comp_usu_mod        VARCHAR2 (30),	
    comp_fecha_mod      DATE,
    comp_maquina_mod    VARCHAR2 (100),
	comp_ip_mod    		VARCHAR2 (100)
  );
  
COMMENT ON TABLE SIEDU_COMPETENCIA
IS  'Tabla de Competencias' ;

COMMENT ON COLUMN SIEDU_COMPETENCIA.comp_comp
IS  'Secuencial Identificador de la Competencia' ;

COMMENT ON COLUMN SIEDU_COMPETENCIA.comp_descri
IS  'Descripción de la Competencia' ;

COMMENT ON COLUMN SIEDU_COMPETENCIA.comp_dom_tpcompe
IS
  'Tipo de Competencia ( DOMINIO TIPO_COMPETENCIA )' ;
  
  
ALTER TABLE SIEDU_COMPETENCIA ADD CONSTRAINT SIEDU_COMPETENCIA_PK PRIMARY KEY ( comp_comp ) ;

create sequence siedu_competencia_seq;