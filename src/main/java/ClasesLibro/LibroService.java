package ClasesLibro;

import ClasesLibro.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibroService {
    Scanner teclado = new Scanner(System.in);
    List<Libro> listaLibros = new ArrayList<>();
    LibroDAO libroDAO;

    public LibroService() {
        this.libroDAO = new LibroDAO();
        sincronizar();
    }

    public void sincronizar() {
        listaLibros = libroDAO.listarLibros();
    }
    public void listarLibros() {
        sincronizar();
        listaLibros.forEach(System.out::println);
    }

    public void añadirLibro() {
        //"Método" para evitar agregar Libros no deseados
        //Variables
        int opcion;
        String ISBN, titulo, autor;
        Libro l;
        //Bucle del que sale solo si el usuario está conforme con los datos introducidos
        do {
            System.out.println("Introduce ISBN del libro: ");
            ISBN = teclado.nextLine().trim();
            System.out.println("Introduce titulo del libro: ");
            titulo = teclado.nextLine().trim();
            System.out.println("Introduce autor del libro: ");
            autor = teclado.nextLine().trim();
            l = new Libro(ISBN, titulo, autor);
            System.out.println("Quieres agregar el siguiente Libro? 0 = SI - 1 = NO" + l);
            opcion = teclado.nextInt();
        } while (opcion != 0);
        //Se manda el libro a la bdd
        libroDAO.insertarLibro(l);
    }

    public void eliminarLibro() {
        System.out.print("Introduce el ISBN: ");
        String isbn = teclado.nextLine().trim();
        Libro libro = libroDAO.getLibroIsbn(isbn);
        if (libro != null) {
            libroDAO.eliminarLibro(libro);
            System.out.println("Libro eliminado");
        } else {
            System.out.println("No se encontró un libro con el ISBN proporcionado.");
        }
    }
}
