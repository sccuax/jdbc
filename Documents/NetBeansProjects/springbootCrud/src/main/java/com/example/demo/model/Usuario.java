package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    
    @Column(name = "email", nullable= true, length = 255)
    private String email;
    
    @Column(name = "phone", nullable = false, length = 10)
    private String phone;
    
    // Constructor vacío (OBLIGATORIO para JPA)
    public Usuario() {}
    
    // Constructor con parámetros
    public Usuario(String nombre, String email, String phone) {
        this.nombre = nombre;
        this.email = email;
        this.phone = phone;
    }
    
    // inyectar y obtener informacion de usuarios.
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    // excepciones para obtener solo numeros del input telefono
    public void setPhone(String phone) {
        if (phone != null && !phone.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono debe contener solo números");
        }
        this.phone = phone;
    }
}