package com.washflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("=== WASHFLOW: Cadastro de Veículo ===");
        System.out.print("Digite a placa do carro: ");
        String placa = teclado.nextLine();
        
        System.out.print("Digite o modelo do carro: ");
        String modelo = teclado.nextLine();

        salvarNoBanco(placa, modelo);
    }

    public static void salvarNoBanco(String placa, String modelo) {
        String sql = "INSERT INTO status_carros (placa, modelo, status) VALUES (?, ?, 'Aguardando')";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            stmt.setString(2, modelo);
            stmt.executeUpdate();
            
            System.out.println("Carro cadastrado com sucesso no monitoramento!");
            
        } catch (Exception e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }
}
