package atividades_estagio.erp.controller;
 
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import atividades_estagio.erp.controller.dto.LoginRequest;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;

/**
 * POST /api/login
 * body: { "email": "...", "senha": "..." }
 *
 * Sem autenticação por token por enquanto
 * apenas valida e devolve o usuário (sem a senha).
 */

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource{
	@Inject
	private Usuarios usuarios;
	
	@POST
	public Response login(LoginRequest request) {
		if(request==null || request.getEmail()==null || request.getSenha()==null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"erro\":\"email e senha são obrigatórios\"}")
					.build();
		}
		
		Usuario usuario=usuarios.porEmailESenha(request.getEmail(), request.getSenha());
		
		if(usuario==null) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("{\"erro\":\"email ou senha inválidos\"}")
					.build();
		}
 
		return Response.ok(usuario).build();
	}
}