CREATE TABLE SIEDU_COMPETENCIA_EVENTO
  (
    compe_even   		 NUMBER (8) NOT NULL ,
    compe_comp   		 NUMBER (4) NOT NULL ,
    compe_orden  		 NUMBER (3) NOT NULL,
    compe_usu_crea    	 VARCHAR2 (30)   NOT NULL ,	
    compe_fecha_crea     DATE 		    NOT NULL ,
	compe_maquina_crea   VARCHAR2 (100)  NOT NULL,
	compe_ip_crea    	 VARCHAR2 (100)	NOT NULL,
    compe_usu_mod        VARCHAR2 (30),	
    compe_fecha_mod      DATE,
    compe_maquina_mod    VARCHAR2 (100),
	compe_ip_mod    	 VARCHAR2 (100)
  );
  

COMMENT ON TABLE SIEDU_COMPETENCIA_EVENTO
IS
  'Tabla que relaciona las Competencias de cada Evento' ;
  
COMMENT ON COLUMN SIEDU_COMPETENCIA_EVENTO.compe_even
IS
  'Id del Evento' ;
  
COMMENT ON COLUMN SIEDU_COMPETENCIA_EVENTO.compe_comp
IS
  'Competencia relacionada al evento' ;
  
COMMENT ON COLUMN SIEDU_COMPETENCIA_EVENTO.compe_orden
IS
  'Orden de la Competencia en el Programa' ;
  
ALTER TABLE SIEDU_COMPETENCIA_EVENTO ADD CONSTRAINT SIEDU_COMPETENCIA_PROGRAMA_PK PRIMARY KEY ( compe_even, compe_comp ) ;
ALTER TABLE SIEDU_COMPETENCIA_EVENTO ADD CONSTRAINT SIEDU_COMPE_EVEN_COMPETENCI_FK FOREIGN KEY ( compe_comp ) REFERENCES SIEDU_COMPETENCIA ( comp_comp );
ALTER TABLE SIEDU_COMPETENCIA_EVENTO ADD CONSTRAINT SIEDU_COMPE_PROG_CAPA_PROG_FK FOREIGN KEY ( compe_even ) REFERENCES SIEDU_EVENTO ( even_even );
