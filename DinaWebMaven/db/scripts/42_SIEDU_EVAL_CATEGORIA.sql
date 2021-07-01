CREATE TABLE SIEDU_EVAL_CATEGORIA
  (
    evcr_eval         	NUMBER (8) NOT NULL ,
    evcr_fuerza       	NUMBER (1) NOT NULL ,
    evcr_id_categoria 	NUMBER NOT NULL,
    evcr_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    evcr_fecha_crea     DATE 		    NOT NULL ,
	evcr_maquina_crea   VARCHAR2 (100)  NOT NULL,
	evcr_ip_crea    	VARCHAR2 (100)	NOT NULL,
    evcr_usu_mod        VARCHAR2 (30),	
    evcr_fecha_mod      DATE,
    evcr_maquina_mod    VARCHAR2 (100),
	evcr_ip_mod    		VARCHAR2 (100)		
  ) ;
  
  
COMMENT ON TABLE SIEDU_EVAL_CATEGORIA
IS  'Tabla de Categorías por Evaluación' ;

COMMENT ON COLUMN SIEDU_EVAL_CATEGORIA.evcr_eval
IS  'Id de la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVAL_CATEGORIA.evcr_fuerza
IS  'Identificador de Fuerza' ;
COMMENT ON COLUMN SIEDU_EVAL_CATEGORIA.evcr_id_categoria
IS  'Identificador de Categoría' ;


ALTER TABLE SIEDU_EVAL_CATEGORIA ADD CONSTRAINT SIEDU_EVAL_CATEGORIA_PK PRIMARY KEY ( evcr_eval, evcr_fuerza, evcr_id_categoria ) ;
ALTER TABLE SIEDU_EVAL_CATEGORIA ADD CONSTRAINT SIEDU_EVAL_CATEGORIA_CAT_FK FOREIGN KEY ( evcr_fuerza, evcr_id_categoria ) REFERENCES USR_REHU.CATEGORIAS ( FUERZA, ID_CATEGORIA ) ;
ALTER TABLE SIEDU_EVAL_CATEGORIA ADD CONSTRAINT SIEDU_EVAL_CATEGORIA_EVAL_FK FOREIGN KEY ( evcr_eval ) REFERENCES SIEDU_EVALUACION ( eval_eval ) ;
