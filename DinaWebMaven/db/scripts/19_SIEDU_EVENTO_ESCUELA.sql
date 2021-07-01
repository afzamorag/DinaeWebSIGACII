CREATE TABLE SIEDU_EVENTO_ESCUELA
  (
    evee_evee              	NUMBER (6) NOT NULL ,
    evee_even              	NUMBER (8) ,
    evee_capa              	NUMBER (6) NOT NULL ,
    evee_nro_evento        	NUMBER (3) ,
    evee_trimes            	NUMBER (1) ,
    evee_nro_participantes 	NUMBER (6) ,
    evee_fechaIni          	DATE ,
    evee_fechaFin          	DATE ,
    evee_cerrado           	VARCHAR2 (1) NOT NULL ,
    evee_enti              	NUMBER ,
    evee_dom_financia      	NUMBER (4) ,
    evee_ppto              	NUMBER (6)  ,
    evee_costo_capa        	NUMBER (10) ,
    evee_nro_dias          	NUMBER (3) ,
    evee_nro_convocados    	NUMBER (6) ,
    eve_nro_noaprobados    	NUMBER (6) ,
    eve_nro_aprobados      	NUMBER (6) ,
    evee_conv              	NUMBER (4) ,
    evee_ude_fuerza        	NUMBER (1) ,
    evee_ude_udepe         	NUMBER     ,
    evee_luggeo_capacita    NUMBER (6) ,
	evee_nro_desertados 	NUMBER(6) ,
	evee_eval               NUMBER(8),	
    evee_usu_crea    		VARCHAR2 (30)   NOT NULL ,	
    evee_fecha_crea     	DATE 		    NOT NULL ,
	evee_maquina_crea   	VARCHAR2 (100)  NOT NULL,
	evee_ip_crea    		VARCHAR2 (100)	NOT NULL,
    evee_usu_mod        	VARCHAR2 (30),	
    evee_fecha_mod      	DATE,
    evee_maquina_mod    	VARCHAR2 (100),
	evee_ip_mod    			VARCHAR2 (100)	
  );
  
COMMENT ON TABLE SIEDU_EVENTO_ESCUELA
IS
  'Tabla que relaciona Evento con Escuelas' ;  
  
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_evee
IS
  'Secuencial identificador del registro' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_even
IS
  'Identificador del Evento' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_capa
IS
  'Registro de Capacitación correspondiente al Evento Escuela' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_nro_evento
IS
  'Número del Evento' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_trimes
IS
  'Número del Trimestre' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_nro_participantes
IS
  'Número de participantes programados para el evento' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_fechaIni
IS
  'Fecha de Inicio de Evento' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_fechaFin
IS
  'Fecha Fin del Evento' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_cerrado
IS
  'Indica si el Evento se encuentra en Estado Cerrado S-SI o N-NO' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_enti
IS
  'Entidad que dicta la capacitación' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_dom_financia
IS
  'Financiación de la Capacitación. (DOMINIO FINANCIACION)' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_ppto
IS
  'Registro de Presupuesto relacionado' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_costo_capa
IS
  'Costo de la Capacitación' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_nro_dias
IS
  'Número de días de capacitación' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_nro_convocados
IS
  'Campo calculado por el sistema, realiza la sumatoria de la información de convocados definidos en la Convocatoria' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.eve_nro_noaprobados
IS
  'Campo calculado por el sistema, realiza la sumatoria de la información de participantes definidos en la Convocatoria y que tengan estado No Aprobado.' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.eve_nro_aprobados
IS
  'Campo calculado por el sistema, realiza la sumatoria de la información de participantes definidos en la Convocatoria y que tengan estado Aprobado.' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_ude_fuerza
IS
  'Identificador de la Fuerza de Unidades Dependencia' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_ude_udepe
IS
  'Unidad que desarrolla la Capacitación' ;
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_luggeo_capacita
IS
  'Lugar donde se realiza la Capacitación' ;  
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.EVEE_NRO_DESERTADOS is 
'Campo calculado por el sistema, realiza la sumatoria de la información de participantes definidos en la Convocatoria y que tengan estado Desertado.';
COMMENT ON COLUMN SIEDU_EVENTO_ESCUELA.evee_eval IS ' Evaluación que fue desarrollada para el evento' ;
  
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVENTO_ESCUELA_PK PRIMARY KEY ( evee_evee ) ;
alter table SIEDU_EVENTO_ESCUELA add constraint SIEDU_EVENTO_ESCU_CONVEN_FK foreign key (EVEE_CONV) references SIEDU_CONVENIO (CONV_CONV);
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVENTO_ESCUELA_CAPA_FK FOREIGN KEY ( evee_capa ) REFERENCES SIEDU_PAE_CAPACITACION ( capa_capa ) ;
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVENTO_ESCUELA_CONVE_FK FOREIGN KEY ( evee_conv ) REFERENCES SIEDU_CONVENIO ( conv_conv ) ;
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVENTO_ESCUELA_ENTI_FK FOREIGN KEY ( evee_enti ) REFERENCES SIEDU_ENTIDAD ( enti_enti ) ;
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVENTO_ESCUELA_PPTO_FK FOREIGN KEY ( evee_ppto ) REFERENCES SIEDU_PRESUPUESTO ( ppto_ppto ) ;
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVENTO_ESCUELA_UDEPE_FK FOREIGN KEY ( evee_ude_fuerza, evee_ude_udepe ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVENTO_ESCU_EVENTO_FK FOREIGN KEY ( evee_even ) REFERENCES SIEDU_EVENTO ( even_even ) ;
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVENTO_ESCU_EVAL_FK FOREIGN KEY ( evee_eval ) REFERENCES SIEDU_EVALUACION ( eval_eval ) ;
ALTER TABLE SIEDU_EVENTO_ESCUELA ADD CONSTRAINT SIEDU_EVEN_ESCUELA_CERRADO_CK CHECK (evee_cerrado IN ('S', 'N')) ;

create sequence siedu_evento_escuela_seq;
