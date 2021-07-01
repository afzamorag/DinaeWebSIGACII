CREATE TABLE SIEDU_CERTIFICADO_EVENTO
(	cert_cert           NUMBER(6)       NOT NULL,  
	cert_pare           NUMBER(8)       NOT NULL, 
	cert_consecu_folio  NUMBER(6)       NOT NULL,  
	cert_acta           NUMBER(6)       NOT NULL, 
   cert_usu_crea    	  VARCHAR2 (30)   NOT NULL ,	
   cert_fecha_crea     DATE 		      NOT NULL ,
	cert_maquina_crea   VARCHAR2 (100)  NOT NULL,
	cert_ip_crea    	  VARCHAR2 (100)	NOT NULL,
   cert_usu_mod        VARCHAR2 (30),	
   cert_fecha_mod      DATE,
   cert_maquina_mod    VARCHAR2 (100),
	cert_ip_mod    	  VARCHAR2 (100)		
  );
  
COMMENT ON TABLE SIEDU_CERTIFICADO_EVENTO IS  'Tabla de Certificados de Participación a Eventos';
COMMENT ON COLUMN SIEDU_CERTIFICADO_EVENTO.cert_cert IS  'Secuencial identificador del certificado' ;
COMMENT ON COLUMN SIEDU_CERTIFICADO_EVENTO.cert_pare IS 'Participante Evento al que corresponde el certificado' ;
COMMENT ON COLUMN SIEDU_CERTIFICADO_EVENTO.cert_consecu_folio IS 'Número consecutivo de folio.' ;
COMMENT ON COLUMN SIEDU_CERTIFICADO_EVENTO.cert_acta IS 'Acta de certificacion del evento';

ALTER TABLE SIEDU_CERTIFICADO_EVENTO ADD CONSTRAINT SIEDU_CERTIFICADO_EVENTO_PK PRIMARY KEY ( cert_cert ) ;
ALTER TABLE SIEDU_CERTIFICADO_EVENTO ADD CONSTRAINT SIEDU_CERTI_EVEN_PARTI_EVEN_FK FOREIGN KEY ( cert_pare ) REFERENCES SIEDU_PARTICIPANTE_EVENTO ( pare_pare )  ;
ALTER TABLE SIEDU_CERTIFICADO_EVENTO ADD CONSTRAINT SIEDU_CERTI_ACTA_EVEN_FK FOREIGN KEY (cert_acta) REFERENCES SIEDU_ACTA_EVENTO (acta_acta);
create sequence siedu_certificado_evento_seq nocache;
