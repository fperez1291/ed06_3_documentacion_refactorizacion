package org.ed06.model;

import java.time.LocalDate;
import java.util.*;

public class Hotel {
    private String nombre;
    private String direccion;
    private String telefono;

    private final Map<Integer,Cliente> clientes = new HashMap<>();
    private final List<Habitacion> habitaciones = new ArrayList<>();
    private final Map<Integer,List<Reserva>> reservasPorHabitacion = new HashMap<>();

    public Hotel(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Método para agregar una nueva habitación al hotel
    public void registrarHabitacion(String tipo, double precioBase) {
        Habitacion habitacion = new Habitacion(habitaciones.size() + 1, tipo, precioBase);
        habitaciones.add(habitacion);
        reservasPorHabitacion.put(habitacion.getNumero(), new ArrayList<>());
    }

    public void registrarHabitaciones(List<String> tipos, List<Double> preciosBase) {
        for(int i = 0; i < tipos.size(); i++) {
            String tipo = tipos.get(i);
            double precioBase = preciosBase.get(i);
            registrarHabitacion(tipo, precioBase);
        }
    }

    public void listarHabitacionesDisponibles() {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.isDisponible()) {
                habitacion.mostrarHabitacion();
            }
        }
    }

    public Habitacion getHabitacion(int numero) {
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        return null;
    }

    /**
     * Método para realizar una reserva.
     * Comprueba si hay habitaciones disponibles, si existe el cliente y si las fechas son coherentes.
     * Si encuentra una habitación disponible del tipo solicitado,
     * crea una nueva reserva y la añade a la lista de reservas y devuelve el número de la habitación reservada.
     * Antes de crear la reserva, comprueba si el cliente pasa a ser VIP tras la nueva reserva,
     * en caso de que haya realizado más de 3 reservas en el último año.
     */
    public int reservarHabitacion(int clienteId, String tipo, LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones en el hotel");
            return -4;
        }

        if (this.clientes.get(clienteId) == null) {
            System.out.println("No existe el cliente con id " + clienteId);
            return -3;
        }

        Cliente cliente = this.clientes.get(clienteId);
        if (!fechaEntrada.isBefore(fechaSalida)) {
            System.out.println("La fecha de entrada es posterior a la fecha de salida");
            return -2;
        }

        //buscamos una habitación disponible
        for(Habitacion habitacion : habitaciones) {
            if(habitacion.getTipo().equals(tipo.toUpperCase()) && habitacion.isDisponible()) {
                // Comprobamos si el cliente pasa a ser vip tras la nueva reserva
                int numReservas = obtenerNumReservasUltimaAnualidad(cliente);
                actualizarEstadoClienteVIP(numReservas, cliente);

                // Creamos la reserva
                Reserva reserva = new Reserva(reservasPorHabitacion.size() + 1, habitacion, cliente, fechaEntrada, fechaSalida);
                reservasPorHabitacion.get(habitacion.getNumero()).add(reserva);
                // Marcamos la habitación como no disponible
                habitacion.reservar();

                System.out.println("Reserva realizada con éxito");
                return habitacion.getNumero();
            }
        }
        System.out.println("No hay habitaciones disponibles del tipo " + tipo);
        return -1;

    }

    private static void actualizarEstadoClienteVIP(int numReservas, Cliente cliente) {
        if(numReservas > 3 && !cliente.isEsVip()) {
            cliente.setEsVip(true);
            System.out.println("El cliente " + cliente.getNombre() + " ha pasado a ser VIP");
        }
    }

    private int obtenerNumReservasUltimaAnualidad(Cliente cliente) {
        int numReservas = 0;
        for (List<Reserva> reservasHabitacion : reservasPorHabitacion.values()) {
            for(Reserva reservaCliente : reservasHabitacion) {
                if(reservaCliente.getCliente().equals(cliente)) {
                    if(reservaCliente.getFechaInicio().isAfter(LocalDate.now().minusYears(1))) {
                        numReservas++;
                    }
                }
            }
        }
        return numReservas;
    }

    public void listarReservas() {
        reservasPorHabitacion.forEach((key, value) -> {
            System.out.println("Habitación #" + key);
            value.forEach(reserva -> System.out.println(
                    reserva.toString()));
        });
    }

    public void listarClientes() {
        for(Cliente cliente : clientes.values()) {
            cliente.mostrarDatosCliente();
        }
    }

    public void registrarCliente(String nombre, String email, String dni, boolean esVip) {
        Cliente cliente = new Cliente(clientes.size() + 1, nombre, dni, email, esVip);
        clientes.put(cliente.getId(), cliente);
    }
}
