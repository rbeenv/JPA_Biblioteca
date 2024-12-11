package ClasesUsuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class UsuarioDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
    EntityManager em = emf.createEntityManager();
    //EntityTransaction transaccion = em.getTransaction();

    public List<Usuario> listarUsuarios() {
        return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }

    public  void insertarUsuario(Usuario u) {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    public  void eliminarUsuario(Usuario u) {
        em.getTransaction().begin();
        em.remove(u);
        em.getTransaction().commit();
    }

    public  void actualizarUsuario(Usuario u) {
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
    }

    public Usuario getUsuarioId(int id) {
        Usuario u = em.find(Usuario.class, id);
        return u;
    }
}
