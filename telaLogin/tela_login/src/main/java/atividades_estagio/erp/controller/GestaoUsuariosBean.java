package atividades_estagio.erp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.service.CadastroUsuarioService;
import atividades_estagio.erp.util.FacesMessages;

@Named
//@RequestScoped //a cada requisicao cria um novo
@ViewScoped //termina com a sessao
//@SessionScoped
//@ApplicationScoped
public class GestaoUsuariosBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LoginBean loginBean;

	@Inject
	private Usuarios usuarios;
	
	private Usuario usuario;
	
    @Inject
    private FacesMessages messages; //nao tem get nem set
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	private String termoPesquisa;
	
	private List<Usuario> listaUsuarios;
	
	
	public void prepararNovoUsuario() {
		usuario=new Usuario();
	}
	
	public void prepararEdicao() {
		
	}
	
	public void salvar() {
		//System.out.println("Email: " + usuario.getEmail());
		//System.out.println("Nome: " + usuario.getNome());
		//System.out.println("senha: " + usuario.getSenha());
		
		try {
			cadastroUsuarioService.salvar(usuario);
			
			atualizarPesquisa();
			
			messages.info("Usuario salvo com sucesso");
	        
			PrimeFaces.current().ajax().update(Arrays.asList(
	                "frm:usuariosDataTable", "frm:messages"));
			
		}catch(Exception e){
			messages.info("Algo deu errado");
		}
	}
	
	public void excluir() {
		cadastroUsuarioService.excluir(usuario);
		
		usuario=null;
		
		atualizarPesquisa();
		
		messages.info("Usuario excluído com sucesso");
	}
	
	public void atualizarPesquisa(){
		if(jaHouvePesquisa()) {
			pesquisar();
		}else{
			todosUsuarios();
		}
	} 
	
	private boolean jaHouvePesquisa(){
		return termoPesquisa!=null && !"".equals(termoPesquisa);
	}
	
	public void pesquisar(){
		
		//System.out.println("Usuário logado: " + loginBean.getUsuario());
		
		if(loginBean.isAdmin()) {
			listaUsuarios=usuarios.pesquisar(termoPesquisa);
		}else {
			listaUsuarios=new ArrayList<Usuario>();
			listaUsuarios.add(loginBean.getUsuario());
		}
		
		if (listaUsuarios.isEmpty()) {
            messages.info("Não encontrado");
        }
	}
	
	public void todosUsuarios() {
		if(loginBean.isAdmin()) {
			listaUsuarios=usuarios.todos();
		}else {
			listaUsuarios=new ArrayList<Usuario>();
			listaUsuarios.add(loginBean.getUsuario());
		}
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
	
	public void setUsuario(Usuario usuario) {
		this.usuario=usuario;
	}
	
	public boolean isUsuarioSelecionado() {
		return usuario!=null && usuario.getId()!=null; 
	}
	
} 
