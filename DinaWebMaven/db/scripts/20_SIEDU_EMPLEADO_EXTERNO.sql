CREATE TABLE SIEDU_EMPLEADO_EXTERNO
  (
    empe_empe          		NUMBER (8) NOT NULL ,
    empe_tipoId        		VARCHAR2(2) NOT NULL ,
    empe_nroId         		VARCHAR2 (14) NOT NULL ,
    empe_ude_fuerza    		NUMBER (1) NOT NULL ,
    empe_ude_udepe     		NUMBER NOT NULL ,
    empe_nombres       		VARCHAR2 (30) NOT NULL ,
    empe_apellidos     		VARCHAR2 (30) NOT NULL ,
    empe_fecha_nac     		DATE NOT NULL ,
    empe_grupo_sang    		VARCHAR (2) NOT NULL ,
    empe_factorrh      		VARCHAR (2) NOT NULL ,
    empe_estado_civil  		VARCHAR2(2) NOT NULL ,
    empe_sexo          		VARCHAR2 (2) NOT NULL ,
    empe_barrio_res    		VARCHAR2 (100) ,
    empe_dire_res      		VARCHAR2 (100) NOT NULL ,
    empe_entidad       		VARCHAR2 (100) ,
    empe_luggeo_painac 		NUMBER (6) NOT NULL ,
    empe_luggeo_ciunac 		NUMBER (6) NOT NULL ,
    empe_luggeo_paires 		NUMBER (6) NOT NULL ,
    empe_luggeo_ciures 		NUMBER (6) NOT NULL,
    empe_usu_crea    		VARCHAR2 (30)   NOT NULL ,	
    empe_fecha_crea     	DATE 		    NOT NULL ,
	empe_maquina_crea   	VARCHAR2 (100)  NOT NULL,
	empe_ip_crea    		VARCHAR2 (100)	NOT NULL,
    empe_usu_mod        	VARCHAR2 (30),	
    empe_fecha_mod      	DATE,
    empe_maquina_mod    	VARCHAR2 (100),
	empe_ip_mod    			VARCHAR2 (100)	
  ) ;
  
COMMENT ON TABLE SIEDU_EMPLEADO_EXTERNO
IS 'Tabla de Empleados que no hacen parte de la Policía' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_empe
IS  'Secuencial Identificador del registro de Empleado Externo' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_tipoId
IS  'Tipo Documento de Identificacion.' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_nroId
IS  'Numero Documento o Identificacion del Empleado' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_ude_fuerza
IS  'Fuerza. Por Defecto 6' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_ude_udepe
IS  'Consecutivo de la Unidad Actual del Empleado' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_nombres
IS  'Nombres completos del Empleado' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_apellidos
IS
  'Apellidos del Empleado' ;
COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_fecha_nac
IS  'Fecha de Nacimiento' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_grupo_sang
IS  'Grupo Sanguineo' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_factorrh
IS  'Factor RH' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_estado_civil
IS  'Estado Civil del Empleado' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_sexo
IS  'Sexo del Empleado. BI-Bisexual,HF-Homosexual Femenino,FE-Femenino,MA-Masculino,NR-No Reportado,HM-Homosexual Masculino' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_barrio_res
IS  'Barrio de Residencia' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_dire_res
IS  'Direccion de Residencia' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_entidad
IS  'Entidad o Dependencia' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_luggeo_painac
IS  'País Nacimiento' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_luggeo_ciunac
IS  'Ciudad de nacimiento' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_luggeo_paires
IS  'País de Residencia' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_luggeo_ciures
IS  'Ciudad de Residencia' ;

COMMENT ON COLUMN SIEDU_EMPLEADO_EXTERNO.empe_tipoId
IS
  'Tipo Documento de Identificacion.CC-CEDULA DE CIUDADANIA,CD-CARTA DENTAL,CE-CEDULA EXTRANJERIA,LM-LIBRETA MILITAR,NI-NUMERO IDENTIFICACION TRIBUTARIA,NR-NO REPORTADO,PA-PASAPORTE,PJ-PERSONERIA JURIDICA,RM-REGISTRO MERCANTIL,TD-TARJETA DECADACTILAR,TI-TARJETA IDENTIDAD,TP-TARJETA PROFESIONAL,TS-TARJETA SEGURO SOCIAL,RC-REGISTRO CIVIL,NU-NUMERO UNICO DE IDENTIFICACION PERSONAL,CM-CARNET DIPLOMATICO'' ;' ;

  
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMPLEADO_EXTERNO_PK PRIMARY KEY ( empe_empe ) ;
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMP_EXT_LUGGEO_CIUNAC_FK FOREIGN KEY ( empe_luggeo_ciunac ) REFERENCES USR_REHU.LUGARES_GEOGRAFICOS ( CODIGO ) NOT DEFERRABLE ;
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMP_EXT_LUGGEO_CIURES_FK FOREIGN KEY ( empe_luggeo_ciures ) REFERENCES USR_REHU.LUGARES_GEOGRAFICOS ( CODIGO ) NOT DEFERRABLE ;
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMP_EXT_LUGGEO_PAINAC_FK FOREIGN KEY ( empe_luggeo_painac ) REFERENCES USR_REHU.LUGARES_GEOGRAFICOS ( CODIGO ) NOT DEFERRABLE ;
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMP_EXT_LUGGEO_PAIRES_FK FOREIGN KEY ( empe_luggeo_paires ) REFERENCES USR_REHU.LUGARES_GEOGRAFICOS ( CODIGO ) NOT DEFERRABLE ;

create sequence siedu_empleado_externo_seq nocache;

ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMPLEADO_EXTERNO_UK UNIQUE ( empe_nroId , empe_tipoId ) ;

ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMPLEADO_EXTERNO_CK CHECK (empe_tipoId          IN ( 'CC','CD','CE','LM','NI','NR','PA','PJ','RM','TD','TI','TP','TS','RC','NU','CM' )) ;
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMPLEADO_EXT_GRPSANG_CK CHECK (empe_grupo_sang  IN ( 'A','NR','O','B','AB' )) ;
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMPLEADO_EXT_FACRH_CK CHECK (empe_factorrh      IN ('PO','NE','NR')) ;
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMPLEADO_EXT_ESTCIV_CK CHECK (empe_estado_civil IN ('CA','UL','SO','VI','DI','SE','NR')) ;
ALTER TABLE SIEDU_EMPLEADO_EXTERNO ADD CONSTRAINT SIEDU_EMPLEADO_EXT_SEXO_CK CHECK (empe_sexo           IN ('BI','HF','FE','MA','NR','HM')) ;

