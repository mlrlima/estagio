package atividades_estagio.erp.controller;

import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.security.Secured;
import atividades_estagio.erp.util.JPAUtil;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	private Usuario logado(HttpServletRequest request) {
		return (Usuario) request.getAttribute("usuarioLogado");
	}

	@PostMapping //
	public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario){
		usuario.setId(null);
        usuario.setRole(Role.USER);
        Usuario salvo = JPAUtil.executarEmTransacao(manager -> new Usuarios(manager).guardar(usuario));
		return ResponseEntity.ok(salvo);
	}
	
	@Secured
	@GetMapping
	public ResponseEntity<?> porEmail(@RequestParam String email, HttpServletRequest request){
		Usuario usuarioLogado = logado(request);
        Usuario alvo = getRepo().porEmail(email);
        
        if (alvo == null) return ResponseEntity.notFound().build();
        
        if (usuarioLogado.getRole() != Role.ADMIN && !usuarioLogado.getId().equals(alvo.getId())) {
            return ResponseEntity.status(403).body(Map.of("erro", "Sem permissao"));
        }
        return ResponseEntity.ok(alvo);
	}
	
	@Secured(Role.ADMIN)
    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> todos() {
        return ResponseEntity.ok(getRepo().todos());
    }
	
	@Secured
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id, HttpServletRequest request){
		Usuario usuarioLogado = logado(request);
		
        if (usuarioLogado.getRole() != Role.ADMIN && !usuarioLogado.getId().equals(id)) {
            return ResponseEntity.status(403).body(Map.of("erro", "Sem permissao"));
        }
        
        Usuario usuario = getRepo().porId(id);
        
        if (usuario == null) return ResponseEntity.notFound().build();
        
        JPAUtil.executarEmTransacao(manager -> { new Usuarios(manager).remover(usuario); return null; });
        return ResponseEntity.ok().build();

	} 
	
    @Secured
    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario, HttpServletRequest request) {
        Usuario usuarioLogado = logado(request);
        
        if (usuarioLogado.getRole() != Role.ADMIN && !usuarioLogado.getId().equals(usuario.getId())) {
            return ResponseEntity.status(403).body(Map.of("erro", "Sem permissao"));
        }
        
        if (usuarioLogado.getRole() != Role.ADMIN) {
            usuario.setRole(Role.USER); // usuario comum nao se auto-promove
        }
        
        // senha em branco = mantem a atual (mesmo comportamento do p:password no JSF)
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            Usuario existente = getRepo().porId(usuario.getId());
            usuario.setSenha(existente.getSenha());
        }
        
        
        Usuario salvo = JPAUtil.executarEmTransacao(manager -> new Usuarios(manager).guardar(usuario));
        return ResponseEntity.ok(salvo);
    }
}
