package co.gov.policia.dinae.manager.managedBean;

import co.gov.policia.dinae.dto.OpcionMenuDTO;
import co.gov.policia.dinae.interfaces.IUsuarioLocal;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.inject.Named(value = "menuFaces")
@javax.enterprise.context.SessionScoped
public class MenuFaces implements Serializable {

    @EJB
    private IUsuarioLocal iUsuarioLocal;

    private MenuModel menuModel;

    /**
     *
     * @param listaIdRolUsuario
     * @param estaAutenticado
     */
    public void inicializaMenu(List<Long> listaIdRolUsuario, boolean estaAutenticado) {
        try {

            menuModel = new DefaultMenuModel();

            //SE CONSULTAN LOS MENUS QUE SON PRIVADOS Y PUBLICOS
            List<OpcionMenuDTO> listaMenuPublicoPrivadoOlogueado = iUsuarioLocal.getListaOpcionMenuUsuarioPrivado_Y_O_Publico(listaIdRolUsuario, estaAutenticado);
            Collections.sort(listaMenuPublicoPrivadoOlogueado);

            for (OpcionMenuDTO unaOpcionMenuRama1 : listaMenuPublicoPrivadoOlogueado) {

                DefaultSubMenu smenu = new DefaultSubMenu();
                smenu.setLabel(unaOpcionMenuRama1.getNombre());

                //CONSULTAMOS LOS HIJOS DE ESTE MENU
                List<OpcionMenuDTO> listaOpcionSubMenu = iUsuarioLocal.getListaOpcionSubMenuUsuarioPrivado_Y_O_Publico(listaIdRolUsuario, unaOpcionMenuRama1.getIdOpcionMenu(), estaAutenticado);
                Collections.sort(listaOpcionSubMenu);

                if (!listaOpcionSubMenu.isEmpty()) {
                    //SI TIENE HIJOS, LOS CARGAMOS
                    for (OpcionMenuDTO opcionMenuHijoRama2 : listaOpcionSubMenu) {

                        if (opcionMenuHijoRama2.getAccion() == null) {
                            DefaultSubMenu smenu2 = new DefaultSubMenu();
                            smenu2.setLabel(opcionMenuHijoRama2.getNombre());

                            List<OpcionMenuDTO> listaOpciones = iUsuarioLocal.getListaOpcionSubMenuUsuarioPrivado_Y_O_Publico(listaIdRolUsuario, opcionMenuHijoRama2.getIdOpcionMenu(), estaAutenticado);
                            Collections.sort(listaOpciones);

                            if (!listaOpciones.isEmpty()) {

                                for (OpcionMenuDTO opcionMenuHijoRama3 : listaOpciones) {
                                    DefaultMenuItem uIComponentMI = crearSubMenuItem(opcionMenuHijoRama3);
                                    smenu2.getElements().add(uIComponentMI);

                                }

                            }
                            smenu.getElements().add(smenu2);
                        } else {
                            DefaultMenuItem uIComponent = crearSubMenuItem(opcionMenuHijoRama2);
                            smenu.getElements().add(uIComponent);
                        }

                    }

                }

                menuModel.addElement(smenu);
            }

        } catch (Exception e) {

            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "LOGIN FACES", e);
            throw new RuntimeException("EL MENU NO PUSO SER CARGADO");
        }
    }

    /**
     *
     * @param opcionMenuHijo
     * @return
     */
    private DefaultMenuItem crearSubMenuItem(OpcionMenuDTO opcionMenuHijo) {

        DefaultMenuItem menuItem = new DefaultMenuItem();

        menuItem.setValue(opcionMenuHijo.getNombre());
        menuItem.setImmediate(true);
        menuItem.setAjax(false);

        if (opcionMenuHijo.getTitulo() != null) {
            menuItem.setTitle(opcionMenuHijo.getTitulo());
        }

        if (opcionMenuHijo.getAccion() != null && opcionMenuHijo.getAccion().startsWith("OUTCOME:")) {

            menuItem.setOutcome(opcionMenuHijo.getAccion().replaceFirst("OUTCOME:", ""));

        } else if (opcionMenuHijo.getAccion() != null && opcionMenuHijo.getAccion().startsWith("BEAN:")) {

            menuItem.setCommand(opcionMenuHijo.getAccion().replaceFirst("BEAN:", ""));

        }
        return menuItem;
    }

    /**
     *
     * @return
     */
    public MenuModel getMenuModel() {

        if (menuModel == null) {
            //AQUI INGRESA CUANDO EL BEAN SE CARGA POR PRIMERA VEZ
            inicializaMenu(null, false);

        }

        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

}
