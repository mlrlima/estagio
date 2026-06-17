package atividades_estagio.erp.util;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Interceptor //intercepta chamadas a métodos quando tiverem @Transacional
@Transacional
@Priority(Interceptor.Priority.APPLICATION) //Ativa automaticamente o interceptor no CDI.
//Sem ela, antigamente era necessário registrar o interceptor no beans.xml
//define a ordem de execução quando existem vários interceptors
public class TransacionalInterceptor implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Inject
	private EntityManager manager;
	
	
	//ele vai chamar essa classe pra fazer transacoes mais certeiramente e nao dar commit errado
	
	
	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception{
		EntityTransaction trx=manager.getTransaction();
		boolean criador=false;
		
		try {
			if(!trx.isActive()) {
				// truque para fazer rollback no que já passou
                // (senão, um futuro commit confirmaria até mesmo operações sem transação)
                trx.begin();
                trx.rollback();

                // agora sim inicia a transação
                trx.begin();

                criador = true;
			}
			return context.proceed(); //continuar com o metodo
		}catch(Exception e) {
			if (trx != null && criador) {
                trx.rollback();
            }

            throw e;
		}finally {
			if (trx != null && trx.isActive() && criador) {
                trx.commit();
            }
		}
	}
}
