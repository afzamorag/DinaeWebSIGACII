package co.gov.policia.dinae.filter;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Pattern;
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
@javax.servlet.annotation.WebFilter(description = "Filtro de presentacion DINAE-WEB",
        urlPatterns = {"/pages/login/*", "/faces/pages/login/*"})
public class PaginaFilterPresentacion implements Filter, Serializable {

  @javax.inject.Inject
  private LoginFaces loginBean;

  private static Pattern excludeUrls = Pattern.compile("^.*/(css|js|images|jpg|png|gif)/.*$", Pattern.CASE_INSENSITIVE);

  private boolean excludeFromFilter(String path) {

    return excludeUrls.matcher(path).find();

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    String url = ((HttpServletRequest) request).getRequestURI();

    if (url.endsWith(".jpg") || url.endsWith(".js") || url.endsWith(".png") || url.endsWith(".gif") || url.endsWith(".css")) {
      chain.doFilter(request, response);
      return;
    }

    if (!loginBean.isLoggedIn()) {

      loginBean.setMostrarImagenMenuArriba(false);
      loginBean.setMostrarMenu(false);

    }

    if ("/faces".equals(url)) {

      String contextPath = ((HttpServletRequest) request).getContextPath();
      ((HttpServletResponse) response).sendRedirect(contextPath + "/pages/login/welcome.xhtml");
    } else if (url.contains("welcome.xhtml")) {

      //SOLO APLICA SI EL USUARIO NO SE HA LOGEADO
      if (!loginBean.isLoggedIn()) {
        loginBean.setMostrarImagenMenuArriba(true);
      }

    } else if (url.contains("inicio.xhtml")) {

      //SOLO APLICA SI EL USUARIO NO SE HA LOGEADO
      if (!loginBean.isLoggedIn()) {
        loginBean.setMostrarImagenMenuArriba(true);
        loginBean.setMostrarMenu(true);
      }

    } else if (url.contains("login.xhtml")) {

      //SOLO APLICA SI EL USUARIO NO SE HA LOGEADO
      if (!loginBean.isLoggedIn()) {
        loginBean.setMostrarImagenMenuArriba(true);
        loginBean.setMostrarMenu(true);
      }

    }

    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // no hace nada
  }

  @Override
  public void destroy() {
    // no hace nada
  }
}
