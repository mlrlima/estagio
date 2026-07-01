package atividades_estagio.erp.controller.dto;

import atividades_estagio.erp.model.Usuario;

public class LoginResponse{
	
	private Usuario usuario;
	private String token;
	
	public LoginResponse(Usuario usuario, String token) {
		this.usuario = usuario;
		this.token = token;		
	}
	
	//getters e settes
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
 
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}