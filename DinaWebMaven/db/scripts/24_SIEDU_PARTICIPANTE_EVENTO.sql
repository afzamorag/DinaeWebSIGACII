CREATE TABLE SIEDU_PARTICIPANTE_EVENTO
  (
    pare_pare   			 NUMBER (8) 	 NOT NULL ,
    pare_evee   			 NUMBER (6) 	 NOT NULL ,
	pare_pers   			 NUMBER (10) 	 NOT NULL,
    pare_estado 			 VARCHAR2 (1) 	 NOT NULL,
    pare_edad                NUMBER (2) 	 NOT NULL ,
    pare_cumple_perfil       VARCHAR2 (1) 	 NOT NULL ,
    pare_tiempo_servicio     NUMBER (2) 	 NOT NULL ,
    pare_ude_fuerza          NUMBER (1) 	 NOT NULL ,
    pare_ude_udepe           NUMBER 		 NOT NULL ,
    pare_cargo               NUMBER 		 NOT NULL ,
    pare_grados_numerico     NUMBER (6) 	 NOT NULL ,
    pare_grados_id_categoria NUMBER (6) 	 NOT NULL,
    pare_usu_crea    		 VARCHAR2 (30)   NOT NULL ,	
    pare_fecha_crea     	 DATE 		     NOT NULL ,
	pare_maquina_crea   	 VARCHAR2 (100)  NOT NULL,
	pare_ip_crea    		 VARCHAR2 (100)	 NOT NULL,
    pare_usu_mod        	 VARCHAR2 (30),	
    pare_fecha_mod      	 DATE,
    pare_maquina_mod    	 VARCHAR2 (100),
	pare_ip_mod    			 VARCHAR2 (100)
  );
  
ALTER TABLE SIEDU_PARTICIPANTE_EVENTO ADD CONSTRAINT SIEDU_PARTICIP_EVEN_ESTADO_CK CHECK (pare_estado IN ( 'P', 'A', 'N','D' )) ;
ALTER TABLE SIEDU_PARTICIPANTE_EVENTO ADD CONSTRAINT SIEDU_PART_EVE_CUMPLE_PRFL_CK CHECK (pare_cumple_perfil IN ('S', 'N')) ;

COMMENT ON TABLE SIEDU_PARTICIPANTE_EVENTO
IS
  'Tabla de que relaciona a las Personas Participantes del Evento' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_pare
IS
  'Secuencial identificador del registro' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_evee
IS
  'Identificador del Evento Escuela' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_estado
IS
  'Estado de la participación del empleado al Evento. P-Pendiente, A-Aprobado, N-No Aprobado, D-Desertado' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_pers
IS
  'Identificador de Persona del Participante' ;
  
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_edad
IS
  'Edad del participante al momento del Evento' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_cumple_perfil
IS
  'Indica el cumplimiento del perfil S-Si, N-No' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_tiempo_servicio
IS
  'Tiempo de servicio en años al momento de participar en el evento' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_ude_fuerza
IS
  'Fuerza donde labora el participante al momento del Evento' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_ude_udepe
IS
  'Dependencia donde labora el participante al momento del Evento' ;
COMMENT ON COLUMN SIEDU_PARTICIPANTE_EVENTO.pare_cargo
IS
  'Cargo del participante al momento del Evento' ;

ALTER TABLE SIEDU_PARTICIPANTE_EVENTO ADD CONSTRAINT SIEDU_PARTICIPANTE_EVENTO_PK PRIMARY KEY ( pare_pare ) ;
ALTER TABLE SIEDU_PARTICIPANTE_EVENTO ADD CONSTRAINT SIEDU_PARTICIPA_EVEN_ESCU_FK FOREIGN KEY ( pare_evee ) REFERENCES SIEDU_EVENTO_ESCUELA ( evee_evee ) ;
ALTER TABLE SIEDU_PARTICIPANTE_EVENTO ADD CONSTRAINT SIEDU_PARTICIPA_EVEN_PERS_FK FOREIGN KEY ( pare_pers ) REFERENCES SIEDU_PERSONA ( pers_pers ) ;
ALTER TABLE SIEDU_PARTICIPANTE_EVENTO ADD CONSTRAINT SIEDU_PARTI_EVEN_CARGOS_FK FOREIGN KEY ( pare_ude_fuerza, pare_cargo ) REFERENCES USR_REHU.CARGOS ( FUERZA, CARGO ) ;
ALTER TABLE SIEDU_PARTICIPANTE_EVENTO ADD CONSTRAINT SIEDU_PARTI_EVEN_UDE_FK FOREIGN KEY ( pare_ude_fuerza, pare_ude_udepe ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;


create sequence siedu_participante_evento_seq;