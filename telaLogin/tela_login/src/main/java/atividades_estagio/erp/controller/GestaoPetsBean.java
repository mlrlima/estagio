package atividades_estagio.erp.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import atividades_estagio.erp.model.Especie;
import atividades_estagio.erp.model.Pet;

@Named
@ViewScoped
public class GestaoPetsBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Pet pet=new Pet();
	
	
	public void salvar() {
		System.out.println("Nome: "+pet.getNome());
		System.out.println("Especie: "+pet.getEspecie());
		System.out.println("Nascimento: "+pet.getDataNascimento());
	}
	
	public Pet getPet(){
		return pet;
	}
	
	public Especie[] getEspecies() {
		return Especie.values();
	}
}
