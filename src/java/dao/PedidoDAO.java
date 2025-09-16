package dao;

import database.Conexao;
import model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void inserirPedido(Pedido pedido) throws SQLException {
        String query = "INSERT INTO Pedido(cliente_id, data_pedido, volume_m3, peso_kg, status) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getClienteId());
            stmt.setDate(2, Date.valueOf(pedido.getDataPedido()));
            stmt.setDouble(3, pedido.getVolumeM3());
            stmt.setDouble(4, pedido.getPesoKg());
            stmt.setString(5, pedido.getStatus());
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));
                }
            }
        }
    }

    public Pedido buscarPedidoPorId(int id) throws SQLException {
        String query = "SELECT * FROM Pedido WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPedido(rs);
                }
            }
        }

        return null;
    }

    public List<Pedido> listarPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();

        String query = "SELECT * FROM Pedido";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(mapPedido(rs));
            }
        }

        return pedidos;
    }

    public void atualizarPedido(Pedido pedido) throws SQLException {
        String query = "UPDATE Pedido SET cliente_id = ?, data_pedido = ?, volume_m3 = ?, peso_kg = ?, status = ? WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pedido.getClienteId());
            stmt.setDate(2, Date.valueOf(pedido.getDataPedido()));
            stmt.setDouble(3, pedido.getVolumeM3());
            stmt.setDouble(4, pedido.getPesoKg());
            stmt.setString(5, pedido.getStatus());
            stmt.setInt(6, pedido.getId());
            stmt.executeUpdate();
        }
    }

    public void excluirPedido(int id) throws SQLException {
        String query = "DELETE FROM Pedido WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Pedido mapPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();

        pedido.setId(rs.getInt("id"));
        pedido.setClienteId(rs.getInt("cliente_id"));
        pedido.setDataPedido(rs.getDate("data_pedido").toLocalDate());
        pedido.setVolumeM3(rs.getDouble("volume_m3"));
        pedido.setPesoKg(rs.getDouble("peso_kg"));
        pedido.setStatus(rs.getString("status"));

        return pedido;
    }

}