package ClasesUsuario;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsuarioService {
    Scanner teclado = new Scanner(System.in);
    List<Usuario> listaUsuarios = new ArrayList<>();
    UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        sincronizar();
    }

    public void sincronizar() {
        listaUsuarios = usuarioDAO.listarUsuarios();
    }
    public void listarUsuario() {
        System.out.println("Introduce id del usuario: ");
        int idUsuario = teclado.nextInt();
        listaUsuarios.stream().filter(usuario -> usuario.getId() == idUsuario).forEach(System.out::println);
    }

    public void crearUsuario() {
        int opcion;
        Usuario u;
        //Comprobación similar a la de Libro pero con Usuario
        do {
        System.out.println("Introduce el DNI del usuario: ");
        String dni = teclado.nextLine().trim();
        System.out.println("Introduce el nombre del usuario: ");
        String nombre = teclado.nextLine().trim();
        System.out.println("Introduce el email del usuario: ");
        String email = teclado.nextLine().trim();
        System.out.println("Introduce la contraseña del usuario: ");
        String contrasena = teclado.nextLine().trim();
        System.out.println("Introduce el tipo de usuario (1 > Normal 2 > Administrador): ");
        int tipo = teclado.nextInt();
        String estado = "";
        switch (tipo){
            case 1 -> estado = "Normal";
            case 2 -> estado = "Administrador";
            default -> System.err.println("Tipo no válido. Operación cancelada.");

        }
        u = new Usuario(dni, nombre, email, contrasena, estado);
        System.out.println("Quieres agregar el siguiente Usuario? 0 = SI - 1 = NO" + u);
        opcion = teclado.nextInt();
        } while (opcion != 0);
        usuarioDAO.insertarUsuario(u);
    }

    public boolean isAdministrador() {
        System.out.println("Ingrese su id: ");
        int id = teclado.nextInt();
        System.out.println("Introduce su contraseña: ");
        teclado.nextLine(); //Consumir salto de linea
        String contraseña = teclado.nextLine().trim();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId() == id) {
                //Usuario encontrado, verificamos contraseña
                if (usuario.getPassword().equals(contraseña)) {
                    if (usuario.getTipo().equalsIgnoreCase("administrador")) {
                        System.out.println("Inicio de sesión correcto");
                        return true;  //Es admin
                    } else {
                        System.out.printf("El Usuario %s no es administrador", usuario.getId());
                        return false;  //No es admin
                    }
                } else {
                    System.err.println("Contraseña incorrecta");
                    return false;  // Contraseña incorrecta
                }
            }

        }
        throw new RuntimeException("El usuario con ese id no fue encontrado");
    }


    public boolean isUsuario() {
        System.out.println("Ingrese su id: ");
        int id = teclado.nextInt();
        System.out.println("Introduce su contraseña: ");
        teclado.nextLine(); //Consumir salto de línea
        String contraseña = teclado.nextLine();

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId() == id) {
                //Usuario encontrado, verificamos contraseña
                if (usuario.getPassword().equals(contraseña)) {
                    //Verificamos si es un usuario "normal"
                    if (usuario.getTipo().equalsIgnoreCase("normal")) {
                        System.out.println("Inicio de sesión correcto");
                        return true; //Es "normal"
                    } else {
                        System.out.printf("El Usuario %s no es normal", usuario.getId());
                        return false; //No es "normal"
                    }
                } else {
                    System.err.println("Contraseña incorrecta");
                    return false; //Contraseña incorrecta
                }
            }
        }
        throw new RuntimeException("El usuario con ese id no fue encontrado");
    }

    public void penalizarUsuario() {
        System.out.println("Introduce el id del usuario que quieres penalizar: ");
        int id = teclado.nextInt();
        Usuario u = usuarioDAO.getUsuarioId(id);
        if (u != null) {
            u.setPenalizacionHasta(LocalDate.now().plusDays(15));
            usuarioDAO.actualizarUsuario(u);
            System.out.printf("Usuario %s penalizado hasta %s", u.getId(), LocalDate.now().plusDays(15));
        } else {
            System.err.println("Usuario nulo");
        }
    }

}
