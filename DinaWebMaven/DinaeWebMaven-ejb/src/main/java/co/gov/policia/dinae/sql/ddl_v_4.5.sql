drop table EVENTO_PROYECTO cascade constraint;
CREATE TABLE EVENTO_PROYECTO
  (
    ID_EVENTO_PROYECTO NUMBER NOT NULL ,
    ID_FUENTE_PROYECTO NUMBER NOT NULL,
    COSTO              NUMBER(15,2) NOT NULL ,
    ID_PROYECTO        NUMBER NOT NULL ,
    TITULO_EVENTO      VARCHAR2(256 ) NOT NULL,
    OBJETIVO_EVENTO    VARCHAR2(512) NOT NULL,
    FECHA_REGISTRO DATE  NOT NULL,
    ID_USUARIO_ROL NUMBER NOT NULL,
    CODIGO_CIUDAD      VARCHAR2(6),
    NOMBRE_CIUDAD      VARCHAR2(100),
    CONSTRAINT EVENTO_PROYECTO_PK PRIMARY KEY (ID_EVENTO_PROYECTO),
    CONSTRAINT EVENTO_PROYECTO_FK1 FOREIGN KEY (ID_PROYECTO) REFERENCES PROYECTO (ID_PROYECTO) ,
    CONSTRAINT EVENTO_USUARIOROL_FK FOREIGN KEY (ID_USUARIO_ROL) REFERENCES USUARIO_ROL (ID_USUARIO_ROL) ,
    CONSTRAINT EVENTO_FUENTE_PRO_FK FOREIGN KEY (ID_FUENTE_PROYECTO) REFERENCES FUENTE_PROYECTO (ID_FUENTE_PROYECTO)
  );

drop table TIPO_GASTO_EVENTO cascade constraint;
CREATE TABLE TIPO_GASTO_EVENTO
  (
    ID_GASTO_EVENTO    NUMBER NOT NULL ,
    ID_EVENTO_PROYECTO NUMBER NOT NULL ,
    ID_TIPO_GASTO      NUMBER NOT NULL ,    
    ID_USUARIO_ROL NUMBER NOT NULL ,
    MAQUINA        VARCHAR2(100),
	FECHA_REGISTRO DATE NOT NULL,
    CONSTRAINT TIPO_GASTO_EVENTO_PK PRIMARY KEY (ID_GASTO_EVENTO),
	CONSTRAINT TIPO_GASTO_USUARIOROL_FK FOREIGN KEY (ID_USUARIO_ROL) REFERENCES USUARIO_ROL (ID_USUARIO_ROL) ,
	CONSTRAINT TIPO_GASTO_EVENTO_PRO_FK FOREIGN KEY (ID_EVENTO_PROYECTO) REFERENCES EVENTO_PROYECTO (ID_EVENTO_PROYECTO) ,
	CONSTRAINT TIPO_GASTO_CONSTANTES_FK FOREIGN KEY (ID_TIPO_GASTO) REFERENCES CONSTANTES (ID_CONSTANTES)
);

DROP TABLE INFORME_AVANCE CASCADE CONSTRAINTS;
CREATE TABLE INFORME_AVANCE
  (
    ID_INFORME_AVANCE NUMBER NOT NULL ,
    ID_PROYECTO       NUMBER NOT NULL ,
    FECHA_INICIO DATE NOT NULL,
    FECHA_FINALIZACION DATE NOT NULL,
    ID_USUARIO_ROL NUMBER NOT NULL,
    FECHA_REGISTRO TIMESTAMP NOT NULL ,
	NOMBRE_ARCHIVO              VARCHAR2(100),
	NOMBRE_ARCHIVO_FISICO       VARCHAR2(100),
	TIPO_CONTENIDO_ARCHIVO      VARCHAR2(200),
    CONSTRAINT INFORME_AVANCE_PK PRIMARY KEY (ID_INFORME_AVANCE),
    CONSTRAINT INFO_AVAN_PROY_FK FOREIGN KEY (ID_PROYECTO) REFERENCES PROYECTO (ID_PROYECTO),
	CONSTRAINT USUROL_USUARIOROL_FK FOREIGN KEY (ID_USUARIO_ROL) REFERENCES USUARIO_ROL (ID_USUARIO_ROL)	 	
  );

