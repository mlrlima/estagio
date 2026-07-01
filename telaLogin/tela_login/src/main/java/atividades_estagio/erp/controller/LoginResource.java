package atividades_estagio.erp.controller;
 
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.controller.dto.LoginRequest;
import atividades_estagio.erp.controller.dto.LoginResponse;
import atividades_estagio.erp.controller.security.Secured;
import atividades_estagio.erp.controller.security.TokenService;

/**
 * POST /api/login
 * body: { "email": "...", "senha": "..." }
 *
* POST /api/login  -> público, devolve { usuario, token }
 * POST /api/logout -> exige login, invalida o token enviado
 */

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource{
	@Inject
	private Usuarios usuarios;
	
	@Inject
	private TokenService tokenService;
	
	@POST
	public Response login(LoginRequest request) {
		//verifica se os dados do input sao validos
		if(request==null || request.getEmail()==null || request.getSenha()==null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"erro\":\"email e senha são obrigatórios\"}")
					.build();
		}
		
		Usuario usuario=usuarios.porEmailESenha(request.getEmail(), request.getSenha());
		// verifica se o usuario existe
		if(usuario==null) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("{\"erro\":\"email ou senha inválidos\"}")
					.build();
		}
		
		String token=tokenService.gerarToken(usuario);
		
		return Response.ok(new LoginResponse(usuario,token)).build();
	}
	
	
	@POST
	@Path("/logout")
	@Secured()
	public Response logout(@Context HttpHeaders headers) {
		String authHeader=headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			tokenService.invalidar(authHeader.substring("Bearer ".length()).trim());
		}
		
		return Response.noContent().build();
	}
}