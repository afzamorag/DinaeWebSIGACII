CREATE OR REPLACE PROCEDURE SIEDU_INSERTAR_META_PAE_ESC ( p_pae 	in 	number, 
														  p_usuario	in	siedu_pae_capacitacion.capa_usu_crea%type,
														  p_fecha   in	siedu_pae_capacitacion.capa_fecha_crea%type,
														  p_maquina in	siedu_pae_capacitacion.capa_maquina_crea%type,
														  p_ip		in	siedu_pae_capacitacion.capa_ip_crea%type,  
														  p_msg 	out varchar2
  ) IS
	--
	cursor c_pae_escuela is
	select t.*
	  from siedu_pae_capacitacion t
	 where t.capa_pae = p_pae
	   and t.capa_total_eventos <> 0;
	--
	v_id_evento_escuela         siedu_evento_escuela.evee_evee%type;
	v_capa_id                   siedu_pae_capacitacion.capa_capa%type;
	v_pae_nro_eventos           siedu_pae_capacitacion.capa_total_eventos%type;
	v_trimestre                 siedu_pae_capacitacion.capa_even_t1%type;
	v_trim                      number;
	v_contar                    number;
	v_numero_evento             number;
	--
    Begin
		for c_pae_esc in c_pae_escuela loop
		begin
		    dbms_output.put_line('procesando capa:'||c_pae_esc.capa_capa);
            v_pae_nro_eventos              := c_pae_esc.capa_total_eventos;
            v_capa_id                      := c_pae_esc.capa_capa;
            v_trim                         := 1;
            v_contar                       := 1;
            v_numero_evento                := 1;
            begin
			  dbms_output.put_line('procesando capa:'||c_pae_esc.capa_capa||' Eventos:'||v_pae_nro_eventos);
              while v_numero_evento <= v_pae_nro_eventos loop
			  begin
                case
                  when v_trim = 1 then v_trimestre := c_pae_esc.capa_even_t1;
                  when v_trim = 2 then v_trimestre := c_pae_esc.capa_even_t2;
                  when v_trim = 3 then v_trimestre := c_pae_esc.capa_even_t3;
                  when v_trim = 4 then v_trimestre := c_pae_esc.capa_even_t4;
                end case;
				dbms_output.put_line('Trim:'||v_trim||' Eventos:'||v_trimestre);
				--
                select siedu_evento_escuela_seq.nextval into v_id_evento_escuela from dual;
				--
				if v_contar > v_trimestre then
					v_trim := v_trim + 1;
					v_contar := 1;
				else
					insert into siedu_evento_escuela 
					( evee_evee, evee_capa, evee_nro_evento, evee_cerrado, evee_trimes,
					  evee_usu_crea, evee_fecha_crea, evee_maquina_crea, evee_ip_crea )
					values
					( v_id_evento_escuela, v_capa_id, v_numero_evento, 'NO', v_trim,
                      p_usuario, p_fecha, p_maquina, p_ip );
					--
					v_numero_evento := v_numero_evento + 1;
					v_contar := v_contar + 1;
					--
				end if;
			  end;
              end loop;
             end;
	    end;
        end loop;
        commit;
		--
End SIEDU_INSERTAR_META_PAE_ESC;
/