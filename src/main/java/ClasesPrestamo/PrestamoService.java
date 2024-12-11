package ClasesPrestamo;
import ClasesEjemplar.*;
import ClasesUsuario.*;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrestamoService {
    Scanner teclado = new Scanner(System.in);
    List<Prestamo> listaPrestamos = new ArrayList<>();
    PrestamoDAO prestamoDAO;
    UsuarioDAO usuarioDAO;
    EjemplarDAO ejemplarDAO;

    public PrestamoService() {
        this.usuarioDAO = new UsuarioDAO();
        this.ejemplarDAO = new EjemplarDAO();
        this.prestamoDAO = new PrestamoDAO();
        sincronizar();
    }
    public void sincronizar() {
        listaPrestamos = prestamoDAO.listarPrestamos();
    }
    public void listarPrestamos() {listaPrestamos.forEach(System.out::println);}

    public void listarPrestamosDeUsuario() {
        System.out.println("Inserte el id del usuario: ");
        int idUsuario = teclado.nextInt();
        listaPrestamos.stream().filter(prestamo -> prestamo.getUsuario().getId() == idUsuario).forEach(System.out::println);
    }

    public void registrarPrestamo() {
        //Pedimos datos básicos por consola
        System.out.println("Introduce el id del usuario: ");
        int idUsuario = teclado.nextInt();
        System.out.println("Introduce el id del ejemplar: ");
        int idEjemplar = teclado.nextInt();
        //Creamos Ejemplar y Usuario con los valores que hemos solicitado previamente
        Usuario u = usuarioDAO.getUsuarioId(idUsuario);
        Ejemplar e = ejemplarDAO.EjemplarPorID(idEjemplar);
        //Verificar que el ejemplar esté disponible
        if (!e.getEstado().equals("Disponible")) {
            System.err.printf("El ejemplar está %s", e.getEstado());
        }
        //Número de prestamos activos del Usuario 'u'
        int prestamosActivos = (int) listaPrestamos.stream().filter(p -> p.getUsuario().getId().equals(u.getId())).count();
        if (prestamosActivos >= 3) {
            throw new RuntimeException("El usuario ya tiene 3 préstamos activos y no puede solicitar más.");
        }
        //Verificar que el usuario esté penalizado
        if (u.getPenalizacionHasta() != null) {
            System.out.println("Hay una penalización activa para ese usuario hasta " + u.getPenalizacionHasta());
        }
        //Registrar el préstamo
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = LocalDate.now().plusDays(15);
        Prestamo p = new Prestamo(u, e, fechaPrestamo, fechaDevolucion);
        //Insertamos el Préstamo
        prestamoDAO.insertarPrestamos(p);
        //Modificamos su estado a "No disponible"
        e.setEstado("No disponible");
        ejemplarDAO.modificarEjemplares(e);
        //Mensaje de salida del método para el "cliente"
        System.out.printf("Préstamo registrado al usuario %s del libro %s hasta el %s", p.getUsuario().getId(), p.getEjemplar().getIsbn(), p.getFechaDevolucion());
    }

    public void registrarDevolucionPrestamo() {
        //Pedimos valores por consola
        System.out.println("Introduce el id del préstamo a devolver: ");
        int idPrestamo = teclado.nextInt();
        //Buscamos el Préstamo en la bdd
        Prestamo p = prestamoDAO.prestamoPorID(idPrestamo);
        Ejemplar e = p.getEjemplar();
        Usuario u = p.getUsuario();
        // Cambiar el estado del ejemplar a "Disponible"
        e.setEstado("Disponible");
        ejemplarDAO.modificarEjemplares(e);
        // Registrar la fecha de devolución del préstamo
        LocalDate fechaDevolucion = LocalDate.now();
        p.setFechaDevolucion(fechaDevolucion);
        prestamoDAO.actualizarPrestamos(p);
        //Calcular si se devolvió tarde o no
        if (fechaDevolucion.isAfter(p.getFechaDevolucion())) {
            int diasRetraso = (int) java.time.temporal.ChronoUnit.DAYS.between(p.getFechaDevolucion(), fechaDevolucion);
            int penalizacionDias = diasRetraso * 15;
            // Registrar la penalización al usuario
            LocalDate nuevaPenalizacion = LocalDate.now().plusDays(penalizacionDias);
            if (u.getPenalizacionHasta() == null || nuevaPenalizacion.isAfter(u.getPenalizacionHasta())) {
                u.setPenalizacionHasta(nuevaPenalizacion);
                usuarioDAO.actualizarUsuario(u);
            }
            System.out.println("El usuario tiene una penalización hasta " + nuevaPenalizacion + " por devolverlo tarde");
        } else{
            System.out.println("El ejemplar se devolvió correctamente");
        }
    }


    public void eliminarPrestamo() {
        listarPrestamos();
        System.out.println("Introduce el id del prestamo a eliminar: ");
        int id = Integer.parseInt(teclado.nextLine());
        if (prestamoDAO.prestamoPorID(id) != null) {
            prestamoDAO.eliminarPrestamos(prestamoDAO.prestamoPorID(id));
            System.out.println("Prestamo eliminado");
        }
    }
}
