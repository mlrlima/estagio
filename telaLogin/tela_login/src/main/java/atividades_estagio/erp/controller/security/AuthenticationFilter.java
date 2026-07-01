package atividades_estagio.erp.controller.security;
 
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
 
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
 
import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;

/**
 * Roda antes de qualquer endpoint anotado com @Secured.
 *
 * - Sem @Secured no método/classe          -> endpoint público, deixa passar
 * - @Secured() sem token válido             -> 401
 * - @Secured(Role.ADMIN) com token de USER  -> 403
 */

@Provider
@Priority(Priorities.AUTHENTICATION) //em caso de varios filtros
public class AuthenticationFilter implements ContainerRequestFilter{
	
	@Context //injeta metadata http
	private ResourceInfo resourceInfo; //qual método será chamado
	
	@Inject
	private TokenService tokenService;
	
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		Method metodo= resourceInfo.getResourceMethod();
		
		//checa se o metodo ou a classe tem @secured
		Secured secured=metodo.getAnnotation(Secured.class);
		if(secured==null) {
			secured=resourceInfo.getResourceClass().getAnnotation(Secured.class);
		}
		if(secured==null) return;
		//
		
		
		//checa se vem com o prefixo bearer
		// O padrão esperado é Authorization: Bearer <token>
		String authHeader=requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			abortar(requestContext, Response.Status.UNAUTHORIZED,
					"Token ausente. Envie o header 'Authorization: Bearer <token>'.");
			return;
		}
		//
		
		//valida token
		String token = authHeader.substring("Bearer ".length()).trim();
		Usuario usuario = tokenService.validar(token);
		if (usuario == null) {
			abortar(requestContext, Response.Status.UNAUTHORIZED, "Token inválido ou expirado. Faça login novamente.");
			return;
		}
		//
		
		//para o caso de Secured(Role.ADMIN)
		Role[] rolesPermitidas = secured.value();
		if (rolesPermitidas.length > 0) {
			boolean permitido = Arrays.stream(rolesPermitidas).anyMatch(r -> r == usuario.getRole());
			if (!permitido) {
				abortar(requestContext, Response.Status.FORBIDDEN,
						"Você não tem permissão (role) para acessar este recurso.");
				return;
			}
		}
		
		requestContext.setSecurityContext(new UsuarioSecurityContext(usuario));
		
	}
	
	
	//cancela requsicao
	private void abortar(ContainerRequestContext requestContext, Response.Status status, String mensagem) {
		String corpo = "{\"erro\":\"" + mensagem.replace("\"", "'") + "\"}";
		requestContext.abortWith(
				Response.status(status)
						.entity(corpo)
						.type("application/json")
						.build());
	}
	
}