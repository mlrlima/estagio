package atividades_estagio.erp.controller;

import java.io.Serializable;
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
	private Usuarios usuarios;
	
	private Usuario usuario;
	
    @Inject
    private FacesMessages messages;
	
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
		}else{
			todosUsuarios();
		}
		
		messages.info("Usuario salvo com sucesso");
        
		PrimeFaces.current().ajax().update(Arrays.asList(
                "frm:usuariosDataTable", "frm:messages"));
	}
	
	private boolean jaHouvePesquisa(){
		return termoPesquisa!=null && !"".equals(termoPesquisa);
	}
	
	public void pesquisar(){
		listaUsuarios=usuarios.pesquisar(termoPesquisa);
		
		if (listaUsuarios.isEmpty()) {
            messages.info("Não encontrado");
        }
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
	
	public void setUsuario(Usuario usuario) {
		this.usuario=usuario;
	}
	
	public boolean isUsuarioSelecionado() {
		return usuario!=null && usuario.getId()!=null; 
	}
	
} 
