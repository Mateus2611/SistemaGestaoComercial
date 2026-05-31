package br.com.gestaocomercial.app.src.Model.Response;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;

import java.sql.Date;

public class VendaResponse {

    public Integer id;
    public Orcamento orcamento;
    public Date dataCriacao;
    public Date prazoPagamento;
    public Date dataConclusao;
    public Venda.StatusPagamento status;

    public VendaResponse(Integer id, Orcamento orcamento, Date dataCriacao, Date prazoPagamento, Date dataConclusao, Venda.StatusPagamento status) {
        this.id = id;
        this.orcamento = orcamento;
        this.dataCriacao = dataCriacao;
        this.prazoPagamento = prazoPagamento;
        this.dataConclusao = dataConclusao;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(Date prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public Date getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Venda.StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(Venda.StatusPagamento status) {
        this.status = status;
    }
}
