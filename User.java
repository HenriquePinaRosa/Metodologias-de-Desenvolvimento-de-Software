package com.hotel.model;

public class User {
    private String name;
    private String document;
    private int phone;
    private String email; 
    private String username; 
    private String password;
    private String accId;
    private String role;

    public User(String name, String document, int phone, String email, String accId, String password) {
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.accId = accId;
    }

    public void listarReservas() {
        // Implementation for listing reservations
        System.out.println("Listing reservations.");
    }

    public void cancelarReserva() {
        // Implementation for canceling a reservation
        System.out.println("Reservation canceled.");
    }

    public void listarIncidentes() {
        // Implementation for listing incidents
        System.out.println("Listing incidents.");
    }

    public String getAccId() {
        return accId;
    }

    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}