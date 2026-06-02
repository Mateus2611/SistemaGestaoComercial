package br.com.gestaocomercial.app.src.Model.DTO;

import br.com.gestaocomercial.app.src.Model.Orcamento;

public class UpdateOrcamentoDTO {

    public UpdateOrcamentoDTO() {
    }

    public UpdateOrcamentoDTO(Integer id, Orcamento.StatusOrcamento status) {
        Id = id;
        Status = status;
    }

    public Integer Id;
    public Orcamento.StatusOrcamento Status;
}
