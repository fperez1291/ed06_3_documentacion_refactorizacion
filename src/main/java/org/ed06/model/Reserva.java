package org.ed06.model;

import java.time.LocalDate;

public class Reserva {
    public static final int LIM_DIAS_RESERVA_SIN_DESCUENTO_ADICIONAL = 7;
    public static final double MULTIPLICADOR_PRECIO_FINAL_CON_DESCUENTO_ADICIONAL = 0.95;
    private int        id;
    private Habitacion habitacion;
    private Cliente    cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double    precioTotal;

    public Reserva(int id, Habitacion habitacion, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = calcularPrecioFinal();
    }

    public int getId() {
        return id;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * Calcula el precio total de la reserva. Para calcular el precio total, se debe calcular el precio base de la
     * habitación por el número de noches de la reserva. En el caso de que el cliente sea vip, se aplicará un descuento
     * del 10%. Además, si el intervalo de fechas es mayor a 7 días, se aplicará un descuento adicional del 5%.
     *
     * @return precio total de la reserva
     */
    public double calcularPrecioFinal() {
        //calculamos los días de la reserva
        int numDiasReserva = fechaFin.getDayOfYear() - fechaInicio.getDayOfYear();
        // Calculamos el precio base de la habitación por el número de noches de la reserva
        double precioBase = habitacion.getPrecioBase() * numDiasReserva;
        double precioFinal = precioBase;

        precioFinal = cliente.aplicarDescuentoClienteVIP(precioFinal);

        precioFinal = aplicarDescuentoAdicional(numDiasReserva, precioFinal);

        return precioFinal;
    }

    private static double aplicarDescuentoAdicional(int numDiasReserva, double precioFinal) {
        // Si el intervalo de fechas es mayor a 7 días, aplicamos un descuento adicional del 5%
        if (numDiasReserva > LIM_DIAS_RESERVA_SIN_DESCUENTO_ADICIONAL) {
            precioFinal *= MULTIPLICADOR_PRECIO_FINAL_CON_DESCUENTO_ADICIONAL;
        }
        return precioFinal;
    }

    public void mostrarReserva() {
        System.out.println("Reserva #" + id);
        habitacion.mostrarHabitacion();
        cliente.mostrarCliente();
        System.out.println("Fecha de inicio: " + fechaInicio);
        System.out.println("Fecha de fin: " + fechaFin);
        System.out.printf("Precio total: %.2f €\n", precioTotal);
    }

}
