CREATE TABLE SIEDU_PAE_CAPACITACION
  (
    capa_capa                NUMBER (6)      NOT NULL ,
    capa_pae                 NUMBER (6)      NOT NULL ,
    capa_ude_fuerza_escu     NUMBER (1)      NOT NULL ,
    capa_ude_escu            NUMBER          NOT NULL ,
    capa_dom_proce           NUMBER (4)      NOT NULL ,
    capa_dom_estra           NUMBER (4)      NOT NULL ,
    capa_fuerza_carrera      NUMBER (1)      NOT NULL ,    
    capa_id_carrera          NUMBER          NOT NULL ,
    capa_nro_nece            NUMBER (4)      NOT NULL ,
    capa_estado              VARCHAR2 (30)   NOT NULL ,
    capa_total_personas      NUMBER (6)      default 0 NOT NULL ,
    capa_total_eventos       NUMBER (4)      default 0 NOT NULL ,
    capa_dom_modal           NUMBER (4)      NULL ,
    capa_ppto            	 VARCHAR2(2)      NULL ,
    capa_even_t1             NUMBER (6)      default 0 NOT NULL ,
    capa_pers_t1             NUMBER (6)      default 0 NOT NULL ,
    capa_even_t2             NUMBER (6)      default 0 NOT NULL ,
    capa_pers_t2             NUMBER (6)      default 0 NOT NULL ,
    capa_even_t3             NUMBER (6)      default 0 NOT NULL ,
    capa_pers_t3             NUMBER (6)      default 0 NOT NULL ,
    capa_even_t4             NUMBER (6)      default 0 NOT NULL ,
    capa_pers_t4             NUMBER (6)      default 0 NOT NULL,
    capa_usu_crea    	     VARCHAR2 (30)   NOT NULL ,	
    capa_fecha_crea          DATE 		     NOT NULL ,
	capa_maquina_crea        VARCHAR2 (100)  NOT NULL,
	capa_ip_crea    	     VARCHAR2 (100)	 NOT NULL,
    capa_usu_mod             VARCHAR2 (30),	
    capa_fecha_mod           DATE,
    capa_maquina_mod         VARCHAR2 (100),	
	capa_ip_mod    		     VARCHAR2 (100),
    capa_externo             VARCHAR2(2) default 'NO' NOT NULL 
  ) ;
  
ALTER TABLE SIEDU_PAE_CAPACITACION ADD CONSTRAINT SIEDU_PAE_CAPACITA_PK PRIMARY KEY ( capa_capa ) ;
ALTER TABLE SIEDU_PAE_CAPACITACION ADD CONSTRAINT SIEDU_PAE_CAPACITA_UK UNIQUE ( capa_pae , capa_ude_fuerza_escu , capa_ude_escu , capa_fuerza_carrera, capa_id_carrera , capa_dom_proce , capa_dom_estra ) ;
ALTER TABLE SIEDU_PAE_CAPACITACION ADD CONSTRAINT SIEDU_PAE_CAPACITACION_PAE_FK FOREIGN KEY ( capa_pae ) REFERENCES SIEDU_PAE ( pae_pae ) ;
ALTER TABLE SIEDU_PAE_CAPACITACION ADD CONSTRAINT SIEDU_CAPACITACION_ESCUELA_FK FOREIGN KEY ( capa_ude_fuerza_escu, capa_ude_escu ) REFERENCES USR_REHU.UNIDADES_DEPENDENCIA ( FUERZA, CONSECUTIVO ) ;
ALTER TABLE SIEDU_PAE_CAPACITACION ADD CONSTRAINT SIEDU_PAE_CAPACITA_CARRERAS_FK FOREIGN KEY ( capa_fuerza_carrera, capa_id_carrera ) REFERENCES USR_EDUC.CARRERAS ( FUERZA, ID_CARRERA ) ;

ALTER TABLE SIEDU_PAE_CAPACITACION ADD CONSTRAINT SIEDU_PAE_CAPACITA_ESTADO_CK CHECK (capa_estado IN ('APROBADO', 'PENDIENTE', 'NO APROBADO')) ;
ALTER TABLE SIEDU_PAE_CAPACITACION ADD CONSTRAINT SIEDU_PAE_CAPACITA_EXTERNO_CK CHECK (capa_externo IN ('SI', 'NO')) ;
alter table SIEDU_PAE_CAPACITACION add constraint SIEDU_PAE_CAPACITA_PPTO_CK CHECK ( capa_ppto in ('SI', 'NO'));

create sequence siedu_pae_capacitacion_seq NOCACHE;

COMMENT ON TABLE SIEDU_PAE_CAPACITACION
IS
  'Tabla de definición del PAE de Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_capa
IS
  'Número de registro de PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_pae
IS
  'Identificador del PAE' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_ude_fuerza_escu
IS
  'Identificador de la Fuerza de la Escuela' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_ude_escu
IS
  'Identificador de la Escuela. Unidades_Dependencia (ESCUELA)' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_dom_proce
IS
  'Proceso ( DOMINIO PROCESO )' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_dom_estra
IS
  'Estrategia ( DOMINIO ESTRATEGIA )' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_fuerza_carrera
IS
  'Identificador de Fuerza de la Carrera' ;  
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_id_carrera
IS
  'Identificador de la Carrera' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_nro_nece
IS
  'Número de cupos necesarios a ser cubiertos para la vigencia del PAE' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_estado
IS
  'Estado de registro del PAE Capacitación. APROBADO, PENDIENTE, NO APROBADO.' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_total_personas
IS
  'Total de cupos incluidos en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_total_eventos
IS
  'Número de eventos incluidos en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_dom_modal
IS
  'Modalidad ( DOMINIO METODOLOGIA )' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_ppto IS  'Indica si el registro haría parte del Presupuesto SI/NO' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_even_t1
IS
  'Número de eventos para el trimestre 1 incluido en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_pers_t1
IS
  'Número de cupos para el trimestre 1 incluido en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_even_t2
IS
  'Número de eventos para el trimestre 2  incluido en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_pers_t2
IS
  'Número de cupos para el trimestre 2 incluido en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_even_t3
IS
  'Número de eventos para el trimestre 3 incluido en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_pers_t3
IS
  'Número de cupos para el trimestre 3 incluido en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_even_t4
IS
  'Número de eventos para el trimestre 4 incluido en el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PAE_CAPACITACION.capa_pers_t4
IS
  'Número de cupos para el trimestre 4 incluido en el PAE Capacitación' ;
Comment on column SIEDU_PAE_CAPACITACION.capa_externo is 'Indica si la Capacitación es impartida por una entidad externa (SI/NO)';

  
