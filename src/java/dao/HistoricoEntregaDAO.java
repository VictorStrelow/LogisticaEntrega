package dao;

import database.Conexao;
import model.HistoricoEntrega;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoricoEntregaDAO {

    public void inserirHistorico(HistoricoEntrega historico) throws SQLException {
        String query = "INSERT INTO historico_entrega (entrega_id, data_evento, descricao) VALUES (?, ?, ?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, historico.getEntregaId());
            stmt.setDate(2, Date.valueOf(historico.getDataEvento()));
            stmt.setString(3, historico.getDescricao());
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    historico.setId(rs.getInt(1));
                }
            }
        }
    }

    public HistoricoEntrega buscarHistoricoPorId(int id) throws SQLException {
        String query = "SELECT * FROM historico_entrega WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapHistorico(rs);
                }
            }
        }

        return null;
    }

    public List<HistoricoEntrega> listarPorEntrega(int entregaId) throws SQLException {
        List<HistoricoEntrega> historicos = new ArrayList<>();

        String query = "SELECT * FROM historico_entrega WHERE entrega_id = ? ORDER BY data_evento";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entregaId);

            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    historicos.add(mapHistorico(rs));
                }
            }
        }

        return historicos;
    }

    public void atualizarHistorico(HistoricoEntrega historico) throws SQLException {
        String query = "UPDATE HistoricoEntrega SET entrega_id = ?, data_evento = ?, descricao = ? WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, historico.getEntregaId());
            stmt.setDate(2, Date.valueOf(historico.getDataEvento()));
            stmt.setString(3, historico.getDescricao());
            stmt.setInt(4, historico.getId());
            stmt.executeUpdate();
        }
    }

    public void excluirHistorico(int id) throws SQLException {
        String query = "DELETE FROM HistoricoEntrega WHERE id = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private HistoricoEntrega mapHistorico(ResultSet rs) throws SQLException {
        HistoricoEntrega historico = new HistoricoEntrega();

        historico.setId(rs.getInt("id"));
        historico.setEntregaId(rs.getInt("entrega_id"));
        historico.setDataEvento(String.valueOf(LocalDateTime.from(rs.getDate("data_evento").toLocalDate())));
        historico.setDescricao(rs.getString("descricao"));

        return historico;
    }

}