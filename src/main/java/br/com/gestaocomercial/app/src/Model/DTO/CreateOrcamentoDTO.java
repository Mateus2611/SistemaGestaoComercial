package br.com.gestaocomercial.app.src.Model.DTO;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CreateOrcamentoDTO {
    public Integer idCliente;
    public List<CreateOrcamentoProdutoDTO> Produtos;
    public Integer validade;
    public BigDecimal Desconto;
}
