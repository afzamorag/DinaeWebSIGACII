create table siedu_log (
	log_log number(10) not null,
	log_text varchar2(4000));
	
create sequence siedu_log_seq nocache;	

create or replace procedure pr_log ( p_text varchar2) as
PRAGMA AUTONOMOUS_TRANSACTION;
begin
   insert into siedu_log (log_log, log_text ) values (
   siedu_log_seq.nextval, p_text
   );
   commit;
exception 
   when others then rollback;   
end pr_log;
/