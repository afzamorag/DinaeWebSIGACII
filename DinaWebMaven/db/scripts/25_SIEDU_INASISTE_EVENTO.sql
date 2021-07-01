CREATE TABLE SIEDU_INASISTE_EVENTO
  (
    inae_inae   			NUMBER (8) NOT NULL ,
    inae_pare   			NUMBER (8) NOT NULL ,
    inae_fecha  			DATE NOT NULL ,
    inae_tiempo 			NUMBER (3),
    inae_usu_crea    		VARCHAR2 (30)   NOT NULL ,	
    inae_fecha_crea     	DATE 		    NOT NULL ,
	inae_maquina_crea   	VARCHAR2 (100)  NOT NULL,
	inae_ip_crea    		VARCHAR2 (100)	NOT NULL,
    inae_usu_mod        	VARCHAR2 (30),	
    inae_fecha_mod      	DATE,
    inae_maquina_mod    	VARCHAR2 (100),
	inae_ip_mod    			VARCHAR2 (100)	
  );
  
COMMENT ON TABLE SIEDU_INASISTE_EVENTO
IS
  'Tabla de Inasistencias de Participantes a Eventos' ;
  
COMMENT ON COLUMN SIEDU_INASISTE_EVENTO.inae_inae
IS
  'Secuencial identificador del registro de inasistencia' ;
COMMENT ON COLUMN SIEDU_INASISTE_EVENTO.inae_pare
IS
  'Secuencial Identificador de Participante Evento' ;
COMMENT ON COLUMN SIEDU_INASISTE_EVENTO.inae_fecha
IS
  'Fecha de la Inasistencia' ;
COMMENT ON COLUMN SIEDU_INASISTE_EVENTO.inae_tiempo
IS
  'Tiempo de Inasistencia. Valores comprendidos entre 1 y 480 minutos' ;
ALTER TABLE SIEDU_INASISTE_EVENTO ADD CONSTRAINT SIEDU_INASISTE_EVENTO_PK PRIMARY KEY ( inae_inae ) ;
ALTER TABLE SIEDU_INASISTE_EVENTO ADD CONSTRAINT SIEDU_INASISTE_EVENTO_UK UNIQUE ( inae_pare , inae_fecha ) ;
ALTER TABLE SIEDU_INASISTE_EVENTO ADD CONSTRAINT SIEDU_INASI_EVEN_PARTI_FK FOREIGN KEY ( inae_pare ) REFERENCES SIEDU_PARTICIPANTE_EVENTO ( pare_pare ) ;

create sequence siedu_inasiste_evento_seq;