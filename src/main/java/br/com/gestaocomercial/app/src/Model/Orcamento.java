package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Table(name = "orcamento")
public class Orcamento {

    public Orcamento() {
    }

    public Orcamento(Integer idCliente, Date dataCriacao, Date dataValidade, StatusOrcamento status, BigDecimal desconto) {
        IdCliente = idCliente;
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Status = status;
        Desconto = desconto;
    }

    @Id
    @Column(name = "Id")
    private Integer Id;
    @Column(name = "Id_Cliente")
    private Integer IdCliente;
    @OneToOne(mappedBy = "orcamento", cascade = CascadeType.REMOVE)
    private Cliente Cliente;
    @OneToMany(mappedBy = "orcamento_produto", cascade = CascadeType.REMOVE)
    private Iterable<Produto> Produtos;
    @Column(name = "Data_Criacao")
    private Date DataCriacao;
    @Column(name = "Data_Validade")
    private Date DataValidade;
    @Column(name = "Valor")
    private BigDecimal Valor;
    @Column(name = "Status_Orcamento")
    private StatusOrcamento Status;
    @Column(name = "Desconto")
    private BigDecimal Desconto;


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

    public Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(Cliente cliente) {
        Cliente = cliente;
    }

    public Iterable<Produto> getProdutos() {
        return Produtos;
    }

    public void setProdutos(List<Produto> produto) {
        Produtos = produto;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Date getDataValidade() {
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
