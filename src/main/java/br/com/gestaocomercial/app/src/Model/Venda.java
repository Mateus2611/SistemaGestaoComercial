package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Table(name = "venda")
public class Venda {

    public Venda() {
    }

    public Venda(Integer idOrcamento, Date dataCriacao, Date prazoPagamento, Date dataConclusao, StatusPagamento statusPagamento) {
        IdOrcamento = idOrcamento;
        DataCriacao = dataCriacao;
        PrazoPagamento = prazoPagamento;
        DataConclusao = dataConclusao;
        StatusPagamento = statusPagamento;
    }

    public Venda(Integer id, Integer idOrcamento, Date dataCriacao, Date prazoPagamento, Date dataConclusao, StatusPagamento statusPagamento) {
        Id = id;
        IdOrcamento = idOrcamento;
        DataCriacao = dataCriacao;
        PrazoPagamento = prazoPagamento;
        DataConclusao = dataConclusao;
        StatusPagamento = statusPagamento;
    }


    @Id
    @Column(name = "Id")
    private Integer Id;
    @Column(name = "Id_Orcamento")
    private Integer IdOrcamento;
    private Orcamento Orcamento;
    @Column(name = "Data_Criacao")
    private Date DataCriacao;
    @Column(name = "Prazo_Pagamento")
    private Date PrazoPagamento;
    @Column(name = "Data_Conclusao")
    private Date DataConclusao;
    @Column(name = "Status_Pagamento")
    private StatusPagamento StatusPagamento;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getIdOrcamento() {
        return IdOrcamento;
    }

    public void setIdOrcamento(Integer idOrcamento) {
        IdOrcamento = idOrcamento;
    }

    public Orcamento getOrcamento() {
        return Orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        Orcamento = orcamento;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Date getPrazoPagamento() {
        return PrazoPagamento;
    }

    public void setPrazoPagamento(Date prazoPagamento) {
        PrazoPagamento = prazoPagamento;
    }

    public Date getDataConclusao() {
        return DataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        DataConclusao = dataConclusao;
    }

    public StatusPagamento getStatusPagamento() {
        return StatusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        StatusPagamento = statusPagamento;
    }

    public enum StatusPagamento {
        APROVADO,
        CANCELADO,
        PENDENTE,
        EXPIRADO
    }
}
