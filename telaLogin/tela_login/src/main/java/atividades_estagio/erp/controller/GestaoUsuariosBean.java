package atividades_estagio.erp.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;

@Named
//@RequestScoped //a cada requisicao cria um novo
@ViewScoped //termina com a sessao
//@SessionScoped
//@ApplicationScoped
public class GestaoUsuariosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	
	private List<Usuario> listaUsuarios;
	
	public void todosUsuarios() {
		listaUsuarios=usuarios.todos();
	}
	
	public List<Usuario> getListaUsuarios(){
		return listaUsuarios;
	}
	
} 
