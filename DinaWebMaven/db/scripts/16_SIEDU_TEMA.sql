CREATE TABLE SIEDU_TEMA
  (
    tema_tema         	NUMBER (8) NOT NULL ,
    tema_even         	NUMBER (8) NOT NULL ,
    tema_orden        	NUMBER (3) NOT NULL ,
    tema_descri       	VARCHAR2 (100) NOT NULL ,
    tema_horas        	NUMBER (4) NOT NULL ,
    tema_dom_tematica 	NUMBER (4) ,
    tema_tema_padre   	NUMBER (8),
    tema_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    tema_fecha_crea     DATE 		    NOT NULL ,
	tema_maquina_crea   VARCHAR2 (100)  NOT NULL,
	tema_ip_crea    	VARCHAR2 (100)	NOT NULL,
    tema_usu_mod        VARCHAR2 (30),	
    tema_fecha_mod      DATE,
    tema_maquina_mod    VARCHAR2 (100),
	tema_ip_mod    		VARCHAR2 (100)	
  );
  
COMMENT ON TABLE SIEDU_TEMA
IS
  'Tabla de Temas y Subtemas de los Eventos' ;
  
COMMENT ON COLUMN SIEDU_TEMA.tema_tema
IS
  'Secuencial Identificador del Tema' ;
  
COMMENT ON COLUMN SIEDU_TEMA.tema_even
IS
  'Evento al que corresponde el Tema' ;
  
COMMENT ON COLUMN SIEDU_TEMA.tema_orden
IS
  'Orden del tema' ;
  
COMMENT ON COLUMN SIEDU_TEMA.tema_descri
IS
  'Descripción del Tema' ;
  
COMMENT ON COLUMN SIEDU_TEMA.tema_horas
IS
  'Horas asignadas al tema' ;
  
COMMENT ON COLUMN SIEDU_TEMA.tema_dom_tematica
IS
  'Id de dominio de la temática (  DOMINIO TEMATICA )' ;
  
COMMENT ON COLUMN SIEDU_TEMA.tema_tema_padre
IS
  'Identificador del Tema padre' ;
  
ALTER TABLE SIEDU_TEMA ADD CONSTRAINT SIEDU_TEMA_PK PRIMARY KEY ( tema_tema ) ;
ALTER TABLE SIEDU_TEMA ADD CONSTRAINT SIEDU_TEMA_CAPAC_PROGR_FK FOREIGN KEY ( tema_even ) REFERENCES SIEDU_EVENTO ( even_even );
ALTER TABLE SIEDU_TEMA ADD CONSTRAINT SIEDU_TEMA_TEMA_FK FOREIGN KEY ( tema_tema_padre ) REFERENCES SIEDU_TEMA ( tema_tema );
ALTER TABLE SIEDU_TEMA ADD CONSTRAINT SIEDU_TEMA_UK UNIQUE ( tema_even , tema_descri , tema_tema_padre ) ;

create sequence siedu_tema_seq nocache;
