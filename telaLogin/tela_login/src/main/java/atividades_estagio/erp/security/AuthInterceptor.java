package atividades_estagio.erp.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import atividades_estagio.erp.model.Role;
import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.repository.Usuarios;
import atividades_estagio.erp.util.JPAUtil;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) {
		//preHandle intercepta o request HTTP antes de chegar no controller
		
		
		//Nem toda requisição vai para um método de controller.
		if(!(handler instanceof HandlerMethod)) return true;
		
		//identificar o metodo que será chamado
		HandlerMethod hm=(HandlerMethod) handler;
		
		Secured secured = hm.getMethodAnnotation(Secured.class); //anotacao do metodo
		if(secured==null){
			secured= hm.getBeanType().getAnnotation(Secured.class); //anotacao da classe
		}
		if(secured==null) return true;
		
		
		String header = request.getHeader("Authorization");
		if(header==null || !header.startsWith("Bearer ")) {
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return false;
		}
		
		String token=header.substring(7);
		Long usuarioId= GeradorToken.validar(token);
		if(usuarioId ==null) {
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido ou expirado");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return false;
		}
		
		Usuarios repo= new Usuarios(JPAUtil.getEntityManager());
		Usuario usuario=repo.porId(usuarioId);
		if(usuario==null) {
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario nao encontrado");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return false;
		}
		
		Role[] rolesPermitidas=secured.value();
		if(rolesPermitidas.length > 0) {
			
			boolean permitido=false;
			for(Role r:rolesPermitidas) {
				if(r== usuario.getRole()) {
					permitido=true;
					break;
				}
			}
			
			if(!permitido) {
				try {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sem permissao");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return false;
			}
		}
		
		request.setAttribute("usuarioLogado", usuario);
        return true;
		
	}
}
