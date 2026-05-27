package br.com.gestaocomercial.app.src.Model;


import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Avaliacao")
public class Avaliacao {

    public Avaliacao() {
    }

    public Avaliacao(Integer id, Integer idVenda, Float nota,
                     Date dataCriacao, String descricao, String titulo) {
        Id = id;
        this.Venda.setId(idVenda);
        Nota = nota;
        DataCriacao = dataCriacao;
        Descricao = descricao;
        Titulo = titulo;
    }

    public Avaliacao(String titulo, String descricao,
                     Date dataCriacao, Float nota, Integer idVenda) {
        Titulo = titulo;
        Descricao = descricao;
        DataCriacao = dataCriacao;
        Nota = nota;
        this.Venda.setId(idVenda);
    }

    @Id
    @Column(name = "Id")
    private Integer Id;
    @OneToOne
    @JoinColumn(name = "Id_Venda")
    private Venda Venda;
    @Column(name = "Titulo")
    private String Titulo;
    @Column(name = "Descricao")
    private String Descricao;
    @Column(name = "Data_Criacao")
    private Date DataCriacao;
    @Column(name = "Nota")
    private Float Nota;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Venda getVenda() {
        return Venda;
    }

    public void setVenda(Venda venda) {
        Venda = venda;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Float getNota() {
        return Nota;
    }

    public void setNota(Float nota) {
        Nota = nota;
    }
}
