package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Orcamento_Produto")
public class OrcamentoProduto {

    @EmbeddedId
    private OrcamentoProdutoId id = new OrcamentoProdutoId();

    @ManyToOne
    @MapsId("idOrcamento")
    @JoinColumn(name = "Id_Orcamento")
    private Orcamento orcamento;

    @ManyToOne
    @MapsId("idProduto")
    @JoinColumn(name = "Id_Produto")
    private Produto produto;

    @Column(name = "Quantidade")
    private Integer quantidade;

    public OrcamentoProduto() {
    }

    public OrcamentoProduto(Orcamento orcamento, Produto produto, Integer quantidade) {
        this.orcamento = orcamento;
        this.produto = produto;
        this.quantidade = quantidade;
        this.id = new OrcamentoProdutoId(orcamento.getId(), produto.getId());
    }

    public OrcamentoProdutoId getId() {
        return id;
    }

    public void setId(OrcamentoProdutoId id) {
        this.id = id;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}