CREATE TABLE SIEDU_EVALUACION
  (
    eval_eval      			NUMBER (8) NOT NULL ,
    eval_pae       			NUMBER (6) NOT NULL ,
    eval_descri    			VARCHAR2 (100) NOT NULL ,
    eval_even      			NUMBER (8) NOT NULL ,
    eval_dom_proce 			NUMBER (4) NOT NULL ,
    eval_dom_modal 			NUMBER (4) NOT NULL ,
    eval_nro_preg  			NUMBER (2) NOT NULL ,
    eval_fecha     			DATE NOT NULL ,
    eval_estado    			VARCHAR2 (1) NOT NULL,
    eval_aplicada           VARCHAR2 (1) DEFAULT 'N',    
    eval_usu_crea    		VARCHAR2 (30)   NOT NULL ,	
    eval_fecha_crea     	DATE 		    NOT NULL ,
	eval_maquina_crea   	VARCHAR2 (100)  NOT NULL,
	eval_ip_crea    		VARCHAR2 (100)	NOT NULL,
    eval_usu_mod        	VARCHAR2 (30),	
    eval_fecha_mod      	DATE,
    eval_maquina_mod    	VARCHAR2 (100),
	eval_ip_mod    			VARCHAR2 (100)		
  ) ;
ALTER TABLE SIEDU_EVALUACION ADD CONSTRAINT SIEDU_EVALUACION_ESTADO_CK CHECK (eval_estado IN ( 'P', 'A', 'I' )) ;
ALTER TABLE SIEDU_EVALUACION ADD CONSTRAINT SIEDU_EVALUACION_APLICADA_CK CHECK (eval_aplicada IN ('S', 'N'));

COMMENT ON TABLE SIEDU_EVALUACION
IS  'Tabla de Evaluaciones en Vigencia - PAE' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_eval
IS  'Secuencial identificador único del registro de Evaluación' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_pae
IS  'Identificador del PAE' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_descri
IS  'Descripción de la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_even
IS  'Identificador del Evento' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_dom_proce
IS  'Proceso ( DOMINIO PROCESO )' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_dom_modal
IS  'Modalidad ( DOMINIO METODOLOGIA )' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_nro_preg
IS  'Número de Preguntas que componen la Evaluación. ' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_fecha
IS  'Fecha de la versión de la Evaluación' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_estado
IS  'Estado de la Evaluación P-Pendiente, A-Activa, I-Inactiva' ;
COMMENT ON COLUMN SIEDU_EVALUACION.eval_aplicada IS 'Indica si para la Evaluación ya hay o ha habido participantes.' ;

ALTER TABLE SIEDU_EVALUACION ADD CONSTRAINT SIEDU_EVALUACION_PK PRIMARY KEY ( eval_eval ) ;
ALTER TABLE SIEDU_EVALUACION ADD CONSTRAINT SIEDU_EVALUACION_EVEN_UK UNIQUE ( eval_even , eval_estado ) ;
ALTER TABLE SIEDU_EVALUACION ADD CONSTRAINT SIEDU_EVALUACION_EVENTO_FK FOREIGN KEY ( eval_even ) REFERENCES SIEDU_EVENTO ( even_even ) ;
ALTER TABLE SIEDU_EVALUACION ADD CONSTRAINT SIEDU_EVALUACION_PAE_FK FOREIGN KEY ( eval_pae ) REFERENCES SIEDU_PAE ( pae_pae ) ;

create sequence siedu_evaluacion_seq;
