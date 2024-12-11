package ClasesPrestamo;

import ClasesEjemplar.*;
import ClasesUsuario.*;
import ClasesLibro.*;
import ClasesPrestamo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class PrestamoDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaccion = em.getTransaction();

    public List<Prestamo> listarPrestamos() {
        return em.createQuery("select p from Prestamo p").getResultList();
    }

    public void insertarPrestamos(Prestamo p) {
        transaccion.begin();
        em.persist(p);
        transaccion.commit();
    }
    public void eliminarPrestamos(Prestamo p) {
        transaccion.begin();
        em.remove(p);
        transaccion.commit();
    }

    public void actualizarPrestamos(Prestamo p) {
        transaccion.begin();
        em.merge(p);
        transaccion.commit();
    }

    public Prestamo prestamoPorID(int id) {
        Prestamo p = em.find(Prestamo.class, id);
        return p;
    }
}
