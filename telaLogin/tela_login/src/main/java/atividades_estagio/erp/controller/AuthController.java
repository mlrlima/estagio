package atividades_estagio.erp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.security.GeradorToken;
import atividades_estagio.erp.security.Secured;
import atividades_estagio.erp.security.GeradorToken;
import atividades_estagio.erp.util.JPAUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private Usuarios getRepo() {
		return new Usuarios(JPAUtil.getEntityManager());
	}
	
	public static class LoginRequest{
		public String email;
		public String senha;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req){
		Usuario usuario=getRepo().porEmailESenha(req.email, req.senha);
		
		if(usuario==null) {
			return ResponseEntity.status(401).body(Map.of("erro", "Email ou senha invalidos"));
		}
		
		String token=GeradorToken.gerar(usuario);
		
		Map<String, Object> resposta = new HashMap<>();
        resposta.put("token", token);
        resposta.put("id", usuario.getId());
        resposta.put("nome", usuario.getNome());
        resposta.put("email", usuario.getEmail());
        resposta.put("role", usuario.getRole());
        
        return ResponseEntity.ok(resposta);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String auth){
		if(auth!=null && auth.startsWith("Bearer ")) {
			GeradorToken.invalidar(auth.substring(7));
		}
		
		return ResponseEntity.ok().build();
	}
	
	@Secured
	@GetMapping("/me")
	public ResponseEntity<Usuario> me(HttpServletRequest request){
		return ResponseEntity.ok((Usuario) request.getAttribute("usuarioLogado"));
	}

}
