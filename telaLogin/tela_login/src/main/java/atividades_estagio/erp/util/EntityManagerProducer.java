package atividades_estagio.erp.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//define beans shared across the entire application context
// ideal for resources like shared caches or configuration objects
//that need to persist throughout the application's lifecycle
@ApplicationScoped
public class EntityManagerProducer {
	
	private EntityManagerFactory factory;
	
	public EntityManagerProducer(){
		System.out.println("Creating EMF...");

	    try {
	        this.factory =
	            Persistence.createEntityManagerFactory("AtividadesEstagioPU");

	        System.out.println("EMF created successfully");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Produces //para ajudar o cdi a criar
	@RequestScoped // a cada requisicao  cria uma instancia nova
	public EntityManager createEntityManager() {
		return this.factory.createEntityManager();
	}
	
	
	//@Disposes  utilizado somente para (ensinar ao CDI como) descartar
	//objetos que foram produzidos com @Produces
	public void closeEntityManager(@Disposes EntityManager manager) {
		manager.close();
	}
}
