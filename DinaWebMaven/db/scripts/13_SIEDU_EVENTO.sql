CREATE TABLE SIEDU_EVENTO
  (
    even_even        	NUMBER (8) NOT NULL ,
    even_dom_modal   	NUMBER (4) NOT NULL ,
    even_dom_proce   	NUMBER (4) NOT NULL ,
    even_fuerza      	NUMBER (1) NOT NULL ,
    even_id_carrera  	NUMBER NOT NULL ,
    even_justifica   	VARCHAR2 (1000) NOT NULL ,
    even_obje_gral   	VARCHAR2 (500) NOT NULL ,
    even_obje_espe   	VARCHAR2 (1000) NOT NULL ,
    even_dirigidoa   	VARCHAR2 (1000) NOT NULL ,
    even_inten_horas 	NUMBER (4) NOT NULL ,
    even_fecha_ver   	DATE NOT NULL ,
    even_estado      	VARCHAR2 (1) NOT NULL,
    even_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    even_fecha_crea     DATE 		    NOT NULL ,
	even_maquina_crea   VARCHAR2 (100)  NOT NULL,
	even_ip_crea    	VARCHAR2 (100)	NOT NULL,
    even_usu_mod        VARCHAR2 (30),	
    even_fecha_mod      DATE,
    even_maquina_mod    VARCHAR2 (100),
	even_ip_mod    		VARCHAR2 (100)		
  );
  
COMMENT ON TABLE SIEDU_EVENTO
IS  'Tabla de Eventos o Programas de Capacitación' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_even
IS  'Identificación secuencial única del Programa de Capacitación' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_dom_modal
IS  'Modalidad ( DOMINIO METODOLOGIA )' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_dom_proce
IS  'Proceso ( DOMINIO PROCESO )' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_justifica
IS  'Justificación' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_obje_gral
IS  'Objetivo General del Programa' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_obje_espe
IS  'Objetivos específicos del Programa' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_dirigidoa
IS  'Dirigido a, o perfil requerido por el Programa' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_inten_horas
IS  'Intensidad horaria del Programa' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_fecha_ver
IS  'Fecha de generación del Programa de Capacitación' ;
  
COMMENT ON COLUMN SIEDU_EVENTO.even_estado
IS  'Estado del Programa de Capacitación' ;
  
ALTER TABLE SIEDU_EVENTO ADD CONSTRAINT SIEDU_EVENTO_PK PRIMARY KEY ( even_even ) ;

create sequence siedu_evento_seq nocache;

ALTER TABLE SIEDU_EVENTO ADD CONSTRAINT SIEDU_EVENTO_CARRERAS_FK FOREIGN KEY ( even_fuerza, even_id_carrera ) REFERENCES USR_EDUC.CARRERAS ( FUERZA, ID_CARRERA ) NOT DEFERRABLE ;
-- ALTER TABLE SIEDU_EVENTO ADD CONSTRAINT SIEDU_EVENTO_UK UNIQUE ( even_id_carrera , even_dom_modal , even_dom_proce , even_fuerza , even_estado ) ;
