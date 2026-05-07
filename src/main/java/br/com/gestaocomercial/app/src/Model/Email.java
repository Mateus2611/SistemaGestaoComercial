package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "email")
public class Email {

    public Email() {
    }

    public Email(String endereco) {
        Endereco = endereco;
    }

    public Email(Integer id, Integer idCliente, String endereco) {
        Id = id;
        IdCliente = idCliente;
        Endereco = endereco;
    }

    @Id
    @Column(name = "Id")
    private Integer Id;
    @Column(name = "Id_Cliente")
    public Integer IdCliente;
    @Column(name = "Endereco")
    public String Endereco;

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

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }
}
