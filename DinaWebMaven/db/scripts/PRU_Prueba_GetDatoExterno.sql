set serveroutput on size 1000000

declare
v_s varchar2(4000);
begin
siedu_qnecesidad.pr_obtNecesidadE ( 	
								1,					-- p_pae 		in	siedu_pae.pae_pae%type, 
							--	'DITAH',			-- p_origen 	in	siedu_necesidad.nece_origen%type,
								'BD',				-- p_usuario	in	siedu_necesidad.nece_usu_crea%type,
								sysdate,			-- p_fecha     in	siedu_necesidad.nece_fecha_crea%type,
								'SOFTSTBD01',		-- p_maquina   in	siedu_necesidad.nece_maquina_crea%type,
								'192.168.0.100',	-- p_ip		in	siedu_necesidad.nece_ip_crea%type,
								v_s				    -- p_msg       out varchar2
							);
dbms_output.put_line('Salida:'||v_s);							
end;
/		

update siedu_necesidad
set nece_dom_proce = 11,
nece_dom_estra = 14;

insert into siedu_cobertura (
COBE_COBE,
COBE_PAE,
COBE_UDE_FUERZA_ESCU,
COBE_UDE_ESCU,
COBE_DOM_ESTRA,
COBE_UDE_FUERZA_UFISI,
COBE_UDE_UFISI,
COBE_USU_CREA,
COBE_FECHA_CREA,
COBE_MAQUINA_CREA,	
cobe_ip_crea )  values 	(
siedu_cobertura_seq.nextval,
1,
6,	
47213,
14, 
6,								-- ,ENEC_UDE_FUERZA_UFISI
17406,							-- ,ENEC_UDE_UFISI
'BD',			
sysdate,		
'SOFTSTBD01',	
'192.168.0.100'
);

insert into siedu_cobertura (
COBE_COBE,
COBE_PAE,
COBE_UDE_FUERZA_ESCU,
COBE_UDE_ESCU,
COBE_DOM_ESTRA,
COBE_UDE_FUERZA_UFISI,
COBE_UDE_UFISI,
COBE_USU_CREA,
COBE_FECHA_CREA,
COBE_MAQUINA_CREA,	
cobe_ip_crea )  values 	(
siedu_cobertura_seq.nextval,
1,
6,	
47213,
14, 
6,								-- ,ENEC_UDE_FUERZA_UFISI
17407,							-- ,ENEC_UDE_UFISI
'BD',			
sysdate,		
'SOFTSTBD01',	
'192.168.0.100'
);

commit;				