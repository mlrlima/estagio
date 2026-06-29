package atividades_estagio.erp.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import atividades_estagio.erp.model.Pet;
import atividades_estagio.erp.model.Usuario;

public class Pets implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Pets() {
	}
	
	public Pets(EntityManager manager) {
		this.manager = manager;
	}
	
	public Pet porId(Long id) {
		return manager.find(Pet.class, id);
	}

	//pesquisar por nome
	public List<Pet> pesquisar(String nome) {
		String jpql = "from Pet where nome like :nome";
		
		TypedQuery<Pet> query = manager.createQuery(jpql, Pet.class);
		query.setParameter("nome", nome + "%");
		return query.getResultList();
	}
	
	//retorna lista com todos os pets
	public List<Pet> todos(){
		TypedQuery<Pet> query=manager.createQuery("from Pet", Pet.class);
		return query.getResultList();
	}
	
	public List<Pet> filtrarPetsDoUsuario(List<Pet> todosPets, Usuario usuario) {
	    List<Pet> doUsuario = new ArrayList<>();

	    for (Pet pet : todosPets) {
	        if (pet.getDono().getId().equals(usuario.getId())) {
	        	doUsuario.add(pet);
	        }
	    }

	    return doUsuario;
	}

	//create && update
	public Pet guardar(Pet pet) {
		//atualizar - update ou insert
		return manager.merge(pet);
	}

	//delete
	public void remover(Pet pet) {
		pet = porId(pet.getId());
		manager.remove(pet);
	}
	
}
