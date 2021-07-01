CREATE TABLE SIEDU_PRESUPUESTO
  (
    ppto_ppto           NUMBER (6)      NOT NULL ,
    ppto_capa           NUMBER (6)      NOT NULL ,
    ppto_nro_contra     VARCHAR2 (200)  NOT NULL ,
    ppto_vlr_contra     NUMBER (12,2)   NOT NULL ,
    ppto_fecha_contra   DATE ,
    ppto_dom_recu       NUMBER (4) ,
    ppto_origen         VARCHAR2 (10),
    ppto_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    ppto_fecha_crea     DATE 		    NOT NULL ,
	ppto_maquina_crea   VARCHAR2 (100)  NOT NULL,
	ppto_ip_crea    	VARCHAR2 (100)	NOT NULL,
    ppto_usu_mod        VARCHAR2 (30),	
    ppto_fecha_mod      DATE,
    ppto_maquina_mod    VARCHAR2 (100),
	ppto_ip_mod    		VARCHAR2 (100)	
  );
  
ALTER TABLE SIEDU_PRESUPUESTO ADD CONSTRAINT SIEDU_PRESUPUESTO_PK PRIMARY KEY ( ppto_ppto ) ;
ALTER TABLE SIEDU_PRESUPUESTO ADD CONSTRAINT SIEDU_PRESUPUESTO_UK UNIQUE ( ppto_capa , ppto_nro_contra ) ;
ALTER TABLE SIEDU_PRESUPUESTO ADD CONSTRAINT SIEDU_PRESUPU_PAE_FK FOREIGN KEY ( ppto_capa ) REFERENCES SIEDU_PAE_CAPACITACION ( capa_capa ) ;

create sequence siedu_presupuesto_seq;
  
COMMENT ON TABLE SIEDU_PRESUPUESTO
IS
  'Tabla de definición del Presupuesto para el PAE Capacitación' ;
COMMENT ON COLUMN SIEDU_PRESUPUESTO.ppto_ppto
IS
  'Secuencial identificador del registro ' ;
COMMENT ON COLUMN SIEDU_PRESUPUESTO.ppto_capa
IS
  'Identificador del PAE Capacitación al que hace referencia' ;
COMMENT ON COLUMN SIEDU_PRESUPUESTO.ppto_nro_contra
IS
  'Número de Contrato' ;
COMMENT ON COLUMN SIEDU_PRESUPUESTO.ppto_vlr_contra
IS
  'Valor del Contrato' ;
COMMENT ON COLUMN SIEDU_PRESUPUESTO.ppto_fecha_contra
IS
  'Fecha de Contrato' ;
COMMENT ON COLUMN SIEDU_PRESUPUESTO.ppto_origen
IS
  'Orígen del registro, correspondiente al usuario que realice la inserción.' ;
COMMENT ON COLUMN SIEDU_PRESUPUESTO.ppto_dom_recu
IS
  'Recurso asociado al presupuesto.  ( DOMINIO RECURSO )' ;

