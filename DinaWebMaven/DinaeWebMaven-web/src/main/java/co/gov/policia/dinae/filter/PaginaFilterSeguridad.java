package co.gov.policia.dinae.filter;

import co.gov.policia.dinae.cache.KeyPropertiesFactory;
import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafael.blanco@pointmind.com>
 */
@javax.servlet.annotation.WebFilter(description = "Filtro de seguridad DINAE-WEB", urlPatterns = {"/pages/secured/*", "/faces/pages/secured/*"})
public class PaginaFilterSeguridad implements Filter, Serializable {

  @javax.inject.Inject
  private LoginFaces loginBean;

  private final KeyPropertiesFactory keyPropertiesFactory = KeyPropertiesFactory.getInstance();

  /**
   * VERIFICA SI UN USUARIO ESTA LOGUEADO. Y DETERMINA SI ESTE DEBE SER REDIRECCIONADO A LA PAGINA login.xhtml.
   *
   * @param request
   * @param response
   * @param chain
   * @throws java.io.IOException
   * @throws javax.servlet.ServletException
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    String contextPath = ((HttpServletRequest) request).getContextPath();

    //PRIMERO VERIFICAMOS SI EL REQUEST ES UNA PAGINA QUE NECESITA LOGIN
    String url = ((HttpServletRequest) request).getRequestURI().replaceFirst("/faces", "").replaceFirst(contextPath, "");

    if (!keyPropertiesFactory.paginaNoRequiereLogin(url)) {
      //LA URL REQUIERE DE LOGIN, ES PRIVADA
      //OBTENEMOS EL OBJETO LOGIN DE SESSION
      if (loginBean == null || !loginBean.isLoggedIn() || loginBean.getPerfilUsuarioDTO() == null) {

        ((HttpServletRequest) request).getSession().invalidate();
        ((HttpServletResponse) response).sendRedirect(contextPath + "/pages/login/login.xhtml?faces-redirect=true");

      } else {

        //VERIFICAMOS QUE EL USUARIO LOGUEADO TENGA LOS PERMISO PARA INGRESAR AL REQUEST REQUERIDO
        Set<String> listaRolesUsuarioLogueado = loginBean.getPerfilUsuarioDTO().getListaPaginasAccesos();
        if (listaRolesUsuarioLogueado == null) {

          loginBean.logout();
          ((HttpServletResponse) response).sendRedirect(contextPath + "/pages/login/acceso_denegado.xhtml?faces-redirect=true");

        }

        boolean accesoPermitido = false;
        for (String unaUrlAcceso : listaRolesUsuarioLogueado) {

          if (unaUrlAcceso.contains(url)) {

            accesoPermitido = true;
            break;
          }

        }
        //VERIFICAMOS SI TIENE O NO PERMISO PARA ACCEDER AL RECURSO
        if (!accesoPermitido) {

          ((HttpServletResponse) response).sendRedirect(contextPath + "/pages/login/acceso_denegado.xhtml?faces-redirect=true");

        }
      }

    } else {
      //NO REQUIERE LOGIN
    }

    chain.doFilter(request, response);

  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    // no hace nada
  }

  @Override
  public void destroy() {
    // no hace nada
  }

}
