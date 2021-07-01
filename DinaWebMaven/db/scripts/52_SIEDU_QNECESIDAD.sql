CREATE OR REPLACE PACKAGE SIEDU_QNECESIDAD IS
--
-------------------------------------------------------------------------------
--
  Procedure pr_obtNecesidadE ( 	p_pae 		in	siedu_pae.pae_pae%type, 
--								p_origen 	in	siedu_necesidad.nece_origen%type,
								p_usuario	in	siedu_necesidad.nece_usu_crea%type,
								p_fecha     in	siedu_necesidad.nece_fecha_crea%type,
								p_maquina   in	siedu_necesidad.nece_maquina_crea%type,
								p_ip		in	siedu_necesidad.nece_ip_crea%type,
								p_msg       out varchar2
							);
--
-------------------------------------------------------------------------------
--
  Procedure pr_consolida_necesidad_Capa ( p_pae 		in 	number, 
									 p_usuario	in	siedu_pae_capacitacion.capa_usu_crea%type,
									 p_fecha    in	siedu_pae_capacitacion.capa_fecha_crea%type,
									 p_maquina  in	siedu_pae_capacitacion.capa_maquina_crea%type,
									 p_ip		in	siedu_pae_capacitacion.capa_ip_crea%type,  
									 p_msg 		out varchar2
  );
--
-------------------------------------------------------------------------------
--
  Procedure pr_consolida_necesidad_Form ( p_pae 		in 	number, 
									 p_usuario	in	siedu_pae_capacitacion.capa_usu_crea%type,
									 p_fecha    in	siedu_pae_capacitacion.capa_fecha_crea%type,
									 p_maquina  in	siedu_pae_capacitacion.capa_maquina_crea%type,
									 p_ip		in	siedu_pae_capacitacion.capa_ip_crea%type,  
									 p_msg 		out varchar2
  );
--
-------------------------------------------------------------------------------
--
END SIEDU_QNECESIDAD;
/
show errors

