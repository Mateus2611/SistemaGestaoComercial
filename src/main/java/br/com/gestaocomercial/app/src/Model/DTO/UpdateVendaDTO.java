package br.com.gestaocomercial.app.src.Model.DTO;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;

import java.sql.Date;

public class UpdateVendaDTO {
    public UpdateVendaDTO() {
    }

    public UpdateVendaDTO(Integer id, Venda.StatusPagamento statusPagamento) {
        this.id = id;
        this.statusPagamento = statusPagamento;
    }

    public Integer id;
    public Venda.StatusPagamento statusPagamento = null;
}
