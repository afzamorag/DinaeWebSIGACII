alter table GRUPO_INVESTIGACION MODIFY DESCRIPCION VARCHAR2(150);

ALTER TABLE SEMILLERO_PROYECTO ADD APORTE_INVESTIGACION VARCHAR2(512) NOT NULL;

drop SEQUENCE SEC_SEMILLERO_PROYECTO;
CREATE SEQUENCE SEC_SEMILLERO_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

ALTER TABLE SEMILLERO_PROYECTO ADD ID_UNIDAD_POLICIAL NUMBER;
ALTER TABLE SEMILLERO_PROYECTO ADD CONSTRAINT ID_UNIDAD_UNIDAD_POLICIAL_FK FOREIGN KEY (ID_UNIDAD_POLICIAL) REFERENCES UNIDAD_POLICIAL (ID_UNIDAD_POLICIAL);
ALTER TABLE SEMILLERO_PROYECTO MODIFY ID_SEMILLERO NUMBER NULL;

ALTER TABLE INVESTIGADORES_PROYECTO ADD CALCULO_HORA_TOTAL NUMBER;

ALTER TABLE INVESTIGADORES_PROYECTO ADD ID_UNIDAD_POLICIAL NUMBER;
ALTER TABLE INVESTIGADORES_PROYECTO ADD CONSTRAINT ID_UNIDA_INVES_PRO_POLICIAL_FK FOREIGN KEY (ID_UNIDAD_POLICIAL) REFERENCES UNIDAD_POLICIAL (ID_UNIDAD_POLICIAL);

DROP TABLE INSTITUCIONES_PROYECTO CASCADE CONSTRAINT;
CREATE TABLE INSTITUCIONES_PROYECTO
  (
    ID_INSTITUCION_PROYECTO NUMBER NOT NULL,
    FECHA_REGISTRO 			TIMESTAMP NOT NULL,
    ID_USUARIO_ROL 			NUMBER NOT NULL,
    ID_INSTITUCION          NUMBER NOT NULL,
    APORTE_INVESTIGACION    VARCHAR2(512) NOT NULL,
    VALOR_OTRO_TIPO         VARCHAR2(100),
    ID_PROYECTO             NUMBER NOT NULL,
    CONSTRAINT INSTITUCIONES_PROYECTO_PK PRIMARY KEY (ID_INSTITUCION_PROYECTO),
    CONSTRAINT INSTITU_PROYECTO_FK FOREIGN KEY (ID_PROYECTO) REFERENCES PROYECTO (ID_PROYECTO),
	CONSTRAINT INSTITU_CONSTANTES_FK FOREIGN KEY (ID_INSTITUCION) REFERENCES CONSTANTES (ID_CONSTANTES),
	CONSTRAINT INSTITU_USUARIOROL_FK FOREIGN KEY (ID_USUARIO_ROL) REFERENCES USUARIO_ROL (ID_USUARIO_ROL)
  );

drop SEQUENCE SEC_INSTITUCIONES_PROYECTO;
CREATE SEQUENCE SEC_INSTITUCIONES_PROYECTO
  START WITH 1
  INCREMENT by 1
  MINVALUE 1
;

 DROP TABLE EQUIPOS_INVESTIGACION CASCADE CONSTRAINT;
  CREATE TABLE EQUIPOS_INVESTIGACION
  (
    ID_EQUIPO_INVESTIGACION NUMBER NOT NULL,
    VALOR                   NUMBER(15,2) NOT NULL,
    ESPECIFICACIONES        VARCHAR2(512) NOT NULL,
    NOMBRE_EQUIPO           VARCHAR2(250) NOT NULL,
    ID_FUENTE_PROYECTO      NUMBER NOT NULL,
    ORIGEN                  VARCHAR2(100),
    CONSTRAINT EQUIPOS_INVESTIGACION_PK PRIMARY KEY (ID_EQUIPO_INVESTIGACION),
    CONSTRAINT FUENTE_EQUI_INVESTIGACION_FK FOREIGN KEY (ID_FUENTE_PROYECTO) REFERENCES FUENTE_PROYECTO (ID_FUENTE_PROYECTO)
  );

ALTER TABLE FUENTE_PROYECTO ADD FUENTE_BASE CHAR(1) DEFAULT 'N';

