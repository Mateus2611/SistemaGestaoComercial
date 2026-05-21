package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "orcamento_produto")
@IdClass(OrcamentoProdutoId.class)
public class OrcamentoProduto {

    public OrcamentoProduto() {
    }

    public OrcamentoProduto(Integer idOrcamento, Integer idProduto, Integer quantidade) {
        IdOrcamento = idOrcamento;
        IdProduto = idProduto;
        Quantidade = quantidade;
    }

    public OrcamentoProduto(Integer idOrcamento, Integer idProduto) {
        IdOrcamento = idOrcamento;
        IdProduto = idProduto;
    }

    @Id
    @Column(name = "Id_Orcamento")
    private Integer IdOrcamento;
    @Id
    @Column(name = "Id_Produto")
    private Integer IdProduto;
    @Column(name = "Quantidade")
    private Integer Quantidade;

    public Integer getIdOrcamento() {
        return IdOrcamento;
    }

    public void setIdOrcamento(Integer idOrcamento) {
        IdOrcamento = idOrcamento;
    }

    public Integer getIdProduto() {
        return IdProduto;
    }

    public void setIdProduto(Integer idProduto) {
        IdProduto = idProduto;
    }

    public Integer getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        Quantidade = quantidade;
    }
}
