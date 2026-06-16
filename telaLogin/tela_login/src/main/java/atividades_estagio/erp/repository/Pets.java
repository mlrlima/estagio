package atividades_estagio.erp.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import atividades_estagio.erp.model.Pet;

public class Pets implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	public Pets() {
	}
	
	public Pets(EntityManager manager) {
		this.manager = manager;
	}
	
	public Pet porId(Long id) {
		return manager.find(Pet.class, id);
	}

	//pesquisar usuario por nome
	public List<Pet> pesquisar(String nome) {
		String jpql = "from Pet where nome like :nome";
		
		TypedQuery<Pet> query = manager.createQuery(jpql, Pet.class);
		query.setParameter("nome", nome + "%");
		return query.getResultList();
	}

	public Pet guardar(Pet pet) {
		//atualizar - update ou insert
		return manager.merge(pet);
	}

	public void remover(Pet pet) {
		pet = porId(pet.getId());
		manager.remove(pet);
	}
	
}
