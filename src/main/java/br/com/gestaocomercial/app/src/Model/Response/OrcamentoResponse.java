package br.com.gestaocomercial.app.src.Model.Response;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.OrcamentoProduto;
import br.com.gestaocomercial.app.src.Model.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrcamentoResponse {

    public OrcamentoResponse(Integer id, Integer idCliente, Cliente cliente, List<Produto> produtos, Date dataCriacao, Date dataValidade, BigDecimal valor, Orcamento.StatusOrcamento status, BigDecimal desconto) {
        Id = id;
        IdCliente = idCliente;
        Cliente = cliente;
        Produtos = produtos;
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    public Integer Id;
    public Integer IdCliente;
    public Cliente Cliente;
    public List<Produto> Produtos;
    public Date DataCriacao;
    public Date DataValidade;
    public BigDecimal Valor;
    public Orcamento.StatusOrcamento Status;
    public BigDecimal Desconto;
}
