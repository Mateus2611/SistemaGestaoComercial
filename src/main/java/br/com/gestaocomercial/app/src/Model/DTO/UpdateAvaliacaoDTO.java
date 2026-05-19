package br.com.gestaocomercial.app.src.Model.DTO;

import jakarta.persistence.Column;

import java.sql.Date;

public class UpdateAvaliacaoDTO {
    public Integer id;
    public String Titulo = null;
    public String Descricao = null;
    public Float Nota = null;
}
