import ClasesEjemplar.*;
import ClasesUsuario.*;
import ClasesLibro.*;
import ClasesPrestamo.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EjemplarService ejemplarService = new EjemplarService();
        LibroService libroService = new LibroService();
        PrestamoService prestamoService = new PrestamoService();
        UsuarioService usuarioService = new UsuarioService();
        Scanner teclado = new Scanner(System.in);
        int a;

        do {
            System.out.println("1. Iniciar Sesión como Usuario");
            System.out.println("2. Iniciar Sesión como Administrador");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            a = teclado.nextInt();

            switch (a) {
                case 0 -> System.err.println("Abandonando Biblioteca");
                case 1 -> {
                    //Usuario
                    teclado.nextLine();
                    System.out.print("Ingrese su ID de Usuario: ");
                    if (usuarioService.isUsuario()) {
                        int opUsu;
                        do {
                            System.out.println("USUARIO");
                            System.out.println("1 > Listar préstamos");
                            System.out.println("2 > Listar datos usuarios");
                            System.out.println("0 > Salir");
                            System.out.print("OPCIÓN: ");
                            opUsu = teclado.nextInt();

                            switch (opUsu) {
                                case 1 -> prestamoService.listarPrestamosDeUsuario();
                                case 2 -> usuarioService.listarUsuario();
                            }
                        } while (opUsu != 0);
                    }
                }
                case 2 -> {
                    //Admin
                    if (usuarioService.isAdministrador()) {
                        int opAdm;
                        do {
                            System.out.println("ADMINISTRADOR");
                            System.out.println("1 > Registrar nuevo libro");
                            System.out.println("2 > Listar Libro");
                            System.out.println("3 > Eliminar Libro");
                            System.out.println("4 > Contar ejemplares disponibles");
                            System.out.println("5 > Registrar ejemplar");
                            System.out.println("6 > Listar Ejemplares");
                            System.out.println("7 > Crear Usuario");
                            System.out.println("8 > Penalizar Usuario");
                            System.out.println("9 > Registrar prestamo");
                            System.out.println("10 > Listar prestamos");
                            System.out.println("11 > Eliminar prestamo");
                            System.out.println("12 > Devolver prestamo");
                            System.out.println("0 > SALIR");
                            System.out.print("OPCION: ");
                            opAdm = teclado.nextInt();

                            switch (opAdm) {
                                case 1-> libroService.añadirLibro();
                                case 2 -> libroService.listarLibros();
                                case 3-> libroService.eliminarLibro();
                                case 4-> ejemplarService.numeroEjemplaresDisponibles();
                                case 5->ejemplarService.agregarEjemplar();
                                case 6->ejemplarService.listarEjemplar();
                                case 7->usuarioService.crearUsuario();
                                case 8-> usuarioService.penalizarUsuario();
                                case 9->prestamoService.registrarPrestamo();
                                case 10->prestamoService.listarPrestamos();
                                case 11->prestamoService.eliminarPrestamo();
                                case 12->prestamoService.registrarDevolucionPrestamo();
                            }
                        } while (opAdm != 0);
                    }
                }
                default -> System.err.println("Introduce una Opción Valida");
            }
        } while (a != 0);
    }
}
