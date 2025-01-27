package com.hotel.model;

public class Staff extends User {
    
    public Staff(String name, String document, int phone, String email, String accId, String password) {
        super(name, document, phone, email, accId, password);
        setRole("Staff");
    }

    // No business logic methods here
}