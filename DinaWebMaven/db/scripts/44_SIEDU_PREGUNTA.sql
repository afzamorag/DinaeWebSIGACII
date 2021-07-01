CREATE TABLE SIEDU_PREGUNTA
  (
    preg_preg     NUMBER (6) NOT NULL ,
    preg_dom_tipo NUMBER (4) NOT NULL ,
    preg_descri   VARCHAR2 (200) NOT NULL,
    preg_usu_crea    	 VARCHAR2 (30)   NOT NULL ,	
    preg_fecha_crea      DATE 		     NOT NULL ,
	preg_maquina_crea    VARCHAR2 (100)  NOT NULL,
	preg_ip_crea    	 VARCHAR2 (100)	 NOT NULL,
    preg_usu_mod         VARCHAR2 (30),	
    preg_fecha_mod       DATE,
    preg_maquina_mod     VARCHAR2 (100),
	preg_ip_mod    		 VARCHAR2 (100)			
  ) ;
  
COMMENT ON TABLE SIEDU_PREGUNTA
IS  'Tabla de Preguntas o Aspectos' ;

COMMENT ON COLUMN SIEDU_PREGUNTA.preg_preg
IS  'Secuencial Identificador de la Pregunta o Aspecto' ;

COMMENT ON COLUMN SIEDU_PREGUNTA.preg_dom_tipo
IS  'Tipo de Registro (DOMINIO PENDIENTE)' ;

COMMENT ON COLUMN SIEDU_PREGUNTA.preg_descri
IS  'Descripción de la Pregunta o Aspecto' ;

ALTER TABLE SIEDU_PREGUNTA ADD CONSTRAINT SIEDU_PREGUNTA_PK PRIMARY KEY ( preg_preg ) ;
ALTER TABLE SIEDU_PREGUNTA ADD CONSTRAINT SIEDU_PREGUNTA_DOM_TIPO_FK FOREIGN KEY ( preg_dom_tipo ) REFERENCES USR_DISIGAC2.SIEDU_DOMINIO ( ID_DOMINIO ) ;

create sequence siedu_pregunta_seq;