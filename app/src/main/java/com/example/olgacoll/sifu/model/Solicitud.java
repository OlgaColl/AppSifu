package com.example.olgacoll.sifu.model;

/**
 * Created by olgacoll on 11/5/17.
 */

public class Solicitud {

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String provincia;
    private String comentarios;

    public Solicitud(){

    }

    public Solicitud(String nombre, String apellidos, String email, String telefono, String provincia, String comentarios) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.provincia = provincia;
        this.comentarios = comentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", provincia=" + provincia +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
}


