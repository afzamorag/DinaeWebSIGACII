-- Generado por Oracle SQL Developer Data Modeler 3.1.0.699
--   en:        2013-09-19 07:49:00 CDT
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g
CREATE TABLE AREA_CIENCIA_TECNOLOGIA 
    ( 
     ID_AREA_CIENCIA_TECNOLOGIA NUMBER  NOT NULL , 
     NOMBRE VARCHAR2 (50)  NOT NULL 
    ) 
;



ALTER TABLE AREA_CIENCIA_TECNOLOGIA 
    ADD CONSTRAINT AREA_CIENCIA_TECNOLOGIA_PK PRIMARY KEY ( ID_AREA_CIENCIA_TECNOLOGIA ) ;

CREATE SEQUENCE SEC_AREA_CIENCIA_TECNOLOGIA
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE CONSTANTES 
    ( 
     ID_CONSTANTES NUMBER  NOT NULL , 
     TIPO VARCHAR2 (20)  NOT NULL , 
     CODIGO VARCHAR2 (10)  NOT NULL , 
     VALOR VARCHAR2 (100)  NOT NULL 
    ) 
;



ALTER TABLE CONSTANTES 
    ADD CONSTRAINT VALOR_SELECCION_GENERICO_PK PRIMARY KEY ( ID_CONSTANTES ) ;

CREATE SEQUENCE SEC_CONSTANTES
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE FUNCIONARIO_NECESIDAD 
    ( 
     ID_FUNCIONARIO_NECESIDAD NUMBER  NOT NULL , 
     IDENTIFICACION VARCHAR2 (50)  NOT NULL , 
     NOMBRE_COMPLETO VARCHAR2 (100)  NOT NULL , 
     CORREO VARCHAR2 (100)  NOT NULL , 
     GRADO VARCHAR2 (100)  NOT NULL , 
     TELEFONO VARCHAR2 (50)  NOT NULL , 
     ID_PROPUESTA_NECESIDAD NUMBER NOT NULL
    ) 
;



ALTER TABLE FUNCIONARIO_NECESIDAD 
    ADD CONSTRAINT FUNCIONARIO_NECESIDAD_PK PRIMARY KEY ( ID_FUNCIONARIO_NECESIDAD ) ;

CREATE SEQUENCE SEC_FUNCIONARIO_NECESIDAD
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE COMPROMISO_PERIODO 
    ( 
     ID_COMPROMISO_PERIODO NUMBER  NOT NULL , 
     NUMERO_INCREMENTA NUMBER (4),
     FECHA DATE  NOT NULL , 
     TIPO_COMPROMISO NUMBER , 
     ID_PERIODO NUMBER 
    ) 
;



ALTER TABLE COMPROMISO_PERIODO 
    ADD CONSTRAINT COMPROMISO_PERIODO_PK PRIMARY KEY ( ID_COMPROMISO_PERIODO ) ;

CREATE SEQUENCE SEC_COMPROMISO_PERIODO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE LINEA 
    ( 
     ID_LINEA NUMBER  NOT NULL , 
     NOMBRE VARCHAR2 (100)  NOT NULL , 
     ID_AREA_CIENCIA_TECNOLOGIA NUMBER 
    ) 
;

ALTER TABLE LINEA 
    ADD CONSTRAINT "LINEA PK" PRIMARY KEY ( ID_LINEA ) ;

CREATE SEQUENCE SEC_LINEA
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE OPCION_MENU 
    ( 
     ID_OPCION_MENU NUMBER  NOT NULL , 
     NOMBRE VARCHAR2 (50)  NOT NULL , 
     TIPO_ACCESO CHAR (1)  NOT NULL , 
     ACCION VARCHAR2 (100) , 
     ID_OPCION_MENU2 NUMBER , 
     ORDEN NUMBER (2) 
    ) 
;



ALTER TABLE OPCION_MENU 
    ADD CONSTRAINT OPCION_MENU_PK PRIMARY KEY ( ID_OPCION_MENU ) ;

CREATE SEQUENCE SEC_OPCION_MENU
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE PERIODO 
    ( 
     ID_PERIODO NUMBER  NOT NULL , 
     ANIO NUMBER (4)  NOT NULL , 
     DESCRIPCION VARCHAR2 (500)  NOT NULL , 
     FECHA_INICIO DATE  NOT NULL , 
     FECHA_FIN DATE  NOT NULL , 
     CANTIDAD NUMBER (2)  NOT NULL , 
     ESTADO CHAR (1)  NOT NULL 
    ) 
;



ALTER TABLE PERIODO 
    ADD CONSTRAINT PERIODO_PK PRIMARY KEY ( ID_PERIODO ) ;

CREATE SEQUENCE SEC_PERIODO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE PROPUESTA_NECESIDAD 
    ( 
     ID_PROPUESTA_NECESIDAD NUMBER  NOT NULL , 
     TEMA VARCHAR2 (512)  NOT NULL , 
     RESULTADO_ESPERADO VARCHAR2 (700)  NOT NULL , 
     ID_LINEA NUMBER NOT NULL, 
     ID_PERIODO NUMBER NOT NULL, 
     IDENTIFICADOR_USUARIO_MODIF VARCHAR2 (50)  NOT NULL , 
     FECHA_REGISTRO DATE NOT NULL , 
     ID_CONSTANTES NUMBER NOT NULL, 
     ID_UNIDAD_POLICIAL NUMBER NOT NULL,
     FUENTE_INFORMACION VARCHAR2 (700) , 
     POSIBLE_ACTIVIDAD VARCHAR2 (700) , 
     FECHA_MODIFICACION DATE , 
     FECHA_ENVIO DATE , 
     IDENTIFICADOR_USUARIO_ENVIO VARCHAR2 (50) , 
     NOMBRE_ARCHIVO VARCHAR2 (100), 
     NOMBRE_ARCHIVO_FISICO VARCHAR2 (100), 
    ) 
; 


ALTER TABLE PROPUESTA_NECESIDAD 
    ADD CONSTRAINT "DATOS_NECESIDAD PK" PRIMARY KEY ( ID_PROPUESTA_NECESIDAD ) ;

CREATE SEQUENCE SEC_PROPUESTA_NECESIDAD
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE ROL 
    ( 
     ID_ROL NUMBER  NOT NULL , 
     NOMBRE VARCHAR2 (100)  NOT NULL 
    ) 
;



ALTER TABLE ROL 
    ADD CONSTRAINT ROL_PK PRIMARY KEY ( ID_ROL ) ;

CREATE SEQUENCE SEC_ROL
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE ROL_OPCION_MENU 
    ( 
     ID_ROL_OPCION_MENU NUMBER  NOT NULL , 
     ID_ROL NUMBER , 
     ID_OPCION_MENU NUMBER 
    ) 
;



ALTER TABLE ROL_OPCION_MENU 
    ADD CONSTRAINT "ROL_OPCION_MENU PK" PRIMARY KEY ( ID_ROL_OPCION_MENU ) ;

CREATE SEQUENCE SEC_ROL_OPCION_MENU
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE TIPO_UNIDAD 
    ( 
     ID_TIPO_UNIDAD NUMBER  NOT NULL , 
     NOMBRE VARCHAR2 (50) 
    ) 
