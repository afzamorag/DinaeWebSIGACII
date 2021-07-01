CREATE TABLE SIEDU_PERSONA
  (
    pers_pers            	NUMBER (10) NOT NULL ,
    pers_nroid           	VARCHAR2 (14) ,
    pers_tpoid           	NUMBER(4) ,
    pers_nombres         	VARCHAR2 (30) NOT NULL ,
    pers_apellidos       	VARCHAR2 (30) NOT NULL ,
    pers_empe            	NUMBER (8) ,
    pers_emp_unde_fuerza 	NUMBER (1) ,
    pers_emp_unde_udepe  	NUMBER ,
    pers_emp_consecutivo 	NUMBER,
    pers_usu_crea    		VARCHAR2 (30)   NOT NULL ,	
    pers_fecha_crea     	DATE 		    NOT NULL ,
	pers_maquina_crea   	VARCHAR2 (100)  NOT NULL,
	pers_ip_crea    		VARCHAR2 (100)	NOT NULL,
    pers_usu_mod        	VARCHAR2 (30),	
    pers_fecha_mod      	DATE,
    pers_maquina_mod    	VARCHAR2 (100),
	pers_ip_mod    			VARCHAR2 (100)	
  );
  
ALTER TABLE SIEDU_PERSONA ADD CONSTRAINT SIEDU_PERSONA_EXTERNO_CHK CHECK ( ( (pers_empe IS NOT NULL) 
                                                                             AND 
																			 (pers_emp_unde_fuerza IS NULL) 
																			  AND (pers_emp_unde_udepe IS NULL) 
																			  AND (pers_emp_consecutivo IS NULL) 
																			) 
																		   OR ( (pers_emp_unde_fuerza IS NOT NULL) 
																		       AND (pers_emp_unde_udepe IS NOT NULL) 
																			   AND (pers_emp_consecutivo IS NOT NULL) 
																			   AND (pers_empe IS NULL) ) 
																		   OR ( (pers_empe IS NULL) 
																			   AND (pers_emp_unde_fuerza IS NULL) 
																		       AND (pers_emp_unde_udepe IS NULL) 
																			   AND (pers_emp_consecutivo IS NULL) ) 
																	     ) ;

ALTER TABLE SIEDU_PERSONA ADD CONSTRAINT SIEDU_PERSONA_TPOID_CK CHECK (pers_tpoid IN ('CC','CD','CE','LM','NI','NR','PA','PJ','RM','TD','TI','TP','TS','RC','NU','CM' )) ;
																		 
COMMENT ON TABLE SIEDU_PERSONA
IS
  'Tabla de Personas' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_pers
IS
  'Secuencial identificador de la Persona' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_nroid
IS
  'Número de Identificación de la Persona' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_tpoid IS  'Tipo de Identificación de la Persona. CC-CEDULA DE CIUDADANIA,CD-CARTA DENTAL,CE-CEDULA EXTRANJERIA,LM-LIBRETA MILITAR,NI-NUMERO IDENTIFICACION TRIBUTARIA,NR-NO REPORTADO,PA-PASAPORTE,PJ-PERSONERIA JURIDICA,RM-REGISTRO MERCANTIL,TD-TARJETA DECADACTILAR,TI-TARJETA IDENTIDAD,TP-TARJETA PROFESIONAL,TS-TARJETA SEGURO SOCIAL,RC-REGISTRO CIVIL,NU-NUMERO UNICO DE IDENTIFICACION PERSONAL,CM-CARNET DIPLOMATICO' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_nombres
IS
  'Nombres completos del Empleado' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_apellidos
IS
  'Apellidos del Empleado' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_empe
IS
  'Secuencial Identificador de Empleado Externo' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_emp_unde_fuerza
IS
  'Identificador de Fuerza de la Dependencia del Empleado' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_emp_unde_udepe
IS
  'Identificador de la Dependencia del Empleado' ;
COMMENT ON COLUMN SIEDU_PERSONA.pers_emp_consecutivo
IS
  'Consecutivo del Empleado' ;
ALTER TABLE SIEDU_PERSONA ADD CONSTRAINT SIEDU_PERSONA_PK PRIMARY KEY ( pers_pers ) ;
ALTER TABLE SIEDU_PERSONA ADD CONSTRAINT SIEDU_PERSONA_EMPLEADOS_FK FOREIGN KEY ( pers_emp_unde_fuerza, pers_emp_unde_udepe, pers_emp_consecutivo ) REFERENCES USR_REHU.EMPLEADOS ( UNDE_FUERZA, UNDE_CONSECUTIVO, CONSECUTIVO ) NOT DEFERRABLE ;
ALTER TABLE SIEDU_PERSONA ADD CONSTRAINT SIEDU_PERSONA_EMPLE_EXTERNO_FK FOREIGN KEY ( pers_empe ) REFERENCES SIEDU_EMPLEADO_EXTERNO ( empe_empe );

create sequence siedu_persona_seq nocache;