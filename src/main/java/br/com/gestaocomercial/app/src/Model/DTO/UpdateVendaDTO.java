package br.com.gestaocomercial.app.src.Model.DTO;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;

import java.sql.Date;

public class UpdateVendaDTO {
    public Integer id;
    public Orcamento orcamento = null;
    public Venda.StatusPagamento statusPagamento = null;
    public Date prazoPagamento = null;
}
