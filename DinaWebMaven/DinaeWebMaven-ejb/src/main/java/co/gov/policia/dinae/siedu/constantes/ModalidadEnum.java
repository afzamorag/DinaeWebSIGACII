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
public enum ModalidadEnum {
    PRESENCIAL(8L, "presencial"),
    SEMIPRESENCIAL(9L, "semipresencial"),
    VIRTUAL(10L, "virtual");

    private final Long id;
    private final String descripcion;

    private ModalidadEnum(Long id, String nombre) {
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
    public static ModalidadEnum findById(Integer id) {
        for (ModalidadEnum c : ModalidadEnum.values()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
