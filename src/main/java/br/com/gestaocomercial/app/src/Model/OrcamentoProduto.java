package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Table(name = "orcamento_produto")
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

    @Column(name = "Id_Orcamento")
    private Integer IdOrcamento;
    @Column(name = "Id_Produto")
    private Integer IdProduto;
    @Column(name = "Quantidade")
    public Integer Quantidade;

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
