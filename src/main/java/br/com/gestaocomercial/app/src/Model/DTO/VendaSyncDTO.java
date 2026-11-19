package br.com.gestaocomercial.app.src.Model.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VendaSyncDTO(
        Integer id,
        Integer orcamentoId,
        Integer clienteId,
        LocalDate dataCriacao,
        String statusPagamento,
        BigDecimal valorFinal,
        Float notaAvaliacao
) {}