DROP TABLE RESULTADOS_INVESTIGACION CASCADE CONSTRAINTS;
CREATE TABLE RESULTADOS_INVESTIGACION
  (
    ID_RESULTADO_INVESTIGACION NUMBER  NOT NULL,
    ID_TIPO_PRODUCTO           NUMBER NOT NULL ,
    DESCRIPCION                VARCHAR2(512),
    FECHA_REGISTRO TIMESTAMP NOT NULL ,
    ID_USUARIO_ROL NUMBER NOT NULL ,
    ID_PROYECTO    NUMBER NOT NULL ,
	CONSTRAINT RESULTADOS_INVESTIGACION_PK PRIMARY KEY (ID_RESULTADO_INVESTIGACION),    
	CONSTRAINT RESUL_AVAN_PROY_FK FOREIGN KEY (ID_PROYECTO) REFERENCES PROYECTO (ID_PROYECTO),
	CONSTRAINT RESUL_USUARIOROL_FK FOREIGN KEY (ID_USUARIO_ROL) REFERENCES USUARIO_ROL (ID_USUARIO_ROL)	,
	CONSTRAINT RESUL_CONSTANTES_FK FOREIGN KEY (ID_TIPO_PRODUCTO) REFERENCES CONSTANTES (ID_CONSTANTES)	 	
  );

DROP TABLE ACTIVIDADES_REALIZADAS_PROY CASCADE CONSTRAINTS; 
  CREATE TABLE ACTIVIDADES_REALIZADAS_PROY
  (
    ID_ACTIVIDAD_REALIZADA_PROY NUMBER NOT NULL ,
    DESCRIPCION_ACTIVIDAD       VARCHAR2(512) NOT NULL ,
    ID_COMPROMISO_PROYECTO      NUMBER NOT NULL ,
    ID_PROYECTO    NUMBER NOT NULL ,
    FECHA_REGISTRO DATE NOT NULL ,
    ID_USUARIO_ROL NUMBER NOT NULL ,
    MAQUINA        VARCHAR2(100),
    CONSTRAINT ACTIV_REALIZAD_PROYECTO_PK PRIMARY KEY (ID_ACTIVIDAD_REALIZADA_PROY),
	CONSTRAINT PROYECTO_ACTI_PROY_FK FOREIGN KEY (ID_PROYECTO) REFERENCES PROYECTO (ID_PROYECTO),
	CONSTRAINT USUARIO_ACTI_PROY_FK FOREIGN KEY (ID_USUARIO_ROL) REFERENCES USUARIO_ROL (ID_USUARIO_ROL),
	CONSTRAINT COMPROMISO_ACTI_PROY_FK FOREIGN KEY (ID_COMPROMISO_PROYECTO) REFERENCES COMPROMISO_PROYECTO (ID_COMPROMISO_PROYECTO)
  );

  CREATE SEQUENCE SEC_ACTIVIDADES_REALIZADAS_PRO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

DROP TABLE RESULTADOS_ALCANZADOS_PROY CASCADE CONSTRAINTS; 
CREATE TABLE RESULTADOS_ALCANZADOS_PROY
  (
    ID_RESULTADOS_ALCANZADOS_PROY NUMBER NOT NULL ,
    RESULTADO_ALCANZADO       VARCHAR2(512) NOT NULL ,
    ID_COMPROMISO_PROYECTO      NUMBER NOT NULL ,
    ID_PROYECTO    NUMBER NOT NULL ,
    FECHA_REGISTRO DATE NOT NULL ,
    ID_USUARIO_ROL NUMBER NOT NULL ,
    MAQUINA        VARCHAR2(100),
    CONSTRAINT RESULTADOS_ALCANZADOS_PROY_PK PRIMARY KEY (ID_RESULTADOS_ALCANZADOS_PROY),
	CONSTRAINT PROYECTO_RESUL_ALCA_PROY_FK FOREIGN KEY (ID_PROYECTO) REFERENCES PROYECTO (ID_PROYECTO),
	CONSTRAINT USUARIO_RESUL_ALCA_PROY_FK FOREIGN KEY (ID_USUARIO_ROL) REFERENCES USUARIO_ROL (ID_USUARIO_ROL),
	CONSTRAINT COMPROMISO_RESUL_ALCA_PROY_FK FOREIGN KEY (ID_COMPROMISO_PROYECTO) REFERENCES COMPROMISO_PROYECTO (ID_COMPROMISO_PROYECTO)
  );

 CREATE SEQUENCE SEC_RESULTADOS_ALCANZADOS_PRO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;