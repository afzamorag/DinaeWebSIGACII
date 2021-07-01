CREATE TABLE SIEDU_EVAL_PREGUNTA
  (
    evpre_eval         NUMBER (8) NOT NULL ,
    evpre_preg         NUMBER (6) NOT NULL ,
    evpre_orden        NUMBER (4) NOT NULL ,
    evpre_comp         NUMBER (4) NOT NULL ,
    evpre_peval_factor NUMBER (8) NOT NULL ,
    evpre_vlr_porc     NUMBER (5,2),
    evpre_usu_crea     VARCHAR2 (30)   NOT NULL ,	
    evpre_fecha_crea   DATE 		   NOT NULL ,
	evpre_maquina_crea VARCHAR2 (100)  NOT NULL,
	evpre_ip_crea      VARCHAR2 (100)  NOT NULL,
    evpre_usu_mod      VARCHAR2 (30),	
    evpre_fecha_mod    DATE,
    evpre_maquina_mod  VARCHAR2 (100),
	evpre_ip_mod       VARCHAR2 (100)			
  ) ;
  
COMMENT ON TABLE SIEDU_EVAL_PREGUNTA
IS  'Tabla de Preguntas que componen una Evaluación' ;
COMMENT ON COLUMN SIEDU_EVAL_PREGUNTA.evpre_eval
IS  'Identificador de la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVAL_PREGUNTA.evpre_preg
IS  'Secuencial de la Pregunta dentro de la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVAL_PREGUNTA.evpre_orden
IS  'Orden de la Pregunta en la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVAL_PREGUNTA.evpre_comp
IS  'Identificador de la Competencia' ;
COMMENT ON COLUMN SIEDU_EVAL_PREGUNTA.evpre_peval_factor
IS  'Parámetro de Evaluación - Factor de la Pregunta en la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVAL_PREGUNTA.evpre_vlr_porc
IS  'Valor porcentual asignado a determinada pregunta con respecto a las demás que hacen parte de un grupo de competencias o factores.' ;

ALTER TABLE SIEDU_EVAL_PREGUNTA ADD CONSTRAINT SIEDU_EVAL_PREGUNTA_PK PRIMARY KEY ( evpre_preg, evpre_eval ) ;
ALTER TABLE SIEDU_EVAL_PREGUNTA ADD CONSTRAINT SIEDU_EVAL_PREGUNTA_COMPE_FK FOREIGN KEY ( evpre_comp ) REFERENCES SIEDU_COMPETENCIA ( comp_comp ) ;
ALTER TABLE SIEDU_EVAL_PREGUNTA ADD CONSTRAINT SIEDU_EVAL_PREGUNTA_PREG_FK FOREIGN KEY ( evpre_preg ) REFERENCES SIEDU_PREGUNTA ( preg_preg ) ;
ALTER TABLE SIEDU_EVAL_PREGUNTA ADD CONSTRAINT SIEDU_PREGUNTA_EVALUACION_FK FOREIGN KEY ( evpre_eval ) REFERENCES SIEDU_EVALUACION ( eval_eval ) ;
ALTER TABLE SIEDU_EVAL_PREGUNTA ADD CONSTRAINT SIEDU_PREG_PAR_EVAL_FACTOR_FK FOREIGN KEY ( evpre_peval_factor ) REFERENCES SIEDU_PARAMETRO_EVALUACION ( peval_peval ) ;
