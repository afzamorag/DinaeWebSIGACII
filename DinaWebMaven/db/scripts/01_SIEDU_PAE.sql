CREATE TABLE SIEDU_PAE
  (
    pae_pae             	NUMBER (6)      				NOT NULL ,
    pae_vigencia        	VARCHAR2 (7)    				NOT NULL ,
    pae_estado          	VARCHAR2 (1)    				NOT NULL,
	pae_nece_ya_importada 	VARCHAR2 (2) 	DEFAULT 'NO' 	NOT NULL
    pae_usu_crea    		VARCHAR2 (30)   				NOT NULL ,	
    pae_fecha_crea      	DATE 		    				NOT NULL ,
	pae_maquina_crea    	VARCHAR2 (100)  				NOT NULL,	
	pae_ip_crea    			VARCHAR2 (100)  				NOT NULL,
    pae_usu_mod         	VARCHAR2 (30),	
    pae_fecha_mod       	DATE,
    pae_maquina_mod     	VARCHAR2 (100),
	pae_ip_mod     			VARCHAR2 (100)	
) ;
  
ALTER TABLE SIEDU_PAE ADD CONSTRAINT SIEDU_PAE_PK PRIMARY KEY ( pae_pae ) ;
  
ALTER TABLE SIEDU_PAE ADD CONSTRAINT SIEDU_PAE_ESTADO_CK CHECK (pae_estado IN ('C' , 'M', 'D')) ;

alter table siedu_pae add constraint siedu_pae_nece_importada_ck check ( pae_nece_ya_importada IN ( 'SI', 'NO' ) );

create sequence siedu_pae_seq;

COMMENT ON TABLE SIEDU_PAE IS  'Registro de PAE - Plan Anual de Educación de la vigencia' ;
COMMENT ON COLUMN SIEDU_PAE.pae_pae IS  'Secuencial identificacor único del registro' ;
COMMENT ON COLUMN SIEDU_PAE.pae_vigencia IS  'Vigencia a la que corresponde el PAE' ;
COMMENT ON COLUMN SIEDU_PAE.pae_estado IS  'Estado del PAE. C-En construcción , M-En modificación o  D-Cerrada' ;
COMMENT ON COLUMN SIEDU_PAE.pae_nece_ya_importada IS 'Indica si los registros de necesidad externa ya han sido importados para esta vigencia.' ;
  

