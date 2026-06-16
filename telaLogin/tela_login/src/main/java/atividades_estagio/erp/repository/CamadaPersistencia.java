package atividades_estagio.erp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import atividades_estagio.erp.model.Usuario;
import atividades_estagio.erp.model.Especie;
import atividades_estagio.erp.model.Pet;

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

        // Criando um novo usuario
        Usuario usuario=new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setNome("segundo usuario");
        usuario.setSenha("senha:P");

        usuarios.guardar(usuario);
        
        //Long id=usuario.getId();

        em.getTransaction().commit();

        // Verificando se a inserção funcionou
        List<Usuario> listaDeUsuarios2 = usuarios.pesquisar("");
        System.out.println(listaDeUsuarios2);

        em.close();
        emf.close();
        
        
        testarPet();
    }
    
    public static void testarPet() {
    	
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("AtividadesEstagioPU");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        
  
        //Usuario usuario=em.find(Usuario.class, id);
        //System.out.println(usuario);
        
    ///// pet
        
        // Declarando os repositórios
           Pets pets = new Pets(em);

           // Buscando as informações do banco
           List<Pet> listaDePets = pets.pesquisar("");
           System.out.println(listaDePets);

           // Criando um novo pet
           Pet pet=new Pet();
           //pet.setDono(usuario);
           pet.setNome("darwin");
           pet.setEspecie(Especie.PEIXE);

           pets.guardar(pet);

           em.getTransaction().commit();

           // Verificando se a inserção funcionou
           List<Pet> listaDePets2 = pets.pesquisar("");
           System.out.println(listaDePets2);
            
           em.close();
           emf.close();
    }

}
