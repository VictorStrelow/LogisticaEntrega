package utils;

import dao.*;
import database.Conexao;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Utils {

    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private static final PedidoDAO pedidoDAO = new PedidoDAO();
    private static final EntregaDAO entregaDAO = new EntregaDAO();
    private static final HistoricoEntregaDAO historicoDAO = new HistoricoEntregaDAO();

    // =================== INPUT HELPERS ===================
    public static int readInt(String msg, Scanner sc) {
        System.out.print(msg);
        return Integer.parseInt(sc.nextLine());
    }

    public static double readDouble(String msg, Scanner sc) {
        System.out.print(msg);
        return Double.parseDouble(sc.nextLine());
    }

    public static String readString(String msg, Scanner sc) {
        System.out.print(msg);
        return sc.nextLine();
    }

    // =================== MENUS ===================
    public static void mostrarMenuPrincipal() {

        System.out.println("""
                |============================================|
                |======== MENU LOGÍSTICA DE ENTREGAS ========|
                |============================================|
                | 1 | Cadastros.                             |
                | 2 | Pedidos e Entregas.                    |
                | 3 | Relatórios.                            |
                | 4 | Gerenciamento e Exclusões.             |
                | 0 | Sair.                                  |
                |============================================|
                """);
    }

    public static void menuCadastros(Scanner sc) {
        boolean voltar = false;

        do {
            System.out.println("""
                    |============================================|
                    |=============== MENU CADASTROS =============|
                    |============================================|
                    | 1 | Cadastrar Cliente.                     |
                    | 2 | Cadastrar Motorista.                   |
                    | 3 | Criar Pedido.                          |
                    | 0 | Voltar.                                |
                    |============================================|
                    """);

            int opcao = readInt("Escolha: ", sc);

            try {
                switch (opcao) {
                    case 1 -> cadastrarCliente(sc);
                    case 2 -> cadastrarMotorista(sc);
                    case 3 -> criarPedido(sc);
                    case 0 -> voltar = true;
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (!voltar);
    }

    public static void menuPedidosEntregas(Scanner sc) {
        boolean voltar = false;

        do {
            System.out.println("""
                    |============================================|
                    |========== MENU PEDIDOS E ENTREGAS =========|
                    |============================================|
                    | 1 | Atribuir Pedido a Motorista.           |
                    | 2 | Registrar Evento de Entrega.           |
                    | 3 | Atualizar Status da Entrega.           |
                    | 4 | Listar Todas as Entregas.              |
                    | 0 | Voltar.                                |
                    |============================================|
                    """);

            int opcao = readInt("Escolha: ", sc);

            try {
                switch (opcao) {
                    case 1 -> atribuirPedidoMotorista(sc);
                    case 2 -> registrarEventoEntrega(sc);
                    case 3 -> atualizarStatusEntrega(sc);
                    case 4 -> listarEntregas();
                    case 0 -> voltar = true;
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (!voltar);
    }

    public static void menuRelatorios(Scanner sc) {
        boolean voltar = false;

        do {
            System.out.println("""
                    |============================================|
                    |============== MENU RELATÓRIOS =============|
                    |============================================|
                    | 1 | Total de Entregas por Motorista.       |
                    | 2 | Clientes com Maior Volume Entregue.    |
                    | 3 | Pedidos Pendentes por Estado.          |
                    | 4 | Entregas Atrasadas por Cidade.         |
                    | 0 | Voltar.                                |
                    |============================================|
                    """);

            int opcao = readInt("Escolha: ", sc);

            try {
                switch (opcao) {
                    case 1 -> relatorioEntregasPorMotorista();
                    case 2 -> relatorioClientesMaiorVolume();
                    case 3 -> relatorioPedidosPendentesPorEstado();
                    case 4 -> relatorioEntregasAtrasadasPorCidade();
                    case 0 -> voltar = true;
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (!voltar);
    }

    public static void menuExclusoes(Scanner sc) {
        boolean voltar = false;

        do {
            System.out.println("""
                    |============================================|
                    |====== MENU GERENCIAMENTO / EXCLUSÕES ======|
                    |============================================|
                    | 1 | Buscar Pedido por CPF/CNPJ Cliente.    |
                    | 2 | Cancelar Pedido.                       |
                    | 3 | Excluir Entrega.                       |
                    | 4 | Excluir Cliente.                       |
                    | 5 | Excluir Motorista.                     |
                    | 0 | Voltar.                                |
                    |============================================|
                    """);

            int opcao = readInt("Escolha: ", sc);

            try {
                switch (opcao) {
                    case 1 -> buscarPedidoPorCpf(sc);
                    case 2 -> cancelarPedido(sc);
                    case 3 -> excluirEntrega(sc);
                    case 4 -> excluirCliente(sc);
                    case 5 -> excluirMotorista(sc);
                    case 0 -> voltar = true;
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (!voltar);
    }

    // =================== OPERAÇÕES ===================
    private static void cadastrarCliente(Scanner sc) throws SQLException {
        Cliente c = new Cliente();
        c.setNome(readString("Nome: ", sc));
        c.setCpfCnpj(readString("CPF/CNPJ: ", sc));
        c.setEndereco(readString("Endereço: ", sc));
        c.setCidade(readString("Cidade: ", sc));
        c.setEstado(readString("Estado: ", sc));
        clienteDAO.inserirCliente(c);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void cadastrarMotorista(Scanner sc) throws SQLException {
        Motorista m = new Motorista();
        m.setNome(readString("Nome: ", sc));
        m.setCnh(readString("CNH: ", sc));
        m.setVeiculo(readString("Veículo: ", sc));
        m.setCidadeBase(readString("Cidade Base: ", sc));
        motoristaDAO.inserirMotorista(m);
        System.out.println("Motorista cadastrado com sucesso!");
    }

    private static void criarPedido(Scanner sc) throws SQLException {
        Pedido p = new Pedido();
        p.setClienteId(readInt("ID do Cliente: ", sc));
        p.setDataPedido(LocalDate.now());
        p.setVolumeM3(readDouble("Volume (m³): ", sc));
        p.setPesoKg(readDouble("Peso (kg): ", sc));
        p.setStatus("PENDENTE");
        pedidoDAO.inserirPedido(p);
        System.out.println("Pedido criado com sucesso!");
    }

    private static void atribuirPedidoMotorista(Scanner sc) throws SQLException {
        Entrega e = new Entrega();
        e.setPedidoId(readInt("ID do Pedido: ", sc));
        e.setMotoristaId(readInt("ID do Motorista: ", sc));
        e.setDataSaida(LocalDate.now());
        e.setStatus("EM TRANSPORTE");
        entregaDAO.inserirEntrega(e);
        System.out.println("Entrega criada com sucesso!");
    }

    private static void registrarEventoEntrega(Scanner sc) throws SQLException {
        HistoricoEntrega h = new HistoricoEntrega();
        h.setEntregaId(readInt("ID da Entrega: ", sc));
        h.setDataEvento(String.valueOf(LocalDate.now().atStartOfDay()));
        h.setDescricao(readString("Descrição: ", sc));
        historicoDAO.inserirHistorico(h);
        System.out.println("Evento registrado com sucesso!");
    }

    private static void atualizarStatusEntrega(Scanner sc) throws SQLException {
        int id = readInt("ID da Entrega: ", sc);
        Entrega e = entregaDAO.buscarEntregaPorId(id);

        if (e == null) {
            System.out.println("Entrega não encontrada!");
            return;
        }

        String novoStatus = readString("Novo Status: ", sc);
        e.setStatus(novoStatus);

        if (novoStatus.equalsIgnoreCase("ENTREGUE")) {
            e.setDataEntrega(LocalDate.now());
        }

        entregaDAO.atualizarEntrega(e);
        System.out.println("Status atualizado!");
    }

    private static void listarEntregas() throws SQLException {

        List<Entrega> entregas = entregaDAO.listarEntregas();

        entregas.forEach(e -> System.out.println(
                "Entrega " + e.getId() +
                        " | Pedido " + e.getPedidoId() +
                        " | Motorista " + e.getMotoristaId() +
                        " | Status " + e.getStatus()
        ));
    }

    // =================== RELATÓRIOS ===================
    private static void relatorioEntregasPorMotorista() throws SQLException {
        String query = "SELECT  m.id, m.nome, COUNT(e.id) AS total FROM Motorista m LEFT JOIN Entrega e ON m.id = e.motorista_id GROUP BY m.id, m.nome;";

        try(Connection conn = Conexao.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Motorista " + rs.getString("nome") +
                        " | Total Entregas: " + rs.getInt("total"));
            }
        }
    }

    private static void relatorioClientesMaiorVolume() throws SQLException {
        String query = "SELECT c.nome, SUM(p.volume_m3) AS total_volume FROM Cliente c JOIN pedido p ON c.id = p.cliente_id JOIN entrega e ON p.id = e.pedido_id AND e.status = 'ENTREGUE' GROUP BY c.nome ORDER BY total_volume DESC LIMIT 10;";

        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Cliente " + rs.getString("nome") +
                        " | Volume Total: " + rs.getDouble("total_volume"));
            }
        }
    }

    private static void relatorioPedidosPendentesPorEstado() throws SQLException {
        String query = "SELECT c.estado, COUNT(p.id) AS qtd FROM Pedido p JOIN Cliente c ON p.cliente_id = c.id WHERE p.status = 'PENDENTE' GROUP BY c.estado;";

        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Estado: " + rs.getString("estado") +
                        " | Pedidos Pendentes: " + rs.getInt("qtd"));
            }
        }
    }

    private static void relatorioEntregasAtrasadasPorCidade() throws SQLException {
        String query = "SELECT c.cidade, COUNT(e.id) AS qtd FROM Entrega e JOIN Pedido p ON e.pedido_id = p.id JOIN Cliente c ON p.cliente_id = c.id WHERE e.status = 'ATRASADA' GROUP BY c.cidade;";

        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Cidade: " + rs.getString("cidade") +
                        " | Entregas Atrasadas: " + rs.getInt("qtd"));
            }
        }
    }

    // =================== EXCLUSÕES ===================
    private static void buscarPedidoPorCpf(Scanner sc) throws SQLException {
        String cpf = readString("Digite o CPF/CNPJ: ", sc);

        String query = "SELECT p.id, p.status, p.data_pedido FROM Pedido p JOIN Cliente c ON p.cliente_id = c.id WHERE c.cpf_cnpj = ?;";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Pedido " + rs.getInt("id") +
                            " | Status: " + rs.getString("status") +
                            " | Data: " + rs.getDate("data_pedido"));
                }
            }
        }
    }

    private static void cancelarPedido(Scanner sc) throws SQLException {
        int id = readInt("ID do Pedido: ", sc);

        Pedido p = pedidoDAO.buscarPedidoPorId(id);

        if (p == null) {
            System.out.println("Pedido não encontrado!");
            return;
        }

        p.setStatus("CANCELADO");
        pedidoDAO.atualizarPedido(p);
        System.out.println("Pedido cancelado!");
    }

    private static void excluirEntrega(Scanner sc) throws SQLException {
        int id = readInt("ID da Entrega: ", sc);
        entregaDAO.excluirEntrega(id);
        System.out.println("Entrega excluída!");
    }

    private static void excluirCliente(Scanner sc) throws SQLException {
        int id = readInt("ID do Cliente: ", sc);
        clienteDAO.excluirCliente(id);
        System.out.println("Cliente excluído!");
    }

    private static void excluirMotorista(Scanner sc) throws SQLException {
        int id = readInt("ID do Motorista: ", sc);
        motoristaDAO.excluirMotorista(id);
        System.out.println("Motorista excluído!");
    }

}