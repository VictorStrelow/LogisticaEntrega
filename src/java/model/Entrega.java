package model;

import java.time.LocalDate;

public class Entrega {

    private int id;
    private int pedidoId;
    private int motoristaId;
    private LocalDate dataSaida;
    private LocalDate dataEntrega;
    private String status;

    public Entrega() {}
    public Entrega(int id, int pedidoId, int motoristaId, LocalDate dataSaida, LocalDate dataEntrega, String status) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.motoristaId = motoristaId;
        this.dataSaida = dataSaida;
        this.dataEntrega = dataEntrega;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }
    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getMotoristaId() {
        return motoristaId;
    }
    public void setMotoristaId(int motoristaId) {
        this.motoristaId = motoristaId;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }
    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }
    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}