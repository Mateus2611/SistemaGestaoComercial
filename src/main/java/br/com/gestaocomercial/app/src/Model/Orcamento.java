package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Table(name = "orcamento")
public class Orcamento {

    public Orcamento() {
    }

    public Orcamento(Integer id, Integer idCliente, Date dataCriacao, Date dataValidade,
                     BigDecimal valor, StatusOrcamento status, BigDecimal desconto) {
        Id = id;
        IdCliente = idCliente;
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    public Orcamento(Integer idCliente, Date dataCriacao, Date dataValidade,
                     BigDecimal valor, StatusOrcamento status, BigDecimal desconto) {
        IdCliente = idCliente;
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    public Orcamento(Integer idCliente, BigDecimal valor, StatusOrcamento status, BigDecimal desconto) {
        IdCliente = idCliente;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Id
    @Column(name = "Id")
    private Integer Id;
    @Column(name = "Id_Cliente")
    private Integer IdCliente;
    private String nomeCliente;
    private List<String> nomeProdutos;
    @Column(name = "Data_Criacao")
    public Date DataCriacao;
    @Column(name = "Data_Validade")
    public Date DataValidade;
    @Column(name = "Valor")
    public BigDecimal Valor;
    @Column(name = "Status_Orcamento")
    public StatusOrcamento Status;
    @Column(name = "Desconto")
    public BigDecimal Desconto;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(Integer idCliente) {
        IdCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public List<String> getNomeProdutos() {
        return nomeProdutos;
    }

    public void setNomeProdutos(List<String> nomeProdutos) {
        this.nomeProdutos = nomeProdutos;
    }

    public java.util.Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public java.util.Date getDataValidade() {
        return DataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        DataValidade = dataValidade;
    }

    public BigDecimal getValor() {
        return Valor;
    }

    public void setValor(BigDecimal valor) {
        Valor = valor;
    }

    public StatusOrcamento getStatus() {
        return Status;
    }

    public BigDecimal getDesconto() {
        return Desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        Desconto = desconto;
    }

    public void setStatus(StatusOrcamento status) {
        Status = status;
    }

    public enum StatusOrcamento {
        APROVADO,
        REPROVADO,
        EXPIRADO,
        PENDENTE
    }
}
