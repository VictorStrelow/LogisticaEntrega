package model;

public class HistoricoEntrega {

    private int id;
    private int entregaId;
    private String dataEvento;
    private String descricao;

    public HistoricoEntrega() {}
    public HistoricoEntrega(int id, int entregaId, String dataEvento, String descricao) {
        this.id = id;
        this.entregaId = entregaId;
        this.dataEvento = dataEvento;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getEntregaId() {
        return entregaId;
    }
    public void setEntregaId(int entregaId) {
        this.entregaId = entregaId;
    }

    public String getDataEvento() {
        return dataEvento;
    }
    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}