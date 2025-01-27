package com.hotel.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.hotel.datastore.DataStore;
import com.hotel.model.Quarto;
import com.hotel.model.Reserva;
import com.hotel.model.Staff;

public class QuartoService {
    private final DataStore dataStore;

    public QuartoService() {
        this.dataStore = DataStore.getInstance();
    }

    public void addQuarto(Quarto quarto) {
        dataStore.addQuarto(quarto);
        System.out.println("Room added: " + quarto.getQuartoId());
    }

    public List<Quarto> getAllQuartos() {
        return dataStore.getQuartos(); 
    }

    public Quarto getQuartoById(String quartoId) {
        Quarto quarto = dataStore.findQuartoById(quartoId);
        if (quarto != null) {
            System.out.println("Room found: " + quarto.getQuartoId());
        } else {
            System.out.println("Room not found: " + quartoId);
        }
        return quarto;
    }

    public void removeQuarto(Quarto quarto) {
        dataStore.removeQuarto(quarto);
        System.out.println("Room removed: " + quarto.getQuartoId());
    }

    public void editarQuarto(String roomId, int n_camas, int n_toilet, String vista, boolean cozinha, boolean varanda, int capacidade) {
        Quarto quarto = dataStore.findQuartoById(roomId);
        if (quarto != null) {
            quarto.setNCamas(n_camas);
            quarto.setNToilet(n_toilet);
            quarto.setVista(vista);
            quarto.setCozinha(cozinha);
            quarto.setVaranda(varanda);
            quarto.setCapacidade(capacidade);
            System.out.println("Room " + roomId + " edited.");
        } else {
            System.out.println("Room not found: " + roomId);
        }
    }

    public boolean isQuartoDisponivel(Quarto quarto, Date checkIn, Date checkOut) {
        List<Reserva> reservas = dataStore.getReservas().stream()
            .filter(reserva -> reserva.getQuartoIds().contains(quarto.getQuartoId()))
            .collect(Collectors.toList());

        for (Reserva reserva : reservas) {
            if (!(checkOut.before(reserva.getCheckIn()) || checkIn.after(reserva.getCheckOut()))) {
                return false;
            }
        }

        return true;
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
}