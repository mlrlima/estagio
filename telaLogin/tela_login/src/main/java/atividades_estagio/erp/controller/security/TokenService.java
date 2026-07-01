package atividades_estagio.erp.controller.security;
 
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
 
import javax.enterprise.context.ApplicationScoped;
 
import atividades_estagio.erp.model.Usuario;
 
/**
 * Guarda os tokens de login em memória (Map token -> Usuario).
 *
 * Simples de propósito: sem expiração, sem persistência em banco.
 * Se o servidor reiniciar, todo mundo precisa logar de novo.
 * Suficiente para o projeto de estudo; numa app real isso seria
 * JWT ou uma tabela de sessões no banco.
 */

@ApplicationScoped
public class TokenService{
	
	private final ConcurrentHashMap<String, Usuario> tokens=new ConcurrentHashMap<>();;
	
	public String gerarToken(Usuario usuario) {
		String token= UUID.randomUUID().toString(); //Universally Unique Identifier (UUID)
		tokens.put(token, usuario);
		
		return token;
	}
	
	public Usuario validar(String token) {
		if(token==null || !tokens.containsKey(token) ) return null;
		
		return tokens.get(token);
	}
	
	public void invalidar(String token) {
		if(token==null || !tokens.containsKey(token) ) return;
		
		tokens.remove(token);
	}
}