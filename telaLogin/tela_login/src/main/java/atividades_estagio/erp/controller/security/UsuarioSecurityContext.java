package atividades_estagio.erp.controller.security;

import java.security.Principal;
 
import javax.ws.rs.core.SecurityContext;
 
import atividades_estagio.erp.model.Usuario;

/**
 * SecurityContext customizado. Depois que o AuthenticationFilter valida
 * o token, ele guarda o Usuario logado aqui, e os métodos do resource
 * podem pegar de volta assim:
 *
 *   @Context SecurityContext securityContext
 *   Usuario logado = ((UsuarioSecurityContext) securityContext).getUsuario();
 */

public class UsuarioSecurityContext implements SecurityContext{
	private final Usuario usuario;
	
	public UsuarioSecurityContext(Usuario usuario) {
		this.usuario=usuario;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	
	@Override
	public Principal getUserPrincipal() {
		return() -> String.valueOf(usuario.getId());
	}
	
	@Override
	public boolean isUserInRole(String role) {
		if(usuario.getRole()==null &&
				!usuario.getRole().name().equalsIgnoreCase(role)) return false;
		
		return true;
	}
	
	@Override
	public boolean isSecure() {
		return false;
	}
 
	@Override
	public String getAuthenticationScheme() {
		return "Bearer";
	}
}