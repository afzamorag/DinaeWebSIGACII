CREATE TABLE SIEDU_NECESIDAD
  (
    nece_nece               NUMBER (6)      NOT NULL ,
    nece_pae                NUMBER (6)      NOT NULL ,
    nece_ude_fuerza_region  NUMBER (1)      NOT NULL ,
    nece_ude_region         NUMBER (6)      NOT NULL ,
    nece_ude_fuerza_ufisi   NUMBER (1)      NOT NULL ,    
    nece_ude_ufisi          NUMBER (6)      NOT NULL ,
    nece_ude_fuerza_udepe   NUMBER (1)      NOT NULL ,    
    nece_ude_udepe          NUMBER (6)      NOT NULL ,
    nece_fuerza_carrera     NUMBER (1)      NOT NULL ,    
    nece_id_carrera         NUMBER (6)      NOT NULL ,
    nece_nro                NUMBER (6)      NOT NULL ,
    nece_estado             VARCHAR2 (1)    NOT NULL ,
    nece_dom_proce          NUMBER (4) ,
    nece_dom_estra          NUMBER (4) ,
    nece_origen             VARCHAR2 (10)   NOT NULL ,
    nece_nove               NUMBER (4)      NOT NULL,
    nece_usu_crea    	    VARCHAR2 (30)   NOT NULL ,	
    nece_fecha_crea         DATE 		    NOT NULL ,
	nece_maquina_crea       VARCHAR2 (100)  NOT NULL,
	nece_ip_crea    	    VARCHAR2 (100)	NOT NULL,
    nece_usu_mod            VARCHAR2 (30),	
    nece_fecha_mod          DATE,
    nece_maquina_mod        VARCHAR2 (100),
	nece_ip_mod    		    VARCHAR2 (100)
  ) ;
  
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESIDAD_PK PRIMARY KEY ( nece_nece ) ;
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESID_UK UNIQUE ( nece_pae, nece_ude_fuerza_region, nece_ude_region , nece_ude_fuerza_ufisi, nece_ude_ufisi , nece_ude_fuerza_udepe , nece_ude_udepe ,nece_fuerza_carrera, nece_id_carrera ) ;
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESIDAD_PAE_FK FOREIGN KEY ( nece_pae ) REFERENCES SIEDU_PAE ( pae_pae ) ;
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESIDAD_NOVEDAD_PAE_FK FOREIGN KEY ( nece_nove ) REFERENCES SIEDU_NOVEDAD_PAE ( nove_nove ) ;
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESIDAD_CARRERAS_FK FOREIGN KEY ( nece_fuerza_carrera, nece_id_carrera ) REFERENCES USR_EDUC.CARRERAS ( FUERZA, ID_CARRERA ) ;
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESIDAD_UDE_REGION_FK FOREIGN KEY ( nece_ude_fuerza_region, nece_ude_region ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESIDAD_UDE_UDEPE_FK FOREIGN KEY ( nece_ude_fuerza_udepe, nece_ude_udepe ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESIDAD_UDE_UFISI_FK FOREIGN KEY ( nece_ude_fuerza_ufisi, nece_ude_ufisi ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;

  
ALTER TABLE SIEDU_NECESIDAD ADD CONSTRAINT SIEDU_NECESIDAD_ESTADO CHECK (nece_estado IN ( 'A','P', 'N')) ;

create sequence siedu_necesidad_seq;


COMMENT ON TABLE SIEDU_NECESIDAD
IS
  'Tabla correspondiente a las necesidades de cupos a ser tenidas en cuenta en la planeación' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_nece
IS
  'Secuencial de creación del registro de necesidad' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_pae
IS
  'Identificador del PAE al que corresponde la necesidad' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_ude_fuerza_region
IS
  'Identificador de la Fuerza de la Región' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_ude_region
IS
  'Identificador de la Región a la que corresponde la necesidad. Unidades_Dependencia (REGION)' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_ude_fuerza_ufisi
IS
  'Identificador de Fuerza de la Unidad Física a la que corresponde la necesidad.' ;  
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_ude_ufisi
IS
  'Identificador de la Unidad Física a la que corresponde la necesidad. Unidades_Dependencia ( DIRECCION, OFICINA ASESORA, DEPARTAMENTO DE POLICIA, METROPOLITANA, ESCUELA, REGION)' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_ude_fuerza_udepe
IS
  'Identificador de Fuerza de la Unidad Depende a la que corresponde la necesidad.' ;  
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_ude_udepe
IS
  'Identificador de la Unidad Depende a la que corresponde la necesidad. Unidades_Dependencia ( DIRECCION, OFICINA ASESORA )' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_fuerza_carrera
IS
  'Identificador de Fuerza de la Carrera' ;  
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_id_carrera
IS
  'Identificador de la Carrera' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_nro
IS
  'N�mero de cupos necesarios' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_estado
IS
  'Estado de registro de necesidad. A-Aprobado, P-Pendiente, N-No aprobado.' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_dom_proce
IS
  'Proceso ( DOMINIO PROCESO )' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_dom_estra
IS
  'Estrategia ( DOMINIO ESTRATEGIA )' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_origen
IS
  'Or�gen del registro, puede ser la DITAH, o el correspondiente al usuario que realice la inserción.' ;
COMMENT ON COLUMN SIEDU_NECESIDAD.nece_nove
IS
  'Identificador de la novedad que relaciona el acto administrativo que soporta el registro de necesidad.' ;

