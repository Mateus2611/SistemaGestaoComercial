package br.com.gestaocomercial.app.src.Model;

import java.io.Serializable;
import java.util.Objects;

public class OrcamentoProdutoId implements Serializable {

    public OrcamentoProdutoId() {}

    public OrcamentoProdutoId(Integer idOrcamento, Integer idProduto) {
        IdOrcamento = idOrcamento;
        IdProduto = idProduto;
    }

    private Integer IdOrcamento;
    private Integer IdProduto;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrcamentoProdutoId that = (OrcamentoProdutoId) obj;
        return Objects.equals(IdOrcamento, that.IdOrcamento) && Objects.equals(IdProduto, that.IdProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdOrcamento, IdProduto);
    }
}
