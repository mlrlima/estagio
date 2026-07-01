package atividades_estagio.erp.controller.security;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
import atividades_estagio.erp.model.Role;

/**
 * Marca um endpoint (ou classe inteira) como exigindo login.
 *
 * @Secured()            -> exige apenas estar logado (qualquer role)
 * @Secured(Role.ADMIN)   -> exige estar logado E ser ADMIN
 *
 * Endpoints SEM essa anotação continuam públicos (ex: /login).
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured{
	Role[] value() default{};
}