;



ALTER TABLE TIPO_UNIDAD 
    ADD CONSTRAINT TIPO_UNIDAD_PK PRIMARY KEY ( ID_TIPO_UNIDAD ) ;

CREATE SEQUENCE SEC_TIPO_UNIDAD
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE TIPO_UNIDAD_PERIODO 
    ( 
     ID_TIPO_UNIDAD_PERIODO NUMBER  NOT NULL , 
     ID_TIPO_UNIDAD NUMBER , 
     ID_PERIODO NUMBER 
    ) 
;



ALTER TABLE TIPO_UNIDAD_PERIODO 
    ADD CONSTRAINT TIPO_UNIDAD_PERIODO_PK PRIMARY KEY ( ID_TIPO_UNIDAD_PERIODO ) ;

CREATE SEQUENCE SEC_TIPO_UNIDAD_PERIODO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE UNIDAD_POLICIAL 
    ( 
     ID_UNIDAD_POLICIAL NUMBER  NOT NULL , 
     NOMBRE VARCHAR2 (50)  NOT NULL , 
     SIGLA VARCHAR2 (10)  NOT NULL , 
     ID_TIPO_UNIDAD NUMBER 
    ) 
;



ALTER TABLE UNIDAD_POLICIAL 
    ADD CONSTRAINT UNIDAD_POLICIAL_PK PRIMARY KEY ( ID_UNIDAD_POLICIAL ) ;

CREATE SEQUENCE SEC_UNIDAD_POLICIAL
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE UNIDAD_POLICIAL_PERIODO 
    ( 
     ID_UNIDAD_POLICIAL_PERIODO NUMBER  NOT NULL , 
     ID_UNIDAD_POLICIAL NUMBER , 
     ID_PERIODO NUMBER 
    ) 
;



ALTER TABLE UNIDAD_POLICIAL_PERIODO 
    ADD CONSTRAINT UNIDAD_POLICIAL_PERIODO_PK PRIMARY KEY ( ID_UNIDAD_POLICIAL_PERIODO ) ;

CREATE SEQUENCE SEC_UNIDAD_POLICIAL_PERIODO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE USUARIO_ROL 
    ( 
     ID_USUARIO_ROL NUMBER  NOT NULL , 
     IDENTIFICADOR_USUARIO VARCHAR2 (50)  NOT NULL , 
     ID_ROL NUMBER
    ) 
;

ALTER TABLE USUARIO_ROL 
    ADD CONSTRAINT USUARIO_ROL_PK PRIMARY KEY ( ID_USUARIO_ROL ) ;

ALTER TABLE USUARIO_ROL 
    ADD CONSTRAINT FK_USUARIO_ROL_Y_ROL FOREIGN KEY 
    ( 
     ID_ROL
    ) 
    REFERENCES ROL 
    ( 
     ID_ROL
    ) 
;


CREATE SEQUENCE SEC_USUARIO_ROL
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE USUARIO_UNIDAD_POLICIAL 
    ( 
     ID_USUARIO_UNIDAD_POLICIAL NUMBER  NOT NULL , 
     IDENTIFICADOR_USUARIO VARCHAR2 (50)  NOT NULL , 
     ID_UNIDAD_POLICIAL NUMBER 
    ) 
;



ALTER TABLE USUARIO_UNIDAD_POLICIAL 
    ADD CONSTRAINT USUARIO_UNIDAD_POLICIAL_PK PRIMARY KEY ( ID_USUARIO_UNIDAD_POLICIAL ) ;




ALTER TABLE USUARIO_UNIDAD_POLICIAL 
    ADD CONSTRAINT FK_UNIDA_POLI_Y_USU_UNI_POL FOREIGN KEY 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
    REFERENCES UNIDAD_POLICIAL 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
;


CREATE TABLE VISTA_FUNCIONARIO 
    ( 
     ID_VISTA_FUNCIONARIO NUMBER  NOT NULL , 
     IDENTIFICACION VARCHAR2 (50)  NOT NULL , 
     NOMBRE_COMPLETO VARCHAR2 (100)  NOT NULL , 
     CORREO VARCHAR2 (100)  NOT NULL , 
     GRADO VARCHAR2 (100)  NOT NULL 
    ) 
;



ALTER TABLE VISTA_FUNCIONARIO 
    ADD CONSTRAINT "VISTA_FUNCIONARIO PK" PRIMARY KEY ( ID_VISTA_FUNCIONARIO ) ;




ALTER TABLE FUNCIONARIO_NECESIDAD 
    ADD CONSTRAINT FK_DATOS_NECE_Y_FUNCIONARIO FOREIGN KEY 
    ( 
     ID_PROPUESTA_NECESIDAD
    ) 
    REFERENCES PROPUESTA_NECESIDAD 
    ( 
     ID_PROPUESTA_NECESIDAD
    ) 
;


ALTER TABLE LINEA 
    ADD CONSTRAINT FK_LINEA_Y_AREA_CIENCIA FOREIGN KEY 
    ( 
     ID_AREA_CIENCIA_TECNOLOGIA
    ) 
    REFERENCES AREA_CIENCIA_TECNOLOGIA 
    ( 
     ID_AREA_CIENCIA_TECNOLOGIA
    ) 
;


ALTER TABLE PROPUESTA_NECESIDAD 
    ADD CONSTRAINT FK_LINEA_Y_DATOS_NECE FOREIGN KEY 
    ( 
     ID_LINEA
    ) 
    REFERENCES LINEA 
    ( 
     ID_LINEA
    ) 
;


ALTER TABLE OPCION_MENU 
    ADD CONSTRAINT FK_OPCION_MENU_SI_MISMO FOREIGN KEY 
    ( 
     ID_OPCION_MENU2
    ) 
    REFERENCES OPCION_MENU 
    ( 
     ID_OPCION_MENU
    ) 
;


ALTER TABLE PROPUESTA_NECESIDAD 
    ADD CONSTRAINT FK_PERIODO_Y_DATOS_NECE FOREIGN KEY 
    ( 
     ID_PERIODO
    ) 
    REFERENCES PERIODO 
    ( 
     ID_PERIODO
    ) 
;


ALTER TABLE HITOS_PERIODO 
    ADD CONSTRAINT FK_PERIODO_Y_HITOS_PERIODO FOREIGN KEY 
    ( 
     ID_PERIODO
    ) 
    REFERENCES PERIODO 
    ( 
     ID_PERIODO
    ) 
;


ALTER TABLE ROL_OPCION_MENU 
    ADD CONSTRAINT FK_ROL_OPC_MENU_Y_OPC_MENU FOREIGN KEY 
    ( 
     ID_OPCION_MENU
    ) 
    REFERENCES OPCION_MENU 
    ( 
     ID_OPCION_MENU
    ) 
;


ALTER TABLE ROL_OPCION_MENU 
    ADD CONSTRAINT FK_ROL_Y_ROL_OPC_MENU FOREIGN KEY 
    ( 
     ID_ROL
    ) 
    REFERENCES ROL 
    ( 
     ID_ROL
    ) 
