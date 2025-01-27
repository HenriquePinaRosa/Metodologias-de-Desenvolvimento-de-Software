package com.hotel.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hotel.model.Client;
import com.hotel.model.Gestor;
import com.hotel.model.Quarto;
import com.hotel.model.Reserva;
import com.hotel.model.Staff;
import com.hotel.model.User;

public class DataStore {
    private static DataStore instance;
    private final List<User> users;
    private final List<Quarto> quartos;
    private final List<Reserva> reservas;

    private DataStore() {
        users = new ArrayList<>();
        quartos = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // User management methods
    public synchronized void addUser(User user) {
        users.add(user);
    }

    public synchronized List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public synchronized List<Staff> getStaffMembers() {
        return users.stream()
                .filter(user -> user instanceof Staff)
                .map(user -> (Staff) user)
                .collect(Collectors.toList());
    }

    public synchronized List<Gestor> getGestores() {
        return users.stream()
                .filter(user -> user instanceof Gestor)
                .map(user -> (Gestor) user)
                .collect(Collectors.toList());
    }

    public synchronized List<Client> getClients() {
        return users.stream()
                .filter(user -> user instanceof Client)
                .map(user -> (Client) user)
                .collect(Collectors.toList());
    }

    public synchronized User findUserByAccId(String accId) {
        return users.stream()
                .filter(user -> user.getAccId().equals(accId))
                .findFirst()
                .orElse(null);
    }

    public synchronized void updateUser(User updatedUser) {
        User existingUser = findUserByAccId(updatedUser.getAccId());
        if (existingUser != null) {
            users.remove(existingUser);
            users.add(updatedUser);
        }
    }

    public synchronized void removeUser(User user) {
        users.remove(user);
    }

    // Room management methods
    public synchronized void addQuarto(Quarto quarto) {
        quartos.add(quarto);
    }

    public synchronized List<Quarto> getQuartos() {
        return new ArrayList<>(quartos);
    }

    public synchronized Quarto findQuartoById(String quartoId) {
        return quartos.stream()
                .filter(quarto -> quarto.getQuartoId().equals(quartoId))
                .findFirst()
                .orElse(null);
    }

    public synchronized void updateQuarto(Quarto updatedQuarto) {
        Quarto existingQuarto = findQuartoById(updatedQuarto.getQuartoId());
        if (existingQuarto != null) {
            quartos.remove(existingQuarto);
            quartos.add(updatedQuarto);
        }
    }

    public synchronized void removeQuarto(Quarto quarto) {
        quartos.remove(quarto);
    }

    public synchronized List<Quarto> getQuartosNeedingMaintenance() {
        return quartos.stream()
                .filter(Quarto::isNeedsMaintenance)
                .collect(Collectors.toList());
    }

    // Reservation management methods
    public synchronized void addReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public synchronized List<Reserva> getReservas() {
        return new ArrayList<>(reservas);
    }

    public synchronized Reserva findReservaById(String reservaId) {
        return reservas.stream()
                .filter(reserva -> reserva.getReservaId().equals(reservaId))
                .findFirst()
                .orElse(null);
    }

    public synchronized void updateReserva(Reserva updatedReserva) {
        Reserva existingReserva = findReservaById(updatedReserva.getReservaId());
        if (existingReserva != null) {
            reservas.remove(existingReserva);
            reservas.add(updatedReserva);
        }
    }

    public synchronized void removeReserva(Reserva reserva) {
        reservas.remove(reserva);
    }

    // Utility methods
    public synchronized void clearAllData() {
        users.clear();
        quartos.clear();
        reservas.clear();
    }

    public synchronized void printSummary() {
        System.out.println("Users:");
        users.forEach(user -> System.out.println(user.getName()));
        System.out.println("Rooms:");
        quartos.forEach(quarto -> System.out.println(quarto.getQuartoId()));
        System.out.println("Reservations:");
        reservas.forEach(reserva -> System.out.println(reserva.getReservaId()));
        System.out.println("Rooms Needing Maintenance:");
        getQuartosNeedingMaintenance().forEach(quarto -> System.out.println(quarto.getQuartoId()));
    }
}