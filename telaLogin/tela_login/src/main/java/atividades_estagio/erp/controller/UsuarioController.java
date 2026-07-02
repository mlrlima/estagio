package atividades_estagio.erp.controller;

import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.util.JPAUtil;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private Usuarios getRepo() {
		return new Usuarios(JPAUtil.getEntityManager());
	}

	@PostMapping //
	public ResponseEntity<Void> guardarUsuario(@RequestBody Usuario usuario){
		getRepo().guardar(usuario);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<Usuario> porEmail(@RequestParam String email){
		Usuario usuario=getRepo().porEmail(email);
		return ResponseEntity.ok(usuario);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(Long id){
		Usuario usuario=getRepo().porId(id);
	    getRepo().remover(usuario);
		return ResponseEntity.ok().build();
	} 
	
	@PutMapping
	public ResponseEntity<Void> atualizar(@RequestBody Usuario usuario){
		getRepo().guardar(usuario);
		return ResponseEntity.ok().build();
	}
}
