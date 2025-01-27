package com.hotel.service;

import com.hotel.datastore.DataStore;

public class ClientService {
    private final DataStore dataStore;

    public ClientService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void sugerirQuarto(String roomDetails) {
        // Logic to suggest a room
        System.out.println("Room suggested with details: " + roomDetails);
    }

    // Additional client-specific methods
}