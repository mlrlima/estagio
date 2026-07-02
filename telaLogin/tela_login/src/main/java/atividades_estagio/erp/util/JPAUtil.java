package atividades_estagio.erp.util;

import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
	
	 /* Usar isso pra QUALQUER escrita (merge/remove) chamada a partir dos
	 * @RestController - esse EntityManager e RESOURCE_LOCAL, entao nao tem
	 * transacao automatica como tem no lado CDI.
	 */
	public static <T> T executarEmTransacao(Function<EntityManager, T> acao) {
		EntityManager manager = getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		try {
			tx.begin();
			T resultado = acao.apply(manager);
			tx.commit();
			return resultado;
		} catch (RuntimeException e) {
			if (tx.isActive()) tx.rollback();
			throw e;
		} finally {
			manager.close();
		}
	}
	
}
