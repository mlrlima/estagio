package atividades_estagio.erp.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
	private LoginBean loginBean;
	
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
	
	private Long usuarioId;
	
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId=usuarioId;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	
	public void pesquisar() {
		listaPets=pets.pesquisar(termoPesquisa);
		
		if(!listaPets.isEmpty() && !loginBean.isAdmin()) {
			listaPets=pets.filtrarPetsDoUsuario(listaPets, loginBean.getUsuario());
		}
		
		if(listaPets.isEmpty()) messages.info("Não encontrado");

	}
	
	public void todosPets() throws IOException {
		try {
			listaPets=pets.todos();
			
			if(!listaPets.isEmpty() && !loginBean.isAdmin()) {
				listaPets=pets.filtrarPetsDoUsuario(listaPets, loginBean.getUsuario());
			}
		}catch(Exception e) {
			messages.info("Não há usuario logado");
			loginBean.redirecionarTelaLogin();
		}
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
		
		//System.out.println("chegou aqui");
		pet=new Pet();
	}
	
	public void salvar() throws IOException {
		
		try {
		
			pet.setDono(usuarios.porId(usuarioId));
			//System.out.println("chegou aqui 2");
			cadastroPetService.salvar(pet);
			//System.out.println("chegou aqui3");
			
			atualizarPesquisa();
			
			messages.info("Pet salvo com sucesso");
			
			PrimeFaces.current().ajax().update(Arrays.asList(
	                "frm:petsDataTable", "frm:messages"));
			
		}catch(Exception e) {
			e.printStackTrace();
			messages.info("Algo deu errado");
		}
		
		
	}
	
	public void atualizarPesquisa() throws IOException{
		if(jaHouvePesquisa()) {
			pesquisar();
		}else{
			todosPets();
		}
	}
	
	private boolean jaHouvePesquisa(){
		return termoPesquisa!=null && !"".equals(termoPesquisa);
	}
	
	public void excluirPetsUsuario(Usuario usuario) throws IOException{
		listaPets=pets.todos();
		listaPets=pets.filtrarPetsDoUsuario(listaPets, usuario);
		
		for (Pet it:listaPets) {
		    cadastroPetService.excluir(it);
		}
		
		atualizarPesquisa();
	}
	
	public void excluir() throws IOException {
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
