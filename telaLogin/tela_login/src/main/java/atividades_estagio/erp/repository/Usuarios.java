package atividades_estagio.erp.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import atividades_estagio.erp.model.Usuario;

public class Usuarios implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	public Usuarios() {
	}
	
	public Usuarios(EntityManager manager) {
		this.manager = manager;
	}
	
	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public List<Usuario> pesquisar(String nome) {
		String jpql = "from Usuario where nome like :nome";
		
		TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);
		query.setParameter("nome", nome + "%");
		return query.getResultList();
	}

	public Usuario guardar(Usuario usuario) {
		//atualizar - update ou insert
		return manager.merge(usuario);
	}

	public void remover(Usuario usuario) {
		usuario = porId(usuario.getId());
		manager.remove(usuario);
	}
	
}
