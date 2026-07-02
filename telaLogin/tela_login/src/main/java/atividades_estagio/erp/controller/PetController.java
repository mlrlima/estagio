package atividades_estagio.erp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import atividades_estagio.erp.model.Pet;
import atividades_estagio.erp.repository.Pets;
import atividades_estagio.erp.util.JPAUtil;

@RestController
@RequestMapping("/pet")
public class PetController {

	private Pets getRepo() {
		return new Pets(JPAUtil.getEntityManager());
	}
	
	@PostMapping
	public ResponseEntity<Void> guardarPet(@RequestBody Pet pet){
		getRepo().guardar(pet);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pet> porId(@PathVariable Long id){
		Pet pet=getRepo().porId(id);
		return ResponseEntity.ok(pet);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id){
		Pet pet=getRepo().porId(id);
		getRepo().remover(pet);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Void> atualizar(@RequestBody Pet pet){
		getRepo().guardar(pet);
		return ResponseEntity.ok().build();
	}
	
}
