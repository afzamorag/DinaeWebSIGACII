CREATE TABLE SIEDU_EVENTO_CATEGORIA
  (
    evca_evca      		NUMBER (6) NOT NULL ,
    evca_even      		NUMBER (8) NOT NULL ,
    evca_fuerza    		NUMBER (1) NOT NULL ,
    evca_categoria_id	NUMBER NOT NULL ,
    evca_orden     		NUMBER (2) NOT NULL,
	evca_usu_crea    	VARCHAR2 (30)   NOT NULL ,	
    evca_fecha_crea     DATE 		    NOT NULL ,
	evca_maquina_crea   VARCHAR2 (100)  NOT NULL,
	evca_ip_crea    	VARCHAR2 (100)	NOT NULL,
    evca_usu_mod        VARCHAR2 (30),	
    evca_fecha_mod      DATE,
    evca_maquina_mod    VARCHAR2 (100),
	evca_ip_mod    		VARCHAR2 (100)
  ) ;
  
COMMENT ON TABLE SIEDU_EVENTO_CATEGORIA
IS
  'Categorías de Perfil requerido para el evento' ;
COMMENT ON COLUMN SIEDU_EVENTO_CATEGORIA.evca_evca
IS
  'Identificador del registro de Categoría de Perfil requerido para el evento' ;
COMMENT ON COLUMN SIEDU_EVENTO_CATEGORIA.evca_even
IS
  'Identificador del Evento' ;
COMMENT ON COLUMN SIEDU_EVENTO_CATEGORIA.evca_fuerza
IS
  'Id de Fuerza de la Categoría' ;
COMMENT ON COLUMN SIEDU_EVENTO_CATEGORIA.evca_categoria_id
IS
  'Identificador de Categoría' ;
COMMENT ON COLUMN SIEDU_EVENTO_CATEGORIA.evca_orden
IS
  'Orden de las Categorías relacionadas con el Evento.' ;
  
ALTER TABLE SIEDU_EVENTO_CATEGORIA ADD CONSTRAINT SIEDU_EVENTO_CATEGORIA_PK PRIMARY KEY ( evca_evca ) ;
ALTER TABLE SIEDU_EVENTO_CATEGORIA ADD CONSTRAINT SIEDU_EVENTO_CATEG_CATEG_FK FOREIGN KEY ( evca_fuerza, evca_categoria_id ) REFERENCES USR_REHU.CATEGORIAS ( FUERZA, ID_CATEGORIA ) ;
ALTER TABLE SIEDU_EVENTO_CATEGORIA ADD CONSTRAINT SIEDU_EVENTO_CATEG_EVENTO_FK FOREIGN KEY ( evca_even ) REFERENCES SIEDU_EVENTO ( even_even ) ;
ALTER TABLE SIEDU_EVENTO_CATEGORIA ADD CONSTRAINT SIEDU_EVENTO_CATEGORIA_UK UNIQUE ( evca_even , evca_categoria_id , evca_fuerza ) ;


CREATE SEQUENCE SIEDU_EVENTO_CAT_SEQ_GEN NOCACHE;
