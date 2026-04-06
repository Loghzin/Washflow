package com.washflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n=== WASHFLOW: Monitoramento ===");
            System.out.println("1. Novo Cadastro (Entrada)");
            System.out.println("2. Ver Dashboard (Lista)");
            System.out.println("3. Mudar Status (Simular Arrastar)");
            System.out.println("4. Sair");
            System.out.print("Escolha: ");
            opcao = teclado.nextInt();
            teclado.nextLine(); 

            if (opcao == 1) {
                System.out.print("Placa: ");
                String placa = teclado.nextLine();
                System.out.print("Modelo: ");
                String modelo = teclado.nextLine();
                
                if (placa.trim().isEmpty()) {
                    System.out.println("Erro: Placa e/ou modelo vazios.");
                } else {
                    salvarNoBanco(placa, modelo);
                }
            } else if (opcao == 2) {
                listarDashboard();
            } else if (opcao == 3) {
                System.out.print("Placa do carro para mudar: ");
                String p = teclado.nextLine();
                System.out.println("Novo Status: 1-LAVANDO | 2-SECAGEM | 3-PRONTO");
                int s = teclado.nextInt();
                String statusTxt = (s==1)?"Em Lavagem":(s==2)?"Secagem":"Pronto";
                
                atualizarStatus(p, statusTxt);
            }
        }
        teclado.close();
    }

    public static void salvarNoBanco(String placa, String modelo) {
        String sql = "INSERT INTO status_carros (placa, modelo, status) VALUES (?, ?, 'Aguardando')";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            stmt.setString(2, modelo);
            stmt.executeUpdate();
            System.out.println("Carro cadastrado!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void listarDashboard() {
        String sql = "SELECT placa, modelo, status FROM status_carros";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- DASHBOARD ATUALIZADO ---");
            while (rs.next()) {
                System.out.println("[" + rs.getString("status") + "] " + 
                                   rs.getString("modelo") + " (" + rs.getString("placa") + ")");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
    }

    public static void atualizarStatus(String placa, String novoStatus) {
        String sql = "UPDATE status_carros SET status = ? WHERE placa = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setString(2, placa);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Status movido para: " + novoStatus);
            else System.out.println("Placa não encontrada.");
        } catch (Exception e) {
            System.out.println("Erro no Update: " + e.getMessage());
        }
    }
}