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
	
	public Usuario porEmail(String email){
		try {
			String jpql="from Usuario where email = :email";
			
			TypedQuery<Usuario> query=manager.createQuery(jpql, Usuario.class);
			query.setParameter("email", email);
			return query.getSingleResult(); //espero q isso retorne um Usuario
		}catch(Exception e) {
			return null;
		}
		
	}
	
	public Usuario porEmailESenha(String email, String senha){
		try {
			String jpql="from Usuario where email = :email and senha = :senha";
			
			TypedQuery<Usuario> query=manager.createQuery(jpql, Usuario.class);
			query.setParameter("email", email);
			query.setParameter("senha", senha);
			return query.getSingleResult();
		}catch(Exception e){
			return null;
		}
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
