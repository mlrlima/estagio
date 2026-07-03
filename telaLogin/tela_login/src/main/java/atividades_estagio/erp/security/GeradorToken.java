package atividades_estagio.erp.security;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import atividades_estagio.erp.model.Usuario;

public class GeradorToken {

	                       // token e id
	private static final Map<String, Long> tokens=new ConcurrentHashMap<>();
	
	public static String gerar(Usuario usuario) {
		//UUID == Universally Unique Identifier
		String token=UUID.randomUUID().toString();
		
		tokens.put(token, usuario.getId());
		
		return token;
	}
	
	public static Long validar(String token) {
		if(token==null || token=="") return null;
		if(tokens.get(token) ==null) return null;
		
		return tokens.get(token); //retorna o id do usuario
	}
	
	public static void invalidar(String token) {
		tokens.remove(token);
	}
	
}
