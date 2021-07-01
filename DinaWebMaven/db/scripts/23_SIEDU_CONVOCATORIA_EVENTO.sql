CREATE TABLE SIEDU_CONVOCATORIA_EVENTO
  (
    cone_cone            	NUMBER (6) NOT NULL ,
    cone_evee            	NUMBER (6) NOT NULL ,
    cone_ude_fuerza      	NUMBER (1) NOT NULL ,
    cone_ude_udepe       	NUMBER NOT NULL ,
    cone_dom_tpcomu      	NUMBER (4) ,
    cone_nro_comu        	VARCHAR2 (20) ,
    cone_nro_convocados  	NUMBER (6) ,
    cone_nro_presentados 	NUMBER (6),
    cone_usu_crea    		VARCHAR2 (30)   NOT NULL ,	
    cone_fecha_crea     	DATE 		    NOT NULL ,
	cone_maquina_crea   	VARCHAR2 (100)  NOT NULL,
	cone_ip_crea    		VARCHAR2 (100)	NOT NULL,
    cone_usu_mod        	VARCHAR2 (30),	
    cone_fecha_mod      	DATE,
    cone_maquina_mod    	VARCHAR2 (100),
	cone_ip_mod    			VARCHAR2 (100)		
  );
  
COMMENT ON TABLE SIEDU_CONVOCATORIA_EVENTO
IS
  'Tabla de Convocatorias por Evento' ;
COMMENT ON COLUMN SIEDU_CONVOCATORIA_EVENTO.cone_evee
IS
  'Secuencial identificador del Evento Escuela' ;
COMMENT ON COLUMN SIEDU_CONVOCATORIA_EVENTO.cone_ude_fuerza
IS
  'Identificador de la Fuerza' ;
COMMENT ON COLUMN SIEDU_CONVOCATORIA_EVENTO.cone_ude_udepe
IS
  'Identificador de Unidad Dependencia' ;
COMMENT ON COLUMN SIEDU_CONVOCATORIA_EVENTO.cone_dom_tpcomu
IS
  'Id del Tipo de Comunicación ( DOMINIO TIPO_COMUNICACION)' ;
COMMENT ON COLUMN SIEDU_CONVOCATORIA_EVENTO.cone_nro_comu
IS
  'Número de Comunicación' ;
COMMENT ON COLUMN SIEDU_CONVOCATORIA_EVENTO.cone_nro_convocados
IS
  'Número de Convocados al Evento' ;
COMMENT ON COLUMN SIEDU_CONVOCATORIA_EVENTO.cone_nro_presentados
IS
  'Número de Presentados.  Calculado.' ;

ALTER TABLE SIEDU_CONVOCATORIA_EVENTO ADD CONSTRAINT SIEDU_CONVOCATORIA_EVENTO_PK PRIMARY KEY ( cone_cone ) ;
ALTER TABLE SIEDU_CONVOCATORIA_EVENTO ADD CONSTRAINT SIEDU_CONVOC_EVENTO_UDEPE_FK FOREIGN KEY ( cone_ude_fuerza, cone_ude_udepe ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO );
ALTER TABLE SIEDU_CONVOCATORIA_EVENTO ADD CONSTRAINT SIEDU_CONVO_EVENTO_ESCUELA_FK FOREIGN KEY ( cone_evee ) REFERENCES SIEDU_EVENTO_ESCUELA ( evee_evee );

create sequence siedu_convocatoria_evento_seq;
