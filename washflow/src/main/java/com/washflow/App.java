package com.washflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("=== WASHFLOW: Cadastro de Veículo ===");
        System.out.print("Digite a placa: ");
        String placa = teclado.nextLine();
        
        System.out.print("Digite o modelo: ");
        String modelo = teclado.nextLine();

        salvarNoBanco(placa, modelo);
        
        // Chama a lista logo após salvar para ver o dashboard
        listarDashboard();
        
        teclado.close();
    }

    public static void salvarNoBanco(String placa, String modelo) {
        String sql = "INSERT INTO status_carros (placa, modelo, status) VALUES (?, ?, 'Aguardando')";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            stmt.setString(2, modelo);
            stmt.executeUpdate();
            System.out.println("Carro cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static void listarDashboard() {
        String sql = "SELECT placa, modelo, status FROM status_carros";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- DASHBOARD OPERACIONAL ---");
            while (rs.next()) {
                System.out.println("[" + rs.getString("status") + "] " + 
                                   rs.getString("modelo") + " - " + rs.getString("placa"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar dashboard: " + e.getMessage());
        }
    }
}