package atividades_estagio.erp.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Ativa o JAX-RS (RESTEasy). Todos os endpoints REST ficarão
 * disponíveis sob o prefixo /api, ex: /api/usuarios, /api/pets, /api/login
 *
 * Não é necessário sobrescrever getClasses()/getSingletons():
 * o resteasy-servlet-initializer faz o scan automático das
 * classes anotadas com @Path.
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
}