create or replace 
PACKAGE SIEDU_QAUDIT IS
--
-- Manuel Pérez
-- Paquete con las operaciones relacionadas con la Auditoría de Tablas.
-- Versión 5-03/02/2014
   --
   PROCEDURE GENERA_TRIGGER (
      p_tabla       IN       VARCHAR2,   -- Nombre de la Tabla que se va a auditar
      p_operacion   IN       VARCHAR2,   -- Letras que Indican la(s) operaciones a auditar.  Ej IAB.
      p_msg         OUT      VARCHAR2    -- Mensaje de Error
   -- Procedimiento que realiza la creación de un trigger de auditoría sobre la tabla p_tabla, en el esquema actual. 
   );
   --
   PROCEDURE ELIMINA_TRIGGER (
      p_tabla       IN       VARCHAR2,   -- Nombre de la Tabla que se va a dejar de auditar 
      p_msg         OUT      VARCHAR2    -- Mensaje de Error
   -- Procedimiento que realiza la eliminación de un trigger de auditoría sobre la tabla p_tabla, en el esquema actual.      
   );      
   --
   PROCEDURE INS_AUD (
      p_usuario        IN VARCHAR2,
      p_tabla          IN VARCHAR2,
      p_accion         IN VARCHAR2,
      p_valor_actual   IN CLOB,
      p_valor_anterior IN CLOB,
      p_maquina        IN VARCHAR2,	  
      p_ip             IN VARCHAR2
	); 
	--
END SIEDU_QAUDIT;
/
show errors

