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
        Cliente = cliente;
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Status = status;
        Desconto = desconto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "Id_Cliente")
    private Cliente Cliente;

    @OneToMany(mappedBy = "Orcamento", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrcamentoProduto> orcamentoProdutos;

    @Column(name = "Data_Criacao")
    private Date DataCriacao;

    @Column(name = "Data_Validade")
    private Date DataValidade;

    @Column(name = "Valor")
    private BigDecimal Valor;

    @Column(name = "Status_Orcamento")
    @Enumerated(EnumType.STRING)
    private StatusOrcamento Status;

    @Column(name = "Desconto")
    private BigDecimal Desconto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(Cliente cliente) {
        Cliente = cliente;
    }

    public List<OrcamentoProduto> getOrcamentoProdutos() {
        return orcamentoProdutos;
    }

    public void setOrcamentoProdutos(List<OrcamentoProduto> orcamentoProdutos) {
        this.orcamentoProdutos = orcamentoProdutos;
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

    public void setStatus(StatusOrcamento status) {
        Status = status;
    }

    public BigDecimal getDesconto() {
        return Desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        Desconto = desconto;
    }

    public enum StatusOrcamento {
        APROVADO,
        REPROVADO,
        EXPIRADO,
        PENDENTE
    }
}