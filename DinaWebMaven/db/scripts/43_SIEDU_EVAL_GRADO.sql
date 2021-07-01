CREATE TABLE SIEDU_EVAL_GRADO
  (
    evgr_eval            NUMBER (8) NOT NULL ,
    evgr_grad_fuerza     NUMBER (1) NOT NULL ,
    evgr_grad_alfabetico VARCHAR2 (5) NOT NULL,
    evgr_usu_crea    	 VARCHAR2 (30)   NOT NULL ,	
    evgr_fecha_crea      DATE 		     NOT NULL ,
	evgr_maquina_crea    VARCHAR2 (100)  NOT NULL,
	evgr_ip_crea    	 VARCHAR2 (100)	 NOT NULL,
    evgr_usu_mod         VARCHAR2 (30),	
    evgr_fecha_mod       DATE,
    evgr_maquina_mod     VARCHAR2 (100),
	evgr_ip_mod    		 VARCHAR2 (100)		
  ) ;
  
COMMENT ON TABLE SIEDU_EVAL_GRADO
IS  'Tabla de Grados por Evaluación' ;

COMMENT ON COLUMN SIEDU_EVAL_GRADO.evgr_eval
IS  'Identificador de la Evaluación' ;

COMMENT ON COLUMN SIEDU_EVAL_GRADO.evgr_grad_fuerza
IS  'Identificador de Fuerza' ;

COMMENT ON COLUMN SIEDU_EVAL_GRADO.evgr_grad_alfabetico
IS  'Identificador del Grado Alfabético' ;

ALTER TABLE SIEDU_EVAL_GRADO ADD CONSTRAINT SIEDU_EVAL_GRADO_PK PRIMARY KEY ( evgr_eval, evgr_grad_fuerza, evgr_grad_alfabetico ) ;
ALTER TABLE SIEDU_EVAL_GRADO ADD CONSTRAINT SIEDU_EVAL_GRADO_EVAL_FK FOREIGN KEY ( evgr_eval ) REFERENCES SIEDU_EVALUACION ( eval_eval ) ;
ALTER TABLE SIEDU_EVAL_GRADO ADD CONSTRAINT SIEDU_EVAL_GRADO_GRADOS_FK FOREIGN KEY ( evgr_grad_fuerza, evgr_grad_alfabetico ) REFERENCES USR_REHU.GRADOS ( FUERZA, ALFABETICO ) ;
