package com.hotel.service;

import java.util.List;

import com.hotel.datastore.DataStore;
import com.hotel.model.User;

public class UserService {
    private final DataStore dataStore;

    public UserService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void addUser(User user) {
        dataStore.addUser(user);
        System.out.println("User added: " + user.getName());
    }

    public User getUserById(String accId) {
        return dataStore.getUsers().stream()
                .filter(user -> user.getAccId().equals(accId))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return dataStore.getUsers();
    }

    public void updateUser(User user) {
        User existingUser = getUserById(user.getAccId());
        if (existingUser != null) {
            dataStore.removeUser(existingUser);
            dataStore.addUser(user);
            System.out.println("User updated: " + user.getName());
        } else {
            System.out.println("User not found: " + user.getAccId());
        }
    }

    public void deleteUser(String accId) {
        User user = getUserById(accId);
        if (user != null) {
            dataStore.removeUser(user);
            System.out.println("User deleted: " + user.getName());
        } else {
            System.out.println("User not found: " + accId);
        }
    }

    public void checkIn() {
        
    }
    
    /*public void checkOut(Reserva reserva, ManutencaoService reservaService) {
        // Call the checkOut method from ReservaService
        reservaService.checkOut(reserva);

        // Call the registarManutencao method from ReservaService
        reservaService.registarManutencao(reserva);
    }*/

    // Additional common user methods
}