delete from GRUPO_INVESTIGACION;
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('1','GRUPO DE INVESTIGACI??N EN PSICOLOGIA FORENSE Y PERFILACION CRIMINAL GRUPSICRIM-MEBAR','COL0096293',to_timestamp('01/01/09 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('2','GRUPO DE INVESTIGACI??N OBSERVATORIO DEL DELITO OBSER DIJIN','COL0117778',to_timestamp('01/04/09 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('3','CENTRO DE INTELIGENCIA PROSPECTIVA (CIPRO)','COL0022369',to_timestamp('01/07/01 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('4','LABORATORIO DE GENETICA FORENSE','COL0013094',to_timestamp('01/01/87 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('5','ESCUELA DE INVESTIGACI??N CRIMINAL ESINC- DINAE','COL0092159',to_timestamp('01/11/08 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('6','INVESTUD INVESTIGACION Y ESTUDIOS EXPERIMENTALES ORIENTADOS AL TRABAJO POLICIAL','COL0074115',to_timestamp('01/09/05 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('7','ESCUELA DE CADETES DE POLICIA GENERAL FRANCISCO DE PAULA SANTANDER','COL0078651',to_timestamp('01/02/08 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('8','COGNICI??N Y VIOLENCIA DISAN','COL0044994',to_timestamp('02/06/02 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('9','GRUPO INNOVACI??N ESREY','COL0022502',to_timestamp('01/01/08 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('10','ESTEL DINAE','COL0085075',to_timestamp('01/01/08 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));
Insert into GRUPO_INVESTIGACION (ID_GRUPO_INVESTIGACION,DESCRIPCION,CODIGO_GRUPO,FECHA_REGISTRO_GRUPO) values ('11','GRUPO DE INVESTIGACI??N ESECU-DINAE','COL0123209',to_timestamp('01/01/06 12:00:00,000000000 AM','DD/MM/RR HH12:MI:SS,FF AM'));

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('80','TIPO_ESTADO_PROPUESTA_CONVOCATORIA','En elaboraci??n','En elaboraci??n');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('81','TIPO_ESTADO_PROPUESTA_CONVOCATORIA','En revisi??n de Jefe de la Unidad','En revisi??n de Jefe de la Unidad');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('82','TIPO_ESTADO_PROPUESTA_CONVOCATORIA','No aceptado por Jefe de Unidad','No aceptado por Jefe de Unidad');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('83','TIPO_ESTADO_PROPUESTA_CONVOCATORIA','Aceptado por Jefe de Unidad','Aceptado por Jefe de Unidad');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('84','TIPO_ESTADO_PROPUESTA_CONVOCATORIA','Enviada a VICIN','Enviada a VICIN');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('85','TIPO_ESTADO_PROPUESTA_CONVOCATORIA','Evaluada','Evaluada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('86','TIPO_ESTADO_PROPUESTA_CONVOCATORIA','No aprobada','No aprobada');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('87','TIPO_ESTADO_PROPUESTA_CONVOCATORIA','Para finalizar','Para finalizar');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('88','TIPO_ITEM_COMENTARIO_REVISION_PROYECTO','Objetivo','Objetivo');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('89','TIPO_ITEM_COMENTARIO_REVISION_PROYECTO','Planteamiento del proyecto','Planteamiento del proyecto');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('90','TIPO_ITEM_COMENTARIO_REVISION_PROYECTO','Indicadores','Indicadores');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('91','TIPO_ITEM_COMENTARIO_REVISION_PROYECTO','Otro','Otro');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('92','DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL','DURACION_PROYECTOS_DE_CONVOCATORIA','12');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('93','DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL','DURACION_PROYECTOS_INSTITUCIONALES','10');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('94','DURACION_PROYECTO_CONVOCATORIA_INSTITUCIONAL','CANTIDAD_DE_SEMANAS_POR_MES','4.33');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('95','TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO','Colciencias','Colciencias');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('96','TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO','Ministerio de Defensa','Ministerio de Defensa');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('97','TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO','Investigare','Investigare');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('98','NOMBRE_FUENTE_FINANCIERA_CONSTANTE','Polic??a Nacional','Polic??a Nacional-Interna');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('99','NOMBRE_FUENTE_FINANCIERA_CONSTANTE','VICIN','VICIN-Interna');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('120', 'ORIGEN_EQUIPO_INVESTIGACION', 'Propio', 'Propio');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('121', 'ORIGEN_EQUIPO_INVESTIGACION', 'Adquisici??n', 'Adquisici??n');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('122', 'ORIGEN_EQUIPO_INVESTIGACION', 'Alquiler', 'Alquiler');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('100','ESTADO_COMPROMISO_PROYECTO','Pendiente','Pendiente');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('101','ESTADO_COMPROMISO_PROYECTO','En elaboraci??n','En elaboraci??n');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('102','ESTADO_COMPROMISO_PROYECTO','En revisi??n del Jefe','En revisi??n del Jefe');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('103','ESTADO_COMPROMISO_PROYECTO','No Aceptado','No Aceptado');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('104','ESTADO_COMPROMISO_PROYECTO','Aceptado','Aceptado');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('105','ESTADO_COMPROMISO_PROYECTO','Enviado a Vicin','Enviado a Vicin');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('106','ESTADO_COMPROMISO_PROYECTO','Revisado','Revisado');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('107','ESTADO_COMPROMISO_PROYECTO','Cumplido','Cumplido');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('108','ESTADO_COMPROMISO_PROYECTO','Correci??n Compromiso','Correci??n Compromiso');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('109','TIPO_INSTITUCIONES_PARTICIPANTES_PROYECTO','Otro','Otro');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('110','TIPO_ESTADO_PROYECTO','En Ejecuci??n','En Ejecuci??n');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('111','TIPO_ESTADO_PROYECTO','Culminado','Culminado');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('112','TIPO_ESTADO_PROYECTO','Evaluado','Evaluado');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('113','TIPO_ESTADO_PROYECTO','En Implementaci??n','En Implementaci??n');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('114','TIPO_ESTADO_PROYECTO','Implementado','Implementado');

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('115','TIPO_FUENTE_FINANCIERA','Externa','Externa');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('116','TIPO_FUENTE_FINANCIERA','Interna','Interna');

commit;