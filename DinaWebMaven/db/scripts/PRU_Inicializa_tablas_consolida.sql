-- Inicializa tablas involucradas con consolidación 
delete from siedu_consolida_pae; --
delete from siedu_pae_formacion; -- where form_usu_crea in ( 'BD', 'd5a9p6s7');
delete from SIEDU_EVENTO_ESCUELA; --
delete from SIEDU_EVAL_GRADO; --
delete from siedu_pae_capacitacion; -- where capa_usu_crea in ( 'BD', 'd5a9p6s7');
delete from siedu_necesidad; -- where nece_usu_crea in ( 'BD', 'd5a9p6s7');
delete from SIEDU_COBERTURA; --
delete from SIEDU_NOVEDAD_PAE; --
delete from SIEDU_ARCHIVO; --
delete from SIEDU_EVAL_PREGUNTA; --
delete from SIEDU_EVALUACION; --
delete from SIEDU_PAE; --
update siedu_necesidad_e set enec_enviado = 'N'; --

commit;