package atividades_estagio.erp.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Utilitário usado pelos @RestController (Spring MVC) para obter um EntityManager
 * a partir da mesma persistence-unit já configurada no persistence.xml (usada pelo JSF/CDI).
 *
 * Isso evita duplicar config de conexão e evita que o lado Spring precise
 * do CDI/Weld pra funcionar.
 */
public class JPAUtil {

	private static final EntityManagerFactory FACTORY=
			Persistence.createEntityManagerFactory("AtividadesEstagioPU");
	
	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}
	
}