CREATE OR REPLACE PACKAGE BODY SIEDU_QNECESIDAD IS
--
  Procedure pr_obtNecesidadE ( 	p_pae 		in	siedu_pae.pae_pae%type, 
--								p_origen 	in	siedu_necesidad.nece_origen%type,
								p_usuario	in	siedu_necesidad.nece_usu_crea%type,
								p_fecha     in	siedu_necesidad.nece_fecha_crea%type,
								p_maquina   in	siedu_necesidad.nece_maquina_crea%type,
								p_ip		in	siedu_necesidad.nece_ip_crea%type,
								p_msg       out varchar2
							) is
    --
	v_nove siedu_novedad_pae.nove_nove%type;
	--
	cursor c_nove is
	select max(nove_nove)
	from siedu_novedad_pae
	where nove_pae = p_pae;
	--
	cursor c_nece_e is
	select 
	    enec_enec, 
		enec_ude_fuerza_region,  		-- ,nece_ude_fuerza_region
		enec_ude_region,       			-- ,nece_ude_region       			
		enec_ude_fuerza_ufisi, 			-- ,nece_ude_fuerza_ufisi 			
		enec_ude_ufisi,        			-- ,nece_ude_ufisi        			
		enec_ude_fuerza_udepe, 			-- ,nece_ude_fuerza_udepe 			
		enec_ude_udepe,        			-- ,nece_ude_udepe        			
		enec_fuerza_carrera,   			-- ,nece_fuerza_carrera   			
		enec_id_carrera,   				-- ,nece_id_carrera 
		enec_origen,
		enec_nro 						-- ,nece_nro	
	from siedu_necesidad_e, siedu_pae
	where enec_vigencia = pae_vigencia
	  and pae_pae = p_pae
	  and enec_enviado = 'N';			-- No enviados
	--
  Begin
    --
	pr_log ('SIEDU_QNECESIDAD.pr_obtNecesidadE '
	||' - '||p_pae
	||' - '||p_usuario	
	||' - '||p_fecha     
	||' - '||p_maquina   
	||' - '||p_ip		
	);
	-- Obtener la novedad vigente para el PAE
	open c_nove;
	fetch c_nove into v_nove;
	close c_nove;
	--
	pr_log ('SIEDU_QNECESIDAD.pr_obtNecesidadE Nove:'||v_nove);
	--
	if v_nove is not null then
	begin
	   for r in c_nece_e loop
	   begin
		-- Inserción del registro de la Necesidad
		 insert into siedu_necesidad (
			nece_nece
			,nece_pae
			,nece_ude_fuerza_region 
			,nece_ude_region        
			,nece_ude_fuerza_ufisi  
			,nece_ude_ufisi         
			,nece_ude_fuerza_udepe  
			,nece_ude_udepe         
			,nece_fuerza_carrera    
			,nece_id_carrera   
			,nece_nro
			,nece_estado
			,nece_dom_proce
			,nece_dom_estra
			,nece_origen
			,nece_nove
			,nece_usu_crea
			,nece_fecha_crea
			,nece_maquina_crea
			,nece_ip_crea
			,nece_usu_mod
			,nece_fecha_mod
			,nece_maquina_mod
			,nece_ip_mod ) values (
				siedu_necesidad_seq.nextval,	-- nece_nece
				p_pae,							-- ,nece_pae
				r.enec_ude_fuerza_region,  		-- ,nece_ude_fuerza_region
				r.enec_ude_region,       			-- ,nece_ude_region       			
				r.enec_ude_fuerza_ufisi, 			-- ,nece_ude_fuerza_ufisi 			
				r.enec_ude_ufisi,        			-- ,nece_ude_ufisi        			
				r.enec_ude_fuerza_udepe, 			-- ,nece_ude_fuerza_udepe 			
				r.enec_ude_udepe,        			-- ,nece_ude_udepe        			
				r.enec_fuerza_carrera,   			-- ,nece_fuerza_carrera   			
				r.enec_id_carrera,   				-- ,nece_id_carrera 
				r.enec_nro,						-- ,nece_nro
				'PENDIENTE',					-- ,nece_estado
				null,							-- ,nece_dom_proce
				null,							-- ,nece_dom_estra
				r.enec_origen,					-- ,nece_origen
				v_nove,							-- ,nece_nove
				p_usuario,						-- ,nece_usu_crea
				p_fecha,     					-- ,nece_fecha_crea
				p_maquina,  					-- ,nece_maquina_crea
				p_ip,							-- ,nece_ip_crea
				null,							-- ,nece_usu_mod
				null,							-- ,nece_fecha_mod
				null,							-- ,nece_maquina_mod
				null 							-- ,nece_ip_mod 
			);
		--
			-- Actualizo el registro orígen como transmitido
			update siedu_necesidad_e
			set enec_enviado = 'S'
			where enec_enec = r.enec_enec;
		--
			-- Si todo está correcto, se marca el campo pae_nece_ya_importada
            update siedu_pae
            set pae_nece_ya_importada = 'SI',
				pae_usu_mod           = p_usuario,
				pae_fecha_mod         = sysdate,
				pae_maquina_mod       = p_maquina,
				pae_ip_mod            = p_ip
            where pae_pae = p_pae;
		--
			COMMIT;
		--	
	   exception
	      when dup_val_on_index then  -- Viola llave única, sumo las necesidades
		  begin
			update siedu_necesidad
			set nece_nro = nece_nro + r.enec_nro,
			    nece_estado = 'PENDIENTE',
				nece_usu_mod     = p_usuario,	
				nece_fecha_mod   = p_fecha,    
				nece_maquina_mod = p_maquina,  
				nece_ip_mod      = p_ip		
			where nece_pae                = p_pae						
			  and nece_ude_fuerza_region  = r.enec_ude_fuerza_region  	
			  and nece_ude_region         = r.enec_ude_region       	
			  and nece_ude_fuerza_ufisi   = r.enec_ude_fuerza_ufisi 	
			  and nece_ude_ufisi          = r.enec_ude_ufisi        	
			  and nece_ude_fuerza_udepe   = r.enec_ude_fuerza_udepe 	
			  and nece_ude_udepe          = r.enec_ude_udepe        	
			  and nece_fuerza_carrera     = r.enec_fuerza_carrera   	
			  and nece_id_carrera         = r.enec_id_carrera;  
			--
			-- Actualizo el registro orígen como transmitido
			--
			update siedu_necesidad_e
			set enec_enviado = 'S'
			where enec_enec = r.enec_enec;
			--
			-- Si todo está correcto, se marca el campo pae_nece_ya_importada
            update siedu_pae
            set pae_nece_ya_importada = 'SI',
				pae_usu_mod           = p_usuario,
				pae_fecha_mod         = sysdate,
				pae_maquina_mod       = p_maquina,
				pae_ip_mod            = p_ip
            where pae_pae = p_pae; 	
			--
			COMMIT;
			--				  
		  end;           					
	      when others then 
			ROLLBACK;
			p_msg := 'Error obteniendo necesidad externa. '||sqlerrm;
			--raise_application_error (-20100, p_msg);
			exit;
	   end;
	   end loop;
	end;
	else
	   p_msg := 'No se encontró Novedad activa para el PAE '||p_pae;
	end if;
  End pr_obtNecesidadE;
