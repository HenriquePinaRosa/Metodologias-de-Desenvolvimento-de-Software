package com.hotel.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.hotel.datastore.DataStore;
import com.hotel.model.Client;
import com.hotel.model.Quarto;
import com.hotel.model.Reserva;
import com.hotel.model.Staff;
import com.hotel.model.User;

public class StaffService {
    protected DataStore dataStore;

    public StaffService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void verificarManutencao(Staff staff) {
        // Logic to check maintenance
        System.out.println("Maintenance checked by staff: " + staff.getName());
    }

    public void registerClient(User user, Client client) {
        if (user.getRole().equals("Staff") || user.getRole().equals("Gestor")) {
            dataStore.addUser(client);
            System.out.println("Client registered by " + user.getRole() + ": " + user.getName());
        } else {
            System.out.println("Only Staff or Gestor can register a client.");
        }
    }

    public void reservarQuarto(Staff staff) {
        ReservaService reservaService = new ReservaService(dataStore);

        System.out.println("Insira o ID do cliente:");
        String clientId = System.console().readLine();

        System.out.println("Insira o número de pessoas:");
        int nPessoas = Integer.parseInt(System.console().readLine());

        System.out.println("Insira a data de check-in (yyyy-MM-dd):");
        String checkInStr = System.console().readLine();
        LocalDate checkInLocalDate = LocalDate.parse(checkInStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date checkIn = Date.valueOf(checkInLocalDate);

        System.out.println("Insira a data de check-out (yyyy-MM-dd):");
        String checkOutStr = System.console().readLine();
        LocalDate checkOutLocalDate = LocalDate.parse(checkOutStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date checkOut = Date.valueOf(checkOutLocalDate);

        System.out.println("Deseja verificar a sugestão do sistema? (S/N):");
        boolean verificarSugestao = System.console().readLine().equalsIgnoreCase("S");

        List<String> quartoIds = new ArrayList<>();

        if (verificarSugestao) {
            System.out.println("Insira o tipo de vista desejada (ou deixe em branco se não importa):");
            String vista = System.console().readLine();

            System.out.println("Precisa de cozinha? (S/N ou deixe em branco se não importa):");
            String cozinhaStr = System.console().readLine();
            Boolean cozinha = cozinhaStr.isEmpty() ? null : cozinhaStr.equalsIgnoreCase("S");

            System.out.println("Precisa de varanda? (S/N ou deixe em branco se não importa):");
            String varandaStr = System.console().readLine();
            Boolean varanda = varandaStr.isEmpty() ? null : varandaStr.equalsIgnoreCase("S");

            System.out.println("Deseja um único quarto para todas as pessoas? (S/N):");
            boolean unicoQuarto = System.console().readLine().equalsIgnoreCase("S");

            if (unicoQuarto) {
                quartoIds = reservaService.sugerirQuartoUnico(nPessoas, checkIn, checkOut, vista, cozinha, varanda);
            } else {
                quartoIds = reservaService.sugerirMultiplosQuartos(nPessoas, checkIn, checkOut, vista, cozinha, varanda);
            }

            if (quartoIds.isEmpty()) {
                System.out.println("Nenhum quarto disponível atende às especificações.");
                return;
            }

            System.out.println("Quartos sugeridos: " + String.join(", ", quartoIds));
            System.out.println("Aceitar sugestão? (S/N):");
            if (!System.console().readLine().equalsIgnoreCase("S")) {
                System.out.println("Reserva não realizada.");
                return;
            }
        } else {
            listarQuartos();
            System.out.println("Insira os IDs dos quartos separados por vírgula:");
            String quartosInput = System.console().readLine();
            String[] quartosArray = quartosInput.split(",");
            for (String quartoId : quartosArray) {
                quartoIds.add(quartoId.trim());
            }
        }

        Reserva reserva = new Reserva(clientId, nPessoas, checkIn, checkOut, quartoIds);
        reservaService.addReserva(reserva);
        System.out.println("Reserva adicionada pelo staff: " + staff.getName());
    }

    public void listarQuartos() {
        System.out.println(String.format("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s",
            "Room ID", "Beds", "Toilets", "View", "Available", "Kitchen", "Balcony", "Capacity"));
        System.out.println("--------------------------------------------------------------------------------");

        dataStore.getQuartos().forEach(quarto -> {
            System.out.println(String.format("%-10s %-10d %-10s %-10s %-10b %-10b %-10b %-10d",
                quarto.getQuartoId(), quarto.getNCamas(), quarto.getNToilet(), quarto.getVista(),
                quarto.isDisponivel(), quarto.isCozinha(), quarto.isVaranda(), quarto.getCapacidade()));
        });
    }

    public void listarReservas() {
        System.out.println(String.format("%-15s %-15s %-15s %-15s %-15s %-15s",
            "Reserva ID", "Client ID", "Check-In", "Check-Out", "Estado", "Quartos"));
        System.out.println("--------------------------------------------------------------------------------");

        dataStore.getReservas().forEach(reserva -> {
            System.out.println(String.format("%-15s %-15s %-15s %-15s %-15s %-15s",
                reserva.getReservaId(), reserva.getClientId(), reserva.getCheckIn(), reserva.getCheckOut(),
                reserva.getEstado(), String.join(", ", reserva.getQuartoIds())));
        });
    }

    public void registarManutencao(Staff staff, String quartoId, String tipoManutencao) {
        Quarto quarto = dataStore.findQuartoById(quartoId);
        if (quarto != null) {
            quarto.setTipoManutencao(tipoManutencao);
            quarto.setEstadoManutencao("Em andamento");
            quarto.setNeedsMaintenance(true);
            dataStore.updateQuarto(quarto);
            System.out.println("Maintenance registered by staff: " + staff.getName() + " for room: " + quartoId + " with type: " + tipoManutencao);
        } else {
            System.out.println("Room not found: " + quartoId);
        }
    }

    public void listarQuartosParaManutencao() {
        System.out.println("Rooms needing maintenance:");
        dataStore.getQuartos().stream()
            .filter(Quarto::isNeedsMaintenance)
            .forEach(quarto -> {
                System.out.println("Room ID: " + quarto.getQuartoId() + ", Maintenance Type: " + quarto.getTipoManutencao() + ", Status: " + quarto.getEstadoManutencao());
            });
    }

    public void listarManutencoesConcluidas() {
        System.out.println("Completed maintenance:");
        dataStore.getQuartos().stream()
            .filter(quarto -> !quarto.isNeedsMaintenance() && "Concluída".equals(quarto.getEstadoManutencao()))
            .forEach(quarto -> {
                System.out.println("Room ID: " + quarto.getQuartoId() + ", Maintenance Type: " + quarto.getTipoManutencao());
            });
    }

    public void listarManutencoesEmAndamento() {
        System.out.println("Ongoing maintenance:");
        dataStore.getQuartos().stream()
            .filter(quarto -> quarto.isNeedsMaintenance() && "Em andamento".equals(quarto.getEstadoManutencao()))
            .forEach(quarto -> {
                System.out.println("Room ID: " + quarto.getQuartoId() + ", Maintenance Type: " + quarto.getTipoManutencao());
            });
    }

    public void concluirManutencao(String quartoId) {
        Quarto quarto = dataStore.findQuartoById(quartoId);
        if (quarto != null && quarto.isNeedsMaintenance()) {
            quarto.setNeedsMaintenance(false);
            quarto.setEstadoManutencao("Concluída");
            quarto.setDisponivel(true); // Marcar o quarto como disponível novamente
            dataStore.updateQuarto(quarto);
            System.out.println("Maintenance completed for room: " + quartoId);
        } else {
            System.out.println("Room not found or no maintenance needed: " + quartoId);
        }
    }

    // Additional staff-specific methods
}