package br.com.gestaocomercial.app.src.Model.DTO;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;

public class UpdateVendaDTO {
    public Integer id;
    public Orcamento orcamento = null;
    public Venda.StatusPagamento statusPagamento = null;
}
