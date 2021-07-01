ALTER TABLE EVALUACION_PROYECTO ADD CONSTRAINT USUARIO_ROL_FK FOREIGN KEY (ID_USUARIO_ROL_EVALUADOR)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL) ENABLE;

--cu-iv-03
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (253, 'TIPO_RELACION_SEMILLERO', 'Asesor metodológico', 'Asesor metodológico');
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (254, 'TIPO_RELACION_SEMILLERO', 'Coordinador', 'Coordinador');
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (255, 'TIPO_RELACION_SEMILLERO', 'Integrante del semillero', 'Integrante del semillero');

ALTER TABLE EVIDENCIA_PROYECTO ADD (ID_COMPROMISO_PROYECTO NUMBER);
ALTER TABLE EVIDENCIA_PROYECTO ADD CONSTRAINT ID_COMPROPROY_COMPROPROY_FK FOREIGN KEY (ID_COMPROMISO_PROYECTO) REFERENCES COMPROMISO_PROYECTO (ID_COMPROMISO_PROYECTO);

--cu-iv-03
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (253, 'TIPO_RELACION_SEMILLERO', 'Asesor metodológico', 'Asesor metodológico');
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (254, 'TIPO_RELACION_SEMILLERO', 'Coordinador', 'Coordinador');
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (255, 'TIPO_RELACION_SEMILLERO', 'Integrante del semillero', 'Integrante del semillero');

ALTER TABLE RECURSO_HUMANO_SEMILLERO MODIFY (IDENTIFICACION VARCHAR2(20) );

INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (256, 'TIPO_VINCULACION_SEMILLERO', 'Docente', 'Docente');
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (257, 'TIPO_VINCULACION_SEMILLERO', 'Administrativo', 'Administrativo');
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (258, 'TIPO_VINCULACION_SEMILLERO', 'Uniformado', 'Uniformado');
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (259, 'TIPO_VINCULACION_SEMILLERO', 'No Uniformado', 'No Uniformado'); 
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (260, 'TIPO_VINCULACION_SEMILLERO', 'Contrato', 'Contrato');

ALTER TABLE RECURSO_HUMANO_SEMILLERO ADD CONSTRAINT REHU_SEMI_CONST_FK2 FOREIGN KEY (TIPO_VINCULACION)
REFERENCES CONSTANTES (ID_CONSTANTES) ENABLE;

CREATE SEQUENCE SEC_RECURSO_HUMANO_SEMILLERO INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1;

ALTER TABLE RECURSO_HUMANO_SEMILLERO ADD (FECHA_INACTIVA TIMESTAMP );

ALTER TABLE CRONOGRAMA_SEMILLERO ADD CONSTRAINT CRONOGRAMA_SEMILLERO_FK4 FOREIGN KEY (ID_USUARIO_ROL)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

ALTER TABLE CRONOGRAMA_SEMILLERO ADD CONSTRAINT CRONOGRAMA_SEMILLERO_FK5 FOREIGN KEY (ID_USUARIO_ROL_ACTUALIZA)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

CREATE SEQUENCE SEC_CRONOGRAMA_SEMILLERO INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1;

ALTER TABLE CRONOGRAMA_SEMILLERO ADD (ARCHIVO_EVIDENCIA_FOTO_ORIG VARCHAR2(512));
ALTER TABLE CRONOGRAMA_SEMILLERO ADD (ARCHIVO_EVIDENCIA_ORIGINAL VARCHAR2(512) );

ALTER TABLE SEMILLERO_INVESTIGACION ADD CONSTRAINT SEMILLERO_INVESTIGACION_FK1 FOREIGN KEY (ID_USUARIO_ROL)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

ALTER TABLE SEMILLERO_INVESTIGACION ADD CONSTRAINT SEMILLERO_INVESTIGACION_FK2 FOREIGN KEY (ID_USUARIO_ROL_ACTUALIZA)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

ALTER TABLE EVIDENCIA_PROYECTO ADD (ID_COMPROMISO_PROYECTO NUMBER);
ALTER TABLE EVIDENCIA_PROYECTO ADD CONSTRAINT ID_COMPROPROY_COMPROPROY_FK FOREIGN KEY (ID_COMPROMISO_PROYECTO) REFERENCES COMPROMISO_PROYECTO (ID_COMPROMISO_PROYECTO);

