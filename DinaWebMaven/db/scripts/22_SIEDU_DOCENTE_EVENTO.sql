CREATE TABLE SIEDU_DOCENTE_EVENTO
  (
    doce_doce 				NUMBER (6) NOT NULL ,
    doce_evee 				NUMBER (6) NOT NULL ,
    doce_tema 				NUMBER (8) NOT NULL ,
    doce_pers 				NUMBER (10) NOT NULL,
    doce_usu_crea    		VARCHAR2 (30)   NOT NULL ,	
    doce_fecha_crea     	DATE 		    NOT NULL ,
	doce_maquina_crea   	VARCHAR2 (100)  NOT NULL,
	doce_ip_crea    		VARCHAR2 (100)	NOT NULL,
    doce_usu_mod        	VARCHAR2 (30),	
    doce_fecha_mod      	DATE,
    doce_maquina_mod    	VARCHAR2 (100),
	doce_ip_mod    			VARCHAR2 (100)		
  );
  
COMMENT ON TABLE SIEDU_DOCENTE_EVENTO
IS
  'Tabla de Docentes por Evento y Tema' ;
COMMENT ON COLUMN SIEDU_DOCENTE_EVENTO.doce_doce
IS
  'Identificador secuencial del registro' ;
COMMENT ON COLUMN SIEDU_DOCENTE_EVENTO.doce_evee
IS
  'Identificador del Registro de Evento Escuela' ;
COMMENT ON COLUMN SIEDU_DOCENTE_EVENTO.doce_pers
IS
  'Identificador de Persona del Docente' ;
  
ALTER TABLE SIEDU_DOCENTE_EVENTO ADD CONSTRAINT SIEDU_DOCENTE_EVENTO_PK PRIMARY KEY ( doce_doce ) ;
ALTER TABLE SIEDU_DOCENTE_EVENTO ADD CONSTRAINT SIEDU_DOCENTE_EVENTO_UK UNIQUE ( doce_evee , doce_tema , doce_pers ) ;

ALTER TABLE SIEDU_DOCENTE_EVENTO ADD CONSTRAINT SIEDU_DOCENTE_EVEN_ESCU_FK FOREIGN KEY ( doce_evee ) REFERENCES SIEDU_EVENTO_ESCUELA ( evee_evee );
ALTER TABLE SIEDU_DOCENTE_EVENTO ADD CONSTRAINT SIEDU_DOCENTE_EVEN_PERS_FK FOREIGN KEY ( doce_pers ) REFERENCES SIEDU_PERSONA ( pers_pers );
ALTER TABLE SIEDU_DOCENTE_EVENTO ADD CONSTRAINT SIEDU_DOCENTE_EVEN_SUBTEMA_FK FOREIGN KEY ( doce_tema ) REFERENCES SIEDU_TEMA ( tema_tema );

create sequence siedu_docente_evento_seq;
