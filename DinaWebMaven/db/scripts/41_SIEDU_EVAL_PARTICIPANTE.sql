CREATE TABLE SIEDU_EVAL_PARTICIPANTE
  (
    evpa_eval    			NUMBER (8) NOT NULL ,
    evpa_pare    			NUMBER (8) NOT NULL ,
    evpa_observa 			VARCHAR2 (500) ,
    evpa_estado  			VARCHAR2 (1) NOT NULL,
    evpa_usu_crea    		VARCHAR2 (30)   NOT NULL ,	
    evpa_fecha_crea     	DATE 		    NOT NULL ,
	evpa_maquina_crea   	VARCHAR2 (100)  NOT NULL,
	evpa_ip_crea    		VARCHAR2 (100)	NOT NULL,
    evpa_usu_mod        	VARCHAR2 (30),	
    evpa_fecha_mod      	DATE,
    evpa_maquina_mod    	VARCHAR2 (100),
	evpa_ip_mod    			VARCHAR2 (100)		
	
  ) ;
  
ALTER TABLE SIEDU_EVAL_PARTICIPANTE ADD CONSTRAINT SIEDU_EVAL_PARTICI_ESTADO_CK CHECK (evpa_estado IN ( 'A', 'I', 'D' )) ;

COMMENT ON TABLE SIEDU_EVAL_PARTICIPANTE
IS  'Tabla de Registro de Evaluación de un Participante Evento' ;

COMMENT ON COLUMN SIEDU_EVAL_PARTICIPANTE.evpa_eval
IS  'Identificador de la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVAL_PARTICIPANTE.evpa_pare
IS  'Identificador del Participante Evento' ;
COMMENT ON COLUMN SIEDU_EVAL_PARTICIPANTE.evpa_observa
IS  'Campo de inserción de sugerencias y/u observaciones de la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVAL_PARTICIPANTE.evpa_estado
IS  'Estado de la Evaluación del Participante. A-Activa, I-Inactiva, D-Desarrollada' ;

ALTER TABLE SIEDU_EVAL_PARTICIPANTE ADD CONSTRAINT SIEDU_EVAL_PARTICIPANTE_PK PRIMARY KEY ( evpa_eval, evpa_pare ) ;
ALTER TABLE SIEDU_EVAL_PARTICIPANTE ADD CONSTRAINT SIEDU_EVAL_PARTICIP_EVAL_FK FOREIGN KEY ( evpa_eval ) REFERENCES SIEDU_EVALUACION ( eval_eval ) ;
ALTER TABLE SIEDU_EVAL_PARTICIPANTE ADD CONSTRAINT SIEDU_EVAL_PARTICIP_PARTI_FK FOREIGN KEY ( evpa_pare ) REFERENCES SIEDU_PARTICIPANTE_EVENTO ( pare_pare ) ;
