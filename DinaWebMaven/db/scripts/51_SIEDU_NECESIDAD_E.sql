CREATE TABLE SIEDU_NECESIDAD_E
  (
    enec_enec              NUMBER (6) NOT NULL ,
    enec_vigencia          VARCHAR2(7) NOT NULL ,
    enec_ude_fuerza_region NUMBER (1) NOT NULL ,
    enec_ude_region        NUMBER NOT NULL ,
    enec_ude_fuerza_ufisi  NUMBER (1) NOT NULL ,
    enec_ude_ufisi         NUMBER NOT NULL ,
    enec_ude_fuerza_udepe  NUMBER (1) NOT NULL ,
    enec_ude_udepe         NUMBER NOT NULL ,
    enec_fuerza_carrera    NUMBER (1) NOT NULL ,
    enec_id_carrera        NUMBER NOT NULL ,
    enec_origen            VARCHAR2 (10) NOT NULL ,	
    enec_nro               NUMBER (6) NOT NULL,
	enec_enviado           VARCHAR2(1) DEFAULT 'N' NOT NULL
  ) ;

COMMENT ON TABLE SIEDU_NECESIDAD_E
IS  'Tabla correspondiente a las necesidades de cupos definidas por la entidad externa' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_enec 
IS  'Secuencial de creación del registro de necesidad externa' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_vigencia
IS  'Cadena que identifica la vigencia para la cual se define la necesidad' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_ude_fuerza_region
IS  'Identificador de la Fuerza de la Región' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_ude_region
IS  'Identificador de la Región a la que corresponde la necesidad. Unidades_Dependencia (REGION)' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_ude_fuerza_ufisi
IS  'Identificador de Fuerza de la Unidad Física a la que corresponde la necesidad.' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_ude_ufisi
IS  'Identificador de la Unidad Física a la que corresponde la necesidad. Unidades_Dependencia ( DIRECCION, OFICINA ASESORA, DEPARTAMENTO DE POLICIA, METROPOLITANA, ESCUELA, REGION)' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_ude_fuerza_udepe
IS  'Identificador de Fuerza de la Unidad Depende a la que corresponde la necesidad.' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_ude_udepe
IS  'Identificador de la Unidad Depende a la que corresponde la necesidad. Unidades_Dependencia ( DIRECCION, OFICINA ASESORA )' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_fuerza_carrera
IS  'Identificador de Fuerza de la Carrera' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_id_carrera
IS  'Identificador de la Carrera' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_nro
IS  'Número de cupos necesarios' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_origen
IS  'Orígen del registro, puede ser la DITAH, o el correspondiente al usuario que realice la inserción.' ;

COMMENT ON COLUMN SIEDU_NECESIDAD_E.enec_enviado
IS  'Indica si el registro ya fué tenido en cuenta (S/N) y transmitido al SIGAC' ;

ALTER TABLE SIEDU_NECESIDAD_E ADD CONSTRAINT SIEDU_NECESIDE_PK PRIMARY KEY ( enec_enec ) ;
--ALTER TABLE SIEDU_NECESIDAD_E ADD CONSTRAINT SIEDU_NECESIDE_UK UNIQUE ( enec_ude_fuerza_region , enec_ude_region , enec_ude_fuerza_ufisi , enec_ude_ufisi , enec_ude_fuerza_udepe , enec_ude_udepe , enec_fuerza_carrera , enec_id_carrera ) ;
ALTER TABLE SIEDU_NECESIDAD_E ADD CONSTRAINT SIEDU_NECESIDE_CARRERAS_FK FOREIGN KEY ( enec_fuerza_carrera, enec_id_carrera ) REFERENCES USR_EDUC.CARRERAS ( FUERZA, ID_CARRERA ) ;
ALTER TABLE SIEDU_NECESIDAD_E ADD CONSTRAINT SIEDU_NECESIDE_UDE_REGION_FK FOREIGN KEY ( enec_ude_fuerza_region, enec_ude_region ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_NECESIDAD_E ADD CONSTRAINT SIEDU_NECESIDE_UDE_UDEPE_FK FOREIGN KEY ( enec_ude_fuerza_udepe, enec_ude_udepe ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_NECESIDAD_E ADD CONSTRAINT SIEDU_NECESIDE_UDE_UFISI_FK FOREIGN KEY ( enec_ude_fuerza_ufisi, enec_ude_ufisi ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_NECESIDAD_E ADD CONSTRAINT SIEDU_NECESIDE_ENVIADO CHECK ( enec_enviado in ('S','N') );

CREATE SEQUENCE  SIEDU_NECESIDAD_E_SEQ  NOCACHE;
