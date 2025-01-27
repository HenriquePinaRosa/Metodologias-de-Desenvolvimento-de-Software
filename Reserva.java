package com.hotel.model;

import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.hotel.datastore.DataStore;

public class Reserva {
    private String reservaId;
    private String clientId;
    private int nPessoas;
    private Date checkIn;
    private Date checkOut;
    private String estado;
    private List<String> quartoIds;

    public Reserva(String clientId, int n_pessoas ,Date checkIn, Date checkOut, List<String> quartoIds) {
        this.reservaId = generateUniqueReservaId();
        this.clientId = clientId;
        this.nPessoas = n_pessoas;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.estado = "CONFIRMADA";
        this.quartoIds = quartoIds;
    }

    public String getReservaId() {
        return reservaId;
    }

    public void setReservaId(String reservaId) {
        this.reservaId = reservaId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getNPessoas() {
        return nPessoas;
    }

    public void setNPessoas(int nPessoas) {
        this.nPessoas = nPessoas;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getQuartoIds() {
        return quartoIds;
    }

    public void setQuartoIds(List<String> quartoIds) {
        this.quartoIds = quartoIds;
    }

    private String generateUniqueReservaId() {
        DataStore dataStore = DataStore.getInstance();
        Random random = new Random();

        Set<String> existingIds = dataStore.getReservas().stream()
                .map(Reserva::getReservaId)
                .collect(Collectors.toSet());

        String newId;
        do {
            newId = String.format("RES-%04d", random.nextInt(10000));
        } while (existingIds.contains(newId));

        return newId;
    }
}