package atividades_estagio.erp.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;

@Named
//@RequestScoped //a cada requisicao cria um novo
@ViewScoped //termina com a sessao
//@SessionScoped
//@ApplicationScoped
public class GestaoUsuariosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario=new Usuario();
	
	
	public void salvar() {
		System.out.println("Nome: "+usuario.getNome());
		System.out.println("Email: "+usuario.getEmail());
		System.out.println("Senha: "+usuario.getSenha());
		System.out.println("Role: "+usuario.getRole());
	}
	
	public Usuario getUsuario(){
		return usuario;
	}
	
	public Role[] getRoles() {
		return Role.values();
	}
}
