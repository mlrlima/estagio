package atividades_estagio.erp.controller;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Sem isso, qualquer violação de @NotNull/@Email/@Size etc. nas entidades
 * (Usuario, Pet) vira um erro 500. Com isso vira um 400 com mensagem
 * clara em JSON.
 */
@Provider //provides a service to the framework.
public class ConstraintViolationExceptionMapper
implements ExceptionMapper<ConstraintViolationException> {
//Whenever a ConstraintViolationException is thrown
//use this class to create the HTTP response
	
	
	@Override
	public Response toResponse(ConstraintViolationException exception) {
		String mensagens = exception.getConstraintViolations().stream() //Get all validation errors
				.map(v -> v.getPropertyPath()/* field name */ + ": " + v.getMessage()) //Convert each violation into text
				.collect(Collectors.joining("; ")); //todas as mensagens em uma unica string

		String corpo = "{\"erro\":\"" + mensagens.replace("\"", "'") + "\"}";

		return Response.status(Response.Status.BAD_REQUEST)
				.entity(corpo)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}