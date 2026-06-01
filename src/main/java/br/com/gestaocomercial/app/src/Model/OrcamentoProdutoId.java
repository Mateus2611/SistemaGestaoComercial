package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrcamentoProdutoId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idOrcamento;
    private Integer idProduto;

    public OrcamentoProdutoId() {}

    public OrcamentoProdutoId(Integer idOrcamento, Integer idProduto) {
        this.idOrcamento = idOrcamento;
        this.idProduto = idProduto;
    }

    public Integer getIdOrcamento() {
        return idOrcamento;
    }

    public void setIdOrcamento(Integer idOrcamento) {
        this.idOrcamento = idOrcamento;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrcamentoProdutoId that = (OrcamentoProdutoId) obj;
        return Objects.equals(idOrcamento, that.idOrcamento) &&
                Objects.equals(idProduto, that.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrcamento, idProduto);
    }
}