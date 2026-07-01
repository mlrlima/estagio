package atividades_estagio.erp.controller;
 
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import atividades_estagio.erp.model.Pet;
import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Pets;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.controller.security.Secured;
import atividades_estagio.erp.controller.security.UsuarioSecurityContext;
import atividades_estagio.erp.service.CadastroPetService;
import atividades_estagio.erp.service.CadastroUsuarioService;
 
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource{
	
	@Inject
	private Usuarios usuarios;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Inject
	Pets pets;
	
	@Inject
	CadastroPetService cadastroPetService;
	
	@GET
	@Secured(Role.ADMIN) //apenas adms podem visualizar todos
	public List<Usuario> todos(){
		return usuarios.todos();
	}
	
	@GET
	@Path("/{id}")
	@Secured()
	public Response porId(@PathParam("id") Long id, @Context SecurityContext securityContext) { //"Take the value from {id} in the URL and store it in this variable."
		Usuario logado=usuarioLogado(securityContext);
		
		if(!ehAdminOuDono(logado, id)) return respostaProibido();
		
		Usuario usuario=usuarios.porId(id);
		if(usuario==null) return respostaProibido();
		
		return Response.ok(usuario).build();
		// Response.status(200).entity(usuario).build();
		//HTTP status 200 OK; the Usuario object as the response body
	}
	
	@POST //enviar dados para criar um novo
	public Response criar(Usuario usuario) {
		usuario.setId(null); // garante que é um registro novo, ignora id enviado no body
		cadastroUsuarioService.salvar(usuario);
		return Response.status(Response.Status.CREATED).entity(usuario).build();
	}
	
	@PUT //substituir um que ja existe
	@Path("/{id}")
	@Secured()
	public Response atualizar(@PathParam("id") Long id, Usuario usuario, @Context SecurityContext securityContext ) {
		Usuario logado=usuarioLogado(securityContext);
	
		if(!ehAdminOuDono(logado, id)) return respostaProibido();
		
		
		Usuario existente=usuarios.porId(id);
		
		if(existente==null) return Response.status(Response.Status.NOT_FOUND).build();
		
		usuario.setId(id);
		cadastroUsuarioService.salvar(usuario);
		return Response.ok(usuario).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Secured()
	public Response excluir(@PathParam("id") Long id) throws IOException{
		Usuario logado=usuarioLogado(securityContext);
		
		if(!ehAdminOuDono(logado, id)) return respostaProibido();
		
		Usuario usuario=usuarios.porId(id);
		
		if(usuario==null) return Response.status(Response.Status.NOT_FOUND).build();
		
		excluirPetsUsuario(usuario);
		
		cadastroUsuarioService.excluir(usuario);
		return Response.noContent().build();
	}
	
	private void excluirPetsUsuario(Usuario usuario) throws IOException{
		List<Pet> listaPets=pets.todos();
		listaPets=pets.filtrarPetsDoUsuario(listaPets, usuario);
		
		for (Pet it:listaPets) {
			cadastroPetService.excluir(it);
		}
	}
	
	private Response respostaProibido() {
		return Response.status(Response.Status.FORBIDDEN)
				.entity("{\"erro\":\"acesso proibido\"}")
				.build();
	}
	
	private Usuario usuarioLogado(SecurityContext securityContext) {	
		return ((UsuarioSecurityContext) securityContext).getUsuario();
	}
	
	private boolean ehAdminOuDono(Usuario logado, Long id) {
		if(logado.getRole()==Role.ADMIN || id.equals(logado.getId()) ) return true;
		
		return false;
	}
	
}