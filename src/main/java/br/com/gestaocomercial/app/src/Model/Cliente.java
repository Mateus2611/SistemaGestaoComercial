package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "avaliacao")
public class Cliente {

    public Cliente() {
    }

    public Cliente(Integer id, String nome, List<Email> emails,
                   Integer idEndereco, Date dataCadastro, Date dataInativacao,
                   TipoCliente tipo, StatusCliente status) {
        Id = id;
        Nome = nome;
        Emails = emails;
        IdEndereco = idEndereco;
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
        IdEndereco = idEndereco;
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
    @Column(name = "Id")
    public Integer Id;
    @Column(name = "Nome")
    public String Nome;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    public List<Email> Emails = new ArrayList<Email>();
    @Column(name = "Id_Endereco")
    public Integer IdEndereco;
    @OneToOne
    public Endereco Endereco;
    @Column(name = "Data_Cadastro")
    public Date DataCadastro;
    @Column(name = "Data_Inativacao")
    public Date DataInativacao;
    @Column(name = "Tipo")
    public TipoCliente Tipo;
    @Column(name = "Status_Cliente")
    public StatusCliente Status;

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

    public Integer getIdEndereco() {
        return IdEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        IdEndereco = idEndereco;
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
