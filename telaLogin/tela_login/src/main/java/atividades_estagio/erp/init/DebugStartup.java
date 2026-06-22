package atividades_estagio.erp.init;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DebugStartup {

    @PostConstruct
    public void init() {
        System.out.println(">>> CDI IS WORKING");
    }
}