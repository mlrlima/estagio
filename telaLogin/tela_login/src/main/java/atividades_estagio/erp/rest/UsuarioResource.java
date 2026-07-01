package atividades_estagio.erp.rest;
 
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import atividades_estagio.erp.model.Pet;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Pets;
import atividades_estagio.erp.repository.Usuarios;
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
	
	@GET //pegar os dados sem modificar o estado deles
	public List<Usuario> todos(){
		return usuarios.todos();
	}
	
	@GET
	@Path("/{id}")
	public Response porId(@PathParam("id") Long id) { //"Take the value from {id} in the URL and store it in this variable."
		Usuario usuario=usuarios.porId(id);
		if(usuario==null){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
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
	public Response atualizar(@PathParam("id") Long id, Usuario usuario) {
		Usuario existente=usuarios.porId(id);
		
		if(existente==null) return Response.status(Response.Status.NOT_FOUND).build();
		
		usuario.setId(id);
		cadastroUsuarioService.salvar(usuario);
		return Response.ok(usuario).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws IOException{
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
	
}