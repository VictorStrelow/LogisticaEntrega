package dao;

import model.Cliente;
import database.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClienteDAO {

    public void inserirCliente(Cliente cliente) throws SQLException {
        String query = "INSERT INTO Cliente(nome, cpf_cnpj, endereco, cidade, estado) VALUES (? ,?, ?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getEstado());
            stmt.executeUpdate();

            System.out.println("Cliente Cadastrado com Sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> buscarClientePorId(int id) throws SQLException {
        String query = "SELECT * FROM Cliente WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Collections.singletonList(mapCliente(rs));
                }
            }
        }

        return null;
    }

    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();

        String query = "SELECT id, nome, cpf_cnpj, endereco, cidade, estado FROM Cliente";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpfCnpj = rs.getString("cpf_cnpj");
                String endereco = rs.getString("endereco");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");

                var cliente = new Cliente(id, nome, cpfCnpj, endereco, cidade, estado);
                clientes.add(cliente);
            }
        }

        return clientes;
    }

    public void atualizarCliente(Cliente cliente) throws SQLException {
        String query = "UPDATE Cliente SET nome = ?, cpf_cnpj = ?, endereco = ?, cidade = ?, estado = ? WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getEstado());
            stmt.setInt(6, cliente.getId());
            stmt.executeUpdate();
        }
    }

    public void excluirCliente(int id) throws SQLException {
        String query = "DELETE FROM Cliente WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Cliente mapCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setId(rs.getInt("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpfCnpj(rs.getString("cpf_cnpj"));
        cliente.setEndereco(rs.getString("endereco"));
        cliente.setCidade(rs.getString("cidade"));
        cliente.setEstado(rs.getString("estado"));

        return cliente;
    }

}