package atividades_estagio.erp.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.util.FacesMessages;

@Named
@ViewScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
    private FacesMessages messages;
	
	private Usuario usuario;
	
	@Inject
	private Usuarios usuarios;
	
	private String emailInput;
	private String senhaInput;
	
	public void login() {
		usuario=usuarios.porEmailESenha(emailInput, senhaInput);
		
		if(usuario==null){
			messages.info("Informações incorretas");
		}else {
			messages.info("Login feito com sucesso");
		}
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
}
