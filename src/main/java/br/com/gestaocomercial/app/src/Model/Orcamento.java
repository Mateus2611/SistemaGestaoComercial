package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Orcamento")
public class Orcamento {

    public Orcamento() {
    }

    public Orcamento(Cliente cliente, Date dataCriacao, Date dataValidade, StatusOrcamento status, BigDecimal desconto) {
        this.cliente = cliente;
        this.dataCriacao = dataCriacao;
        this.dataValidade = dataValidade;
        this.status = status;
        this.desconto = desconto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "Id_Cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "orcamento", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrcamentoProduto> orcamentoProdutos;

    @Column(name = "Data_Criacao")
    private Date dataCriacao;

    @Column(name = "Data_Validade")
    private Date dataValidade;

    @Column(name = "Valor")
    private BigDecimal valor;

    @Column(name = "Status_Orcamento")
    @Enumerated(EnumType.STRING)
    private StatusOrcamento status;

    @Column(name = "Desconto")
    private BigDecimal desconto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<OrcamentoProduto> getOrcamentoProdutos() {
        return orcamentoProdutos;
    }

    public void setOrcamentoProdutos(List<OrcamentoProduto> orcamentoProdutos) {
        this.orcamentoProdutos = orcamentoProdutos;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public void setStatus(StatusOrcamento status) {
        this.status = status;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public enum StatusOrcamento {
        APROVADO,
        REPROVADO,
        EXPIRADO,
        PENDENTE
    }
}