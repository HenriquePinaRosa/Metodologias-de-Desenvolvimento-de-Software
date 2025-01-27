package com.hotel.service;

import java.util.Scanner;

import com.hotel.datastore.DataStore;
import com.hotel.model.Gestor;
import com.hotel.model.Quarto;

public class GestorService extends StaffService {
    private final QuartoService quartoService;
    private final Scanner scanner;

    public GestorService(DataStore dataStore) {
        super(dataStore);
        this.quartoService = new QuartoService();
        this.scanner = new Scanner(System.in);
    }

    public void criarQuarto(Gestor gestor) {
        System.out.println("Insira o nº de camas:");
        int n_camas = scanner.nextInt();

        System.out.println("Insira o nº de casas de banho:");
        int n_toilet = scanner.nextInt();

        System.out.println("Insira o tipo de vista:");
        String vista = scanner.next();

        System.out.println("Tem cozinha? (S/N):");
        boolean cozinha = scanner.next().equalsIgnoreCase("S");

        System.out.println("Tem varanda? (S/N):");
        boolean varanda = scanner.next().equalsIgnoreCase("S");

        System.out.println("Qual a capacidade do quarto?:");
        int capacidade = scanner.nextInt();

        Quarto quarto = new Quarto(n_camas, n_toilet, vista, cozinha, varanda, capacidade);
        quartoService.addQuarto(quarto);
        System.out.println("Room created by gestor: " + gestor.getName() + " with ID: " + quarto.getQuartoId());
    }

    public void editarQuarto(Gestor gestor) {
        System.out.println("Insira o ID do quarto a editar:");
        String roomId = scanner.nextLine();

        Quarto quarto = quartoService.getQuartoById(roomId);
        if (quarto == null) {
            System.out.println("Room not found: " + roomId);
            return;
        }

        System.out.println("Insira o nº de camas: (deixe em branco para manter o mesmo)");
        String n_camas_input = scanner.nextLine();
        int n_camas = n_camas_input.isEmpty() ? quarto.getNCamas() : Integer.parseInt(n_camas_input);

        System.out.println("Insira o nº de casas de banho: (deixe em branco para manter o mesmo)");
        String n_toilet_input = scanner.nextLine();
        int n_toilet = n_toilet_input.isEmpty() ? quarto.getNToilet() : Integer.parseInt(n_toilet_input);

        System.out.println("Insira o tipo de vista: (deixe em branco para manter o mesmo)");
        String vista = scanner.nextLine();
        vista = vista.isEmpty() ? quarto.getVista() : vista;

        System.out.println("Tem cozinha? (S/N): (deixe em branco para manter o mesmo)");
        String cozinha_input = scanner.nextLine();
        boolean cozinha = cozinha_input.isEmpty() ? quarto.isCozinha() : cozinha_input.equalsIgnoreCase("S");

        System.out.println("Tem varanda? (S/N): (deixe em branco para manter o mesmo)");
        String varanda_input = scanner.nextLine();
        boolean varanda = varanda_input.isEmpty() ? quarto.isVaranda() : varanda_input.equalsIgnoreCase("S");

        System.out.println("Qual a capacidade do quarto?: (deixe em branco para manter o mesmo)");
        String capacidade_input = scanner.nextLine();
        int capacidade = capacidade_input.isEmpty() ? quarto.getCapacidade() : Integer.parseInt(capacidade_input);

        quartoService.editarQuarto(roomId, n_camas, n_toilet, vista, cozinha, varanda, capacidade);
        System.out.println("Room " + roomId + " edited by gestor: " + gestor.getName());
    }

    public void removerQuarto(Gestor gestor, String roomId) {
        Quarto quarto = quartoService.getQuartoById(roomId);
        if (quarto != null) {
            quartoService.removeQuarto(quarto);
            System.out.println("Room " + roomId + " removed by gestor: " + gestor.getName());
        } else {
            System.out.println("Room not found: " + roomId);
        }
    }

    // Additional gestor-specific methods
}