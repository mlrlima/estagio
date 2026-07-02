package atividades_estagio.erp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import atividades_estagio.erp.security.AuthInterceptor;

/**
 * So escaneia o pacote de controllers REST — nao mexe em nada do JSF/CDI.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "atividades_estagio.erp.controller")
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor());
	}
}