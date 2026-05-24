package org.ed06.app;

import org.ed06.model.Cliente;
import org.ed06.model.Habitacion;
import org.ed06.model.Hotel;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    // Definimos constantes para las diferentes opciones del menú
    private static final int REGISTRAR_HABITACION = 1;
    private static final int LISTAR_HABITACIONES_DISPONIBLES = 2;
    private static final int RESERVAR_HABITACION = 11;
    private static final int LISTAR_RESERVAS = 12;
    private static final int LISTAR_CLIENTES = 21;
    private static final int REGISTRAR_CLIENTE = 22;
    private static final int SALIR = 0;

    public static void main(String[] args) {
        // Creamos un menú para el administrador con las diferentes opciones proporcionadas
        Hotel hotel = new Hotel("El mirador", "Calle Entornos de Desarrollo 6", "123456789");

        Hotel.cargaDeDatos(hotel);

        // Mostramos el menú
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case REGISTRAR_HABITACION -> registrarHabitacion(hotel);
                case LISTAR_HABITACIONES_DISPONIBLES -> hotel.listarHabitacionesDisponibles();
                case RESERVAR_HABITACION -> reservarHabitacion(hotel);
                case LISTAR_RESERVAS -> hotel.listarReservas();
                case LISTAR_CLIENTES -> hotel.listarClientes();
                case REGISTRAR_CLIENTE -> registrarCliente(hotel);
                case SALIR -> {
                    salir();
                    return;
                }
                default -> System.out.println("Opción no válida");
            }
        }
    }

    private static void salir() {
        System.out.println("Saliendo del programa...");
        scanner.close();
    }

    private static void registrarCliente(Hotel hotel) {
        String nombre = pedirNombre();
        String email = pedirEmail();
        String dni = pedirDni();
        System.out.println("¿Es VIP? (true/false): ");
        boolean esVip = scanner.nextBoolean();
        hotel.registrarCliente(nombre, email, dni, esVip);
    }

    private static String pedirDni() {
        String dni;
        while (true) {
            try {
                System.out.println("Introduce el DNI del cliente: ");
                dni = scanner.next();
                Cliente.validarDni(dni);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("DNI no válido. Inténtalo de nuevo.");
            }
        }
        return dni;
    }

    private static String pedirEmail() {
        String email;
        while (true) {
            try {
                System.out.println("Introduce el email del cliente: ");
                email = scanner.next();
                Cliente.validarEmail(email);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Email no válido. Inténtalo de nuevo.");
            }
        }
        return email;
    }

    private static String pedirNombre() {
        String nombre;
        while (true) {
            try {
                System.out.println("Introduce el nombre del cliente: ");
                nombre = scanner.next();
                Cliente.validarNombre(nombre);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Nombre no válido. Inténtalo de nuevo.");
            }
        }
        return nombre;
    }

    private static void reservarHabitacion(Hotel hotel) {
        String tipo;
        System.out.println("Introduce el id del cliente: ");
        int clienteId = scanner.nextInt();

        System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
        tipo = scanner.next();

        LocalDate fechaEntrada = pedirFecha(
                "Introduce la fecha de entrada (año): ",
                "Introduce la fecha de entrada (mes): ",
                "Introduce la fecha de entrada (día): ");

        LocalDate fechaSalida = pedirFecha(
                "Introduce la fecha de salida (año): ",
                "Introduce la fecha de salida (mes): ",
                "Introduce la fecha de salida (día): ");

        int numeroHabitacion = hotel.reservarHabitacion(clienteId, tipo, fechaEntrada,
                fechaSalida);
        System.out.println("Datos de la habitacion");
        Habitacion habitacion = hotel.getHabitacion(numeroHabitacion);
        System.out.println(
                "Habitación #" + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo()
                        + " - Precio base: " + habitacion.getPrecioBase());
        System.out.println("Número de habitación reservada: " + numeroHabitacion);
    }

    private static LocalDate pedirFecha(String peticionAnio, String peticionMes, String peticionDia) {
        System.out.println(peticionAnio);
        int anio = scanner.nextInt();
        scanner.nextLine();

        System.out.println(peticionMes);
        int mes = scanner.nextInt();
        scanner.nextLine();

        System.out.println(peticionDia);
        int dia = scanner.nextInt();
        scanner.nextLine();

        return LocalDate.of(anio, mes, dia);
    }

    private static void registrarHabitacion(Hotel hotel) {
        String tipo;
        System.out.println("Introduce el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
        tipo = scanner.nextLine();
        System.out.println("Introduce el precio base de la habitación: ");
        double precioBase = scanner.nextDouble();
        scanner.nextLine();
        hotel.registrarHabitacion(tipo, precioBase);
        System.out.println("Habitación registrada: " + tipo + " - Precio base: " + precioBase);
    }

    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Registrar habitación");
        System.out.println("2. Listar habitaciones disponibles");
        System.out.println("11. Reservar habitación");
        System.out.println("12. Listar reservas");
        System.out.println("21. Listar clientes");
        System.out.println("22. Registrar cliente");
        System.out.println("0. Salir");
    }
}