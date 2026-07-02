package atividades_estagio.erp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import atividades_estagio.erp.model.Pet;
import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Pets;
import atividades_estagio.erp.security.Secured;
import atividades_estagio.erp.util.JPAUtil;

@RestController
@RequestMapping("/pet")
@Secured
public class PetController {

	private Pets getRepo() {
		return new Pets(JPAUtil.getEntityManager());
	}
	
	private Usuario logado(HttpServletRequest request) {
        return (Usuario) request.getAttribute("usuarioLogado");
    }
	
	private boolean donoOuAdmin(Usuario logado, Pet pet) {
		if(logado.getRole() == Role.ADMIN) return true;
		if(pet.getDono().getId().equals(logado.getId())) return true;
		
        return false;
    }
	
    @GetMapping
    public ResponseEntity<List<Pet>> todos(HttpServletRequest request) {
        Usuario usuarioLogado = logado(request);
        
        List<Pet> todos = getRepo().todos();
        
        if(usuarioLogado.getRole()!= Role.ADMIN) {
        	todos=getRepo().filtrarPetsDoUsuario(todos, usuarioLogado);
        }
        
        return ResponseEntity.ok(todos);
    }
	
	@PostMapping
	public ResponseEntity<?> guardarPet(@RequestBody Pet pet, HttpServletRequest request){
		Usuario usuarioLogado = logado(request);
		
		if (usuarioLogado.getRole() != Role.ADMIN) {
            pet.setDono(usuarioLogado); // usuario comum so cria pet pra si mesmo
        }
		
		pet.setId(null);
		Pet salvo = JPAUtil.executarEmTransacao(manager -> new Pets(manager).guardar(pet));
		return ResponseEntity.ok(salvo);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id, HttpServletRequest request){
		Pet pet=getRepo().porId(id);
		
		if (pet == null) return ResponseEntity.notFound().build();
        if (!donoOuAdmin(logado(request), pet)) {
            return ResponseEntity.status(403).body(Map.of("erro", "Sem permissao"));
        }
		
		return ResponseEntity.ok(pet);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id, HttpServletRequest request){
		Pet pet=getRepo().porId(id);
		
		if (pet == null) return ResponseEntity.notFound().build();
        if (!donoOuAdmin(logado(request), pet)) {
            return ResponseEntity.status(403).body(Map.of("erro", "Sem permissao"));
        }
		
        JPAUtil.executarEmTransacao(manager -> { new Pets(manager).remover(pet); return null; });
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody Pet pet, HttpServletRequest request){
		Pet existente = getRepo().porId(pet.getId());

		if (existente == null) return ResponseEntity.notFound().build();

		Usuario usuarioLogado = logado(request);
		
        if (!donoOuAdmin(usuarioLogado, existente)) {
            return ResponseEntity.status(403).body(Map.of("erro", "Sem permissao"));
        }
        
        if (usuarioLogado.getRole() != Role.ADMIN) {
            pet.setDono(existente.getDono()); // usuario comum nao transfere pet pra outro dono
        }
		
        Pet salvo = JPAUtil.executarEmTransacao(manager -> new Pets(manager).guardar(pet));
		return ResponseEntity.ok(salvo);
	}
	
}