--
-------------------------------------------------------------------------------
--
  Procedure pr_validar_upd_capacitacion ( p_capa number, p_msg out varchar2 ) is
  Begin
     dbms_output.put_line('Validación por Ajuste en el registro de capacitación '||p_capa);
     null;
  End pr_validar_upd_capacitacion;  
--
-------------------------------------------------------------------------------
--
  Procedure pr_consolida_necesidad_Capa ( p_pae 		in 	number, 
									 p_usuario	in	siedu_pae_capacitacion.capa_usu_crea%type,
									 p_fecha    in	siedu_pae_capacitacion.capa_fecha_crea%type,
									 p_maquina  in	siedu_pae_capacitacion.capa_maquina_crea%type,
									 p_ip		in	siedu_pae_capacitacion.capa_ip_crea%type,  
									 p_msg 		out varchar2
  ) is
	--
	cursor c_consolida_necesidad ( p_pae number ) is 
	select
	nece_pae, 
	cobe_ude_fuerza_escu, cobe_ude_escu, 	
	nece_dom_proce,
	nece_dom_estra,	
	nece_fuerza_carrera, nece_id_carrera,	
	sum( nece_nro) total_nro
	from siedu_necesidad, siedu_cobertura 
	where nece_pae 				= p_pae
	  and nece_dom_proce 		is not null
	  and nece_dom_estra 		is not null
	  and nece_pae 				= cobe_pae
	  and nece_ude_fuerza_ufisi = cobe_ude_fuerza_ufisi
	  and nece_ude_ufisi	 	= cobe_ude_ufisi 
	  and nece_dom_estra		= cobe_dom_estra
	  and nece_dom_proce in ( select d.id_dominio from siedu_dominio d, siedu_tipo_dominio t
								where d.id_tipo_dominio = t.id_tipo_dominio
								and t.nombre='PROCESO'
								and d.nombre = 'CAPACITACION' )
	group by 	nece_pae, 
	cobe_ude_fuerza_escu, cobe_ude_escu, 	
	nece_dom_proce,
	nece_dom_estra,	
	nece_fuerza_carrera, nece_id_carrera;	
	--
	--
	v_seq siedu_pae_capacitacion.capa_capa%type;
	v_msg varchar2(4000);
	--
  Begin
	--
	for r in c_consolida_necesidad ( p_pae ) loop
	begin
	    --
		v_seq := siedu_pae_capacitacion_seq.nextval;
		--	
		insert into siedu_pae_capacitacion (
			capa_capa           
			,capa_pae            
			,capa_ude_fuerza_escu
			,capa_ude_escu       
			,capa_dom_proce      
			,capa_dom_estra      
			,capa_fuerza_carrera 
			,capa_id_carrera     
			,capa_nro_nece       
			,capa_estado         
			--,capa_total_personas 
			--,capa_total_eventos  
			,capa_dom_modal      
			,capa_ppto       
			--capa_even_t1        
			--capa_pers_t1        
			--capa_even_t2        
			--capa_pers_t2        
			--capa_even_t3        
			--capa_pers_t3        
			--capa_even_t4        
			--capa_pers_t4        
			,capa_usu_crea    	
			,capa_fecha_crea     
			,capa_maquina_crea   
			,capa_ip_crea    	
			,capa_usu_mod        
			,capa_fecha_mod      
			,capa_maquina_mod    
			,capa_ip_mod 
			--,capa_externo			
		) values (
			v_seq,    						   -- capa_capa           
            p_pae,                             -- capa_pae            
			r.cobe_ude_fuerza_escu,            -- capa_ude_fuerza_escu
			r.cobe_ude_escu,                   -- capa_ude_escu       
			r.nece_dom_proce,                  -- capa_dom_proce      
			r.nece_dom_estra,                  -- capa_dom_estra      
			r.nece_fuerza_carrera,             -- capa_fuerza_carrera 
			r.nece_id_carrera,                 -- capa_id_carrera     
			r.total_nro,                       -- capa_nro_nece       
			'PENDIENTE',                       -- capa_estado   
											   -- capa_total_personas 
											   -- capa_total_eventos  			
			NULL,                              -- capa_dom_modal      
			NULL,                              -- capa_ppto       
			                                   -- capa_even_t1        
			                                   -- capa_pers_t1        
			                                   -- capa_even_t2        
			                                   -- capa_pers_t2        
			                                   -- capa_even_t3        
			                                   -- capa_pers_t3        
			                                   -- capa_even_t4        
			                                   -- capa_pers_t4        
			 p_usuario,	                       -- capa_usu_crea    	
			 p_fecha,                          -- capa_fecha_crea     
			 p_maquina,                        -- capa_maquina_crea   
			 p_ip,		                       -- capa_ip_crea    	
			 NULL,                             -- capa_usu_mod        
			 NULL,                             -- capa_fecha_mod      
			 NULL,                             -- capa_maquina_mod    
			 NULL                              -- capa_ip_mod    		
			                                   -- capa_externo 
		);
		--
		dbms_output.put_line( 'pr_consolida_necesidad_Capa previo a ins consolida_pae '||to_char(sysdate,'hh24:mi:ss'));
		-- Inserción en Consolida_PAE
		insert into siedu_consolida_pae (
		cpae_cpae, 			cpae_nece,			 cpae_capa, 		  cpae_form, 		
		cpae_usu_crea,    	cpae_fecha_crea,     cpae_maquina_crea,   cpae_ip_crea,  		
		cpae_usu_mod,       cpae_fecha_mod,      cpae_maquina_mod,    cpae_ip_mod    	
		)	
		select	-- Obtengo las necesidades que aportaron en la consolidación.  Este query es igual al del cursor c_consolida_necesidad
		      siedu_consolida_pae_seq.nextval, 
		      nece_nece, 
		      v_seq,  -- Secuencial del registro de capacitación
		      null,   -- Secuencial del registro de formación.  NULL para este caso.
		      p_usuario,							
		      p_fecha,   	
		      p_maquina, 	
		      p_ip,
		      null, 								
		      null, 		
		      null, 		
		      null
		 from siedu_necesidad, siedu_cobertura 
		where nece_pae 				= p_pae
		  and nece_dom_proce 		is not null
		  and nece_dom_estra 		is not null
		  and nece_pae				= cobe_pae 
		  and nece_ude_fuerza_ufisi = cobe_ude_fuerza_ufisi
		  and nece_ude_ufisi	 	= cobe_ude_ufisi 
		  and nece_dom_estra		= cobe_dom_estra
		  and cobe_ude_fuerza_escu  = r.cobe_ude_fuerza_escu 
		  and cobe_ude_escu 	    = r.cobe_ude_escu      
		  and nece_dom_proce        = r.nece_dom_proce      
		  and nece_dom_estra	    = r.nece_dom_estra      
		  and nece_fuerza_carrera   = r.nece_fuerza_carrera 
		  and nece_id_carrera       = r.nece_id_carrera     
		  and nece_dom_proce in ( select d.id_dominio from siedu_dominio d, siedu_tipo_dominio t
		 							 where d.id_tipo_dominio = t.id_tipo_dominio
		 							 and t.nombre='PROCESO'
		 							 and d.nombre = 'CAPACITACION' );	
	    --
		  dbms_output.put_line( 'pr_consolida_necesidad_Capa Inserción con Consolida_PAE '||sql%rowcount||' registros');			
        --
		dbms_output.put_line( 'pr_consolida_necesidad_Capa posterior a ins consolida_pae '||to_char(sysdate,'hh24:mi:ss'));
		--
		COMMIT;
		--
    exception
	   when dup_val_on_index then
	   begin
	      --
		  dbms_output.put_line( 'pr_consolida_necesidad_Capa Llave Duplicada '||sqlerrm);
		  v_seq := null;
		  --
	      update siedu_pae_capacitacion
		  set capa_nro_nece       = r.total_nro,
		      capa_estado         = 'PENDIENTE',
		      capa_total_personas = 0, 
			  capa_total_eventos  = 0,
			  capa_even_t1        = 0,
			  capa_even_t2        = 0,
			  capa_even_t3        = 0,
			  capa_even_t4        = 0,
			  capa_pers_t1        = 0,
			  capa_pers_t2        = 0,
			  capa_pers_t3        = 0,
			  capa_pers_t4        = 0,
			  capa_usu_mod     	  = p_usuario,	
			  capa_fecha_mod   	  = p_fecha,    
			  capa_maquina_mod 	  = p_maquina,  
			  capa_ip_mod      	  = p_ip		
		  where capa_pae 				= p_pae
		    and capa_ude_fuerza_escu    = r.cobe_ude_fuerza_escu
			and capa_ude_escu           = r.cobe_ude_escu       
			and capa_fuerza_carrera     = r.nece_fuerza_carrera
			and capa_id_carrera         = r.nece_id_carrera    
			and capa_dom_proce          = r.nece_dom_proce
			and capa_dom_estra          = r.nece_dom_estra
            and capa_nro_nece <> r.total_nro   						-- Incremento o disminución de la necesidad.
	        returning capa_capa into v_seq;
		  --
		  dbms_output.put_line( 'pr_consolida_necesidad_Capa Ajustada Necesidad '||sql%rowcount||' registros');		  
		  -- Si v_seq retorna no nulo,indica que sí hubo update de un registro.
		  if v_seq is not null then
		  begin
			dbms_output.put_line( 'pr_consolida_necesidad_Capa previo a ins consolida_pae '||to_char(sysdate,'hh24:mi:ss'));
			--
			-- Validar lo que ya se tiene para ese registro de Capacitación
			pr_validar_upd_capacitacion ( v_seq, -- p_capa number, 
			                              v_msg  -- p_msg out varchar2 
										 );
	        if v_msg is not null then
			   raise_application_error (-20101, v_msg);
			end if;
			--
			-- Inserción en Consolida_PAE
			insert into siedu_consolida_pae (
			cpae_cpae, 			cpae_nece,			 cpae_capa, 		  cpae_form, 		
			cpae_usu_crea,    	cpae_fecha_crea,     cpae_maquina_crea,   cpae_ip_crea,  		
			cpae_usu_mod,       cpae_fecha_mod,      cpae_maquina_mod,    cpae_ip_mod    	
			)	
			select	-- Obtengo las necesidades que aportaron en la consolidación.  Este query es igual al del cursor c_consolida_necesidad
				  siedu_consolida_pae_seq.nextval, 
				  nece_nece, 
				  v_seq,  -- Secuencial del registro de capacitación que fué incrementado
				  null,   -- Secuencial del registro de formación.  NULL para este caso.
				  p_usuario,							
				  p_fecha,   	
				  p_maquina, 	
				  p_ip,
				  null, 								
				  null, 		
				  null, 		
				  null
			 from siedu_necesidad, siedu_cobertura 
			where nece_pae 				= p_pae
			  and nece_dom_proce 		is not null
			  and nece_dom_estra 		is not null
			  and nece_pae 				= cobe_pae
			  and nece_ude_fuerza_ufisi = cobe_ude_fuerza_ufisi
			  and nece_ude_ufisi	 	= cobe_ude_ufisi 
			  and nece_dom_estra		= cobe_dom_estra
			  and cobe_ude_fuerza_escu  = r.cobe_ude_fuerza_escu 
			  and cobe_ude_escu 	    = r.cobe_ude_escu       
			  and nece_dom_proce        = r.nece_dom_proce      
			  and nece_dom_estra	    = r.nece_dom_estra      
			  and nece_fuerza_carrera   = r.nece_fuerza_carrera 
			  and nece_id_carrera       = r.nece_id_carrera     
			  and nece_dom_proce in ( select d.id_dominio from siedu_dominio d, siedu_tipo_dominio t
										 where d.id_tipo_dominio = t.id_tipo_dominio
										 and t.nombre='PROCESO'
										 and d.nombre = 'CAPACITACION' )
			  and not exists ( select 1 from siedu_consolida_pae where cpae_capa = v_seq and cpae_nece = nece_nece);	
			--
		    dbms_output.put_line( 'pr_consolida_necesidad_Capa Inserción con Consolida_PAE '||sql%rowcount||' registros');				
			dbms_output.put_line( 'pr_consolida_necesidad_Capa posterior a ins consolida_pae '||to_char(sysdate,'hh24:mi:ss'));		  
		  end;
		  end if;
		  --	
		  --
		  --
		  COMMIT;
		  --
        end;		  
	    when others then 
			ROLLBACK;
			p_msg := 'Error consolidando capacitación. '||sqlerrm;
			--raise_application_error (-20100, p_msg);
			exit;			
	end;
	end loop;
	--
	EXCEPTION
	when others then 
		ROLLBACK;
		p_msg := 'Error consolidando capacitación. '||sqlerrm;
		--raise_application_error (-20100, p_msg);
  End pr_consolida_necesidad_Capa;
