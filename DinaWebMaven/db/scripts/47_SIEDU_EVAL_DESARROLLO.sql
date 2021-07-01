CREATE TABLE SIEDU_EVAL_DESARROLLO
  (
    evde_eval  NUMBER (8) NOT NULL ,
    evde_pare  NUMBER (8) NOT NULL ,
    evde_preg  NUMBER (6) NOT NULL ,
    evde_valor NUMBER (1),
    evde_usu_crea     VARCHAR2 (30)   NOT NULL ,	
    evde_fecha_crea   DATE 		   NOT NULL ,
	evde_maquina_crea VARCHAR2 (100)  NOT NULL,
	evde_ip_crea      VARCHAR2 (100)  NOT NULL,
    evde_usu_mod      VARCHAR2 (30),	
    evde_fecha_mod    DATE,
    evde_maquina_mod  VARCHAR2 (100),
	evde_ip_mod       VARCHAR2 (100)		
  ) ;
  
ALTER TABLE SIEDU_EVAL_DESARROLLO ADD CONSTRAINT SIEDU_EVAL_DESA_VALOR_CK CHECK (evde_valor IN (1, 2, 3, 4, 5)) ;

COMMENT ON TABLE SIEDU_EVAL_DESARROLLO
IS  'Tabla de respuestas a las preguntas de la evaluación dadas por el participante' ;
  
COMMENT ON COLUMN SIEDU_EVAL_DESARROLLO.evde_eval
IS  'Id de la EValuación' ;
  
COMMENT ON COLUMN SIEDU_EVAL_DESARROLLO.evde_pare
IS  'Id del Participante Evento' ;
  
COMMENT ON COLUMN SIEDU_EVAL_DESARROLLO.evde_preg
IS  'Id de la Pregunta' ;
  
COMMENT ON COLUMN SIEDU_EVAL_DESARROLLO.evde_valor
IS  'Valor de respuesta a la pregunta de la evaluación por parte del participante.' ;
  
ALTER TABLE SIEDU_EVAL_DESARROLLO ADD CONSTRAINT SIEDU_EVAL_DESARROLLO_PK PRIMARY KEY ( evde_eval, evde_pare, evde_preg ) ;
ALTER TABLE SIEDU_EVAL_DESARROLLO ADD CONSTRAINT SIEDU_EVAL_DESARR_PARTICI_FK FOREIGN KEY ( evde_eval, evde_pare ) REFERENCES SIEDU_EVAL_PARTICIPANTE ( evpa_eval, evpa_pare ) ;
ALTER TABLE SIEDU_EVAL_DESARROLLO ADD CONSTRAINT SIEDU_EVAL_DESAR_PREGUNTA_FK FOREIGN KEY ( evde_preg, evde_eval ) REFERENCES SIEDU_EVAL_PREGUNTA ( evpre_preg, evpre_eval ) ;
