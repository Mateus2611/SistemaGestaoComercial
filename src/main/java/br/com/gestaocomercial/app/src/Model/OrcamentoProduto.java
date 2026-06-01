package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Orcamento_Produto")
public class OrcamentoProduto {

    public OrcamentoProduto() {
    }

    public OrcamentoProduto(Orcamento orcamento, Produto produto, Integer quantidade) {
        Orcamento = orcamento;
        Produto = produto;
        Quantidade = quantidade;
    }

    @EmbeddedId
    private OrcamentoProdutoId id = new OrcamentoProdutoId();

    @ManyToOne
    @MapsId("idOrcamento")
    @JoinColumn(name = "Id_Orcamento")
    private Orcamento Orcamento;

    @ManyToOne
    @MapsId("idProduto")
    @JoinColumn(name = "Id_Produto")
    private Produto Produto;

    @Column(name = "Quantidade")
    private Integer Quantidade;

    public OrcamentoProdutoId getId() {
        return id;
    }

    public void setId(OrcamentoProdutoId id) {
        this.id = id;
    }

    public Orcamento getOrcamento() {
        return Orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        Orcamento = orcamento;
    }

    public Produto getProduto() {
        return Produto;
    }

    public void setProduto(Produto produto) {
        Produto = produto;
    }

    public Integer getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        Quantidade = quantidade;
    }
}