--
-------------------------------------------------------------------------------
--
  Procedure pr_validar_upd_formacion ( p_form number, p_msg out varchar2 ) is
  Begin
     dbms_output.put_line('Validación por Ajuste en el registro de formación '||p_form);
     null;
  End pr_validar_upd_formacion;
--
-------------------------------------------------------------------------------
--
  Procedure pr_consolida_necesidad_Form ( p_pae 		in 	number, 
									 p_usuario	in	siedu_pae_capacitacion.capa_usu_crea%type,
									 p_fecha    in	siedu_pae_capacitacion.capa_fecha_crea%type,
									 p_maquina  in	siedu_pae_capacitacion.capa_maquina_crea%type,
									 p_ip		in	siedu_pae_capacitacion.capa_ip_crea%type,  
									 p_msg 		out varchar2
  ) is
	--
	cursor c_consolida_necesidad ( p_pae number ) is 
	select
	nece_pae, 
	nece_dom_proce,
--	nece_dom_estra,	
	nece_fuerza_carrera, nece_id_carrera,	
	sum( nece_nro) total_nro
	from siedu_necesidad
	where nece_pae 				= p_pae
	  and nece_dom_proce 		is not null
	  --and nece_dom_estra 		is not null
	  and nece_dom_proce in ( select d.id_dominio from siedu_dominio d, siedu_tipo_dominio t
								where d.id_tipo_dominio = t.id_tipo_dominio
								and t.nombre='PROCESO'
								and d.nombre = 'FORMACION' )
	group by 	nece_pae,
	nece_dom_proce,
	nece_dom_estra,	
	nece_fuerza_carrera, nece_id_carrera;
	--
	--
	v_seq siedu_pae_formacion.form_form%type;
	v_msg varchar2(4000);	
	--
  Begin
	--
	for r in c_consolida_necesidad ( p_pae ) loop
	begin
	    --
		v_seq := siedu_pae_formacion_seq.nextval;
		--		
		insert into siedu_pae_formacion (
			 form_form           
	        ,form_pae            
	        ,form_dom_proce      
	        ,form_dom_estra      
	        ,form_necesi         
	        ,form_estado         
	        ,form_fuerza         
	        ,form_id_carrera     
	        ,form_usu_crea    	
	        ,form_fecha_crea     
	        ,form_maquina_crea   
	        ,form_ip_crea    	
	        ,form_usu_mod        
	        ,form_fecha_mod      
	        ,form_maquina_mod    
	        ,form_ip_mod  
		) values (
			v_seq,   						   -- form_form           
            p_pae,                             -- form_pae   
			r.nece_dom_proce,				   -- form_dom_proce   
            null, -- r.nece_dom_estra,                  -- form_dom_estra   
            r.total_nro,                       -- form_necesi      
            'PENDIENTE',                       -- form_estado      
            r.nece_fuerza_carrera,             -- form_fuerza      
            r.nece_id_carrera,                 -- form_id_carrera  
            p_usuario,	                       -- form_usu_crea    
            p_fecha,                           -- form_fecha_crea  
            p_maquina,                         -- form_maquina_crea
            p_ip,		                       -- form_ip_crea    
            NULL,                              -- form_usu_mod     
            NULL,                              -- form_fecha_mod   
            NULL,                              -- form_maquina_mod 
            NULL                               -- form_ip_mod  
		);
		--
		dbms_output.put_line( 'pr_consolida_necesidad_Form previo a ins consolida_pae '||to_char(sysdate,'hh24:mi:ss'));
		-- Inserción en Consolida_PAE
		insert into siedu_consolida_pae (
		cpae_cpae, 			cpae_nece,			 cpae_capa, 		  cpae_form, 		
		cpae_usu_crea,    	cpae_fecha_crea,     cpae_maquina_crea,   cpae_ip_crea,  		
		cpae_usu_mod,       cpae_fecha_mod,      cpae_maquina_mod,    cpae_ip_mod    	
		)	
		select	-- Obtengo las necesidades que aportaron en la consolidación.  Este query es igual al del cursor c_consolida_necesidad
		      siedu_consolida_pae_seq.nextval, 
		      nece_nece, 
		      null,  	-- Secuencial del registro de capacitación. NULL para este caso.
		      v_seq,    -- Secuencial del registro de formación.  
		      p_usuario,							
		      p_fecha,   	
		      p_maquina, 	
		      p_ip,
		      null, 								
		      null, 		
		      null, 		
		      null
		from siedu_necesidad
		where nece_pae 				= p_pae
		  and nece_dom_proce 		is not null
		  --and nece_dom_estra 		is not null
	      and nece_dom_proce        = r.nece_dom_proce
