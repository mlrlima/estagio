package atividades_estagio.id;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class Teste {
	
	@Inject
	private EntityManager manager;
	
	public String retornar(){
		return "hello world";
	}
}
