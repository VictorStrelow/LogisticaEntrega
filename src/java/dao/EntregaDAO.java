package dao;

import database.Conexao;
import model.Entrega;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public void inserirEntrega(Entrega entrega) throws SQLException {
        String query = "INSERT INTO Entrega(pedido_id, motorista_id, data_saida, data_entrega, status) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, entrega.getPedidoId());
            stmt.setInt(2, entrega.getMotoristaId());
            stmt.setDate(3, Date.valueOf(entrega.getDataSaida()));
            stmt.setDate(4, entrega.getDataEntrega() != null ? Date.valueOf(entrega.getDataEntrega()) : null);
            stmt.setString(5, entrega.getStatus());
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    entrega.setId(rs.getInt(1));
                }
            }
        }
    }

    public Entrega buscarEntregaPorId(int id) throws SQLException {
        String query = "SELECT * FROM Entrega WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapEntrega(rs);
                }
            }
        }

        return null;
    }

    public List<Entrega> listarEntregas() throws SQLException {
        List<Entrega> entregas = new ArrayList<>();

        String query = "SELECT * FROM Entrega";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                entregas.add(mapEntrega(rs));
            }
        }

        return entregas;
    }

    public void atualizarEntrega(Entrega entrega) throws SQLException {
        String query = "UPDATE Entrega SET pedido_id = ?, motorista_id = ?, data_saida = ?, data_entrega = ?, status = ? WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entrega.getPedidoId());
            stmt.setInt(2, entrega.getMotoristaId());
            stmt.setDate(3, Date.valueOf(entrega.getDataSaida()));
            stmt.setDate(4, entrega.getDataEntrega() != null ? Date.valueOf(entrega.getDataEntrega()) : null);
            stmt.setString(5, entrega.getStatus());
            stmt.setInt(6, entrega.getId());
            stmt.executeUpdate();
        }
    }

    public void excluirEntrega(int id) throws SQLException {
        String query = "DELETE FROM Entrega WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Entrega mapEntrega(ResultSet rs) throws SQLException {
        Entrega entrega = new Entrega();

        entrega.setId(rs.getInt("id"));
        entrega.setPedidoId(rs.getInt("pedido_id"));
        entrega.setMotoristaId(rs.getInt("motorista_id"));
        entrega.setDataSaida(rs.getDate("data_saida").toLocalDate());
        Date dataEntrega = rs.getDate("data_entrega");
        entrega.setDataEntrega(dataEntrega != null ? dataEntrega.toLocalDate() : null);
        entrega.setStatus(rs.getString("status"));

        return entrega;
    }

}