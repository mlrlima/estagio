package atividades_estagio.erp.rest;
 
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
 
@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource{
	
	@Inject
	private Pets pets;
 
	@Inject
	private Usuarios usuarios;
 
	@Inject
	private CadastroPetService cadastroPetService;
	
	@GET
	public List<Pet> todos() {
		return pets.todos();
	}
	
	@GET
	@Path("/{id}")
	public Response porId(@PathParam("id") Long id) {
		Pet pet=pets.porId(id);
		
		if(pet==null) return Response.status(Response.Status.NOT_FOUND).build();
		
		return Response.ok(pet).build();
	}
	
	@GET
	@Path("/usuario/{userId}")
	public Response petsDoUsuario(@PathParam("userId") Long userId) {
		Usuario usuario=usuarios.porId(userId);
		
		if(usuario==null) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("{\"erro\":\"usuário não encontrado\"}")
					.build();
		}
		
		List<Pet> doUsuario = pets.filtrarPetsDoUsuario(pets.todos(), usuario);
		return Response.ok(doUsuario).build();
	}
	
	
	@POST
	public Response criar(Pet pet) {
		Response erro=resolverDono(pet);
		if(erro!=null) return erro;
		
		pet.setId(null); // garante que é um registro novo
		cadastroPetService.salvar(pet);
		return Response.status(Response.Status.CREATED).entity(pet).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response atualizar(@PathParam("id") Long id, Pet pet) {
		Pet existente=pets.porId(id);
		
		if(existente==null) return Response.status(Response.Status.NOT_FOUND).build();
		
		Response erro=resolverDono(pet);
		if(erro!=null) return erro;
		
		pet.setId(id);
		cadastroPetService.salvar(pet);
		return Response.ok(pet).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response excluiur(@PathParam("id") Long id){
		Pet pet=pets.porId(id);
		
		if(pet==null) return Response.status(Response.Status.NOT_FOUND).build();
		
		cadastroPetService.excluir(pet);
		return Response.noContent().build();
	}
	
	
	/**
	 * O cliente manda só o id do dono (ex: "dono": {"id": 1}).
	 * Aqui resolvemos para o Usuario de verdade antes de salvar,
	 * e validamos que ele existe.
	 */
	private Response resolverDono(Pet pet) {
		if(pet.getDono()==null || pet.getDono().getId()==null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"erro\":\"dono.id é obrigatório\"}")
					.build();
		}
		
		Usuario dono = usuarios.porId(pet.getDono().getId());
		
		if(dono==null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("{\"erro\":\"usuário (dono) não encontrado\"}")
					.build();
		}
		
		pet.setDono(dono);
		return null;
	}
}