package atividades_estagio.erp.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import atividades_estagio.erp.model.Especie;
import atividades_estagio.erp.model.Pet;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Pets;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.service.CadastroPetService;
import atividades_estagio.erp.util.FacesMessages;

@Named
@ViewScoped
public class GestaoPetsBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Pets pets;
	
	private Pet pet;
	
	private List<Pet> listaPets;
	
	private String termoPesquisa;
	
	@Inject
	private CadastroPetService cadastroPetService;
	
    @Inject
    private FacesMessages messages;
    
	@Inject
	private Usuarios usuarios;
	
	public void pesquisar() {
		listaPets=pets.pesquisar(termoPesquisa);
		
		if(listaPets.isEmpty()) messages.info("Não encontrado");
	}
	
	public void todosPets() {
		listaPets=pets.todos();
	}
	
	public List<Pet> getListaPets(){
		return listaPets;
	}
	
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet=pet;
	}
	
	public String getTermoPesquisa(){
		return termoPesquisa;
	}
	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa=termoPesquisa;
	}
	
	public Especie[] getEspecies() {
		return Especie.values();
	}
	
	public void prepararNovoPet(){
		pet=new Pet();
	}
	
	public void salvar() {
		cadastroPetService.salvar(pet);
		
		atualizarPesquisa();
		
		messages.info("Pet salvo com sucesso");
		
		PrimeFaces.current().ajax().update(Arrays.asList(
                "frm:petsDataTable", "frm:messages"));
		
	}
	
	public void atualizarPesquisa(){
		if(jaHouvePesquisa()) {
			pesquisar();
		}else{
			todosPets();
		}
	}
	
	private boolean jaHouvePesquisa(){
		return termoPesquisa!=null && !"".equals(termoPesquisa);
	}
	
	public void excluir() {
		cadastroPetService.excluir(pet);
		
		pet=null;
		
		atualizarPesquisa();
		
		messages.info("Pet excluido com sucesso");
	}
	
	public boolean isPetSelecionado() {
		return pet!=null && pet.getId()!=null;
	}
	
	public List<Usuario> getTodosUsuarios(){
		return usuarios.todos();
	}
	
}
