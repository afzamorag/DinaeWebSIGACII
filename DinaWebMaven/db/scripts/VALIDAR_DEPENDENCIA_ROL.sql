CREATE OR REPLACE FUNCTION VALIDAR_DEPENDENCIA_ROL(p_identificacion in number)
  RETURN siedu_usuario_rol IS
  v_consecutivo_laborando usr_rehu.empleados.unde_consecutivo_laborando%type;
  v_rol                   siedu_usuario_rol;
  CURSOR c_unidades_connect IS
    SELECT usr_rehu.unidades_dependencia.consecutivo unidad_labora,
           usr_rehu.unidades_dependencia.tiun_codigo tiun
      FROM usr_rehu.unidades_dependencia
     START WITH usr_rehu.unidades_dependencia.consecutivo =
                v_consecutivo_laborando
    CONNECT BY PRIOR usr_rehu.unidades_dependencia.unde_consecutivo =
                usr_rehu.unidades_dependencia.consecutivo;
BEGIN
  SELECT usr_rehu.empleados.unde_consecutivo_laborando
    INTO v_consecutivo_laborando
    FROM usr_rehu.empleados
   WHERE usr_rehu.empleados.identificacion = p_identificacion;
  FOR c_unid_conn IN c_unidades_connect LOOP
    dbms_output.put_line('Loop:' || c_unid_conn.unidad_labora);
    CASE
      WHEN c_unid_conn.unidad_labora = 34211 THEN
        v_rol := siedu_usuario_rol('VIECO', c_unid_conn.unidad_labora);
        EXIT;
      WHEN c_unid_conn.unidad_labora = 25550 THEN
        v_rol := siedu_usuario_rol('VICIN', c_unid_conn.unidad_labora);
        EXIT;
      WHEN c_unid_conn.unidad_labora = 25532 THEN
        v_rol := siedu_usuario_rol('PLANE', c_unid_conn.unidad_labora);
        EXIT;
      WHEN c_unid_conn.unidad_labora = 25533 THEN
        v_rol := siedu_usuario_rol('TELEM', c_unid_conn.unidad_labora);
        EXIT;
      WHEN c_unid_conn.unidad_labora = 25541 THEN
        v_rol := siedu_usuario_rol('VIACA', c_unid_conn.unidad_labora);
        EXIT;
      ELSE
        NULL;
    END CASE;
    dbms_output.put_line('Pasa Case v_rol es:' || v_rol.rol);
  END LOOP;
  IF v_rol IS NULL THEN
    FOR c_unid_conn IN c_unidades_connect LOOP
      CASE
        WHEN c_unid_conn.tiun = 15 THEN
          v_rol := siedu_usuario_rol('CIINS', c_unid_conn.unidad_labora);
          EXIT;
        WHEN c_unid_conn.tiun = 10 THEN
          v_rol := siedu_usuario_rol('ESCUE', c_unid_conn.unidad_labora);
          EXIT;
        ELSE
          NULL;
      END CASE;
      dbms_output.put_line('Pasa Case v_rol es:' || v_rol.rol);
    END LOOP;
  END IF;
  IF v_rol IS NULL THEN
    v_rol := siedu_usuario_rol('CONSU', v_consecutivo_laborando);
  END IF;
  dbms_output.put_line('Final v_rol es:' || v_rol.rol);
  RETURN v_rol;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    dbms_output.put_line(' ERROR por NDF');
    RETURN NULL;
  WHEN OTHERS THEN
    dbms_output.put_line(' ERROR por OTHERS ' || sqlerrm);
    RETURN NULL;
END VALIDAR_DEPENDENCIA_ROL;