package atividades_estagio.erp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Registra o DispatcherServlet do Spring MVC no mesmo webapp que ja tem o FacesServlet (JSF).
 * Isso e feito via codigo (Servlet 3.0+), sem precisar mexer no web.xml existente.
 *
 * O DispatcherServlet fica mapeado em /api/*, entao ele so intercepta chamadas
 * pra API REST — as telas .xhtml continuam passando pelo FacesServlet normalmente.
 */
public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
						//representa o servidor (Tomcat)

		// contexto Spring separado, so pros @RestController
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);

		ServletRegistration.Dynamic dispatcher =
				container.addServlet("dispatcher", new DispatcherServlet(context)); //controlador central

		dispatcher.setLoadOnStartup(1); // 1== alta prioridade paea inicializaçao
		dispatcher.addMapping("/api/*");
	}
}