;


ALTER TABLE HITOS_PERIODO 
    ADD CONSTRAINT FK_TIPO_HITO_Y_HITOS_PERIODO FOREIGN KEY 
    ( 
     ID_CONSTANTES
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;


ALTER TABLE TIPO_UNIDAD_PERIODO 
    ADD CONSTRAINT FK_TIPO_UNIDAD_PERIODO FOREIGN KEY 
    ( 
     ID_TIPO_UNIDAD
    ) 
    REFERENCES TIPO_UNIDAD 
    ( 
     ID_TIPO_UNIDAD
    ) 
;


ALTER TABLE TIPO_UNIDAD_PERIODO 
    ADD CONSTRAINT FK_TIPO_UNI_PER_Y_PERIODO FOREIGN KEY 
    ( 
     ID_PERIODO
    ) 
    REFERENCES PERIODO 
    ( 
     ID_PERIODO
    ) 
;


ALTER TABLE UNIDAD_POLICIAL 
    ADD CONSTRAINT FK_TIPO_UNI_Y_UNI_POLICIAL FOREIGN KEY 
    ( 
     ID_TIPO_UNIDAD
    ) 
    REFERENCES TIPO_UNIDAD 
    ( 
     ID_TIPO_UNIDAD
    ) 
;


ALTER TABLE PROPUESTA_NECESIDAD 
    ADD CONSTRAINT FK_UNIDAD_POL_Y_PROPU_NECE FOREIGN KEY 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
    REFERENCES UNIDAD_POLICIAL 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
;


ALTER TABLE UNIDAD_POLICIAL_PERIODO 
    ADD CONSTRAINT FK_UNI_POLI_PERI_Y_PERIODO FOREIGN KEY 
    ( 
     ID_PERIODO
    ) 
    REFERENCES PERIODO 
    ( 
     ID_PERIODO
    ) 
;


ALTER TABLE UNIDAD_POLICIAL_PERIODO 
    ADD CONSTRAINT FK_UNI_POLI_Y_UNI_POL_PERI FOREIGN KEY 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
    REFERENCES UNIDAD_POLICIAL 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
;


ALTER TABLE PROPUESTA_NECESIDAD 
    ADD CONSTRAINT FK_VAL_SELEC_GEN_Y_PROP_NECE FOREIGN KEY 
    ( 
     ID_CONSTANTES
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;

CREATE SEQUENCE SEC_VISTA_FUNCIONARIO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE KEY_PROPERTIES 
    ( 
     ID_KEY_PROPERTIES NUMBER  NOT NULL , 
     CLAVE VARCHAR2 (100)  NOT NULL ,
     VALOR VARCHAR2 (500)  NOT NULL 
    ) 
;

ALTER TABLE KEY_PROPERTIES 
    ADD CONSTRAINT KEY_PROPERTIES_PK PRIMARY KEY ( ID_KEY_PROPERTIES ) ;

ALTER TABLE KEY_PROPERTIES 
    ADD CONSTRAINT KEY_PROPERTIES_UQ UNIQUE ( CLAVE ) ;

CREATE SEQUENCE SEC_KEY_PROPERTIES
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

ALTER TABLE OPCION_MENU MODIFY TIPO_ACCESO VARCHAR2(10);
ALTER TABLE PROPUESTA_NECESIDAD MODIFY ID_UNIDAD_POLICIAL NUMBER NOT NULL;
ALTER TABLE PROPUESTA_NECESIDAD MODIFY ID_CONSTANTES NUMBER NOT NULL;
ALTER TABLE PROPUESTA_NECESIDAD MODIFY ID_LINEA NUMBER NOT NULL;
ALTER TABLE PROPUESTA_NECESIDAD MODIFY ID_PERIODO NUMBER NOT NULL;

--ALTER TABLE USUARIO_ROL ADD ID_UNIDAD_POLICIAL NUMBER;
--ALTER TABLE USUARIO_ROL 
--    ADD CONSTRAINT FK_USUARIO_ROL_Y_UNIDAD_POLI FOREIGN KEY 
--    ( 
--     ID_UNIDAD_POLICIAL
--    ) 
--    REFERENCES UNIDAD_POLICIAL 
--    ( 
--     ID_UNIDAD_POLICIAL
--    ) 
--;


CREATE TABLE COMENTARIO_NECESIDAD 
    ( 
     ID_COMENTARIO_NECESIDAD NUMBER  NOT NULL , 
     FECHA DATE  NOT NULL , 
     AUTOR VARCHAR2 (100)  NOT NULL , 
     COMENTARIO VARCHAR2 (512) , 
     ID_PROPUESTA_NECESIDAD NUMBER NOT NULL
    ) 
;

ALTER TABLE COMENTARIO_NECESIDAD 
    ADD CONSTRAINT COMENTARIO_NECESIDAD_PK PRIMARY KEY ( ID_COMENTARIO_NECESIDAD ) ;

ALTER TABLE COMENTARIO_NECESIDAD 
    ADD CONSTRAINT FK_PROPUESTA_NECE_COMENTARIO FOREIGN KEY 
    ( 
     ID_PROPUESTA_NECESIDAD
    ) 
    REFERENCES PROPUESTA_NECESIDAD 
    ( 
     ID_PROPUESTA_NECESIDAD
    ) 
;

CREATE SEQUENCE SEC_COMENTARIO_NECESIDAD
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE EJECUTOR_NECESIDAD 
    ( 
     ID_EJECUTOR_NECESIDAD NUMBER  NOT NULL , 
     ID_PROPUESTA_NECESIDAD NUMBER NOT NULL, 
     ID_UNIDAD_POLICIAL NUMBER NOT NULL, 
     ID_ROL NUMBER NOT NULL
    ) 
;



ALTER TABLE EJECUTOR_NECESIDAD 
    ADD CONSTRAINT EJECUTOR_NECESIDAD_PK PRIMARY KEY ( ID_EJECUTOR_NECESIDAD ) ;




ALTER TABLE EJECUTOR_NECESIDAD 
    ADD CONSTRAINT FK_CONSTANTE_EJECU_NECES FOREIGN KEY 
    ( 
     ID_ROL
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;


ALTER TABLE EJECUTOR_NECESIDAD 
    ADD CONSTRAINT FK_PROPUE_UNID_Y_EJECUTOR_NEC FOREIGN KEY 
    ( 
     ID_PROPUESTA_NECESIDAD
    ) 
    REFERENCES PROPUESTA_NECESIDAD 
    ( 
     ID_PROPUESTA_NECESIDAD
    ) 
;


ALTER TABLE EJECUTOR_NECESIDAD 
    ADD CONSTRAINT FK_UNIDDAD_POLI_EJECU_NECES FOREIGN KEY 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
    REFERENCES UNIDAD_POLICIAL 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
;

CREATE SEQUENCE SEC_EJECUTOR_NECESIDAD
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

ALTER TABLE PROPUESTA_NECESIDAD MODIFY FECHA_REGISTRO TIMESTAMP NOT NULL;
ALTER TABLE PROPUESTA_NECESIDAD MODIFY FECHA_MODIFICACION TIMESTAMP;
ALTER TABLE PROPUESTA_NECESIDAD MODIFY FECHA_ENVIO TIMESTAMP;
ALTER TABLE VISTA_FUNCIONARIO ADD CARGO VARCHAR2 (100);
ALTER TABLE FUNCIONARIO_NECESIDAD ADD CARGO VARCHAR2 (100);
ALTER TABLE PROPUESTA_NECESIDAD ADD CONCECUTIVO NUMBER(3) DEFAULT 0;

ALTER TABLE OPCION_MENU MODIFY NOMBRE VARCHAR2(100);
ALTER TABLE OPCION_MENU ADD TITULO VARCHAR2 (300);

INSERT INTO AREA_CIENCIA_TECNOLOGIA (ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (1,'Servicio Policial');
INSERT INTO AREA_CIENCIA_TECNOLOGIA (ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (2,'Administrativa');
INSERT INTO AREA_CIENCIA_TECNOLOGIA (ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (3,'Educativa');
INSERT INTO AREA_CIENCIA_TECNOLOGIA (ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (4,'Social');
INSERT INTO AREA_CIENCIA_TECNOLOGIA (ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (5,'Juridica');
INSERT INTO AREA_CIENCIA_TECNOLOGIA (ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (6,'Criminalistica');
INSERT INTO AREA_CIENCIA_TECNOLOGIA (ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (7,'Técnica y tecnológica');

INSERT INTO tipo_unidad (id_tipo_unidad,NOMBRE) VALUES (1,'Escuelas');
INSERT INTO tipo_unidad (id_tipo_unidad,NOMBRE) VALUES (2,'Otros');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('1','TIPO_HITO_PERIODO','Informe de Avance','Informe de Avance');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('2','TIPO_HITO_PERIODO','Informe Final','Informe Final');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('3','TIPO_PROPUESTA_NECESIDAD','En elaboración','En elaboración');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('4','TIPO_PROPUESTA_NECESIDAD','En aprobación','En aprobación Jefe de Unidad');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('5','GRADO_FUNCIONARIO','Subteniente','Subteniente');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('6','GRADO_FUNCIONARIO','Teniente','Teniente');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('7','GRADO_FUNCIONARIO','Capitan','Capitan');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('8','GRADO_FUNCIONARIO','Mayor','Mayor');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('9','GRADO_FUNCIONARIO','Patrullero','Patrullero');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('10','GRADO_FUNCIONARIO','Subintendente','Subintendente');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('11','GRADO_FUNCIONARIO','Intendente','Intendente');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('13','TIPO_PROPUESTA_NECESIDAD','Revisada','Revisada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('12','TIPO_PROPUESTA_NECESIDAD','Enviada a VICIN','Enviada a VICIN');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('14','TIPO_PROPUESTA_NECESIDAD','Pre-Aprobada','Pre-Aprobada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('15','TIPO_PROPUESTA_NECESIDAD','Aprobada','Aprobada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('16','TIPO_PROPUESTA_NECESIDAD','Rechazada','Rechazada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('17','TIPO_PROPUESTA_NECESIDAD','No aprobada','No aprobada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('18','TIPO_PROPUESTA_NECESIDAD','No aceptada','No aceptada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('19','TIPO_PROPUESTA_NECESIDAD','Aceptada','Aceptada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('20','TIPO_ROL_PROYECTO_NECESIDAD','Responsable','Responsable');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('21','TIPO_ROL_PROYECTO_NECESIDAD','De apoyo','De apoyo');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('22','TIPO_COMPROMISO_NECESIDAD_PROYECTO','Convocatoria','De convocatoria de financiación');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('23','TIPO_COMPROMISO_NECESIDAD_PROYECTO','De apoyo','Proyectos institucionales');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('24','TIPO_COMPROMISO_COMPROMISO','N10','N°10');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('25','TIPO_COMPROMISO_COMPROMISO','N11','N°11');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('26','TIPO_COMPROMISO_PERIODO','Formulación del proyecto','Formulación del proyecto');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('27','TIPO_COMPROMISO_PERIODO','Informe de avance','Informe de avance');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('28','TIPO_COMPROMISO_PERIODO','Informe final','Informe final');

INSERT INTO AREA_CIENCIA_TECNOLOGIA VALUES (1,'AREA SERVICIO POLICIAL');
INSERT INTO AREA_CIENCIA_TECNOLOGIA VALUES (2,'AREA ADMINISTRATIVA');
INSERT INTO AREA_CIENCIA_TECNOLOGIA VALUES (3,'AREA EDUCATIVA');
INSERT INTO AREA_CIENCIA_TECNOLOGIA VALUES (4,'AREA SOCIAL');
INSERT INTO AREA_CIENCIA_TECNOLOGIA VALUES (5,'AREA JURIDICA');
INSERT INTO AREA_CIENCIA_TECNOLOGIA VALUES (6,'AREA CRIMINALISTICA');
INSERT INTO AREA_CIENCIA_TECNOLOGIA VALUES (7,'AREA TECNICA Y TECNOLOGICA');


INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (1,1,'LINEA SERVICIOS DE POLICIA');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (2,1,'LINEA SEGURIDAD');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (3,2,'LINEA DESARROLLO INSTITUCIONAL Y ORGANIZACIONAL');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (4,2,'LINEA TALENTO Y DESARROLLO HUMANO');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (5,2,'LINEA ADMINISTRACION DE RECURSOS');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (6,3,'LINEA FORMACION POLICIAL');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (7,3,'LINEA MEDIACIONES PEDAGOGICAS');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (8,4,'LINEA CONFLICTO Y POSTCONFLICTO');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (9,4,'LINEA VIOLENCIA Y SUS MANIFESTACIONES');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (10,5,'LINEA FUNDAMENTOS JURIDICOS DEL SERVICIO DE POLICIA');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (11,6,'LINEA CRIMINOLOGIA, CRIMINALISTICA Y POLICIA JUDICIAL');
INSERT INTO LINEA (ID_LINEA,ID_AREA_CIENCIA_TECNOLOGIA,NOMBRE) VALUES (12,7,'LINEA DESARROLLO TECNOLOGICO E INNOVACION');















CREATE TABLE PROYECTO 
    ( 
     ID_PROYECTO NUMBER  NOT NULL , 
     CODIGO_PROYECTO VARCHAR2 (20) , 
     TITULO_PROPUESTO VARCHAR2 (512)  NOT NULL , 
     ID_LINEA NUMBER  NOT NULL , 
     ID_PERIODO NUMBER  NOT NULL , 
     FUNCION_PROYECTO VARCHAR2 (512) , 
     FECHA_ESTIMADA_INICIO TIMESTAMP , 
     FECHA_ESTIMADA_FINALIZACION TIMESTAMP , 
     VALOR_TOTAL NUMBER (11,2) , 
     CONVOCATORIA VARCHAR2(100), 
     FECHA_REGISTRO TIMESTAMP , 
     TEMA VARCHAR2 (512) , 
     FECHA_APROBACION_COMITE TIMESTAMP  NOT NULL , 
     NRO_ACTA_APROBACION_COMITE VARCHAR2 (20) , 
     ID_USUARIO_ROL NUMBER  NOT NULL , 
     FECHA_ACTUALIZACION TIMESTAMP , 
     ID_USUARIO_ROL_ACTUALIZA NUMBER NOT NULL 
    );
	
ALTER TABLE PROYECTO 
    ADD CONSTRAINT PROYECTO_PK PRIMARY KEY ( ID_PROYECTO ) ;

ALTER TABLE PROYECTO 
    ADD CONSTRAINT PROYECTO_LINEA_FK FOREIGN KEY 
    ( 
     ID_LINEA
    ) 
    REFERENCES LINEA 
    ( 
     ID_LINEA
    ) 
;
ALTER TABLE PROYECTO 
    ADD CONSTRAINT PROYECTO_PERIODO_FK FOREIGN KEY 
    ( 
     ID_PERIODO
    ) 
    REFERENCES PERIODO 
    ( 
     ID_PERIODO
    ) 
;
ALTER TABLE PROYECTO 
    ADD CONSTRAINT PROYECTO_USUARIO_ROL_FK FOREIGN KEY 
    ( 
     ID_USUARIO_ROL
    ) 
    REFERENCES USUARIO_ROL 
    ( 
     ID_USUARIO_ROL
    ) 
;
ALTER TABLE PROYECTO 
    ADD CONSTRAINT PROYECTO_USUARIO_ROL_FKv2 FOREIGN KEY 
    ( 
     ID_USUARIO_ROL_ACTUALIZA
    ) 
    REFERENCES USUARIO_ROL 
    ( 
     ID_USUARIO_ROL
    ) 
;
CREATE SEQUENCE SEC_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE FUENTE_PROYECTO 
    ( 
     ID_FUENTE_PROYECTO NUMBER  NOT NULL , 
     ID_TIPO_FUENTE NUMBER NOT NULL , 
     ID_PROYECTO NUMBER  NOT NULL 
    ) 
;
ALTER TABLE FUENTE_PROYECTO 
    ADD CONSTRAINT FUENTE_PROYECTO_PK PRIMARY KEY ( ID_FUENTE_PROYECTO ) ;

ALTER TABLE FUENTE_PROYECTO 
    ADD CONSTRAINT FUENTE_PROYECTO_PROYECTO_FK FOREIGN KEY 
    ( 
     ID_PROYECTO
    ) 
    REFERENCES PROYECTO 
    ( 
     ID_PROYECTO
    ) 
;
ALTER TABLE FUENTE_PROYECTO 
    ADD CONSTRAINT FUENTE_PROYECTO_CONSTANTES_FK FOREIGN KEY 
    ( 
     ID_TIPO_FUENTE
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;
CREATE SEQUENCE SEC_FUENTE_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE EVENTO_PROYECTO 
    ( 
     ID_EVENTO_PROYECTO NUMBER  NOT NULL , 
	   ID_FUENTE_PROYECTO NUMBER  NOT NULL , 
     COSTO NUMBER (15,2)  NOT NULL,
     TITULO_EVENTO VARCHAR2 (256), 
     OBJETIVO_EVENTO VARCHAR2(512)
    )
;
ALTER TABLE EVENTO_PROYECTO 
    ADD CONSTRAINT EVENTO_PROYECTO_PK PRIMARY KEY ( ID_EVENTO_PROYECTO ) ;

ALTER TABLE EVENTO_PROYECTO 
    ADD CONSTRAINT EVENTO_PROYE_FUENTE_PROYE_FK FOREIGN KEY 
    ( 
     ID_FUENTE_PROYECTO
    ) 
    REFERENCES FUENTE_PROYECTO 
    ( 
     ID_FUENTE_PROYECTO
    ) 
;
CREATE SEQUENCE SEC_EVENTO_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE INVESTIGADORES_PROYECTO 
    ( 
     ID_INVESTIGADOR_PROYECTO NUMBER  NOT NULL , 
     ID_PROYECTO NUMBER  NOT NULL , 
	 VALOR_ESPECIE NUMBER (15,2)  NOT NULL , 
	 ID_CONSTANTES NUMBER  NOT NULL , 
     IDENTIFICACION NUMBER (11) ,      
     FUNCION_PROYECTO VARCHAR2 (512) , 
     HORAS_DEDICACION NUMBER (3) , 
     INVESTIGADO_3ANIOS CHAR (1) , 
     INVESTIGADO_ANUAL CHAR (1) , 
     VALOR_HORA NUMBER (12,2) , 
     ACTIVO CHAR(1)  NOT NULL , 
     VALOR_EFECTIVO NUMBER (15,2) , 
     ID_FUENTE_PROYECTO NUMBER 
    ) 
;
ALTER TABLE INVESTIGADORES_PROYECTO 
    ADD CONSTRAINT INVESTIGADORES_PROYECTO_PK PRIMARY KEY ( ID_INVESTIGADOR_PROYECTO ) ;

ALTER TABLE INVESTIGADORES_PROYECTO 
    ADD CONSTRAINT INVESTIGAD_PROYEC_CONSTANT_FK FOREIGN KEY 
    ( 
     ID_CONSTANTES
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;
ALTER TABLE INVESTIGADORES_PROYECTO 
    ADD CONSTRAINT INVESTI_PROYEC_FUEN_PROYEC_FK FOREIGN KEY 
    ( 
     ID_FUENTE_PROYECTO
    ) 
    REFERENCES FUENTE_PROYECTO 
    ( 
     ID_FUENTE_PROYECTO
    ) 
;

ALTER TABLE INVESTIGADORES_PROYECTO 
    ADD CONSTRAINT INVESTIGA_PROYECT_PROYECTO_FK FOREIGN KEY 
    ( 
     ID_PROYECTO
    ) 
    REFERENCES PROYECTO 
    ( 
     ID_PROYECTO
    ) 
;
CREATE SEQUENCE SEC_INVESTIGADORES_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE SEMILLERO_INVESTIGACION 
    ( 
     ID_SEMILLERO NUMBER  NOT NULL , 
     NOMBRE VARCHAR2 (30)  NOT NULL , 
     ID_UNIDAD_POLICIAL NUMBER  NOT NULL , 
     MISION_SEMILLERO VARCHAR2 (512)  NOT NULL , 
     VISION_SEMILLERO VARCHAR2 (512) 
    ) 
;
ALTER TABLE SEMILLERO_INVESTIGACION 
    ADD CONSTRAINT SEMILLERO_INVESTIGACION_PK PRIMARY KEY ( ID_SEMILLERO ) ;

ALTER TABLE SEMILLERO_INVESTIGACION 
    ADD CONSTRAINT SEMILL_INVESTI_UNID_POLICI_FK FOREIGN KEY 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
    REFERENCES UNIDAD_POLICIAL 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
;
CREATE SEQUENCE SEC_SEMILLERO_INVESTIGACION
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE SEMILLERO_PROYECTO 
    ( 
     ID_SEMILLERO_PROYECTO NUMBER  NOT NULL , 
	 ID_PROYECTO NUMBER  NOT NULL , 
     FECHA_REGISTRO TIMESTAMP , 
     USUARIO VARCHAR2 (100) , 
     ID_SEMILLERO NUMBER  NOT NULL 
    ) 
;
ALTER TABLE SEMILLERO_PROYECTO 
    ADD CONSTRAINT SEMILLERO_PROYECTO_PK PRIMARY KEY ( ID_SEMILLERO_PROYECTO ) ;

ALTER TABLE SEMILLERO_PROYECTO 
    ADD CONSTRAINT SEMILLER_PROYECT_PROYECTO_FK FOREIGN KEY 
    ( 
     ID_PROYECTO
    ) 
    REFERENCES PROYECTO 
    ( 
     ID_PROYECTO
    ) 
;
ALTER TABLE SEMILLERO_PROYECTO 
    ADD CONSTRAINT SEMILL_PROYEC_SEMIL_INVESTI_FK FOREIGN KEY 
    ( 
     ID_SEMILLERO
    ) 
    REFERENCES SEMILLERO_INVESTIGACION 
    ( 
     ID_SEMILLERO
    ) 
;
CREATE SEQUENCE SEC_SEMILLERO_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE TEMA
    ( 
     ID_TEMA NUMBER  NOT NULL , 
     DESCRIPCION_TEMA VARCHAR2(512) NOT NULL 
    ) 
;

ALTER TABLE TEMA
    ADD CONSTRAINT TEMA_PK PRIMARY KEY ( ID_TEMA ) ;

CREATE SEQUENCE SEC_TEMA
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE TEMA_PROYECTO 
    ( 
     ID_TEMA_PROYECTO NUMBER  NOT NULL , 
     TEXTO VARCHAR2 (4000) , 
     ARCHIVO_SOPORTE VARCHAR2 (300) , 
     ID_PROYECTO NUMBER , 
     ID_TEMA NUMBER , 
     FECHA_REGISTRO TIMESTAMP , 
     USUARIO VARCHAR2 (100) 
    ) 
;

ALTER TABLE TEMA_PROYECTO 
    ADD CONSTRAINT TEMA_PROYECTO_PK PRIMARY KEY ( ID_TEMA_PROYECTO ) ;

ALTER TABLE TEMA_PROYECTO 
    ADD CONSTRAINT TEMA_PROYECTO_PROYECTO_FK FOREIGN KEY 
    ( 
     ID_PROYECTO
    ) 
    REFERENCES PROYECTO 
    ( 
     ID_PROYECTO
    ) 
;

ALTER TABLE TEMA_PROYECTO 
    ADD CONSTRAINT TEMA_PROYECTO_TEMAS_FK FOREIGN KEY 
    ( 
     ID_TEMA
    ) 
    REFERENCES TEMA
    ( 
     ID_TEMA
    ) 
;
CREATE SEQUENCE SEC_TEMA_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE GRUPO_INVESTIGACION 
    ( 
     ID_GRUPO_INVESTIGACION NUMBER  NOT NULL , 
     DESCRIPCION VARCHAR2 (50)  NOT NULL , 
     CODIGO_GRUPO VARCHAR2 (10)  NOT NULL , 
     FECHA_REGISTRO_GRUPO TIMESTAMP 
    ) 
;

ALTER TABLE GRUPO_INVESTIGACION 
    ADD CONSTRAINT GRUPO_INVESTIGACION_PK PRIMARY KEY ( ID_GRUPO_INVESTIGACION ) ;
	
CREATE SEQUENCE SEC_GRUPO_INVESTIGACION
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE GRUPO_INVEST_PROYECTO 
    ( 
     ID_GRUPO_INVEST_PROYECTO NUMBER  NOT NULL , 
     FECHA_VINCULACION TIMESTAMP , 
     ID_GRUPO_INVESTIGACION NUMBER  NOT NULL , 
     ID_PROYECTO NUMBER 
    ) 
;

ALTER TABLE GRUPO_INVEST_PROYECTO 
    ADD CONSTRAINT GRUPO_INVEST_PROYECTO_PK PRIMARY KEY ( ID_GRUPO_INVEST_PROYECTO ) ;


ALTER TABLE GRUPO_INVEST_PROYECTO 
    ADD CONSTRAINT GRP_INVEST_PROYE_GRU_INVEST_FK FOREIGN KEY 
    ( 
     ID_GRUPO_INVESTIGACION
    ) 
    REFERENCES GRUPO_INVESTIGACION 
    ( 
     ID_GRUPO_INVESTIGACION
    ) 
;
CREATE SEQUENCE SEC_GRUPO_INVEST_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

ALTER TABLE GRUPO_INVEST_PROYECTO 
    ADD CONSTRAINT GRUPO_INVEST_PROYE_PROYECT_FK FOREIGN KEY 
    ( 
     ID_PROYECTO
    ) 
    REFERENCES PROYECTO 
    ( 
     ID_PROYECTO
    ) 
;

CREATE TABLE HISTORIAL_COMPROMISO 
    ( 
     ID_HISTORIAL_COMPROMISO NUMBER  NOT NULL, 
	 NUEVA_FECHA TIMESTAMP  NOT NULL ,
     ID_USUARIO_ROL NUMBER , 
     FECHA_MODIFICACION TIMESTAMP ,  
     ID_COMPROMISO_PERIODO NUMBER 
    ) 
;

ALTER TABLE HISTORIAL_COMPROMISO 
    ADD CONSTRAINT HISTORIAL_COMPROMISO_PK PRIMARY KEY ( ID_HISTORIAL_COMPROMISO ) ;


ALTER TABLE HISTORIAL_COMPROMISO 
    ADD CONSTRAINT HISTOR_COMPR_COMPRO_PERIOD_FK FOREIGN KEY 
    ( 
     ID_COMPROMISO_PERIODO
    ) 
    REFERENCES COMPROMISO_PERIODO 
    ( 
     ID_COMPROMISO_PERIODO
    ) 
;

ALTER TABLE HISTORIAL_COMPROMISO 
    ADD CONSTRAINT HISTORI_COMPRO_USUARIO_ROL_FK FOREIGN KEY 
    ( 
     ID_USUARIO_ROL
    ) 
    REFERENCES USUARIO_ROL 
    ( 
     ID_USUARIO_ROL
    ) 
;

CREATE SEQUENCE SEC_HISTORIAL_COMPROMISO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE OTROS_GASTOS_PROYECTO 
    ( 
     ID_OTROS_GASTOS_PROYECTO NUMBER NOT NULL, 
     VALOR NUMBER NOT NULL , 
     ID_FUENTE_PROYECTO NUMBER  NOT NULL , 
     ID_TIPO_RUBRO NUMBER  NOT NULL , 
     ID_TIPO_ESPECIE_EFECTIVO NUMBER 
    ) 
;

ALTER TABLE OTROS_GASTOS_PROYECTO 
    ADD CONSTRAINT OTROS_GASTOS_PROYECTO_PK PRIMARY KEY ( ID_OTROS_GASTOS_PROYECTO ) ;
	
ALTER TABLE OTROS_GASTOS_PROYECTO 
    ADD CONSTRAINT OTROS_GAST_PROYE_CONSTANTE_FK FOREIGN KEY 
    ( 
     ID_TIPO_RUBRO
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;

ALTER TABLE OTROS_GASTOS_PROYECTO 
    ADD CONSTRAINT OTROS_GAST_PROYE_CONSTAN_FK_2 FOREIGN KEY 
    ( 
     ID_TIPO_ESPECIE_EFECTIVO
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;

ALTER TABLE OTROS_GASTOS_PROYECTO 
    ADD CONSTRAINT OTROS_GAST_PROY_FUEN_PROYEC_FK FOREIGN KEY 
    ( 
     ID_FUENTE_PROYECTO
    ) 
    REFERENCES FUENTE_PROYECTO 
    ( 
     ID_FUENTE_PROYECTO
    ) 
;

CREATE SEQUENCE SEC_OTROS_GASTOS_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE CIUDAD 
    ( 
     ID_CIUDAD  VARCHAR2 (10) NOT NULL, 
     NOMBRE VARCHAR(100) NOT NULL                 
    ) 
;

ALTER TABLE CIUDAD 
    ADD CONSTRAINT CIUDAD_PK PRIMARY KEY ( ID_CIUDAD ) ;

CREATE TABLE VIAJES_PROYECTO 
    ( 
     ID_VIAJE_PROYECTO NUMBER  NOT NULL , 
	 ID_INVESTIGADOR_PROYECTO NUMBER  NOT NULL , 
	 ID_CIUDAD_ORIGEN VARCHAR2 (10)  NOT NULL , 
     ID_CIUDAD_DESTINO VARCHAR2 (10)  NOT NULL , 
     EVENTO VARCHAR2 (512) NOT NULL, 
     COSTOS_PASAJES NUMBER NOT NULL, 
     COSTOS_VIATICOS NUMBER NOT NULL,  
     ID_FUENTE_PROYECTO NUMBER 
    ) 
;

ALTER TABLE VIAJES_PROYECTO 
    ADD CONSTRAINT VIAJES_PROYECTO_PK PRIMARY KEY ( ID_VIAJE_PROYECTO ) ;

ALTER TABLE VIAJES_PROYECTO 
    ADD CONSTRAINT VIAJES_PROYECTO_CIUDAD_ORI_FK FOREIGN KEY 
    ( 
     ID_CIUDAD_ORIGEN
    ) 
    REFERENCES CIUDAD 
    ( 
     ID_CIUDAD
    ) 
;

ALTER TABLE VIAJES_PROYECTO 
    ADD CONSTRAINT VIAJES_PROYECTO_CIUDA_DES_FK FOREIGN KEY 
    ( 
     ID_CIUDAD_DESTINO
    ) 
    REFERENCES CIUDAD 
    ( 
     ID_CIUDAD
    ) 
;

ALTER TABLE VIAJES_PROYECTO 
    ADD CONSTRAINT VIAJES_PROYEC_FUENT_PROYEC_FK FOREIGN KEY 
    ( 
     ID_FUENTE_PROYECTO
    ) 
    REFERENCES FUENTE_PROYECTO 
    ( 
     ID_FUENTE_PROYECTO
    ) 
;

ALTER TABLE VIAJES_PROYECTO 
    ADD CONSTRAINT VIAJE_PROYE_INVESTI_PROYEC_FK FOREIGN KEY 
    ( 
     ID_INVESTIGADOR_PROYECTO
    ) 
    REFERENCES INVESTIGADORES_PROYECTO 
    ( 
     ID_INVESTIGADOR_PROYECTO
    ) 
;
CREATE SEQUENCE SEC_VIAJES_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE GASTO_EVENTO 
    ( 
     ID_GASTO_EVENTO NUMBER  NOT NULL, 
     ID_TIPO_GASTO NUMBER  NOT NULL ,
	 ID_EVENTO_PROYECTO NUMBER  NOT NULL 
    ) 
;

ALTER TABLE GASTO_EVENTO 
    ADD CONSTRAINT GASTO_EVENTO_PK PRIMARY KEY ( ID_GASTO_EVENTO ) ;

ALTER TABLE GASTO_EVENTO 
    ADD CONSTRAINT GASTO_EVENTO_CONSTANTES_FK FOREIGN KEY 
    ( 
     ID_TIPO_GASTO
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;

ALTER TABLE GASTO_EVENTO 
    ADD CONSTRAINT GASTO_EVENT_EVENTO_PROYECTO_FK FOREIGN KEY 
    ( 
     ID_EVENTO_PROYECTO
    ) 
    REFERENCES EVENTO_PROYECTO 
    ( 
     ID_EVENTO_PROYECTO
    ) 
;
CREATE SEQUENCE SEC_GASTO_EVENTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;


CREATE TABLE EQUIPOS_INVESTIGACION 
    ( 
     ID_EQUIPO_INVESTIGACION NUMBER NOT NULL , 
     VALOR NUMBER (15,2)  NOT NULL , 
     ESPECIFICACIONES VARCHAR2 (512)  NOT NULL , 
     ID_FUENTE_PROYECTO NUMBER  NOT NULL ,
     ORIGEN VARCHAR2(100)
    ) 
;

ALTER TABLE EQUIPOS_INVESTIGACION 
    ADD CONSTRAINT EQUIPOS_INVESTIGACION_PK PRIMARY KEY ( ID_EQUIPO_INVESTIGACION ) ;

ALTER TABLE EQUIPOS_INVESTIGACION 
    ADD CONSTRAINT EQUI_INVESTIGA_FUEN_PROYEC_FK FOREIGN KEY 
    ( 
     ID_FUENTE_PROYECTO
    ) 
    REFERENCES FUENTE_PROYECTO 
    ( 
     ID_FUENTE_PROYECTO
    ) 
;
CREATE SEQUENCE SEC_EQUIPOS_INVESTIGACION
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;



CREATE TABLE EXCEPCIONES_COMPROMISO 
    ( 
     ID_EXCEPCION_COMPROMISO NUMBER  NOT NULL , 
     FECHA_LIMITE TIMESTAMP , 
     ARCHIVO_SOPORTE VARCHAR2 (300) , 
     JUSTIFICACION VARCHAR2 (512) ,
     ID_UNIDAD_POLICIAL NUMBER  NOT NULL	 
     ID_COMPROMISO_PERIODO NUMBER  
    ) 
;

ALTER TABLE EXCEPCIONES_COMPROMISO 
    ADD CONSTRAINT EXCEPCIONES_COMPROMISO_PK PRIMARY KEY ( ID_EXCEPCION_COMPROMISO ) ;


ALTER TABLE EXCEPCIONES_COMPROMISO 
    ADD CONSTRAINT EXCEPC_COMPRO_COMPRO_PERIOD_FK FOREIGN KEY 
    ( 
     ID_COMPROMISO_PERIODO
    ) 
    REFERENCES COMPROMISOS_PERIODO 
    ( 
     ID_COMPROMISO_PERIODO
    ) 
;


ALTER TABLE EXCEPCIONES_COMPROMISO 
    ADD CONSTRAINT EXCEPC_COMPRO_UNID_POLICI_FK FOREIGN KEY 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
    REFERENCES UNIDAD_POLICIAL 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
;

CREATE SEQUENCE SEC_EXCEPCIONES_COMPROMISO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

--falta crear
CREATE TABLE UNIDAD_PROYECTO 
    ( 
     ID_UNIDAD_PROYECTO NUMBER  NOT NULL , 
     ID_UNIDAD_POLICIAL NUMBER  NOT NULL , 
     ID_CONSTANTES NUMBER  NOT NULL , 
     ID_PROYECTO NUMBER  NOT NULL , 
     TIPO_UNIDAD UNKNOWN              , 
     APORTE_INVESTIGACION VARCHAR2 (512) 
    ) 
;
ALTER TABLE UNIDAD_PROYECTO 
    ADD CONSTRAINT UNIDAD_PROYECTO_PK PRIMARY KEY ( ID_UNIDAD_PROYECTO ) ;

ALTER TABLE UNIDAD_PROYECTO 
    ADD CONSTRAINT UNIDAD_PROYECTO_CONSTANTES_FK FOREIGN KEY 
    ( 
     CONSTANTES_ID_CONSTANTES
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;


ALTER TABLE UNIDAD_PROYECTO 
    ADD CONSTRAINT UNIDAD_PROYECTO_PROYECTO_FK FOREIGN KEY 
    ( 
     PROYECTO_ID_PROYECTO
    ) 
    REFERENCES PROYECTO 
    ( 
     ID_PROYECTO
    ) 
;



--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE UNIDAD_PROYECTO 
    ADD CONSTRAINT UNIDAD_PROYECTO_UNIDAD_POLICIAL_FK FOREIGN KEY 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
    REFERENCES UNIDAD_POLICIAL 
    ( 
     ID_UNIDAD_POLICIAL
    ) 
;
CREATE SEQUENCE SEC_UNIDAD_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

ALTER TABLE OPCION_MENU MODIFY ACCION VARCHAR2(300);

ALTER TABLE UNIDAD_POLICIAL ADD ACTIVO CHAR(1) DEFAULT 'Y';
--CONVOCATORIA
--DROP TABLE TIPO_UNIDAD_CONVOCATORIA CASCADE CONSTRAINT
CREATE TABLE CONVOCATORIA 
    ( 
     ID_CONVOCATORIA NUMBER NOT NULL , 
     ID_ESTADO NUMBER NOT NULL,
     ID_TIPO_CONVOCATORIA NUMBER  NOT NULL ,
     CONCECUTIVO NUMBER  NOT NULL ,
--     ID_TIPO_UNIDAD NUMBER  NOT NULL ,
     NOMBRE VARCHAR2(100) NOT NULL,
    DESCRIPCION VARCHAR2(150) NOT NULL,
    FECHA_INICIO TIMESTAMP NOT NULL,
    FECHA_FIN TIMESTAMP NOT NULL,
    NOMBRE_ARCHIVO VARCHAR2(100) NOT NULL,
    NOMBRE_ARCHIVO_FISICO VARCHAR2(100) NOT NULL,
    IDENTIFICADOR_USUARIO_CREA VARCHAR2(50) NOT NULL,
    IDENTIFICADOR_USUARIO_MODIF VARCHAR2(50),
    CONSTRAINT CONVOCATORIA_PK PRIMARY KEY (ID_CONVOCATORIA)
    ) 
;

CREATE TABLE TIPO_UNIDAD_CONVOCATORIA
  (
    ID_TIPO_UNIDAD_CONVOCATORIA NUMBER NOT NULL,
    ID_TIPO_UNIDAD  NUMBER NOT NULL,
    ID_CONVOCATORIA NUMBER NOT NULL,
    CONSTRAINT TIPO_UNIDAD_CONVOCATORIA_PK PRIMARY KEY (ID_TIPO_UNIDAD_CONVOCATORIA),
    CONSTRAINT FK_TIPO_UNID_CONVO_Y_TIPO_UNID FOREIGN KEY (ID_TIPO_UNIDAD) REFERENCES TIPO_UNIDAD (ID_TIPO_UNIDAD),
    CONSTRAINT FK_TIPO_UNID_CONVO_Y_CONVOCATO FOREIGN KEY (ID_CONVOCATORIA) REFERENCES CONVOCATORIA (ID_CONVOCATORIA)
  );
 
ALTER TABLE CONVOCATORIA 
    ADD CONSTRAINT TIPO_CONVOCATOR_CONSTANTES_FK FOREIGN KEY 
    ( 
     ID_TIPO_CONVOCATORIA
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;
ALTER TABLE CONVOCATORIA 
    ADD CONSTRAINT ESTADO_CONSTANTES_FK FOREIGN KEY 
    ( 
     ID_ESTADO
    ) 
    REFERENCES CONSTANTES 
    ( 
     ID_CONSTANTES
    ) 
;
CREATE SEQUENCE SEC_CONVOCATORIA
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE SEQUENCE SEC_TIPO_UNIDAD_CONVOCATORIA
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

CREATE TABLE UNIDAD_POLICIAL_CONVOCATORIA
  (
    ID_UNIDAD_POLICIAL_CONVOCATO NUMBER NOT NULL,
    ID_UNIDAD_POLICIAL  NUMBER NOT NULL,
    ID_CONVOCATORIA NUMBER NOT NULL,
    CONSTRAINT UNIDAD_POLICIAL_CONVOCATOR_PK PRIMARY KEY (ID_UNIDAD_POLICIAL_CONVOCATO),
    CONSTRAINT FK_UNI_POLI_Y_UNI_POLI_CONVO FOREIGN KEY (ID_UNIDAD_POLICIAL) REFERENCES UNIDAD_POLICIAL (ID_UNIDAD_POLICIAL),
    CONSTRAINT FK_CONVOCATO_Y_UNI_POLI_CONVO FOREIGN KEY (ID_CONVOCATORIA) REFERENCES CONVOCATORIA (ID_CONVOCATORIA)
  );
  
  CREATE SEQUENCE SEC_UNIDAD_POLICIAL_CONVOCATO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

ALTER TABLE PERIODO ADD TIPO_REGISTRO VARCHAR2(30) DEFAULT 'NECESIDAD';

CREATE TABLE CONCECUTI_LIBERA_CONVOCATORI
    ( 
      ID_CONCECUTI_LIBERA_CONVOCATOR NUMBER NOT NULL,
      CLAVE_FINANCIA_ENSAYO VARCHAR2(100)  NOT NULL , 
      CONCECUTIVO_LIBERADO NUMBER  NOT NULL,
      CONSTRAINT CONCECUTI_LIBERA_CONVOCATOR_PK PRIMARY KEY ( ID_CONCECUTI_LIBERA_CONVOCATOR ),
      CONSTRAINT CONCECUTI_LIBERA_CONVOCATOR_UQ UNIQUE ( CLAVE_FINANCIA_ENSAYO, CONCECUTIVO_LIBERADO )
    ) 
;

CREATE SEQUENCE SEC_CONCECU_LIBERA_CONVOCATORI
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

alter table KEY_PROPERTIES drop column ID_KEY_PROPERTIES;