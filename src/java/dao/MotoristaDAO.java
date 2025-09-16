package dao;

import database.Conexao;
import model.Motorista;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MotoristaDAO {

    public void inserirMotorista(Motorista motorista) throws SQLException {
        String query = "INSERT INTO Motorista(nome, cnh, veiculo, cidade_base) VALUE (?, ?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCnh());
            stmt.setString(3, motorista.getVeiculo());
            stmt.setString(4, motorista.getCidadeBase());
            stmt.executeUpdate();

            System.out.println("Motorista Cadastrado com Sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Motorista> buscarMotoristaPorId(int id) throws SQLException {
        String query = "SELECT * FROM Motorista WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return  Collections.singletonList(mapMotorista(rs));
                }
            }
        }

        return null;
    }

    public List<Motorista> listarMotoristas() throws SQLException {
        List<Motorista> motoristas = new ArrayList<>();

        String query = "SELECT * FROM Motorista";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                motoristas.add(mapMotorista(rs));
            }
        }

        return motoristas;
    }

    public void atualizarMotorista(Motorista motorista) throws SQLException {
        String query = "UPDATE Motorista SET nome = ?, cnh = ?, veiculo = ?, cidade_base = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCnh());
            stmt.setString(3, motorista.getVeiculo());
            stmt.setString(4, motorista.getCidadeBase());
            stmt.setInt(5, motorista.getId());
            stmt.executeUpdate();
        }
    }

    public void excluirMotorista(int id) throws SQLException {
        String query = "DELETE FROM Motorista WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Motorista mapMotorista(ResultSet rs) throws SQLException {
        Motorista motorista = new Motorista();

        motorista.setId(rs.getInt("id"));
        motorista.setNome(rs.getString("nome"));
        motorista.setCnh(rs.getString("cnh"));
        motorista.setVeiculo(rs.getString("veiculo"));
        motorista.setCidadeBase(rs.getString("cidade_base"));

        return motorista;
    }

}