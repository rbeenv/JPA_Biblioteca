package ClasesEjemplar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class EjemplarDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaccion = em.getTransaction();

    public List<Ejemplar> listarEjemplares(){
        return em.createQuery("select e from Ejemplar e").getResultList();
    }

    public void insertarEjemplares (Ejemplar e){
        transaccion.begin();
        em.persist(e);
        transaccion.commit();
    }

    public void eliminarEjemplares (Ejemplar e){
        transaccion.begin();
        em.remove(e);
        transaccion.commit();
    }

    public void modificarEjemplares (Ejemplar e){
        transaccion.begin();
        em.merge(e);
        transaccion.commit();
    }

    public Ejemplar EjemplarPorID (int id){
        Ejemplar e = em.find(Ejemplar.class, id);
        return e;
    }

}
