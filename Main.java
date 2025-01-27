package com.example;

import java.util.Scanner;

import com.hotel.datastore.DataStore;
import com.hotel.model.Gestor;
import com.hotel.model.Staff;
import com.hotel.model.Reserva;
import com.hotel.service.DataGenerator;
import com.hotel.service.GestorService;
import com.hotel.service.ReservaService;
import com.hotel.service.StaffService;
import com.hotel.service.UserService;

public class Main {
    public static void main(String[] args) {
        DataStore dataStore = DataStore.getInstance();
        Scanner scanner = new Scanner(System.in);

        // Generate and populate data
        DataGenerator.generateData();

        UserService userService = new UserService(dataStore);
        ReservaService reservaService = new ReservaService(dataStore);
        StaffService staffService = new StaffService(dataStore);
        GestorService gestorService = new GestorService(dataStore);

        while (true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1- Gestor");
            System.out.println("2- Staff");
            System.out.println("3- Hospede");
            System.out.println("4- Sair");

            int mainOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (mainOption) {
                case 1:
                    handleGestorOptions(scanner, gestorService, dataStore, reservaService);
                    break;
                case 2:
                    handleStaffOptions(scanner, staffService, reservaService);
                    break;
                case 3:
                    handleClientOptions(scanner, userService, reservaService);
                    break;
                case 4:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void handleGestorOptions(Scanner scanner, GestorService gestorService, DataStore dataStore, ReservaService reservaService) {
        Gestor gestor = new Gestor("David", "345678", 234567890, "david@example.com", "acc004", "password");

        while (true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1- Criar Quarto");
            System.out.println("2- Editar Quarto");
            System.out.println("3- Apagar Quarto");
            System.out.println("4- Listar Quartos");
            System.out.println("5- Reservar Quarto");
            System.out.println("6- Listar reservas");
            System.out.println("7- Registar manutenção");
            System.out.println("8- Listar manutenções concluídas");
            System.out.println("9- Listar quartos para manutenção");
            System.out.println("10- Registar ocupação de hospede");
            System.out.println("11- Fazer check-out de hospede");
            System.out.println("12- Voltar");

            int gestorOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (gestorOption) {
                case 1:
                    gestorService.criarQuarto(gestor);
                    break;
                case 2:
                    gestorService.editarQuarto(gestor);
                    break;
                case 3:
                    System.out.println("Insira o ID do quarto a apagar:");
                    String roomId = scanner.nextLine();
                    gestorService.removerQuarto(gestor, roomId);
                    break;
                case 4:
                    gestorService.listarQuartos();
                    break;
                case 5:
                    gestorService.reservarQuarto(gestor);
                    break;
                case 6:
                    gestorService.listarReservas();
                    break;
                case 7:
                    System.out.println("Insira o ID do quarto para manutenção:");
                    String quartoId = scanner.nextLine();

                    System.out.println("Insira o tipo de manutenção:");
                    String tipoManutencao = scanner.nextLine();

                    gestorService.registarManutencao(gestor, quartoId, tipoManutencao);
                    break;
                case 8:
                    gestorService.listarManutencoesConcluidas();
                    break;
                case 9:
                    gestorService.listarQuartosParaManutencao();
                    break;
                case 10:
                    System.out.println("Insira o ID da reserva para check-in:");
                    String reservaIdCheckIn = scanner.nextLine();
                    Reserva reservaCheckIn = reservaService.getReservaById(reservaIdCheckIn);
                    if (reservaCheckIn != null) {
                        reservaService.checkIn(reservaCheckIn);
                    } else {
                        System.out.println("Reserva não encontrada: " + reservaIdCheckIn);
                    }
                    break;
                case 11:
                    System.out.println("Insira o ID da reserva para check-out:");
                    String reservaIdCheckOut = scanner.nextLine();
                    Reserva reservaCheckOut = reservaService.getReservaById(reservaIdCheckOut);
                    if (reservaCheckOut != null) {
                        reservaService.checkOut(reservaCheckOut, gestor);
                    } else {
                        System.out.println("Reserva não encontrada: " + reservaIdCheckOut);
                    }
                    break;
                case 12:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void handleStaffOptions(Scanner scanner, StaffService staffService, ReservaService reservaService) {
        Staff staff = new Staff("Charlie", "789012", 456789123, "staff@example.com", "acc005", "password");

        while (true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1- Listar quartos");
            System.out.println("2- Reservar quarto");
            System.out.println("3- Listar reservas");
            System.out.println("4- Registar manutenção");
            System.out.println("5- Listar manutenções concluídas");
            System.out.println("6- Listar quartos para manutenção");
            System.out.println("7- Fazer check-in de hospede");
            System.out.println("8- Fazer check-out de hospede");
            System.out.println("9- Voltar");

            int staffOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (staffOption) {
                case 1:
                    staffService.listarQuartos();
                    break;
                case 2:
                    staffService.reservarQuarto(staff);
                    break;
                case 3:
                    staffService.listarReservas();
                    break;
                case 4:
                    System.out.println("Insira o ID do quarto para registar manutenção:");
                    String quartoId = scanner.nextLine();
                    System.out.println("Insira o tipo de manutenção:");
                    String tipoManutencao = scanner.nextLine();
                    staffService.registarManutencao(staff, quartoId, tipoManutencao);
                    break;
                case 5:
                    staffService.listarManutencoesConcluidas();
                    break;
                case 6:
                    staffService.listarQuartosParaManutencao();
                    break;
                case 7:
                    System.out.println("Insira o ID da reserva para check-in:");
                    String reservaIdCheckIn = scanner.nextLine();
                    Reserva reservaCheckIn = reservaService.getReservaById(reservaIdCheckIn);
                    if (reservaCheckIn != null) {
                        reservaService.checkIn(reservaCheckIn);
                    } else {
                        System.out.println("Reserva não encontrada: " + reservaIdCheckIn);
                    }
                    break;
                case 8:
                    System.out.println("Insira o ID da reserva para check-out:");
                    String reservaIdCheckOut = scanner.nextLine();
                    Reserva reservaCheckOut = reservaService.getReservaById(reservaIdCheckOut);
                    if (reservaCheckOut != null) {
                        reservaService.checkOut(reservaCheckOut, staff);
                    } else {
                        System.out.println("Reserva não encontrada: " + reservaIdCheckOut);
                    }
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void handleClientOptions(Scanner scanner, UserService userService, ReservaService reservaService) {
        while (true) {
            System.out.println("Selecione uma opção:");
            System.out.println("1- Check-In");
            System.out.println("2- Check-Out");
            System.out.println("3- Voltar");

            int clientOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (clientOption) {
                case 1:
                    System.out.println("Insira o ID da reserva para check-in:");
                    String reservaIdCheckIn = scanner.nextLine();
                    Reserva reservaCheckIn = reservaService.getReservaById(reservaIdCheckIn);
                    if (reservaCheckIn != null) {
                        reservaService.checkIn(reservaCheckIn);
                    } else {
                        System.out.println("Reserva não encontrada: " + reservaIdCheckIn);
                    }
                    break;
                case 2:
                    System.out.println("Insira o ID da reserva para check-out:");
                    String reservaIdCheckOut = scanner.nextLine();
                    Reserva reservaCheckOut = reservaService.getReservaById(reservaIdCheckOut);
                    if (reservaCheckOut != null) {
                        // Assuming the client is performing the check-out, we need to pass a Staff object
                        // Here we can create a dummy Staff object or handle it differently based on your requirements
                        Staff dummyStaff = new Staff("Client", "000000", 0, "client@example.com", "client", "password");
                        reservaService.checkOut(reservaCheckOut, dummyStaff);
                    } else {
                        System.out.println("Reserva não encontrada: " + reservaIdCheckOut);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}