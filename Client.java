package com.hotel.model;

import java.util.ArrayList;
import java.util.List;

public class Client extends User {

    private final List<String> historicoReservas; 
    
    public Client(String name, String document, int phone, String email, String accId, String password) {
        super(name, document, phone, email, accId, password);
        setRole("Client");
        this.historicoReservas = new ArrayList<>();
    }

    public List<String> getHistoricoReservas() {
        return historicoReservas;
    }

}