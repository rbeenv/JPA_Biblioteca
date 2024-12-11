package ClasesEjemplar;

import ClasesLibro.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EjemplarService {
    Scanner teclado = new Scanner(System.in);
    List<Ejemplar> listaEjemplares = new ArrayList<>();
    EjemplarDAO ejemplarDAO;

    public EjemplarService() {
        this.ejemplarDAO = new EjemplarDAO();
        sincronizar();
    }

    public void sincronizar() {listaEjemplares = ejemplarDAO.listarEjemplares();}
    public void listarEjemplar() {listaEjemplares.forEach(System.out::println);}

    public void agregarEjemplar() {
        System.out.println("Introduce el ISBN del libro asociado: ");
        String isbn = teclado.nextLine().trim();
        System.out.println("Introduce el estado del ejemplar (Disponible - 1, Prestado - 2, Dañado - 3): ");
        int numEstado = teclado.nextInt();
        String estado;
        switch (numEstado){
            case 1 -> estado = "Disponible";
            case 2 -> throw new RuntimeException("No se puede registrar un ejemplar como 'Prestado' aún, Gracias");
            case 3 -> estado = "Dañado";
            default -> {
                System.out.println("Estado no válido. Operación cancelada.");
                return;
            }
        }
        Libro libroIsbn = new LibroDAO().getLibroIsbn(isbn);
        if (libroIsbn == null) {
            System.err.println("No se encontró un libro con el ISBN proporcionado.");
        }

        Ejemplar ejemplar = new Ejemplar(libroIsbn, estado);
        ejemplarDAO.insertarEjemplares(ejemplar);
        sincronizar();
    }

    //Metodo para saber cuantos ejemplares están en disponible
    public void numeroEjemplaresDisponibles() {
        int numeroDeEjemplares = (int) listaEjemplares.stream().filter(ejemplar -> "disponible".equalsIgnoreCase(ejemplar.getEstado())).count();
        System.out.println("Cantidad de ejemplares disponibles: " + numeroDeEjemplares);
        listaEjemplares.stream().filter(ejemplar -> "disponible".equalsIgnoreCase(ejemplar.getEstado())).forEach(System.out::println);
    }

}