--	      and nece_dom_estra	    = r.nece_dom_estra	
	      and nece_fuerza_carrera   = r.nece_fuerza_carrera 
		  and nece_id_carrera	    = r.nece_id_carrera		  
		  and nece_dom_proce in ( select d.id_dominio from siedu_dominio d, siedu_tipo_dominio t
									where d.id_tipo_dominio = t.id_tipo_dominio
									and t.nombre='PROCESO'
									and d.nombre = 'FORMACION' );	
	    --
		dbms_output.put_line( 'pr_consolida_necesidad_Form Inserción con Consolida_PAE '||sql%rowcount||' registros');			
        --
		dbms_output.put_line( 'pr_consolida_necesidad_Form posterior a ins consolida_pae '||to_char(sysdate,'hh24:mi:ss'));
		--		
		--
		COMMIT;
		--
    exception
	   when dup_val_on_index then
	   begin
	      --
		  dbms_output.put_line( 'pr_consolida_necesidad_Form Llave Duplicada');
		  v_seq := null;		  
		  --
	      update siedu_pae_formacion
		  set form_necesi 	   = r.total_nro,
			  form_estado      = 'PENDIENTE',
			  form_usu_mod     = p_usuario,	
			  form_fecha_mod   = p_fecha,    
			  form_maquina_mod = p_maquina,  
			  form_ip_mod      = p_ip		
		  where form_pae 				= p_pae
			and form_fuerza             = r.nece_fuerza_carrera
			and form_id_carrera         = r.nece_id_carrera    
			and form_dom_proce          = r.nece_dom_proce
