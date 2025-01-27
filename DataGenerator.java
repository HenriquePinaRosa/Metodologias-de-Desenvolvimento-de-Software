package com.hotel.service;

import java.time.LocalDate;
import java.util.Arrays;

import com.hotel.datastore.DataStore;
import com.hotel.model.Client;
import com.hotel.model.Gestor;
import com.hotel.model.Quarto;
import com.hotel.model.Reserva;
import com.hotel.model.Staff;

public class DataGenerator {

    public static void generateData() {
        DataStore dataStore = DataStore.getInstance();
        UserService userService = new UserService(dataStore);
        QuartoService roomService = new QuartoService();
        ReservaService reservationService = new ReservaService(dataStore);

        // Add users
        Client client1 = new Client("Alice", "123456", 123456789, "alice@example.com", "acc001", "password");
        System.out.println("Client added: " + client1.getAccId());
        Client client2 = new Client("Bob", "654321", 987654321, "bob@example.com", "acc002", "password");
        Staff staff1 = new Staff("Charlie", "789012", 456789123, "charlie@example.com", "acc003", "password");
        Gestor gestor1 = new Gestor("David", "345678", 234567890, "david@example.com", "acc004", "password");

        userService.addUser(client1);
        userService.addUser(client2);
        userService.addUser(staff1);
        userService.addUser(gestor1);
 
        // Add rooms
        Quarto quarto1 = new Quarto( 2, 1, "Deluxe", true, true, 4);
        Quarto quarto2 = new Quarto( 1, 1, "Standard", true, true, 2);
        Quarto quarto4 = new Quarto( 1, 1, "Standard", true, true, 4);
        Quarto quarto3 = new Quarto( 3, 2, "Suite", true, true, 6);

        roomService.addQuarto(quarto1);
        roomService.addQuarto(quarto2);
        roomService.addQuarto(quarto3);
        roomService.addQuarto(quarto4);

        // Add reservations
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        Reserva reserva1 = new Reserva("acc001", 2, java.sql.Date.valueOf(today), java.sql.Date.valueOf(tomorrow), Arrays.asList(quarto1.getQuartoId()));
        Reserva reserva2 = new Reserva("acc002",1 ,java.sql.Date.valueOf(today), java.sql.Date.valueOf(tomorrow), Arrays.asList(quarto2.getQuartoId()));
        Reserva reserva3 = new Reserva("acc001",3 ,java.sql.Date.valueOf(today), java.sql.Date.valueOf(tomorrow), Arrays.asList(quarto3.getQuartoId()));

        reservationService.addReserva(reserva1);
        reservationService.addReserva(reserva2);
        reservationService.addReserva(reserva3);
    }
}