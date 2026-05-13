package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco {

    public Endereco() {
    }

    public Endereco(Integer id, String cep, String bairro,
                    String estado, String cidade, String logradouro) {
        Id = id;
        Cep = cep;
        Bairro = bairro;
        Estado = estado;
        Cidade = cidade;
        Logradouro = logradouro;
    }

    public Endereco(String cep, String bairro, String estado,
                    String cidade, String logradouro) {
        Cep = cep;
        Bairro = bairro;
        Estado = estado;
        Cidade = cidade;
        Logradouro = logradouro;
    }

    @Id
    @Column(name = "Id")
    private Integer Id;
    @Column(name = "CEP")
    private String Cep;
    @Column(name = "Bairro")
    private String Bairro;
    @Column(name = "Estado")
    private String Estado;
    @Column(name = "Cidade")
    private String Cidade;
    @Column(name = "Logradouro")
    private String Logradouro;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) {
        Logradouro = logradouro;
    }
}
