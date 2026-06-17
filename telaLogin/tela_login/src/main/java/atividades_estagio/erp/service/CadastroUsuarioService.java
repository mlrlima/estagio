package atividades_estagio.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.util.Transacional;

public class CadastroUsuarioService implements Serializable {
	private final long seriaVersionUID=1L;
	
	@Inject
	private Usuarios usuarios;
	
	@Transacional
	public void salvar(Usuario usuario) {
		usuarios.guardar(usuario);
	}
	
	@Transacional
	public void excluir(Usuario usuario) {
		usuarios.remover(usuario);
	}

}
