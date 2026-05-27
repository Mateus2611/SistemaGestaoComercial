package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Email")
public class Email {

    public Email() {
    }

    public Email(String endereco) {
        Endereco = endereco;
    }

    public Email(Integer id, Integer idCliente, String endereco) {
        Id = id;
        this.Cliente.setId(idCliente);
        Endereco = endereco;
    }

    @Id
    @Column(name = "Id")
    private Integer Id;
    @OneToOne
    @JoinColumn(name = "Id_Cliente")
    private Cliente Cliente;
    @Column(name = "Endereco")
    private String Endereco;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(Cliente cliente) {
        Cliente = cliente;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    @Override
    public String toString() {
        return this.Endereco;
    }
}
