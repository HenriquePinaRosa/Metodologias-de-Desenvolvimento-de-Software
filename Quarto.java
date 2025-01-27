package com.hotel.model;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.hotel.datastore.DataStore;

enum estadoManutencao {
    EM_ANDAMENTO, CONCLUIDA, CANCELADA
}

public class Quarto {
    private String quartoId;
    private int nCamas;
    private int nToilet;
    private String vista;
    private boolean cozinha;
    private boolean varanda;
    private int capacidade;
    private boolean needsMaintenance;
    private boolean disponivel;
    private String estadoManutencao;
    private String tipoManutencao;

    public Quarto(int nCamas, int nToilet, String vista, boolean cozinha, boolean varanda, int capacidade) {
        this.quartoId = generateUniqueRoomId();
        this.nCamas = nCamas;
        this.nToilet = nToilet;
        this.vista = vista;
        this.cozinha = cozinha;
        this.varanda = varanda;
        this.capacidade = capacidade;
        disponivel = true;
        needsMaintenance = false;
    }

    public Quarto(String quartoId, int nCamas, int nToilet, String vista, boolean cozinha, boolean varanda, int capacidade) {
        this.quartoId = quartoId != null ? quartoId : generateUniqueRoomId();
        this.nCamas = nCamas;
        this.nToilet = nToilet;
        this.vista = vista;
        this.cozinha = cozinha;
        this.varanda = varanda;
        this.capacidade = capacidade;
    }

    public String getQuartoId() {
        return quartoId;
    }

    public void setQuartoId(String quartoId) {
        this.quartoId = quartoId;
    }

    public int getNCamas() {
        return nCamas;
    }

    public void setNCamas(int nCamas) {
        this.nCamas = nCamas;
    }

    public int getNToilet() {
        return nToilet;
    }

    public void setNToilet(int nToilet) {
        this.nToilet = nToilet;
    }

    public String getVista() {
        return vista;
    }

    public void setVista(String vista) {
        this.vista = vista;
    }

    public boolean isCozinha() {
        return cozinha;
    }

    public void setCozinha(boolean cozinha) {
        this.cozinha = cozinha;
    }

    public boolean isVaranda() {
        return varanda;
    }

    public void setVaranda(boolean varanda) {
        this.varanda = varanda;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setNeedsMaintenance(boolean needsMaintenance) {
        this.needsMaintenance = needsMaintenance;
    }

    public boolean isNeedsMaintenance() {
        return needsMaintenance;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public String getEstadoManutencao() {
        return estadoManutencao;
    }

    public void setEstadoManutencao(String estadoManutencao) {
        this.estadoManutencao = estadoManutencao;
    }

    public String getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(String tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    private String generateUniqueRoomId() {
        DataStore dataStore = DataStore.getInstance();
        Random random = new Random();

        Set<String> existingIds = dataStore.getQuartos().stream()
                .map(Quarto::getQuartoId)
                .collect(Collectors.toSet());

        String newId;
        do {
            newId = String.format("room%04d", random.nextInt(10000));
        } while (existingIds.contains(newId));

        return newId;
    }
}