package atividades_estagio.erp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import atividades_estagio.erp.model.Usuario;

public class CamadaPersistencia {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AtividadesEstagioPU");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Declarando os repositórios
        Usuarios usuarios = new Usuarios(em);

        // Buscando as informações do banco
        List<Usuario> listaDeUsuarios = usuarios.pesquisar("");
        System.out.println(listaDeUsuarios);

        // Criando uma empresa
        Usuario usuario=new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setNome("segundo usuario");
        usuario.setSenha("senha:P");

        usuarios.guardar(usuario);

        em.getTransaction().commit();

        // Verificando se a inserção funcionou
        List<Usuario> listaDeUsuarios2 = usuarios.pesquisar("");
        System.out.println(listaDeUsuarios2);

        em.close();
        emf.close();
    }

}
