package atividades_estagio.erp.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.service.CadastroUsuarioService;

@Named
//@RequestScoped //a cada requisicao cria um novo
@ViewScoped //termina com a sessao
//@SessionScoped
//@ApplicationScoped
public class GestaoUsuariosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	
	private Usuario usuario;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	private String termoPesquisa;
	
	private List<Usuario> listaUsuarios;
	
	
	public void prepararNovoUsuario() {
		usuario=new Usuario();
	}
	
	public void salvar() {
		cadastroUsuarioService.salvar(usuario);
		
		if(jaHouvePesquisa()) {
			pesquisar();
		}
		
		//messages.info("Usuario cadastrado com sucesso");
	}
	
	private boolean jaHouvePesquisa(){
		return termoPesquisa!=null && !"".equals(termoPesquisa);
	}
	
	public void pesquisar(){
		listaUsuarios=usuarios.pesquisar(termoPesquisa);
	}
	
	public void todosUsuarios() {
		listaUsuarios=usuarios.todos();
	}
	
	public List<Usuario> getListaUsuarios(){
		return listaUsuarios;
	}
	
	
	public String getTermoPesquisa(){
		return termoPesquisa;
	}
	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa=termoPesquisa;
	}
	
	public Role[] getRoles() {
		return Role.values();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
} 
