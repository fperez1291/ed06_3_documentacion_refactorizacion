package org.ed06.model;

public class Habitacion {
    private int numero;
    private String tipo; // "SIMPLE", "DOBLE", "SUITE"
    private double precioBase;

    //Todo pendiente cambiar la forma de gestionar la disponibilidad en base a las fechas de las reservas
    private boolean disponible;

    public Habitacion(int numero, String tipo, double precioBase) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.disponible = true;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void reservar() {
        if (disponible) {
            System.out.println("Habitación #" + numero + " ya reservada");
        }
        disponible = true;
    }

    void mostrarHabitacion() {
        System.out.println("Habitación #" + getNumero() + " - Tipo: " + getTipo() + " - Precio base: " + getPrecioBase());
    }
}
