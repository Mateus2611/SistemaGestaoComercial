package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.DTO.CreateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.ItemOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.OrcamentoProduto;
import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Repository.IClienteRepository;
import br.com.gestaocomercial.app.src.Repository.IOrcamentoProdutoRepository;
import br.com.gestaocomercial.app.src.Repository.IOrcamentoRepository;
import br.com.gestaocomercial.app.src.Repository.IProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrcamentoService {

    @Autowired
    private IOrcamentoRepository _orcamentoRepository;
    @Autowired
    private IClienteRepository _clienteRepository;
    @Autowired
    private IOrcamentoProdutoRepository _orcamentoProdutoRepository;
    @Autowired
    private IProdutoRepository _produtoRepository;

    @Transactional
    public Orcamento criar(CreateOrcamentoDTO orcamentoDTO) {
        if (orcamentoDTO == null) {
            throw new IllegalArgumentException("Objeto vazio. Preencha as informações do orçamento.");
        }
        if (orcamentoDTO.getItens() == null || orcamentoDTO.getItens().isEmpty()) {
            throw new IllegalArgumentException("O orçamento deve conter pelo menos um produto na tabela.");
        }

        Cliente cliente = _clienteRepository.findById(orcamentoDTO.getCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + orcamentoDTO.getCliente()));

        Date dataCriacao = Date.valueOf(LocalDate.now());

        Date dataValidade = VerificarValidade(dataCriacao, Date.valueOf(orcamentoDTO.getDataValidade()));

        BigDecimal desconto = orcamentoDTO.getDesconto() != null ? orcamentoDTO.getDesconto() : BigDecimal.ZERO;

        Orcamento orcamento = new Orcamento(
                cliente,
                dataCriacao,
                dataValidade,
                Orcamento.StatusOrcamento.PENDENTE,
                desconto
        );

        List<OrcamentoProduto> orcamentoProdutos = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemOrcamentoDTO itemDto : orcamentoDTO.getItens()) {
            Produto produto = _produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado (ID: " + itemDto.getProdutoId() + ")."));

            BigDecimal quantidade = BigDecimal.valueOf(itemDto.getQuantidade());
            BigDecimal subtotal = produto.getValor().multiply(quantidade);
            valorTotal = valorTotal.add(subtotal);

            OrcamentoProduto item = new OrcamentoProduto(orcamento, produto, itemDto.getQuantidade());
            orcamentoProdutos.add(item);
        }

        valorTotal = valorTotal.subtract(desconto);
        if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
            valorTotal = BigDecimal.ZERO;
        }

        orcamento.setValor(valorTotal);
        orcamento.setOrcamentoProdutos(orcamentoProdutos);

        return _orcamentoRepository.save(orcamento);
    }

    public Page<Orcamento> BuscaGeral(Integer pagina) {
        try {
            Pageable pageable = PageRequest.of(pagina - 1, 15, Sort.by("id").descending());

            return _orcamentoRepository.findAll(pageable);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a busca geral de orçamentos paginados: " + e.getMessage(), e);
        }
    }

    public Iterable<Orcamento> BuscaPorStatusAprovado() {
        try {
            return _orcamentoRepository.findAllByStatus(Orcamento.StatusOrcamento.APROVADO.name());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar orçamentos aprovados: " + e.getMessage(), e);
        }
    }

    public Orcamento BuscaPorId(Integer id) {
        try {
            return _orcamentoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Orçamento não encontrado com o ID: " + id));

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar orçamento por ID: " + e.getMessage(), e);
        }
    }

    public Orcamento Atualizar(UpdateOrcamentoDTO orcamentoDTO) {
        if (orcamentoDTO == null) {
            throw new RuntimeException("Objeto vazio. Preencha as informações.");
        }

        try {
            Orcamento orcamento = _orcamentoRepository.findById(orcamentoDTO.Id)
                    .orElseThrow(() -> new RuntimeException("Orçamento não encontrado com o ID: " + orcamentoDTO.Id));

            if (orcamento.getStatus() == Orcamento.StatusOrcamento.EXPIRADO) {
                throw new RuntimeException("Atualização recusada. Orçamento EXPIRADO no dia: " + orcamento.getDataValidade());
            }

            if (orcamento.getStatus() == Orcamento.StatusOrcamento.REPROVADO) {
                throw new RuntimeException("Atualização recusada. Orçamento está com status REPROVADO.");
            }

            if (LocalDate.now().isAfter(orcamento.getDataValidade().toLocalDate())) {
                orcamento.setStatus(Orcamento.StatusOrcamento.EXPIRADO);
                throw new RuntimeException("Atualização recusada. Orçamento expirado no dia: " + orcamento.getDataValidade());
            }

            orcamento.setStatus(orcamentoDTO.Status);

            return _orcamentoRepository.save(orcamento);

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao atualizar o orçamento: " + e.getMessage(), e);
        }
    }

    static Date VerificarValidade(Date dataCriacao, Date validade) {

        if (validade.before(dataCriacao))
            throw new RuntimeException("Não foi possível criar o orçamento. Data de validade é menor que a data de criação. \nData de criação atual: " + dataCriacao);

        return validade;
    }
}
