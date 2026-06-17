package atividades_estagio.erp.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

//meta anotacoes, que anotam outras anotacoes
@Target({ElementType.METHOD, ElementType.TYPE}) //@ pode ser usada em metodos e classes
@Retention(RetentionPolicy.RUNTIME) //define ate quando a @ existe. nesse caso, durante a execucao
@InterceptorBinding //automaticamente executa begin(), commit(), rollback()
public @interface Transacional {
	// @Transacional
}
