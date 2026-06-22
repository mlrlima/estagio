package atividades_estagio.erp.repository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import atividades_estagio.erp.model.Usuario;

//@ApplicationScoped
public class Usuarios implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuarios() {
	}
	
	public Usuarios(EntityManager manager) {
		this.manager = manager;
	}
	
	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	//pesquisar usuario por nome
	public List<Usuario> pesquisar(String nome) {
		String jpql = "from Usuario where nome like :nome";
		
		TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);
		query.setParameter("nome", nome + "%");
		return query.getResultList();
	}
	
	//retorna lista com todos os usuarios
	public List<Usuario> todos() {
		TypedQuery<Usuario> query = manager.createQuery("from Usuario", Usuario.class);
		return query.getResultList();
	}

	//create && update
	public Usuario guardar(Usuario usuario) {
		//atualizar - update ou insert
		return manager.merge(usuario);
	}

	//delete
	public void remover(Usuario usuario) {
		usuario = porId(usuario.getId());
		manager.remove(usuario);
	}
	
}
