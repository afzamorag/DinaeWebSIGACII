DELETE FROM ACTIVIDADES_IMPLEMENTACION;
DELETE FROM ACTIVIDADES_PLAN_TRABAJO;
DELETE FROM ACTIVIDADES_REALIZADAS_PROY;
DELETE FROM ASESORIA_PROYECTO;
DELETE FROM COMENTARIO;
DELETE FROM COMENTARIO_PROYECTO;
DELETE FROM COMPROMISO_PERIODO;
DELETE FROM COMPROMISO_PROYECTO;
DELETE FROM CONCECUTI_LIBERA_CONVOCATORI;
DELETE FROM CORRECCIONES_COMPROMISO_PROY;
DELETE FROM CRITERIO_EVALUACION;
DELETE FROM DETALLE_EVALUACION;
DELETE FROM DOCUMENTACION_PROYECTO;
DELETE FROM EJECUCION_EQUPOS_PROYECTO;
DELETE FROM EJECUCION_EVENTOS_PROYECTO;
DELETE FROM EJECUCION_HORAS_PROYECTO;
DELETE FROM EJECUCION_OTROS_GASTOS_PROY;
DELETE FROM EJECUCION_VIAJES_PROYECTO;
DELETE FROM ENSAYO_CRITICO;
DELETE FROM EQUIPOS_INVESTIGACION;
DELETE FROM ESTADO_COMPROMISO_PROYECTO;
DELETE FROM EVALUACION_PROYECTO;
DELETE FROM EVALUACION_PROYECTO_GRADO;
DELETE FROM EVALUADORES_PROYECTO_GRADO;
DELETE FROM EVENTO_PROYECTO;
DELETE FROM EVIDENCIA_PROYECTO;
DELETE FROM EXCEPCIONES_COMPROMISO;
DELETE FROM FORMACION_INVESTIGADOR;
DELETE FROM FUENTE_PROYECTO;
DELETE FROM FUNCIONARIO_NECESIDAD;
DELETE FROM GASTO_EVENTO;
DELETE FROM GRADOS_VALORES;
DELETE FROM GRUPO_INVEST_PROYECTO;
DELETE FROM GRUPO_INVESTIGACION;
DELETE FROM HISTORIAL_COMPROMISO;
DELETE FROM HORAS_INVESTIGADOR;
DELETE FROM IMPLEMENTACIONES_PROYECTO;
DELETE FROM INDICADORES_INFORME_AVANCE_IMP;
DELETE FROM INDICADORES_PLAN_TRABAJO;
DELETE FROM INDICADORES_PROYECTO;
DELETE FROM INFORME_AVANCE;
DELETE FROM INFORME_AVANCE_IMPLEMENTACION;
DELETE FROM INSTITUCIONES_PROYECTO;
DELETE FROM INVESTI_DESARROL_INVESTTIGADOR;
DELETE FROM INVESTIGADOR;
DELETE FROM INVESTIGADORES_PROYECTO;
DELETE FROM MODALIDAD_ASESORIA_PROYECTO;
DELETE FROM OTROS_GASTOS_PROYECTO;
DELETE FROM PERIODO;
DELETE FROM PERSONAL_IMPLEMENTACION;
DELETE FROM PERSONAL_INF_AVANCE_IMPL;
DELETE FROM PLAN_TRABAJO_IMPLEMENTACION;
DELETE FROM PROGRAMAS;
DELETE FROM PROPUESTA_NECESIDAD;
DELETE FROM PROYECTO;
DELETE FROM RESENIA_INVESTIGACION;
DELETE FROM RESPONSABLE_ACTIVIDAD_IMPLEMEN;
DELETE FROM RESULTADOS_ALCANZADOS_PROY;
DELETE FROM RESULTADOS_INVESTIGACION;
DELETE FROM RESUMEN_PRESUPUESTO_IMPL;
DELETE FROM RESUMEN_PRESUPUESTO_PROYECTO;
DELETE FROM SEMILLERO_INVESTIGACION;
DELETE FROM SEMILLERO_PROYECTO;
DELETE FROM SEMILLEROS_IMPLEMENTACION;
DELETE FROM SUGERENCIAS_PROY;
DELETE FROM TEMA_PROYECTO;
DELETE FROM TIPO_GASTO_EVENTO;
DELETE FROM TIPO_UNIDAD_PERIODO;
DELETE FROM UNIDAD_EJECUTORA;
DELETE FROM UNIDAD_POLICIAL_PERIODO;
DELETE FROM VERSIONES;
DELETE FROM VIAJES_PROYECTO;

COMMIT;

UPDATE UNIDAD_POLICIAL SET ACTIVO = 'N';

UPDATE UNIDAD_POLICIAL SET ACTIVO = 'Y' WHERE nombre = 'ESCUELA DE CADETES DE POLICIA GENERAL FRANCISCO DE PAULA SAN' or nombre = 'ESCUELA DE CARABINEROS PROVINCIA DE VELEZ' or nombre = 'ESCUELA DE INVESTIGACION CRIMINAL' or nombre = 'ESCUELA DE POLICIA DE ANTISECUESTRO Y ANTIEXTORSION' or nombre = 'ESCUELA DE POLICIA METROPOLITANA DE BOGOTA TC JULIAN GUEVARA';

COMMIT;



select object_name, s.sid, s.serial#, p.spid, l.os_user_name
  from v$locked_object l, dba_objects o, v$session s, v$process p
  where l.object_id = o.object_id and l.session_id = s.sid and s.paddr = p.addr;