package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Venda")
public class Venda {

    public Venda() {
    }

    public Venda(Integer idOrcamento) {
        this.Orcamento.setId(idOrcamento);
    }

    @Id
    @Column(name = "Id")
    private Integer Id;
    @OneToOne
    @JoinColumn(name = "Id_Orcamento")
    private Orcamento Orcamento;
    @Column(name = "Data_Criacao")
    private Date DataCriacao;
    @Column(name = "Prazo_Pagamento")
    private Date PrazoPagamento;
    @Column(name = "Data_Conclusao")
    private Date DataConclusao;
    @Column(name = "Status_Pagamento")
    @Enumerated(EnumType.STRING)
    private StatusPagamento StatusPagamento;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public void setStatusPagamento(String statusPagamento) {
        StatusPagamento = StatusPagamento.valueOf(statusPagamento);
    }

    public enum StatusPagamento {
        APROVADO,
        CANCELADO,
        PENDENTE,
        EXPIRADO
    }
}
