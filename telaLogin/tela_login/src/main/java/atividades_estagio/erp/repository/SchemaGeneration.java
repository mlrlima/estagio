package atividades_estagio.erp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import atividades_estagio.erp.model.Usuario;

public class SchemaGeneration {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AtividadesEstagioPU");

        EntityManager em = emf.createEntityManager();

        List<Usuario> lista = em.createQuery("from Empresa", Usuario.class).getResultList();

        System.out.println(lista);

        em.close();
        emf.close();
    }

}