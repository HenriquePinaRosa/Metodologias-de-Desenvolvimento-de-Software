package com.hotel.model;

public class Gestor extends Staff {
    
    public Gestor(String name, String document, int phone, String email, String accId, String password) { 
        super(name, document, phone, email, accId, password);
        setRole("Gestor");
    }

}