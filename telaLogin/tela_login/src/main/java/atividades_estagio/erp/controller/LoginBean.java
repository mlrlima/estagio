package atividades_estagio.erp.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.util.FacesMessages;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
    private FacesMessages messages;
	
	private Usuario usuario;
	
	@Inject
	private Usuarios usuarios;
	
	private String emailInput;
	private String senhaInput;
	
	public String login() {
		usuario=usuarios.porEmailESenha(emailInput, senhaInput);
		
		if(usuario==null){
			messages.info("Informações incorretas");
			return null;
		}

		messages.info("Login realizado com sucesso!");

	    return "GestaoPets?faces-redirect=true";
	}
	
	public void setEmailInput(String emailInput) {
		this.emailInput=emailInput;
	}
	public String getEmailInput() {
		return emailInput;
	}
	
	public void setSenhaInput(String senhaInput) {
		this.senhaInput=senhaInput;
	}
	public String getSenhaInput() {
		return senhaInput;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public boolean isAdmin(){
	    return isLogado() && usuario.getRole()==Role.ADMIN;
	}

	public boolean isLogado(){
	    return usuario!=null;
	}
}
