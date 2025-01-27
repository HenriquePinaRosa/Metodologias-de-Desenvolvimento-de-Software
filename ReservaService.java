package com.hotel.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hotel.datastore.DataStore;
import com.hotel.model.Quarto;
import com.hotel.model.Reserva;
import com.hotel.model.Staff;

public class ReservaService {
    private final DataStore dataStore;
    private final QuartoService quartoService;

    public ReservaService(DataStore dataStore) {
        this.dataStore = dataStore;
        this.quartoService = new QuartoService();
    }

    public void addReserva(Reserva reserva) {
        dataStore.addReserva(reserva);
    }

    public void checkIn(Reserva reserva) {
        reserva.setEstado("EM_ANDAMENTO"); 
        System.out.println("Occupation registered for reservation: " + reserva.getReservaId());
    }

    public List<String> sugerirQuartoUnico(int nPessoas, Date checkIn, Date checkOut, String vista, Boolean cozinha, Boolean varanda) {
        return dataStore.getQuartos().stream()
            .filter(quarto -> (vista == null || vista.isEmpty() || quarto.getVista().equalsIgnoreCase(vista))
                && (cozinha == null || quarto.isCozinha() == cozinha)
                && (varanda == null || quarto.isVaranda() == varanda)
                && quarto.getCapacidade() >= nPessoas
                && quartoService.isQuartoDisponivel(quarto, checkIn, checkOut))
            .map(Quarto::getQuartoId)
            .collect(Collectors.toList());
    }

    public List<String> sugerirMultiplosQuartos(int nPessoas, Date checkIn, Date checkOut, String vista, Boolean cozinha, Boolean varanda) {
        List<Quarto> quartosDisponiveis = dataStore.getQuartos().stream()
            .filter(quarto -> (vista == null || vista.isEmpty() || quarto.getVista().equalsIgnoreCase(vista))
                && (cozinha == null || quarto.isCozinha() == cozinha)
                && (varanda == null || quarto.isVaranda() == varanda)
                && quartoService.isQuartoDisponivel(quarto, checkIn, checkOut))
            .collect(Collectors.toList());

        List<String> quartoIds = new ArrayList<>();
        int totalCapacidade = 0;

        for (Quarto quarto : quartosDisponiveis) {
            if (totalCapacidade >= nPessoas) {
                break;
            }
            quartoIds.add(quarto.getQuartoId());
            totalCapacidade += quarto.getCapacidade();
        }

        if (totalCapacidade < nPessoas) {
            return new ArrayList<>();
        }

        return quartoIds;
    }

    public void atribuirQuarto(Reserva reserva, String quartoId) {
        // Logic to assign specific room
        System.out.println("Room " + quartoId + " assigned for reservation: " + reserva.getReservaId());
    }

    public void checkOut(Reserva reserva, Staff staff) {
        // Logic to handle check-out
        System.out.println("Check-out completed for reservation: " + reserva.getReservaId());

        // Update reservation status to "FINALIZADA"
        reserva.setEstado("FINALIZADA");

        // Register maintenance for each room in the reservation
        for (String quartoId : reserva.getQuartoIds()) {
            quartoService.registarManutencao(staff, quartoId, "Manutenção pós-checkout e limpeza");
        }
    }

    public Reserva getReservaById(String reservaId) {
        return dataStore.findReservaById(reservaId);
    }

    // Additional reservation-specific methods
}