--			and form_dom_estra          = r.nece_dom_estra
            and form_necesi <> r.total_nro	-- Incremento de la necesidad.	 
		    returning form_form into v_seq;
		  --	
		  dbms_output.put_line( 'pr_consolida_necesidad_Form Ajustada Necesidad '||sql%rowcount||' registros');
		  --
		  if v_seq is not null then
		  begin
		    -- El registro de formación fué alterado, debo inicializar lo que ya haya sido planeado en ese registro.
			update siedu_forma_escuela_cohorte 
			set freh_nro_estu = 0
			where freh_form = v_seq;
			--
			-- Validar lo que ya se tiene para ese registro de Formación
			pr_validar_upd_formacion (   v_seq, -- p_form number, 
			                              v_msg  -- p_msg out varchar2 
										 );
	        if v_msg is not null then
			   raise_application_error (-20101, v_msg);
			end if;
			--
			dbms_output.put_line( 'pr_consolida_necesidad_Form previo a ins consolida_pae '||to_char(sysdate,'hh24:mi:ss'));
			-- Inserción en Consolida_PAE
			insert into siedu_consolida_pae (
			cpae_cpae, 			cpae_nece,			 cpae_capa, 		  cpae_form, 		
			cpae_usu_crea,    	cpae_fecha_crea,     cpae_maquina_crea,   cpae_ip_crea,  		
			cpae_usu_mod,       cpae_fecha_mod,      cpae_maquina_mod,    cpae_ip_mod    	
			)	
			select	-- Obtengo las necesidades que aportaron en la consolidación.  Este query es igual al del cursor c_consolida_necesidad
				  siedu_consolida_pae_seq.nextval, 
				  nece_nece, 
				  null,  	-- Secuencial del registro de capacitación. NULL para este caso.
				  v_seq,    -- Secuencial del registro de formación.  
				  p_usuario,							
				  p_fecha,   	
				  p_maquina, 	
				  p_ip,
				  null, 								
				  null, 		
				  null, 		
				  null
			from siedu_necesidad
			where nece_pae 				= p_pae
			  and nece_dom_proce 		is not null
			  --and nece_dom_estra 		is not null
	          and nece_dom_proce        = r.nece_dom_proce
