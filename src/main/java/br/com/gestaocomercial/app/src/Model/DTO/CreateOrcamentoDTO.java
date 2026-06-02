package br.com.gestaocomercial.app.src.Model.DTO;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import jakarta.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CreateOrcamentoDTO {
    private Integer cliente;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataValidade;
    private BigDecimal desconto;
    private List<ItemOrcamentoDTO> itens;

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public List<ItemOrcamentoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemOrcamentoDTO> itens) {
        this.itens = itens;
    }
}