package com.example.olgacoll.sifu.model;

import android.widget.ImageView;

import java.util.List;

/**
 * Created by olgacoll on 11/5/17.
 */

public class Incidencia {

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private List<String> provincia;
    private String cliente;
    private List<ImageView> imagen;
    private String comentarios;

    public Incidencia(){

    }

    public Incidencia(String nombre, String apellidos, String email, String telefono, List<String> provincia, String cliente, List<ImageView> imagen, String comentarios) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.provincia = provincia;
        this.cliente = cliente;
        this.imagen = imagen;
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

    public List<String> getProvincia() {
        return provincia;
    }

    public void setProvincia(List<String> provincia) {
        this.provincia = provincia;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<ImageView> getImagen() {
        return imagen;
    }

    public void setImagen(List<ImageView> imagen) {
        this.imagen = imagen;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", provincia=" + provincia +
                ", cliente='" + cliente + '\'' +
                ", imagen=" + imagen +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
}

