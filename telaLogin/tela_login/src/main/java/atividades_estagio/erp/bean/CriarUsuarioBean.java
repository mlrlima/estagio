package atividades_estagio.erp.bean;

import java.io.Serializable;
import java.util.Arrays;

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
@ViewScoped
public class CriarUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	Usuario usuario;
	
	@Inject
	Usuarios usuarios;
	
	@Inject
    private FacesMessages messages;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	public void prepararNovoUsuario() {
		usuario=new Usuario();
	}
	
	private boolean emailJaExiste(String email) {
		Usuario existe=usuarios.porEmail(email);
		 
		if(existe==null) return false;
		
		return true;
	}
	
	public void salvar() {
		//System.out.println("Email: " + usuario.getEmail());
		//System.out.println("Nome: " + usuario.getNome());
		//System.out.println("senha: " + usuario.getSenha());
		
		try {
			if(emailJaExiste(usuario.getEmail())) {
				messages.info("Email já em uso");
				PrimeFaces.current().ajax().update(Arrays.asList("frm:messages"));
				return;
			}
			
			cadastroUsuarioService.salvar(usuario);
			messages.info("Usuario salvo com sucesso");
			
		}catch(Exception e){
			messages.info("Algo deu errado");
		}
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
}