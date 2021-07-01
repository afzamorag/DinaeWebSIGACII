/*
 * SoftStudio
 * Copyrigth .2016.
 */
package co.gov.policia.dinae.siedu.constantes;

/**
 * description
 *
 * @author: Diego Poveda <diego.poveda@softstudio.co>
 * @version: 1.0
 * @since: 1.0
 */
public enum SPEnum {

  SP_OBTENER_NECESIDADES_DITHA("SIEDU_QNECESIDAD.pr_obtNecesidadE"),
  SP_CONSOLIDAR_NECESIDADES_CAPACITACION("SIEDU_QNECESIDAD.pr_consolida_necesidad_Capa"),
  SP_CONSOLIDAR_NECESIDADES_FORMACION("SIEDU_QNECESIDAD.pr_consolida_necesidad_Form"),
  SP_INSERTAR_METAS_PAE("SIEDU_INSERTAR_META_PAE_ESC"),
  SP_CERRAR_EVENTO_ESCUELA("CERRAR_EVENTO_ESCUELA"),
  SP_INSERTAR_PARTICIPANTE_EVENTO("INSERTAR_PARTICIPANTE_EVENTO");

  private final String nombreSP;

  SPEnum(String name) {
    this.nombreSP = name;
  }

  public String getNombreSP() {
    return nombreSP;
  }

}
