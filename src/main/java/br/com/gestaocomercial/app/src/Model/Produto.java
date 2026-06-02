package br.com.gestaocomercial.app.src.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Produto")
public class Produto {

    public Produto() {
    }

    public Produto(Integer id, String nome, BigDecimal valor) {
        Id = id;
        Nome = nome;
        Valor = valor;
    }

    public Produto(String nome, BigDecimal valor) {
        Nome = nome;
        Valor = valor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;

    @Column(name = "Nome")
    private String Nome;

    @Column(name = "Valor")
    private BigDecimal Valor;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public BigDecimal getValor() {
        return Valor;
    }

    public void setValor(BigDecimal valor) {
        Valor = valor;
    }
}