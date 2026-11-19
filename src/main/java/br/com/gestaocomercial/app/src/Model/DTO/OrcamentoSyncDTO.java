package br.com.gestaocomercial.app.src.Model.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrcamentoSyncDTO {
    private Integer id;
    private Integer clienteId;
    private String clienteNome;
    private LocalDate dataCriacao;
    private LocalDate dataValidade;
    private BigDecimal valorTotal;
    private BigDecimal desconto;
    private String statusOrcamento;
    private List<ItemSyncDTO> itens;

    public OrcamentoSyncDTO() {}

    public OrcamentoSyncDTO(Integer id, Integer clienteId, String clienteNome, LocalDate dataCriacao,
                            LocalDate dataValidade, BigDecimal valorTotal, BigDecimal desconto,
                            String statusOrcamento, List<ItemSyncDTO> itens) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.dataCriacao = dataCriacao;
        this.dataValidade = dataValidade;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.statusOrcamento = statusOrcamento;
        this.itens = itens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public String getStatusOrcamento() {
        return statusOrcamento;
    }

    public void setStatusOrcamento(String statusOrcamento) {
        this.statusOrcamento = statusOrcamento;
    }

    public List<ItemSyncDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemSyncDTO> itens) {
        this.itens = itens;
    }

    public static class ItemSyncDTO {
        private Integer produtoId;
        private Integer quantidade;

        public ItemSyncDTO() {}

        public ItemSyncDTO(Integer produtoId, Integer quantidade) {
            this.produtoId = produtoId;
            this.quantidade = quantidade;
        }

        public Integer getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Integer produtoId) {
            this.produtoId = produtoId;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }
    }
}