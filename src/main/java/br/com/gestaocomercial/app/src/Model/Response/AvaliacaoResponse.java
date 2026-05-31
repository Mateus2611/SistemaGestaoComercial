package br.com.gestaocomercial.app.src.Model.Response;

import br.com.gestaocomercial.app.src.Model.Venda;

import java.sql.Date;

public class AvaliacaoResponse {

    public Integer id;
    public Integer idVenda;
    public Venda venda;
    public String titulo;
    public String descricao;
    public Date dataCriacao;
    public float nota;

    public AvaliacaoResponse(Integer id, Integer idVenda, Venda venda, String titulo, String descricao, Date dataCriacao, float nota) {
        this.id = id;
        this.idVenda = idVenda;
        this.venda = venda;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.nota = nota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
