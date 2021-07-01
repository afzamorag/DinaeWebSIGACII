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
public enum TipoDominioEnum {

  TIPO_DOCUMENTO(2L, "Tipos de documentos"),
  MODALIDAD(3L, "Modalidad"),
  SEXO(4L, "Sexos"),
  ESTRATEGIA(5L, "Estrategias"),
  PROCESO(6L, "Procesos"),
  BOOLEANO(7L, "Booleano"),
  TIPO_ENTIDAD(10L, "Tipos de entidades"),
  SECTOR_ENTIDAD(11L, "Corresponde a si las entidades o empresas son del sector publico, privado o mixto."),
  TIPO_FINANCIACION(12L, "Recursos de financiacion"),
  TIPO_CONVENIO(14L, "TIPOS DE CONVENIOS SUSCRITOS POR POLICIA CON OTRAS INSTITUCIONES"),
  TIPO_PARAMETRO_EVALUACION(15L, "Tipos de parámetros para evaluaciones"),
  TIPO_COMPETENCIA(16L, "Tipos de competencias"),
  TEMATICA(17L, "Tematicas"),
  SI_NO(18L, "Decisiones"),
  ESTADO_CIVIL(19L, "Estados civiles"),
  TIPO_IDENTIFICACION(20L, "Tipos de identificaciones"),
  MODALIDAD_PROGRAMACION(21L, "Modalidad programación"),
  TIPO_PRODUCCION_INTELECTUAL(22L, "Tipo de producción intelectual");

  private final Long id;
  private final String descripcion;

  private TipoDominioEnum(Long id, String nombre) {
    this.id = id;
    this.descripcion = nombre;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   *
   * @param id
   * @return
   */
  public static TipoDominioEnum findById(Integer id) {
    for (TipoDominioEnum c : TipoDominioEnum.values()) {
      if (c.getId().equals(id)) {
        return c;
      }
    }
    return null;
  }
}
