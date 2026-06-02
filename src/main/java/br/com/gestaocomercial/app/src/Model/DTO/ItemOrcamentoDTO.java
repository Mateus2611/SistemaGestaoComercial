package br.com.gestaocomercial.app.src.Model.DTO;

public class ItemOrcamentoDTO {

    private Integer ProdutoId;
    private Integer Quantidade;

    public Integer getProdutoId() {
        return ProdutoId;
    }

    public void setProdutoId(Integer produtoId) {
        ProdutoId = produtoId;
    }

    public Integer getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        Quantidade = quantidade;
    }
}
