Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('248','TIPO_RESULTADO_RETROALIMENTACION_COMPROMISO','Requiere corrección','Requiere corrección');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('249','TIPO_RESULTADO_RETROALIMENTACION_COMPROMISO','No requiere corrección','No requiere corrección');

ALTER TABLE CRITERIO_EVALUACION  
MODIFY (NOMBRE_CRITERIO VARCHAR2(120 BYTE) );

ALTER TABLE CRITERIO_EVALUACION  
MODIFY (DESCRIPCION_CRITERIO VARCHAR2(320 BYTE) );

-- Submenu CU-PR-20
INSERT INTO OPCION_MENU (ID_OPCION_MENU, NOMBRE, TIPO_ACCESO, ACCION, ID_OPCION_MENU2, ORDEN, TITULO)
VALUES (28, 'Gestionar Implementaciones', 'PRIVADO', 'BEAN:#{cuPr20GestionImplementacionesVigentesAsignadasFaces.initReturnCU}', 3, 6, 'Permite al usuario gestionar sus implementaciones de proyectos de investigación vigentes');

INSERT INTO ROL_OPCION_MENU (ID_ROL_OPCION_MENU, ID_OPCION_MENU, ID_ROL)
VALUES (41, 28, 26);



--CU-PR-08
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (235,'TIPO_TIPOS_DE_PROYECTO','Investigaciones institucionales','Investigaciones institucionales');
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (236,'TIPO_TIPOS_DE_PROYECTO','Trabajos de grado','Trabajos de grado');
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (237,'TIPO_TIPOS_DE_PROYECTO','Para financiacion','Para financiación');
		
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (238,'TIPO_ORIGENES_PROYECTO','Banco de necesidades','Banco de necesidades');
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (239,'TIPO_ORIGENES_PROYECTO','Propuesta de necesidades','Propuesta de necesidades');
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (240,'TIPO_ORIGENES_PROYECTO','Convocatoria','Convocatoria');
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (241,'TIPO_ORIGENES_PROYECTO','Trabajos de grado','Trabajos de grado');
		
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (242,'TIPO_FORMAS_DE_VER','Uno a uno','Uno a uno');
INSERT INTO CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) VALUES (243,'TIPO_FORMAS_DE_VER','Totales por unidad','Totales por unidad');

--Submenu CU-PR-12
INSERT INTO OPCION_MENU (ID_OPCION_MENU, NOMBRE, TIPO_ACCESO, ACCION, ID_OPCION_MENU2, ORDEN, TITULO)
VALUES (32, 'Registrar evaluación de Proyecto de investigación', 'PRIVADO', 'BEAN:#{cuPr12EvaluacionProyectosInvestigacionFaces.initReturnCU}', 3, 7, 'Permite al usuario registrar  la evaluación de un proyecto de investigación cuando estos han culminado.');

INSERT INTO ROL_OPCION_MENU (ID_ROL_OPCION_MENU, ID_OPCION_MENU, ID_ROL)
VALUES (49, 32, 13);

INSERT INTO USUARIO_ROL (ID_USUARIO_ROL, IDENTIFICADOR_USUARIO, ID_ROL)
VALUES (45, '1067843466', 13);


ALTER TABLE COMPROMISO_PROYECTO ADD (FECHA_NUEVO_COMPROMISO DATE);
ALTER TABLE COMPROMISO_PROYECTO ADD (ID_RESULTADO_RETROALIMENTA NUMBER);
ALTER TABLE COMPROMISO_PROYECTO ADD CONSTRAINT ID_RESULTAD_RETR_CONSTANTE_FK FOREIGN KEY (ID_RESULTADO_RETROALIMENTA) REFERENCES CONSTANTES (ID_CONSTANTES);

ALTER TABLE COMPROMISO_IMPLEMENTACION ADD (FECHA_NUEVO_COMPROMISO DATE);
ALTER TABLE COMPROMISO_IMPLEMENTACION ADD (ID_RESULTADO_RETROALIMENTA NUMBER);
ALTER TABLE COMPROMISO_IMPLEMENTACION ADD CONSTRAINT ID_RESU_RETR_IMPL_CONSTANTE_FK FOREIGN KEY (ID_RESULTADO_RETROALIMENTA) REFERENCES CONSTANTES (ID_CONSTANTES);

Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('250','TIPO_DE_PROYECTO','De convocatoria de financiación','De convocatoria de financiación');
Insert into CONSTANTES (ID_CONSTANTES,TIPO,CODIGO,VALOR) values ('251','TIPO_DE_PROYECTO','Proyectos institucionales','Proyectos institucionales');