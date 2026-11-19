package br.com.gestaocomercial.app.src.Model.DTO;

import java.math.BigDecimal;

public class ProdutoSyncDTO {
    private Integer id;
    private String nome;
    private BigDecimal valorAtual;
    private Boolean ativo;

    public ProdutoSyncDTO() {}

    public ProdutoSyncDTO(Integer id, String nome, BigDecimal valorAtual, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.valorAtual = valorAtual;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}