ALTER TABLE COMPROMISO_PERIODO ADD (FECHA_REGISTRO TIMESTAMP);
ALTER TABLE COMPROMISO_PERIODO ADD (FECHA_ACTUALIZA TIMESTAMP);
ALTER TABLE COMPROMISO_PERIODO ADD (IDENTIFICACION_REGISTRO VARCHAR2(50));
ALTER TABLE COMPROMISO_PERIODO ADD (IDENTIFICACION_ACTUALIZA VARCHAR2(50));

drop table EXCEPCIONES_COMPROMISO cascade constraint;
CREATE TABLE EXCEPCIONES_COMPROMISO
  (
    ID_EXCEPCION_COMPROMISO NUMBER NOT NULL,
    FECHA_LIMITE DATE,
    NOMBRE_ARCHIVO_USUARIO       VARCHAR2(100),
    NOMBRE_ARCHIVO_FISICO        VARCHAR2(100),
    JUSTIFICACION        VARCHAR2(512),
    ID_UNIDAD_POLICIAL    NUMBER NOT NULL,
    ID_COMPROMISO_PERIODO NUMBER,
    CONSTRAINT EXCEPCIONES_COMPROMISO_PK PRIMARY KEY (ID_EXCEPCION_COMPROMISO),
    CONSTRAINT ID_UNID_POLI_EXCEP_COMPORO_FK FOREIGN KEY (ID_UNIDAD_POLICIAL) REFERENCES UNIDAD_POLICIAL (ID_UNIDAD_POLICIAL),
    CONSTRAINT ID_COMPRO_PER_EXCEP_COMPORO_FK FOREIGN KEY (ID_COMPROMISO_PERIODO) REFERENCES COMPROMISO_PERIODO (ID_COMPROMISO_PERIODO)
  );
  
  CREATE SEQUENCE SEC_EXCEPCIONES_COMPROMISO
  INCREMENT by 1
  MINVALUE 1
;

ALTER TABLE IMPLEMENTACIONES_PROYECTO ADD (ESTADO_IMPLEMENTACION NUMBER);
ALTER TABLE IMPLEMENTACIONES_PROYECTO ADD CONSTRAINT IMPLEMENTACIONES_PROYECTO_FK1 FOREIGN KEY (ESTADO_IMPLEMENTACION) REFERENCES CONSTANTES (ID_CONSTANTES);

--cu-iv-03

ALTER TABLE REUNIONES_SEMILLERO ADD CONSTRAINT REUNIONES_SEMILLERO_FK3 FOREIGN KEY (ID_USUARIO_ROL)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

ALTER TABLE REUNIONES_SEMILLERO ADD CONSTRAINT REUNIONES_SEMILLERO_FK4 FOREIGN KEY (ID_USUARIO_ROL_ACTUALIZA)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

CREATE SEQUENCE SEC_EVENTOS_CAPACITA_SEMILLERO INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1;
CREATE SEQUENCE SEC_REUNIONES_SEMILLERO INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1;

ALTER TABLE REUNIONES_SEMILLERO MODIFY (MAQUINA_ACTUALIZA VARCHAR2(50 BYTE) );

INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (267,'TIPO_MODALIDAD_PARTICIPACION_SEMILLERO', 'Ponentes', 'Ponentes');
INSERT INTO CONSTANTES (ID_CONSTANTES, TIPO, CODIGO, VALOR) VALUES (268,'TIPO_MODALIDAD_PARTICIPACION_SEMILLERO', 'Asistentes', 'Asistentes');

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO ADD CONSTRAINT EVENTOS_CAPACITACION_SEMI_FK4 FOREIGN KEY (MODALIDAD_PARTICIPACION)
REFERENCES CONSTANTES (ID_CONSTANTES)
ENABLE;

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO 
ADD (CLASE_TEMA_PONENCIA VARCHAR2(512) NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (NOMBRE_EVENTO NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (LUGAR_EVENTO NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (FECHA_INICIO_EVENTO NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (ID_CIUDAD NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (NUMERO_OFICIALES NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (FECHA_CREACION NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (ID_USUARIO_ROL NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (MAQUINA NOT NULL);

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO ADD (ARCHIVO_EVIDENCIA_DOC_ORIG VARCHAR2(512) );

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO ADD (ARCHIVO_EVIDENCIA_FOTOG_ORIG VARCHAR2(512) );

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (NUMERO_OFICIALES NUMBER(4, 0) );

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (NUMERO_SUBOFICIALES NUMBER(4, 0) );

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (NUMERO_ESTUDIANTES NUMBER(4, 0) );

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (NUMERO_NO_UNIFORMADOS NUMBER(4, 0) );

ALTER TABLE EVENTOS_CAPACITACION_SEMILLERO  
MODIFY (MAQUINA_ACTUALIZA VARCHAR2(50 BYTE) );


CREATE TABLE OTROS_ESTIMULOS_SEMILLERO 
(
  ID_OTROS_ESTIMULOS_SEMILLERO NUMBER NOT NULL, 
  MOTIVO_OTORGAMIENTO VARCHAR2(100) NOT NULL, 
  DESCRIPCION_TIPO_ESTIMULO VARCHAR2(4000) NOT NULL,
  FECHA_OTORGAMIENTO TIMESTAMP NOT NULL,
  ID_SEMILLERO NUMBER NOT NULL,
  FECHA_CREACION DATE DEFAULT SYSDATE NOT NULL,
  ID_USUARIO_ROL NUMBER NOT NULL,
  MAQUINA VARCHAR2(50) NOT NULL,
  FECHA_ACTUALIZACION DATE,
  ID_USUARIO_ACTUALIZA NUMBER,
  MAQUINA_ACTUALIZA VARCHAR2(50),
  CONSTRAINT OTROS_ESTIMULOS_SEMILLERO_PK PRIMARY KEY (ID_OTROS_ESTIMULOS_SEMILLERO)
  USING INDEX (CREATE UNIQUE INDEX OTROS_ESTIMULOS_SEMILLERO_PK ON OTROS_ESTIMULOS_SEMILLERO (ID_OTROS_ESTIMULOS_SEMILLERO ASC))
  ENABLE 
);

COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.ID_OTROS_ESTIMULOS_SEMILLERO IS 'Identificador de la tabla';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.MOTIVO_OTORGAMIENTO IS 'Motivo del otorgamiento del estimulo';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.DESCRIPCION_TIPO_ESTIMULO IS 'Descripción del motivo del estimulo';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.FECHA_OTORGAMIENTO IS 'Fecha en que otorgó el estimulo';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.ID_SEMILLERO IS 'Identificador del semillero relacionado';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.FECHA_CREACION IS 'Fecha de creación del registro';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.ID_USUARIO_ROL IS 'Identificador del usuario que crea el registro';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.MAQUINA IS 'Dirección IP ó Hostname de la máquina desde donde se crea el registro';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.FECHA_ACTUALIZACION IS 'Fecha de actualización del registro';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.ID_USUARIO_ACTUALIZA IS 'Identificador del usuario que realiza la actualizacion del registro';
COMMENT ON COLUMN OTROS_ESTIMULOS_SEMILLERO.MAQUINA_ACTUALIZA IS 'Dirección IP ó Hostname de la máquina desde donde se actualiza el registro';

ALTER TABLE OTROS_ESTIMULOS_SEMILLERO ADD CONSTRAINT OTROS_ESTIMULOS_SEMILLERO_FK1 FOREIGN KEY (ID_SEMILLERO)
REFERENCES SEMILLERO_INVESTIGACION (ID_SEMILLERO)
ENABLE;

ALTER TABLE OTROS_ESTIMULOS_SEMILLERO ADD CONSTRAINT OTROS_ESTIMULOS_SEMILLERO_FK2 FOREIGN KEY (ID_USUARIO_ROL)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

ALTER TABLE OTROS_ESTIMULOS_SEMILLERO ADD CONSTRAINT OTROS_ESTIMULOS_SEMILLERO_FK3 FOREIGN KEY (ID_USUARIO_ACTUALIZA)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

CREATE TABLE TALENTO_ESTIMULO_SEMILLERO 
(
  ID_TALENTO_ESTIMULO_SEMILLERO NUMBER NOT NULL,
  ID_RECURSO_HUMANO_SEMILLERO NUMBER NOT NULL, 
  ID_OTROS_ESTIMULOS_SEMILLERO NUMBER NOT NULL, 
  FECHA_CREACION TIMESTAMP NOT NULL, 
  ID_USUARIO_ROL NUMBER NOT NULL, 
  MAQUINA VARCHAR2(50) NOT NULL, 
  CONSTRAINT TALENTO_ESTIMULO_SEMILLERO_PK PRIMARY KEY (ID_TALENTO_ESTIMULO_SEMILLERO)
  ENABLE 
);

COMMENT ON COLUMN TALENTO_ESTIMULO_SEMILLERO.ID_TALENTO_ESTIMULO_SEMILLERO IS 'Identificador de la tabla';
COMMENT ON COLUMN TALENTO_ESTIMULO_SEMILLERO.ID_RECURSO_HUMANO_SEMILLERO IS 'Identificador del talento humano al que se le otorga el estimulo';
COMMENT ON COLUMN TALENTO_ESTIMULO_SEMILLERO.ID_OTROS_ESTIMULOS_SEMILLERO IS 'Identificador del estimulo otorgado';
COMMENT ON COLUMN TALENTO_ESTIMULO_SEMILLERO.FECHA_CREACION IS 'Fecha de creación del registro';
COMMENT ON COLUMN TALENTO_ESTIMULO_SEMILLERO.ID_USUARIO_ROL IS 'Identificador del usuario que crea el registro';
COMMENT ON COLUMN TALENTO_ESTIMULO_SEMILLERO.MAQUINA IS 'Dirección IP ó Hostname de la máquina desde donde se crea el registro';

ALTER TABLE TALENTO_ESTIMULO_SEMILLERO ADD CONSTRAINT TALENTO_ESTIMULO_SEMILLERO_FK1 FOREIGN KEY (ID_RECURSO_HUMANO_SEMILLERO)
REFERENCES RECURSO_HUMANO_SEMILLERO (ID_RECURSO_HUMANO_SEMI)
ENABLE;

ALTER TABLE TALENTO_ESTIMULO_SEMILLERO ADD CONSTRAINT TALENTO_ESTIMULO_SEMILLERO_FK2 FOREIGN KEY (ID_OTROS_ESTIMULOS_SEMILLERO)
REFERENCES OTROS_ESTIMULOS_SEMILLERO (ID_OTROS_ESTIMULOS_SEMILLERO)
ENABLE;

ALTER TABLE TALENTO_ESTIMULO_SEMILLERO ADD CONSTRAINT TALENTO_ESTIMULO_SEMILLERO_FK3 FOREIGN KEY (ID_USUARIO_ROL)
REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
ENABLE;

CREATE SEQUENCE SEC_OTROS_ESTIMULOS_SEMILLERO INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1; 
CREATE SEQUENCE SEC_TALENTO_ESTIMULO_SEMILLERO INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999 MINVALUE 1; 

DROP TABLE INFORME_AVANCE_IMPLEMENTACION CASCADE CONSTRAINTS;
CREATE TABLE INFORME_AVANCE_IMPLEMENTACION
  (
    ID_INFORME_AVANCE_IMPL NUMBER NOT NULL ,
    ID_IMPLEMENTACION_PROY NUMBER NOT NULL ,
    ID_COMPROMISO_IMPLEMENTACION NUMBER NOT NULL ,
    FECHA_APROBACION_COMITE DATE,
    NRO_ACTA_APROBACION_COMITE VARCHAR2(50),
    IDENTIFICACION_JEFE_UNIDAD VARCHAR2(20),
    FECHA_INICIO DATE,
    FECHA_FIN DATE,
    ID_USUARIO_ROL         NUMBER,
    FECHA_CREACION TIMESTAMP,
    USUARIO_CREACION VARCHAR2(50),
    MAQUINA          VARCHAR2(50),    
    CONSTRAINT INFORME_AVANCE_IMPLEMENTAC_PK PRIMARY KEY (ID_INFORME_AVANCE_IMPL),
    CONSTRAINT ID_COMPRO_IMPLEM_AVANC_IMPL_FK FOREIGN KEY (ID_COMPROMISO_IMPLEMENTACION) REFERENCES COMPROMISO_IMPLEMENTACION (ID_COMPROMISO_IMPLEMENTACION) ,
    CONSTRAINT ID_IMPL_PROY_AVANC_IMPL_FK FOREIGN KEY (ID_IMPLEMENTACION_PROY) REFERENCES IMPLEMENTACIONES_PROYECTO (ID_IMPLEMENTACION_PROY),
    CONSTRAINT ID_USUA_ROL_COMP_USARIO_ROL FOREIGN KEY (ID_USUARIO_ROL) REFERENCES USUARIO_ROL(ID_USUARIO_ROL)
  );

CREATE SEQUENCE SEC_INFORME_AVANCE_IMPL
  INCREMENT by 1
  MINVALUE 1
;

ALTER TABLE PLAN_TRABAJO_IMPLEMENTACION ADD (ID_COMPROMISO_IMPLEMENTACION NUMBER);
ALTER TABLE PLAN_TRABAJO_IMPLEMENTACION ADD CONSTRAINT ID_COMPR_IMPL_PLANT_TRA_IMP_FK FOREIGN KEY (ID_COMPROMISO_IMPLEMENTACION) REFERENCES COMPROMISO_IMPLEMENTACION (ID_COMPROMISO_IMPLEMENTACION);

DROP TABLE PERSONAL_INF_AVANCE_IMPL CASCADE CONSTRAINTS;
CREATE TABLE PERSONAL_INF_AVANCE_IMPL
(
    ID_PERSONAL_INF_AVANCE_IMPL NUMBER NOT NULL ,
    ID_INFORME_AVANCE_IMPLEMENTA NUMBER NOT NULL ,
    ID_UNIDAD_POLICIAL NUMBER,
    ORIGEN_SIAF_O_INVESTI CHAR(1) NOT NULL,
    HORAS_EJECUTADAS            NUMBER(3,0),
    FECHA_REGISTRO DATE,
    USUARIO_REGISTRO VARCHAR2(50 ),
    MAQUINA          VARCHAR2(20 ),
    CORREO          VARCHAR2(100),
    NOMBRE_COMPLETO          VARCHAR2(100),
    GRADO          VARCHAR2(50),
    IDENTIFICACION          VARCHAR2(50),    
    CONSTRAINT PERSONAL_INF_AVANCE_IMPL_PK PRIMARY KEY (ID_PERSONAL_INF_AVANCE_IMPL),    
    CONSTRAINT PERSONAL_INF_IMPL_INVEST_FK2 FOREIGN KEY (ID_INFORME_AVANCE_IMPLEMENTA) REFERENCES INFORME_AVANCE_IMPLEMENTACION (ID_INFORME_AVANCE_IMPL),
    CONSTRAINT PERSONAL_INF_IMPL_INVEST_FK3 FOREIGN KEY (ID_UNIDAD_POLICIAL) REFERENCES UNIDAD_POLICIAL (ID_UNIDAD_POLICIAL)
  );


CREATE TABLE ACTIVIDADES_IMPLEMENTACION
  (
    ID_ACTIVIDAD_IMPLEMENTACION NUMBER NOT NULL ,
    ID_INFORME_AVANCE_IMPL      NUMBER NOT NULL ,
	ID_USUARIO_ROL            NUMBER NOT NULL ,    
    ID_ACTIVIDAD_PLAN_TRABAJO NUMBER,
    RESULTADO_ACTIVIDAD       VARCHAR2(4000 ),
    ID_ESTADO_ACTIVIDAD       NUMBER,
    FECHA_REAL_INICIO DATE,
    FECHA_REAL_FIN DATE,
    ARCHIVO_SOPORTE           VARCHAR2(100 ),
    EVIDENCIA_FOTOGRAFICA     VARCHAR2(100 ),
    PORC_CUMPLIMIENTO_PARCIAL NUMBER,
    NUEVA_FECHA_FINALIZACION DATE,
    JUSTIFICACION_PARCIAL VARCHAR2(4000 ),
    ACCIONES_ADEL_LOGRO   VARCHAR2(4000 ),
    NUEVA_FECHA_INICIO DATE,
    NUEVA_FECHA_FIN DATE
	FECHA_REGISTRO TIMESTAMP,    
    USUARIO                   VARCHAR2(50 ),
    MAQUINA                   VARCHAR2(100 ) NOT NULL,
    CONSTRAINT ACTIVIDADES_IMPLEMENTACION_PK PRIMARY KEY (ID_ACTIVIDAD_IMPLEMENTACION),
    CONSTRAINT ACTI_INFOR_AVANCE_IMPL_FK FOREIGN KEY (ID_INFORME_AVANCE_IMPL) REFERENCES IMPLEMENTACIONES_PROYECTO (ID_IMPLEMENTACION_PROY) ,
    CONSTRAINT ACTIVIDADES_IMPLEMENTACIO_FK1 FOREIGN KEY (ID_ACTIVIDAD_PLAN_TRABAJO) REFERENCES ACTIVIDADES_PLAN_TRABAJO (ID_ACTIV_PLAN_TRABAJO) ,
    CONSTRAINT ACTIVIDADES_IMPLEMENTACIO_FK2 FOREIGN KEY (ID_ESTADO_ACTIVIDAD) REFERENCES CONSTANTES (ID_CONSTANTES) 
  );
COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.ID_ACTIVIDAD_IMPLEMENTACION
IS
  'Identificador de actividad de implementación';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.ID_INFORME_AVANCE_IMPL
IS
  'Informe de avance de implementación';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.FECHA_REGISTRO
IS
  'Fecha de registro';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.ID_USUARIO_ROL
IS
  'Identificador de usuario rol que realiza el registro';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.USUARIO
IS
  'Usuario que hace la implementación';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.MAQUINA
IS
  'Máquina donde se genera la soliciitud';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.ID_ACTIVIDAD_PLAN_TRABAJO
IS
  'Identificador de la actividad del plan de trabajo';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.RESULTADO_ACTIVIDAD
IS
  'Resultado de la actividad';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.ID_ESTADO_ACTIVIDAD
IS
  'Identificador del estado de la actividad';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.FECHA_REAL_INICIO
IS
  'Fecha real de inicio de la actividad';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.FECHA_REAL_FIN
IS
  'Fecha real de finalización de la actividad';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.ARCHIVO_SOPORTE
IS
  'Archivo de soporte';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.EVIDENCIA_FOTOGRAFICA
IS
  'Evidencia fotográfica de la actividad';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.PORC_CUMPLIMIENTO_PARCIAL
IS
  'Porcentaje de cumplimiento de la actividad';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.NUEVA_FECHA_FINALIZACION
IS
  'Nueva fecha de finalización cuando la actividad se hace parcialmente';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.JUSTIFICACION_PARCIAL
IS
  'Justificación por la cual la actividad se realiza parcialmente';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.ACCIONES_ADEL_LOGRO
IS
  'Acciones adelantadas para el logro en caso de que la actividad no se cumpla';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.NUEVA_FECHA_INICIO
IS
  'Nueva fecha de inicio para actividad no cumplida';
  COMMENT ON COLUMN ACTIVIDADES_IMPLEMENTACION.NUEVA_FECHA_FIN
IS
  'Nueva fecha fin de la actividad no cumplida';

alter table TEMA_PROYECTO add CONSTRAINT ID_INFOR_AVANCE_IMPL_TEMA_FK FOREIGN KEY (ID_INFORME_AVANCE_IMPL) REFERENCES INFORME_AVANCE_IMPLEMENTACION (ID_INFORME_AVANCE_IMPL);

drop table RESPONSABLE_ACTIVIDAD_IMPLEMEN cascade constraints;
CREATE TABLE RESPONSABLE_ACTIVIDAD_IMPLEMEN
  (
    ID_RESPONSABLE_ACTIVIDAD_IMPL NUMBER NOT NULL ,
    ID_INVESTIGADOR_PROYECTO      NUMBER NOT NULL ,
    ID_PLAN_TRABAJO   NUMBER NOT NULL ,
    CONSTRAINT RESPONSABLE_ACTIVIDAD_IMPL_PK PRIMARY KEY (ID_RESPONSABLE_ACTIVIDAD_IMPL),
    CONSTRAINT RESPONSABLE_ACTIVIDAD_IMP_FK2 FOREIGN KEY (ID_PLAN_TRABAJO) REFERENCES PLAN_TRABAJO_IMPLEMENTACION (ID_PLAN_TRABAJO) ,
    CONSTRAINT RESPONSABLE_ACTIVIDAD_IMP_FK1 FOREIGN KEY (ID_INVESTIGADOR_PROYECTO) REFERENCES INVESTIGADORES_PROYECTO (ID_INVESTIGADOR_PROYECTO) 
  );

ALTER TABLE INVESTIGADORES_PROYECTO  MODIFY (ID_PROYECTO NULL);
ALTER TABLE INVESTIGADORES_PROYECTO  MODIFY (ID_TIPO_VINCULACION NULL);
ALTER TABLE INVESTIGADORES_PROYECTO  MODIFY (ACTIVO NULL);

CREATE SEQUENCE SEC_ACTIVIDADES_PLAN_TRABAJO
  INCREMENT by 1
  MINVALUE 1
;

DROP TABLE RESPONSABLE_ACTIVIDAD_IMPLEMEN CASCADE CONSTRAINT;
CREATE TABLE RESPONSABLE_ACTIVIDAD_IMPLEMEN
  (
    ID_RESPONSABLE_ACTIVIDAD_IMPL NUMBER NOT NULL ,
    ID_INVESTIGADOR_PROYECTO      NUMBER NOT NULL ,
    ID_ACTIVIDAD_PLAN_TRB_IMPL NUMBER NOT NULL ,
    CONSTRAINT RESPONSABLE_ACTIVIDAD_IMPL_PK PRIMARY KEY (ID_RESPONSABLE_ACTIVIDAD_IMPL),    
    CONSTRAINT RESPONSABLE_ACTIVIDAD_IMP_FK1 FOREIGN KEY (ID_INVESTIGADOR_PROYECTO) REFERENCES INVESTIGADORES_PROYECTO (ID_INVESTIGADOR_PROYECTO),
    CONSTRAINT RESPONSABLE_ACTIVIDAD_IMP_FK2 FOREIGN KEY (ID_ACTIVIDAD_PLAN_TRB_IMPL) REFERENCES ACTIVIDADES_PLAN_TRABAJO (ID_ACTIV_PLAN_TRABAJO) 
  );

CREATE SEQUENCE SEC_RESPONSABLE_ACTIVIDAD_IMPL
  INCREMENT by 1
  MINVALUE 1
;

-- cu-ad-01 Administracion de usuarios
CREATE OR REPLACE VIEW USUARIO_ROL_UNIDAD AS
SELECT ROWNUM ID_REG, 
U.ID_UNIDAD_POLICIAL, 
U.NOMBRE NOMBRE_UNIDAD,
R.ID_ROL, 
R.NOMBRE NOMBRE_ROL, 
VF.IDENTIFICACION IDENTIFICACION_USUARIO, 
VF.GRADO GRADO_USUARIO, 
VF.NOMBRE_COMPLETO NOMBRE_COMPLETO_USUARIO,
R.REQUIERE_UNIDAD_POLICIAL,
VF.CARGO CARGO_USUARIO,
VF.CORREO CORREO_USUARIO,
UR.ACTIVO ESTADO_USUARIO_ROL
FROM ROL R 
JOIN USUARIO_ROL UR ON (R.ID_ROL = UR.ID_ROL)
JOIN VISTA_FUNCIONARIO VF ON (VF.IDENTIFICACION = UR.IDENTIFICADOR_USUARIO)
LEFT JOIN UNIDAD_POLICIAL U ON (VF.COD_UNID_LAB = U.ID_UNIDAD_POLICIAL);

Insert into OPCION_MENU (ID_OPCION_MENU,NOMBRE,TIPO_ACCESO,ACCION,ID_OPCION_MENU2,ORDEN,TITULO) values (35,'Usuarios','PRIVADO','BEAN:#{cuAd01TipoUsuarioAdministrador.initReturnCU}',8,1,'Permite al usuario gestionar los usuarios del sistema ');

Insert into ROL_OPCION_MENU (ID_ROL_OPCION_MENU,ID_ROL,ID_OPCION_MENU) values (53,3,35);
Insert into ROL_OPCION_MENU (ID_ROL_OPCION_MENU,ID_ROL,ID_OPCION_MENU) values (54,3,8);

Insert into VISTA_FUNCIONARIO (ID_VISTA_FUNCIONARIO,IDENTIFICACION,NOMBRE_COMPLETO,CORREO,GRADO,CARGO,NOMBRES,APELLIDOS,COD_CARGO,DIREC_EMPLEA,DEPTO_RESIDE,COD_DEPTO_RESIDE,CIUDAD_RESIDE,COD_CIU_RES,SIGLA_LABORANDO,COD_UNID_LAB,TELEFONO,CIU_EXP_DOC,COD_CIU_EXP_DOC,NUMERO_CELULAR,DESCRIPCION,COD_CATEG_EMP,INGRESO_INSTITU,GRADO_ALFABETICO) values (7229164215,'7229164215','Carlos Guzman Pulido','carlos.guzman@pointmind.com','TENIENTE','ADMINISTRADOR USUARIOS','CARLOS','GUZMAN PULIDO','codig_cargo','AV EVERGREEN 123','CUNDINAMARCA','3947','BOGOTA D.C','1228', null, null,'5555555','BOGOTA D.C','1228','3111111111','NIVEL EJECUTIVO','3',to_timestamp('04-JUN-87 12.00.00.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),'TN');

Insert into USUARIO_ROL (ID_USUARIO_ROL,IDENTIFICADOR_USUARIO,ID_ROL,FECHA_INICIO,FECHA_FIN,ACTIVO) values (46,'72291642',3,to_date('10-FEB-14','DD-MON-RR'),null,'S');




delete from ACTIVIDADES_IMPLEMENTACION;
delete from ACTIVIDADES_PLAN_TRABAJO;
delete from ACTIVIDADES_REALIZADAS_PROY;
delete from ASESORIA_PROYECTO;
delete from COMENTARIO;
delete from COMENTARIO_COMPROMISO_PROYECTO;
delete from COMENTARIO_PROYECTO;
delete from COMPROMISO_IMPLEMENTACION;
delete from COMPROMISO_PERIODO;
delete from COMPROMISO_PROYECTO;
delete from CONCECUTI_LIBERA_CONVOCATORI;
delete from CORRECCIONES_COMPROMISO_PROY;
delete from CRONOGRAMA_SEMILLERO;
delete from DETALLE_EVALUACION;
delete from DOCUMENTACION_PROYECTO;
delete from EJECUCION_EQUIPOS_PROYECTO;
delete from EJECUCION_EVENTOS_PROYECTO;
delete from EJECUCION_HORAS_PROYECTO;
delete from EJECUCION_OTROS_GASTOS_PROY;
delete from EJECUCION_VIAJES_PROYECTO;
delete from ENSAYO_CRITICO;
delete from EQUIPOS_INVESTIGACION;
delete from EQUIPOS_INVESTIGACION_VERSION;
delete from ESTADO_COMPROMISO_PROYECTO;
delete from EVALUACION_PROYECTO;
delete from EVALUACION_PROYECTO_GRADO;
delete from EVALUADORES_PROYECTO_GRADO;
delete from EVENTO_GENERAL_PROYECTO;
delete from EVENTO_PROYECTO;
delete from EVENTO_PROYECTO_VERSION;
delete from EVENTOS_CAPACITACION_SEMILLERO;
delete from EVIDENCIA_PROYECTO;
delete from EXCEPCIONES_COMPROMISO;
delete from FORMACION_INVESTIGADOR;
delete from FUENTE_PROYECTO;
delete from FUENTE_PROYECTO_VERSION;
delete from FUNCIONARIO_NECESIDAD;
delete from GASTO_EVENTO;
delete from GRUPO_INVEST_PROYECTO;
delete from GRUPO_INVEST_PROYECTO_VERSION;
delete from HISTORIAL_COMPROMISO;
delete from HISTORIAL_ESTADOS_PROYECTO;
delete from HORAS_INVESTIGADOR;
delete from IMPLEMENTACIONES_PROYECTO;
delete from INDICADOR_COMPROMISO_PROYECTO;
delete from INDICADORES_INFORME_AVANCE_IMP;
delete from INDICADORES_PLAN_TRABAJO;
delete from INDICADORES_PROYECTO;
delete from INDICADORES_PROYECTO_VERSION;
delete from INFORME_AVANCE;
delete from INFORME_AVANCE_IMPLEMENTACION;
delete from INSTITUCIONES_PROYECTO;
delete from INSTITUCIONES_PROYECTO_VERSION;
delete from INVESTI_DESARROL_INVESTTIGADOR;
delete from INVESTIGADOR;
delete from INVESTIGADORES_PROY_VERSION;
delete from INVESTIGADORES_PROYECTO;
delete from MODALIDAD_ASESORIA_PROYECTO;
delete from OTROS_ESTIMULOS_SEMILLERO;
delete from OTROS_GASTOS_PROYECTO;
delete from OTROS_GASTOS_PROYECTO_VERSION;
delete from PERIODO;
delete from PERSONAL_INF_AVANCE_IMPL;
delete from PLAN_TRABAJO_IMPLEMENTACION;
delete from PROPUESTA_NECESIDAD;
delete from PROYECTO;
delete from PROYECTO_VERSION;
delete from PUBLICACION_INVESTIGADOR;
delete from RECURSO_HUMANO_SEMILLERO;
delete from RESENIA_INVESTIGACION;
delete from RESPONSABLE_ACTIVIDAD_IMPLEMEN;
delete from RESULTADOS_ALCANZADOS_PROY;
delete from RESULTADOS_INVESTIGACION;
delete from RESUMEN_PRESUPUESTO_IMPL;
delete from RESUMEN_PRESUPUESTO_PROYECTO;
delete from REUNIONES_SEMILLERO;
delete from SEMILLERO_PROYECTO;
delete from SEMILLERO_PROYECTO_VERSION;
delete from SEMILLEROS_IMPLEMENTACION;
delete from SUGERENCIAS_PROY;
delete from TALENTO_ESTIMULO_SEMILLERO;
delete from TEMA_PROYECTO;
delete from TEMA_PROYECTO_VERSION;
delete from TIPO_GASTO_EVENTO;
delete from TIPO_UNIDAD_PERIODO;
delete from UNIDAD_EJECUTORA;
delete from UNIDAD_POLICIAL_PERIODO;
delete from VERSIONES;
delete from VIAJES_PROYECTO;
delete from VIAJES_PROYECTO_VERSION;


CREATE SEQUENCE SEC_INDICADORES_PLAN_TRABAJO
  INCREMENT by 1
  MINVALUE 1
;
