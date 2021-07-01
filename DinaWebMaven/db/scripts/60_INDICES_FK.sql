CREATE INDEX ISIEDU_CIERRE_CAPACITA_FK 			ON SIEDU_CIERRE_PAE (RPAE_CAPA);
CREATE INDEX ISIEDU_CIERRE_FORMACION_FK 		ON SIEDU_CIERRE_PAE (RPAE_FORM);
CREATE INDEX ISIEDU_CIERRE_NOVEDAD_PAE_FK 		ON SIEDU_CIERRE_PAE (RPAE_NOVE);
CREATE INDEX ISIEDU_COMPE_EVEN_COMPETE_FK 		ON SIEDU_COMPETENCIA_EVENTO (COMPE_COMP);
CREATE INDEX ISIEDU_CONSOLIDA_CAPACITA_FK 		ON SIEDU_CONSOLIDA_PAE (CPAE_CAPA);
CREATE INDEX ISIEDU_CONSOLIDA_FORMACION_FK 		ON SIEDU_CONSOLIDA_PAE (CPAE_FORM);
CREATE INDEX ISIEDU_CONSOLIDA_NECESIDAD_FK 		ON SIEDU_CONSOLIDA_PAE (CPAE_NECE);
CREATE INDEX ICONVENIO_ENTI_FK 					ON SIEDU_CONVENIO (CONVE_ENTI);
CREATE INDEX ISIEDU_DOCENTE_EVEN_PERS_FK 		ON SIEDU_DOCENTE_EVENTO (DOCE_PERS);
CREATE INDEX ISIEDU_DOCENTE_EVEN_SUBTEM_FK 		ON SIEDU_DOCENTE_EVENTO (DOCE_TEMA);
CREATE INDEX ISIEDU_EVALUACION_EVENTO_FK 		ON SIEDU_EVALUACION (EVAL_EVEN);
CREATE INDEX ISIEDU_EVALUACION_PAE_FK 			ON SIEDU_EVALUACION (EVAL_PAE);
CREATE INDEX ISIEDU_EVAL_PREGUNTA_COMPE_FK 		ON SIEDU_EVAL_PREGUNTA (EVPRE_COMP);
CREATE INDEX ISIEDU_EVAL_PREG_PEVAL_ASP_FK 		ON SIEDU_EVAL_PREGUNTA (EVPRE_PEVAL_ASPEC);
CREATE INDEX ISIEDU_EVAL_PREG_PEVAL_PRE_FK 		ON SIEDU_EVAL_PREGUNTA (EVPRE_PEVAL_PREG);
CREATE INDEX ISIEDU_PREG_PAR_EVAL_FACTO_FK 		ON SIEDU_EVAL_PREGUNTA (EVPRE_PEVAL_FACTOR);
CREATE INDEX ISIEDU_CAPA_RPOGR_DOM_MODA_FK 		ON SIEDU_EVENTO (EVEN_DOM_MODAL);
CREATE INDEX ISIEDU_CAPA_RPOGR_DOM_PROS_FK		ON SIEDU_EVENTO (EVEN_DOM_PROCE);
CREATE INDEX ISIEDU_EVENTO_ESCUELA_CAPA_FK 		ON SIEDU_EVENTO_ESCUELA (EVEE_CAPA);
CREATE INDEX ISIEDU_EVENTO_ESCUELA_ENTI_FK 		ON SIEDU_EVENTO_ESCUELA (EVEE_ENTI);
CREATE INDEX ISIEDU_EVENTO_ESCUELA_PPTO_FK 		ON SIEDU_EVENTO_ESCUELA (EVEE_PPTO);
CREATE INDEX ISIEDU_EVENTO_ESCU_CONVEN_FK 		ON SIEDU_EVENTO_ESCUELA (EVEE_CONV);
CREATE INDEX ISIEDU_EVENTO_ESCU_EVENTO_FK 		ON SIEDU_EVENTO_ESCUELA (EVEE_EVEN);
CREATE INDEX ISIEDU_NECESIDAD_NOVE_PAE_FK 		ON SIEDU_NECESIDAD (NECE_NOVE);
CREATE INDEX ISIEDU_NOVEDAD_ARCHIVO_FK 			ON SIEDU_NOVEDAD_PAE (NOVE_ANEXO_PDF);
CREATE INDEX ISIEDU_NOVEDAD_PAE_FK 				ON SIEDU_NOVEDAD_PAE (NOVE_PAE);
CREATE INDEX ISIEDU_PARTICIPA_EVEN_PERS_FK 		ON SIEDU_PARTICIPANTE_EVENTO (PARE_PERS);
CREATE INDEX ISIEDU_PERSONA_EMPLE_EXTER_FK		ON SIEDU_PERSONA (PERS_EMPE);
CREATE INDEX ISIEDU_TEMA_TEMA_FK 			    ON SIEDU_TEMA (TEMA_TEMA_PADRE);
CREATE INDEX ISIEDU_EVENTO_ESCU_EVAL_FK 		ON SIEDU_EVENTO_ESCUELA (EVEE_EVAL);
-- TABLAS EXTERNAS
CREATE INDEX ISIEDU_CAPA_PROGR_CARRERAS_FK		ON SIEDU_EVENTO				 ( EVEN_FUERZA, EVEN_ID_CARRERA );
CREATE INDEX ISIEDU_PERSONA_EMPLEADOS_FK		ON SIEDU_PERSONA			 ( PERS_EMP_UNDE_FUERZA, PERS_EMP_UNDE_UDEPE, PERS_EMP_CONSECUTIVO );
CREATE INDEX ISIEDU_COBER_UDE_ESCU_FK			ON SIEDU_COBERTURA			 ( COBE_UDE_FUERZA_ESCU, COBE_UDE_ESCU );
CREATE INDEX ISIEDU_COBER_UDE_UFISI_FK			ON SIEDU_COBERTURA			 ( COBE_UDE_FUERZA_UFISI, COBE_UDE_UFISI );
CREATE INDEX ISIEDU_NECESIDAD_CARRERAS_FK		ON SIEDU_NECESIDAD			 ( NECE_FUERZA_CARRERA, NECE_ID_CARRERA );
CREATE INDEX ISIEDU_NECESIDAD_UDE_UDEPE_FK		ON SIEDU_NECESIDAD			 ( NECE_UDE_FUERZA_UDEPE, NECE_UDE_UDEPE );
CREATE INDEX ISIEDU_NECESIDAD_UDE_UFISI_FK		ON SIEDU_NECESIDAD			 ( NECE_UDE_FUERZA_UFISI, NECE_UDE_UFISI );
CREATE INDEX ISIEDU_NECESIDAD_UDE_REGION_FK		ON SIEDU_NECESIDAD			 ( NECE_UDE_FUERZA_REGION, NECE_UDE_REGION );
CREATE INDEX ISIEDU_EVAL_GRADO_GRADOS_FK		ON SIEDU_EVAL_GRADO			 ( EVGR_GRAD_FUERZA, EVGR_GRAD_ALFABETICO );
CREATE INDEX ISIEDU_NECESIDE_CARRERAS_FK		ON SIEDU_NECESIDAD_E		 ( ENEC_FUERZA_CARRERA, ENEC_ID_CARRERA );
CREATE INDEX ISIEDU_NECESIDE_UDE_UDEPE_FK		ON SIEDU_NECESIDAD_E		 ( ENEC_UDE_FUERZA_UDEPE, ENEC_UDE_UDEPE );
CREATE INDEX ISIEDU_NECESIDE_UDE_UFISI_FK		ON SIEDU_NECESIDAD_E		 ( ENEC_UDE_FUERZA_UFISI, ENEC_UDE_UFISI );
CREATE INDEX ISIEDU_NECESIDE_UDE_REGION_FK		ON SIEDU_NECESIDAD_E		 ( ENEC_UDE_FUERZA_REGION, ENEC_UDE_REGION );
CREATE INDEX ISIEDU_PAE_FORM_CARRERAS_FK		ON SIEDU_PAE_FORMACION		 ( FORM_FUERZA, FORM_ID_CARRERA );
CREATE INDEX ISIEDU_EVEN_ESCU_LUG_GEO_FK		ON SIEDU_EVENTO_ESCUELA		 ( EVEE_LUGGEO_CAPACITA );
CREATE INDEX ISIEDU_EVENTO_ESCUELA_UDEPE_FK		ON SIEDU_EVENTO_ESCUELA		 ( EVEE_UDE_FUERZA, EVEE_UDE_UDEPE ); 
CREATE INDEX ISIEDU_EMP_EXT_LGGEO_CIUNAC_FK		ON SIEDU_EMPLEADO_EXTERNO	 ( EMPE_LUGGEO_CIUNAC );
CREATE INDEX ISIEDU_EMP_EXT_LGGEO_CIURES_FK		ON SIEDU_EMPLEADO_EXTERNO	 ( EMPE_LUGGEO_CIURES );
CREATE INDEX ISIEDU_EMP_EXT_LGGEO_PAINAC_FK		ON SIEDU_EMPLEADO_EXTERNO	 ( EMPE_LUGGEO_PAINAC );
CREATE INDEX ISIEDU_EMP_EXT_LGGEO_PAIRES_FK		ON SIEDU_EMPLEADO_EXTERNO	 ( EMPE_LUGGEO_PAIRES );
CREATE INDEX ISIEDU_EVENTO_CATEG_CATEG_FK		ON SIEDU_EVENTO_CATEGORIA	 ( EVCA_FUERZA, EVCA_CATEGORIA_ID );
CREATE INDEX ISIEDU_CAPACITACION_ESCUELA_FK		ON SIEDU_PAE_CAPACITACION	 ( CAPA_UDE_FUERZA_ESCU, CAPA_UDE_ESCU );
CREATE INDEX ISIEDU_PAE_CAPACITA_CRAS_FK		ON SIEDU_PAE_CAPACITACION	 ( CAPA_FUERZA_CARRERA, CAPA_ID_CARRERA );
CREATE INDEX ISIEDU_FORM_ESCLA_UND_DEPEN_FK		ON SIEDU_PAE_FORMA_ESCUELA	 ( FRME_UDE_FUERZA, FRME_UDE_ESCU );
CREATE INDEX ISIEDU_CONVOC_EVENTO_UDEPE_FK		ON SIEDU_CONVOCATORIA_EVENTO ( CONE_UDE_FUERZA, CONE_UDE_UDEPE );
CREATE INDEX ISIEDU_PARTI_EVEN_UDE_FK			ON SIEDU_PARTICIPANTE_EVENTO ( PARE_UDE_FUERZA, PARE_UDE_UDEPE );
CREATE INDEX ISIEDU_PARTI_EVEN_CARGOS_FK		ON SIEDU_PARTICIPANTE_EVENTO ( PARE_CARGO_FUERZA, PARE_CARGO );
CREATE INDEX ISIEDU_PARTI_EVEN_GRADOS_FK		ON SIEDU_PARTICIPANTE_EVENTO ( PARE_GRADO_FUERZA, PARE_GRADO_ALFABETICO );
CREATE INDEX ISIEDU_PARTI_EVEN_CATEGOR_FK		ON SIEDU_PARTICIPANTE_EVENTO ( PARE_CATEGORIA_FUERZA, PARE_CATEGORIA_ID );
CREATE INDEX ISIEDU_PARTI_EVEN_UDE_UNID_FK		ON SIEDU_PARTICIPANTE_EVENTO ( PARE_UDE_UNIDAD_FUERZA, PARE_UDE_UDEPE_UNIDAD );