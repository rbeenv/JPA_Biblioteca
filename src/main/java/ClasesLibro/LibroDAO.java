package ClasesLibro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class LibroDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaccion = em.getTransaction();

    public List<Libro> listarLibros(){
        return em.createQuery("select l from Libro l").getResultList();
    }

    public void insertarLibro(Libro l){
        transaccion.begin();
        em.persist(l);
        transaccion.commit();
    }

    public void eliminarLibro(Libro l){
        transaccion.begin();
        em.remove(l);
        transaccion.commit();
    }

    public void actualizarLibro(Libro l){
        transaccion.begin();
        em.merge(l);
        transaccion.commit();
    }

    public Libro getLibroIsbn(String isbn){
        Libro l = em.find(Libro.class, isbn);
        return l;
    }
}
