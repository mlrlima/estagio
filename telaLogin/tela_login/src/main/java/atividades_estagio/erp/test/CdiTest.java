package atividades_estagio.erp.test;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CdiTest {

    public CdiTest() {
        System.out.println(">>> CDI BEAN CREATED");
    }
}