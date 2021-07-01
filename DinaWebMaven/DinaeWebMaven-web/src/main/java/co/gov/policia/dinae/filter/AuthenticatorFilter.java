package co.gov.policia.dinae.filter;

import co.gov.policia.dinae.manager.managedBean.LoginFaces;
import co.gov.policia.dinae.manager.managedBean.MenuFaces;
import co.gov.policia.dinae.util.LookUpWeblogic;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rafael Guillermo Blanco Banquez <rafaelg.blancob@gmail.com>
 */
public class AuthenticatorFilter extends HttpServlet implements Serializable {

  private static final long serialVersionUID = 1L;

  @javax.inject.Inject
  private LoginFaces loginFaces;

  @javax.inject.Inject
  private MenuFaces menuFaces;

  public AuthenticatorFilter() {
    super();
  }

  @Override
  public void service(HttpServletRequest request,
          HttpServletResponse response) throws ServletException,
          IOException {

    if (((HttpServletRequest) request).getUserPrincipal() != null) {

      Principal principal = ((HttpServletRequest) request).getUserPrincipal();

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "INICIA LOGIN - PRINCIPAL NAME: ".concat(principal.getName()));

      //POR MEDIO DEL USUARIO, SOLICITAMOS AL LDAP QUE NOS RETORNE EL NUMERO DE IDENTIFICACION DEL USUARIO
      String nombreUsuario = principal.getName();
      //String numeroDocumento = principal.getName();

      String numeroDocumento = (String) LookUpWeblogic.getInstance().getInformacionUsuarioWebLogic(nombreUsuario);

      if (numeroDocumento == null) {
        //NO ES ENCONTRADO
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "LOOKUP LDAP : NO ENCONTRADO");
        response.setHeader("Osso-Return-Url", "/faces/pages/login/login.xhtml");
        response.sendRedirect(request.getContextPath() + "/faces/pages/login/login.xhtml?Error=true");

        return;
      }

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "LOOKUP LDAP : ".concat(numeroDocumento));

      try {

        if (loginFaces.cargarRoles(numeroDocumento, nombreUsuario)) {

          menuFaces.inicializaMenu(loginFaces.getPerfilUsuarioDTO().getListaIdRolUsuario(), true);

          request.getSession().setMaxInactiveInterval(600);//10 MINUTOS

          response.setHeader("Osso-Return-Url", "/faces/pages/login/inicio.xhtml");
          response.sendRedirect(request.getContextPath() + "/faces/pages/login/inicio.xhtml");

        } else {

          response.setHeader("Osso-Return-Url", "/faces/pages/login/login.xhtml");
          response.sendRedirect(request.getContextPath() + "/faces/pages/login/login.xhtml?Error=true");

        }

      } catch (RuntimeException e) {
        response.setHeader("Osso-Return-Url", "/faces/pages/login/login.xhtml");
        response.sendRedirect(request.getContextPath() + "/faces/pages/login/login.xhtml?Invalido=1");
      }
    } else {

      String parametro = ((HttpServletRequest) request).getParameter("Retry");
      if ("TrueLogin".equals(parametro)) {

        response.setHeader("Osso-Return-Url", "/faces/pages/login/login.xhtml");
        response.sendRedirect(request.getContextPath() + "/faces/pages/login/login.xhtml?faces-redirec=true&Error=true");

      } else {

        response.setHeader("Osso-Return-Url", "/faces/pages/login/welcome.xhtml");
        response.sendRedirect(request.getContextPath() + "/faces/pages/login/welcome.xhtml?faces-redirec=true");

      }

    }
  }
}
