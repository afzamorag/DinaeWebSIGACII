set serveroutput on size 1000000

declare
v_s varchar2(4000);
begin
siedu_insertar_meta_pae_esc ( 	
								1,					-- p_pae 		in	siedu_pae.pae_pae%type, 
								'BD',				-- p_usuario	in	siedu_necesidad.nece_usu_crea%type,
								sysdate,			-- p_fecha     in	siedu_necesidad.nece_fecha_crea%type,
								'SOFTSTBD01',		-- p_maquina   in	siedu_necesidad.nece_maquina_crea%type,
								'192.168.0.100',	-- p_ip		in	siedu_necesidad.nece_ip_crea%type,
								v_s				    -- p_msg       out varchar2
							);
dbms_output.put_line('Salida:'||v_s);							
end;
/	
