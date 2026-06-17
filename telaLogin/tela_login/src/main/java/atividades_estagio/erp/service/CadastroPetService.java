package atividades_estagio.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import atividades_estagio.erp.model.Pet;
import atividades_estagio.erp.repository.Pets;
import atividades_estagio.erp.util.Transacional;

public class CadastroPetService implements Serializable {
	private final long seriaVersionUID=1L;
	
	@Inject
	private Pets pets;
	
	@Transacional
	public void salvar(Pet pet) {
		pets.guardar(pet);
	}
	
	@Transacional
	public void excluir(Pet pet) {
		pets.remover(pet);
	}
}
