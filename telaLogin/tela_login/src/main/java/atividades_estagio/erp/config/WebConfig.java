package atividades_estagio.erp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * So escaneia o pacote de controllers REST — nao mexe em nada do JSF/CDI.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "atividades_estagio.erp.controller")
public class WebConfig {
}