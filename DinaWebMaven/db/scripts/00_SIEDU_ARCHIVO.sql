CREATE TABLE SIEDU_ARCHIVO
  (
    arch_id           NUMBER (8) NOT NULL ,
    arch_nombre       VARCHAR2 (100) NOT NULL ,
    arch_ext          VARCHAR2 (10) NOT NULL ,
    arch_content_type VARCHAR2 (50) ,
    arch_titulo       VARCHAR2 (100),
    arch_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    arch_fecha_crea     DATE 		    NOT NULL ,
	arch_maquina_crea   VARCHAR2 (100)  NOT NULL,	
	arch_ip_crea    	VARCHAR2 (100)	NOT NULL,
    arch_usu_mod        VARCHAR2 (30),	
    arch_fecha_mod      DATE,
    arch_maquina_mod    VARCHAR2 (100),	 
	arch_ip_mod    		VARCHAR2 (100)	
  ) ;
  
ALTER TABLE SIEDU_ARCHIVO ADD CONSTRAINT SIEDU_ARCHIVO_PK PRIMARY KEY ( arch_id ) ;

create sequence siedu_archivo_seq;

COMMENT ON TABLE SIEDU_ARCHIVO IS 'Tabla de archivos de diferentes tipos';
  
COMMENT ON COLUMN SIEDU_ARCHIVO.arch_id IS 'Secuencial identificador único del archivo' ;
COMMENT ON COLUMN SIEDU_ARCHIVO.arch_nombre IS 'Nombre del archivo' ;
COMMENT ON COLUMN SIEDU_ARCHIVO.arch_ext IS 'Estensión del archivo' ;
COMMENT ON COLUMN SIEDU_ARCHIVO.arch_content_type IS 'Tipo de contenido del archivo' ;
COMMENT ON COLUMN SIEDU_ARCHIVO.arch_titulo IS 'Título del archivo' ;

