package org.ed06.model;

public class Cliente {
    public static final int    NOMBRE_LONGITUD_MIN    = 3;
    public static final String REGEX_VALIDACION_EMAIL = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    public static final String REGEX_VALIDACION_DNI   = "[0-9]{8}[A-Z]";

    private int     id;
    private String  nombre;
    private String  dni;
    private String  email;
    private boolean esVip;

    public Cliente(int id, String nombre, String dni, String email, boolean esVip) {
        this.id = id;
        validarNombre(nombre);
        this.nombre = nombre;
        validarDni(dni);
        this.dni = dni;
        validarEmail(email);
        this.email = email;
        this.setEsVip(esVip);
    }

    public static boolean validarNombre(String nombre) {
        // Comprobamos que el nombre no sea nulo, esté vacio y tenga al menos 3 caracteres eliminando espacios inciales y finales
        if (nombre == null || nombre.trim().length() < NOMBRE_LONGITUD_MIN) {
            throw new IllegalArgumentException("El nombre no es válido");
        }
        return true;
    }

    public static boolean validarEmail(String email) {
        if (!email.matches(REGEX_VALIDACION_EMAIL)) {
            throw new IllegalArgumentException("El email no es válido");
        }
        return true;
    }

    public static boolean validarDni(String dni) {
        if (!dni.matches(REGEX_VALIDACION_DNI)) {
            throw new IllegalArgumentException("El DNI no es válido");
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEsVip() {
        return esVip;
    }

    public void setEsVip(boolean esVip) {
        this.esVip = esVip;
    }
}