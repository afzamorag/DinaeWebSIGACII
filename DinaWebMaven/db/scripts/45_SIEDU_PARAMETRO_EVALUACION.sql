CREATE TABLE SIEDU_PARAMETRO_EVALUACION
  (
    peval_peval  NUMBER (8) NOT NULL ,
    peval_descri VARCHAR2 (200) NOT NULL,
    peval_usu_crea    	 VARCHAR2 (30)   NOT NULL ,	
    peval_fecha_crea      DATE 		     NOT NULL ,
	peval_maquina_crea    VARCHAR2 (100)  NOT NULL,
	peval_ip_crea    	 VARCHAR2 (100)	 NOT NULL,
    peval_usu_mod         VARCHAR2 (30),	
    peval_fecha_mod       DATE,
    peval_maquina_mod     VARCHAR2 (100),
	peval_ip_mod    		 VARCHAR2 (100)			
  ) ;
  
COMMENT ON TABLE SIEDU_PARAMETRO_EVALUACION
IS  'Tabla de Parámetros de Evaluación' ;

COMMENT ON COLUMN SIEDU_PARAMETRO_EVALUACION.peval_peval
IS  'Identificador del Parámetro de Evaluación' ;

COMMENT ON COLUMN SIEDU_PARAMETRO_EVALUACION.peval_descri
IS  'Descripción del Parámetro de Evaluación' ;

ALTER TABLE SIEDU_PARAMETRO_EVALUACION ADD CONSTRAINT SIEDU_PARAMETRO_EVALUACION_PK PRIMARY KEY ( peval_peval ) ;
ALTER TABLE SIEDU_PARAMETRO_EVALUACION ADD CONSTRAINT SIEDU_PARAMETRO_EVALUACION_UK UNIQUE ( peval_descri ) ;



create sequence siedu_parametro_evaluacion_seq;