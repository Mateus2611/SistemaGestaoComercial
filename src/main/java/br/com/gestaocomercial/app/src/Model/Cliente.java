package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cliente")
public class Cliente {

    public Cliente() {
    }

    public Cliente(Integer id, String nome, List<Email> emails,
                   Integer idEndereco, Date dataCadastro, Date dataInativacao,
                   TipoCliente tipo, StatusCliente status) {
        Id = id;
        Nome = nome;
        Emails = emails;
        this.Endereco.setId(idEndereco);
        DataCadastro = dataCadastro;
        DataInativacao = dataInativacao;
        Tipo = tipo;
        Status = status;
    }

    public Cliente(Integer id, String nome, Integer idEndereco,
                   Date dataCadastro, Date dataInativacao,
                   TipoCliente tipo, StatusCliente status) {
        Id = id;
        Nome = nome;
        this.Endereco.setId(idEndereco);
        DataCadastro = dataCadastro;
        DataInativacao = dataInativacao;
        Tipo = tipo;
        Status = status;
    }

    public Cliente(TipoCliente tipo, String nome) {
        Tipo = tipo;
        Nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;
    @Column(name = "Nome")
    private String Nome;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Cliente")
    private List<Email> Emails;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Endereco", referencedColumnName = "Id")
    private Endereco Endereco;
    @Column(name = "Data_Cadastro")
    private Date DataCadastro;
    @Column(name = "Data_Inativacao")
    private Date DataInativacao;
    @Column(name = "Tipo")
    @Enumerated(EnumType.STRING)
    private TipoCliente Tipo;
    @Column(name = "Status_Cliente")
    @Enumerated(EnumType.STRING)
    private StatusCliente Status;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public List<Email> getEmails() {
        return Emails;
    }

    public void setEmails(List<Email> emails) {
        Emails = emails;
    }

    public void setDataCadastro(Date dataCadastro) {
        DataCadastro = dataCadastro;
    }

    public Endereco getEndereco() {
        return Endereco;
    }

    public void setEndereco(Endereco endereco) {
        Endereco = endereco;
    }

    public Date getDataCadastro() {
        return DataCadastro;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Date getDataInativacao() {
        return DataInativacao;
    }

    public void setDataInativacao(Date dataInativacao) {
        DataInativacao = dataInativacao;
    }

    public TipoCliente getTipo() {
        return Tipo;
    }

    public void setTipo(TipoCliente tipo) {
        Tipo = tipo;
    }

    public StatusCliente getStatus() {
        return Status;
    }

    public void setStatus(StatusCliente status) {
        Status = status;
    }

    public enum TipoCliente {
        CPF,
        CNPJ
    }

    public enum StatusCliente {
        ATIVO,
        INATIVO
    }
}