create or replace 
PACKAGE BODY SIEDU_QAUDIT IS
--
   PROCEDURE INS_AUD (
      p_usuario        IN VARCHAR2,
      p_tabla          IN VARCHAR2,
      p_accion         IN VARCHAR2,
      p_valor_actual   IN CLOB,
      p_valor_anterior IN CLOB,
      p_maquina        IN VARCHAR2,	  
      p_ip             IN VARCHAR2
	) IS
    v_id NUMBER;
   BEGIN
     IF ( p_accion = 'I' OR p_accion = 'A' -- OR p_accion = 'B'
         ) THEN
     BEGIN     
        SELECT SIEDU_AUDITORIA_SEQ.NEXTVAL INTO v_id FROM DUAL;
        INSERT INTO SIEDU_AUDITORIA (
		AUDI_AUDI,	AUDI_FECHA,	AUDI_TABLA,	AUDI_OPER,	AUDI_REGANTES,		AUDI_REGDESPUES,	AUDI_USUARIO,	AUDI_MAQUINA,	AUDI_IP ) 
		VALUES (
		v_id, 		SYSDATE,	p_tabla,	p_accion,	p_valor_anterior,	p_valor_actual,		p_usuario,		p_maquina,		p_ip
		);
     EXCEPTION
       WHEN OTHERS THEN
          RAISE; --RAISE_APPLICATION_ERROR(-20001, 'Error Insertando AUDITORIA '||sqlerrm);
     END;
     END IF;
   END INS_AUD;
   --
   --   
   FUNCTION OBTENER_PREFIJO_COLUMNA
   (
     p_tabla     IN VARCHAR2
   )
   -- Permite obtener el prefijo de columnas de auditoría
   RETURN VARCHAR2 IS
   v_col VARCHAR2(100);
   BEGIN
     -- El valor del usuario para auditoria se toma del campo USUARIO
     begin
       select 
       substr(column_name, 1, instr(column_name, '_USU_CREA') ) into v_col
       from user_tab_cols c
       where c.table_name = p_tabla
       and ( c.column_name like '%\_USU\_CREA' ESCAPE '\');
       RETURN v_col;
     exception
        when others then
           raise_application_error( -20001,'La Insercion no es auditable en'
           ||' la tabla '||p_tabla||' porque esta no cuenta con las columnas'
           ||' correspondientes.');
     end;
   END OBTENER_PREFIJO_COLUMNA;
   --
   --
   PROCEDURE GENERA_TRIGGER (
      p_tabla       IN       VARCHAR2,
      p_operacion   IN       VARCHAR2,
      p_msg         OUT      VARCHAR2
   -- Procedimiento que realiza la creación de un trigger de auditoría sobre la tabla p_tabla, en el esquema actual. 
   ) IS
   --
    v_comando1      VARCHAR2(4000);
    v_comandoINS    VARCHAR2(4000);   
    v_comandoINST   VARCHAR2(4000); 
    v_comandoFin    VARCHAR2(200);          
    v_operacion1    VARCHAR2(4000);
    v_usuario       VARCHAR2(100);
    v_sesion        VARCHAR2(100);
    v_i             pls_integer;
    v_l             varchar2(1);
    --
   --
   cursor c_columnas ( p_pre varchar2 ) is
   select  LISTAGG(p_pre||c.column_name, '||v_car||')                 
       WITHIN GROUP (ORDER BY c.column_id) COLUMNAS 
	from user_tab_columns c
	where c.table_name = p_tabla
	and ( c.column_name not like '%USU_CREA' 
	 and c.column_name not like '%FECHA_CREA'
	 and c.column_name not like '%MAQUINA_CREA'
	 and c.column_name not like '%IP_CREA'
	 and c.column_name not like '%USU_MOD'
	 and c.column_name not like '%FECHA_MOD'
	 and c.column_name not like '%MAQUINA_MOD'
	 and c.column_name not like '%IP_MOD'
	)
	order by c.column_id;
   --
   v_columnas varchar2(4000);
   v_prefijo varchar2(6);
   --
   BEGIN
    --
    v_prefijo:= OBTENER_PREFIJO_COLUMNA(p_tabla);
    --
	
    v_operacion1 := 'BEFORE ';
    FOR i IN 1..LENGTH(p_operacion) LOOP
    BEGIN
       v_l := SUBSTR(p_operacion, i, 1);
       IF v_l = 'I' THEN v_operacion1 := v_operacion1 || 'INSERT OR '; END IF;
       IF v_l = 'A' THEN v_operacion1 := v_operacion1 || 'UPDATE OR '; END IF;
       IF v_l = 'B' THEN v_operacion1 := v_operacion1 || 'DELETE OR '; END IF;
    END;
    END LOOP;
    --
    v_operacion1 := SUBSTR(v_operacion1,1, LENGTH(v_operacion1)-3)||'ON '|| p_tabla || CHR (10);
    -- 
    --
    v_comando1 :=
         'CREATE OR REPLACE TRIGGER AU_G'
         || substr(p_tabla,1, 23)
         || CHR (10)
         || v_operacion1
         || ' FOR EACH ROW '
         || CHR (10)
          || ' DECLARE operacion VARCHAR2(1); '
         || CHR (10)
         || ' v_rengln   number; '
         || CHR (10)
         || ' v_usuario   varchar2(100); '
         || CHR (10)
         || ' v_maquina   varchar2(100); '
         || CHR (10)      
         || ' v_ip   varchar2(100); '
         || CHR (10)   		 
         || ' v_valor_actual   clob; '
         || CHR (10)   
         || ' v_valor_anterior  clob; '
         || CHR (10) 
         || ' v_car varchar2(1) := ''|''; ' 	
         || CHR (10) 	
         || ' v_tabla varchar2(30) := '''||p_tabla||'''; '
         || CHR (10)   		 		 
         || ' Begin '
         || CHR (10)
         || ' v_valor_anterior := NULL; '
         || CHR (10)		 
         || ' v_valor_actual := NULL; '		
         || CHR (10)		 
         || ' IF   INSERTING THEN '
         || CHR (10)          
         || '  operacion := '
         || CHR (39)
         || 'I'
         || CHR (39)
         || ';'
         || CHR (10)
         ||'  v_usuario := :NEW.'||v_prefijo||'USU_CREA'
         || ';'
         || CHR (10)
         ||'  v_maquina := :NEW.'||v_prefijo||'MAQUINA_CREA'
         || ';'
         || CHR (10)   
         ||'  v_ip := :NEW.'||v_prefijo||'IP_CREA'
         || ';'
         || CHR (10)  		 
         || ' ELSIF  DELETING THEN '
         || CHR (10)          
         || '  operacion := '
         || CHR (39)
         || 'B'
         || CHR (39)
         || ';'
         || CHR (10)
         ||'  v_usuario := :OLD.'||v_prefijo||'USU_CREA'
         || ';'
         || CHR (10)
         ||'  v_maquina := NULL '
         || ';'
         || CHR (10)   
         ||'  v_ip := NULL '
         || ';'
         || CHR (10) 		 
         || ' ELSE operacion := '
         || CHR (39)
         || 'A'
         || CHR (39)
         || ';'
         || CHR (10)
         ||'  v_usuario := :NEW.'||v_prefijo||'USU_MOD'
         || ';'
         || CHR (10)
         ||'  v_maquina := :NEW.'||v_prefijo||'MAQUINA_MOD'
         || ';'
         || CHR (10)   
         ||'  v_ip := :NEW.'||v_prefijo||'IP_MOD'
         || ';'
         || CHR (10) 
		 ||' v_valor_anterior := ';
		 
		 open c_columnas (':OLD.');
		 fetch c_columnas into v_columnas;
		 close c_columnas;
		 v_comando1 := v_comando1 ||v_columnas || ';'||CHR (10)|| ' END IF;'
		 || CHR (10) 
		 || ' v_valor_actual := ';
		 open c_columnas (':NEW.');
		 fetch c_columnas into v_columnas;
		 close c_columnas;
		 v_comando1 := v_comando1 ||v_columnas || ';'||CHR (10);
		 
		 v_comandoINST := 'SIEDU_QAUDIT.INS_AUD ( '
		||' v_usuario, '			
		||' v_tabla, '			
		||' operacion, '			
		||' v_valor_actual, '		
		||' v_valor_anterior, '	
	    ||' v_maquina,	'		
		||' v_ip '				
		||');';	  
      v_comandoFIN := 'END AU_G'|| substr(p_tabla,1, 23)||';';  
      EXECUTE IMMEDIATE v_comando1||' '||v_comandoINST||' '||v_comandoFIN;
   EXCEPTION
      WHEN OTHERS THEN
        -- p_msg := substr(sqlerrm, INSTR(sqlerrm,'-20001')+7, length(sqlerrm));
         p_msg := sqlerrm;
         RAISE;                   
   END GENERA_TRIGGER;
   --
   PROCEDURE ELIMINA_TRIGGER (
   p_tabla       IN       VARCHAR2,
   p_msg         OUT      VARCHAR2    -- Mensaje de Error
   -- Procedimiento que realiza la eliminación de un trigger de auditoría sobre la tabla p_tabla, en el esquema actual.    
   ) IS
   BEGIN
   EXECUTE IMMEDIATE 'DROP TRIGGER AU_G'|| substr(p_tabla,1, 23);
   EXCEPTION
      WHEN OTHERS THEN
        -- p_msg := substr(sqlerrm, INSTR(sqlerrm,'-20001')+7, length(sqlerrm));
         p_msg := sqlerrm;
         RAISE;     
   END ELIMINA_TRIGGER;
--                  
END SIEDU_QAUDIT;
/
show errors