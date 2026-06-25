package atividades_estagio.erp.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import atividades_estagio.erp.model.Especie;
import atividades_estagio.erp.model.Pet;
import atividades_estagio.erp.repository.Pets;

@Named
@ViewScoped
public class GestaoPetsBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Pets pets;
	
	private List<Pet> listaPets;
	
	public void todosPets() {
		listaPets=pets.todos();
	}
	
	public List<Pet> getListaPets(){
		return listaPets;
	}
}
