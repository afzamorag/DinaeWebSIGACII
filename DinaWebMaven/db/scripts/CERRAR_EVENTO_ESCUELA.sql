CREATE OR REPLACE PROCEDURE CERRAR_EVENTO_ESCUELA(p_evee_evee  in number,
                                                  p_usuario    in siedu_evento_escuela.evee_usu_mod%type,
                                                  p_maquina    in siedu_evento_escuela.evee_maquina_mod%type,
                                                  p_ip         in siedu_evento_escuela.evee_ip_mod%type,
                                                  p_evaluacion in siedu_evento_escuela.evee_eval%type,
                                                  p_msg        out varchar2) IS
  -- Se cierra el evento, se suman los convocados y se cuentan los participantes
BEGIN
  DECLARE
    v_convocados           siedu_evento_escuela.evee_nro_convocados%type;
    v_participantes        siedu_evento_escuela.evee_nro_participantes%type;
    v_participantes_unidad siedu_convocatoria_evento.cone_nro_presentados%type;
    --Se crea cursor con los datos de la convocatoria del evento
    CURSOR unidades_convocadas IS
      SELECT T.CONE_CONE, T.CONE_EVEE, T.CONE_UDE_FUERZA, T.CONE_UDE_UDEPE
        FROM SIEDU_CONVOCATORIA_EVENTO T
       WHERE T.CONE_EVEE = p_evee_evee;
    --Se suman los convocados
  BEGIN
    SELECT SUM(siedu_convocatoria_evento.cone_nro_convocados)
      INTO v_convocados
      FROM siedu_convocatoria_evento
     WHERE siedu_convocatoria_evento.cone_evee = p_evee_evee;
    --Se cuentan los participante
    SELECT COUNT(siedu_participante_evento.pare_pers)
      INTO v_participantes
      FROM siedu_participante_evento
     WHERE siedu_participante_evento.pare_evee = p_evee_evee;
    --Se actualiza el estado del evento, los participantes y convocados
    UPDATE siedu_evento_escuela
       SET siedu_evento_escuela.evee_cerrado           = 'SI',
           siedu_evento_escuela.evee_usu_mod           = p_usuario,
           siedu_evento_escuela.evee_fecha_mod         = sysdate,
           siedu_evento_escuela.evee_maquina_mod       = p_maquina,
           siedu_evento_escuela.evee_ip_mod            = p_ip,
           siedu_evento_escuela.evee_nro_participantes = v_participantes,
           siedu_evento_escuela.evee_nro_convocados    = v_convocados,
           siedu_evento_escuela.evee_eval              = p_evaluacion
     WHERE siedu_evento_escuela.evee_evee = p_evee_evee;
    --Se actualizan los presentados por Unidad de acuerdo a la Unidad del Participante
    FOR c_unid_convo IN unidades_convocadas LOOP
      BEGIN
        SELECT COUNT(T.PARE_PERS)
          INTO v_participantes_unidad
          FROM SIEDU_PARTICIPANTE_EVENTO T
         WHERE T.PARE_EVEE = p_evee_evee
           AND T.PARE_UDE_UDEPE_UNIDAD = c_unid_convo.CONE_UDE_UDEPE;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
          v_participantes_unidad := 0;
          BEGIN
            UPDATE siedu_convocatoria_evento
               SET siedu_convocatoria_evento.cone_nro_presentados = v_participantes_unidad
             WHERE siedu_convocatoria_evento.cone_ude_udepe =
                   c_unid_convo.CONE_UDE_UDEPE
               AND siedu_convocatoria_evento.cone_evee = p_evee_evee;
          END;
      END;
    END LOOP;
  END;
  DECLARE
    v_porcentaje_inasistencia number;
    v_tiempo_inasiste         number;
    v_tiempo_evento           number;
    v_estado_participante     varchar2(100);
    -- Se crea cursor con los participantes del evento
    CURSOR c_participantes_evento IS
      SELECT siedu_participante_evento.pare_pare
        FROM siedu_participante_evento
       WHERE siedu_participante_evento.pare_evee = p_evee_evee;
  BEGIN
    -- Se obtiene el tiempo de duraci??n del evento
    SELECT (siedu_evento.even_inten_horas * 60)
      INTO v_tiempo_evento
      FROM siedu_evento, siedu_evento_escuela
     WHERE siedu_evento.even_even = siedu_evento_escuela.evee_even
       AND siedu_evento_escuela.evee_evee = p_evee_evee
       AND siedu_evento.even_estado = 'VIGENTE';
    -- Se actualiza el estado del participante APROBADO, NO APROBADO, DESERTADO
    BEGIN
      FOR c_parti_even IN c_participantes_evento LOOP
        BEGIN
          SELECT SUM(siedu_inasiste_evento.inae_tiempo)
            INTO v_tiempo_inasiste
            FROM siedu_inasiste_evento
           WHERE siedu_inasiste_evento.inae_pare = c_parti_even.pare_pare
           GROUP BY siedu_inasiste_evento.inae_pare;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            v_tiempo_inasiste         := 0;
            v_porcentaje_inasistencia := ((v_tiempo_inasiste * 100) /
                                         v_tiempo_evento);
            BEGIN
              CASE
                WHEN v_porcentaje_inasistencia >= 0 AND
                     v_porcentaje_inasistencia < 25 THEN
                  v_estado_participante := 'APROBADO';
                WHEN v_porcentaje_inasistencia > 25 AND
                     v_porcentaje_inasistencia < 50 THEN
                  v_estado_participante := 'NO APROBADO';
                WHEN v_porcentaje_inasistencia >= 50 THEN
                  v_estado_participante := 'DESERTADO';
              END CASE;
              UPDATE siedu_participante_evento
                 SET siedu_participante_evento.pare_estado      = v_estado_participante,
                     siedu_participante_evento.pare_usu_mod     = p_usuario,
                     siedu_participante_evento.pare_fecha_mod   = sysdate,
                     siedu_participante_evento.pare_maquina_mod = p_maquina,
                     siedu_participante_evento.pare_ip_mod      = p_ip
               WHERE siedu_participante_evento.pare_pare =
                     c_parti_even.pare_pare;
            END;
        END;
      END LOOP;
    END;
  END;
  --Se actualiza los campos aprobados, no aprobados y desertados de la tabla siedu_evento_escuela
  BEGIN
    DECLARE
      v_estado_participantes       varchar2(100);
      v_contador_estado_participa  number;
      v_column_update              varchar2(100);
      v_total_participantes_estado number;
    BEGIN
      v_contador_estado_participa := 1;
      WHILE (v_contador_estado_participa < 4) LOOP
        CASE
          WHEN v_contador_estado_participa = 1 THEN
            v_estado_participantes := 'APROBADO';
            v_column_update        := 'siedu_evento_escuela.eve_nro_aprobados';
          WHEN v_contador_estado_participa = 2 THEN
            v_estado_participantes := 'NO APROBADO';
            v_column_update        := 'siedu_evento_escuela.eve_nro_noaprobados';
          WHEN v_contador_estado_participa = 3 THEN
            v_estado_participantes := 'DESERTADO';
            v_column_update        := 'siedu_evento_escuela.evee_nro_desertados';
        END CASE;
        BEGIN
          SELECT COUNT(siedu_participante_evento.pare_pers)
            INTO v_total_participantes_estado
            FROM siedu_participante_evento
           WHERE siedu_participante_evento.pare_evee = p_evee_evee
             AND siedu_participante_evento.pare_estado =
                 v_estado_participantes;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            v_total_participantes_estado := 0;
        END; --Map
        IF v_estado_participantes = 'APROBADO' THEN
          UPDATE siedu_evento_escuela
             SET eve_nro_aprobados = v_total_participantes_estado
           WHERE siedu_evento_escuela.evee_evee = p_evee_evee;
        ELSIF v_estado_participantes = 'NO APROBADO' THEN
          UPDATE siedu_evento_escuela
             SET eve_nro_noaprobados = v_total_participantes_estado
           WHERE siedu_evento_escuela.evee_evee = p_evee_evee;
        ELSIF v_estado_participantes = 'DESERTADO' THEN
          UPDATE siedu_evento_escuela
             SET evee_nro_desertados = v_total_participantes_estado
           WHERE siedu_evento_escuela.evee_evee = p_evee_evee;
        END IF;
        v_contador_estado_participa := v_contador_estado_participa + 1;
        --Map END;
      END LOOP;
    END;
  END;
  --Se insertar los participantes aprobados para que desarrollen evaluación evento.
  BEGIN
    DECLARE
      v_partici_encues_exist NUMBER(6);
    BEGIN
      SELECT COUNT(siedu_eval_participante.evpa_pare)
        INTO v_partici_encues_exist
        FROM siedu_eval_participante,
             siedu_participante_evento,
             siedu_evento_escuela
       WHERE siedu_eval_participante.evpa_pare =
             siedu_participante_evento.pare_pare
         AND siedu_participante_evento.pare_evee =
             siedu_evento_escuela.evee_evee
         AND siedu_evento_escuela.evee_evee = p_evee_evee
         AND siedu_eval_participante.evpa_eval = p_evaluacion;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        v_partici_encues_exist := 0;
        --Se insertan los participantes aprobados cuando no se ha agregado ninguno para el evento
        IF v_partici_encues_exist = 0 THEN
          BEGIN
            DECLARE
              CURSOR c_participa_evalua_evento IS
                SELECT siedu_participante_evento.pare_pare participante_evento_eval,
                       siedu_evento_escuela.evee_eval      evaluacion_evento_participante
                  FROM siedu_participante_evento, siedu_evento_escuela
                 WHERE siedu_participante_evento.pare_evee =
                       siedu_evento_escuela.evee_evee
                   AND siedu_evento_escuela.evee_evee = p_evee_evee
                   AND siedu_participante_evento.pare_estado = 'APROBADO';
            BEGIN
              FOR c_part_eval_even IN c_participa_evalua_evento LOOP
                INSERT INTO siedu_eval_participante
                  (evpa_eval,
                   evpa_pare,
                   evpa_estado,
                   evpa_usu_crea,
                   evpa_fecha_crea,
                   evpa_maquina_crea,
                   evpa_ip_crea)
                VALUES
                  (c_part_eval_even.participante_evento_eval,
                   c_part_eval_even.evaluacion_evento_participante,
                   'A',
                   p_usuario,
                   sysdate,
                   p_maquina,
                   p_ip);
              END LOOP;
            END;
          END;
        ELSE
          --Se insertan los participantes aprobados cuando ya se han agregado para el evento
          BEGIN
            DECLARE
              CURSOR c_parti_eval_not_exists IS
                SELECT siedu_participante_evento.pare_pare participante_evento_eval,
                       siedu_evento_escuela.evee_eval      evaluacion_evento_participante
                  FROM siedu_participante_evento, siedu_evento_escuela
                 WHERE siedu_participante_evento.pare_evee =
                       siedu_evento_escuela.evee_evee
                   AND siedu_evento_escuela.evee_evee = p_evee_evee
                   AND siedu_participante_evento.pare_estado = 'APROBADO'
                   AND NOT EXISTS
                 (SELECT siedu_eval_participante.*
                          FROM siedu_eval_participante
                         WHERE siedu_eval_participante.evpa_pare =
                               siedu_participante_evento.pare_pare
                           AND siedu_eval_participante.evpa_eval =
                               siedu_evento_escuela.evee_eval);
            BEGIN
              FOR c_part_eval_eve_not_exits IN c_parti_eval_not_exists LOOP
                INSERT INTO siedu_eval_participante
                  (evpa_eval,
                   evpa_pare,
                   evpa_estado,
                   evpa_usu_crea,
                   evpa_fecha_crea,
                   evpa_maquina_crea,
                   evpa_ip_crea)
                VALUES
                  (c_part_eval_eve_not_exits.participante_evento_eval,
                   c_part_eval_eve_not_exits.evaluacion_evento_participante,
                   'A',
                   p_usuario,
                   sysdate,
                   p_maquina,
                   p_ip);
              END LOOP;
            END;
          END;
        END IF;
    END;
  END;
  /*Si hubo actualización de los participantes se vuelve a calcular el estado y se actualiza, se eliminan los que esten
  en certificación y ahora tiene estado NO APROBADO O DESERTADO*/
  BEGIN
    BEGIN
      DECLARE
        CURSOR c_part_elimi_certif_nvo_estado IS
          SELECT siedu_participante_evento.pare_pare pare_eliminar
            FROM siedu_participante_evento, siedu_certificado_evento
           WHERE siedu_participante_evento.pare_pare =
                 siedu_certificado_evento.cert_pare
             AND siedu_participante_evento.pare_evee = p_evee_evee
             AND siedu_participante_evento.pare_estado <> 'APROBADO';
      BEGIN
        FOR c_par_eli_cer_nvo_est IN c_part_elimi_certif_nvo_estado LOOP
          IF c_part_elimi_certif_nvo_estado%NOTFOUND THEN
            EXIT;
          END IF;
          DELETE FROM siedu_certificado_evento WHERE siedu_certificado_evento.cert_pare = c_par_eli_cer_nvo_est.pare_eliminar;
        END LOOP;
      END;
    END;
  END;
END CERRAR_EVENTO_ESCUELA;
/