--	          and nece_dom_estra	    = r.nece_dom_estra	
	          and nece_fuerza_carrera   = r.nece_fuerza_carrera 
		      and nece_id_carrera	    = r.nece_id_carrera				  
			  and nece_dom_proce in ( select d.id_dominio from siedu_dominio d, siedu_tipo_dominio t
										where d.id_tipo_dominio = t.id_tipo_dominio
										and t.nombre='PROCESO'
										and d.nombre = 'FORMACION' )
			  and not exists ( select 1 from siedu_consolida_pae where cpae_form = v_seq and cpae_nece = nece_nece);	
			--
			dbms_output.put_line( 'pr_consolida_necesidad_Form Inserción con Consolida_PAE '||sql%rowcount||' registros');			
			--
			dbms_output.put_line( 'pr_consolida_necesidad_Form posterior a ins consolida_pae '||to_char(sysdate,'hh24:mi:ss'));
			--
          end;			
		  end if;
		   --		  
		  COMMIT;
		  --
        end;		  
	    when others then 
			ROLLBACK;
			p_msg := 'Error consolidando formación. '||sqlerrm;
			--raise_application_error (-20100, p_msg);
			exit;			
	end;
	end loop;
	--
  EXCEPTION	
	when others then 
		ROLLBACK;
		p_msg := 'Error consolidando formación. '||sqlerrm;
		--raise_application_error (-20100, p_msg);
  End pr_consolida_necesidad_Form;
--
-------------------------------------------------------------------------------
--
END SIEDU_QNECESIDAD;
/
show errors

