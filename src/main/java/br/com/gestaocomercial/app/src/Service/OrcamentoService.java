package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.DTO.CreateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.CreateOrcamentoProdutoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.OrcamentoProduto;
import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Model.Response.OrcamentoResponse;
import br.com.gestaocomercial.app.src.Repository.IClienteRepository;
import br.com.gestaocomercial.app.src.Repository.IOrcamentoProdutoRepository;
import br.com.gestaocomercial.app.src.Repository.IOrcamentoRepository;
import br.com.gestaocomercial.app.src.Repository.IProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public OrcamentoResponse Criar(CreateOrcamentoDTO orcamentoDTO) {
        if (orcamentoDTO == null) {
            throw new RuntimeException("Objeto vazio. Preencha as informações.");
        }

        try {
            Date dataCriacao = Date.valueOf(LocalDate.now());
            Date dataValidade = VerificarValidade(dataCriacao, orcamentoDTO.validade);

            Cliente cliente = _clienteRepository.findById(orcamentoDTO.idCliente)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID fornecido."));

            Orcamento orcamento = new Orcamento(cliente, dataCriacao, dataValidade, Orcamento.StatusOrcamento.PENDENTE, orcamentoDTO.Desconto);

            List<Produto> produtosParaResponse = new ArrayList<>();
            List<OrcamentoProduto> orcamentoProdutos = new ArrayList<>();
            BigDecimal valorTotal = BigDecimal.ZERO;

            for (CreateOrcamentoProdutoDTO ps : orcamentoDTO.Produtos) {
                Produto produto = _produtoRepository.findById(ps.ProdutoId)
                        .orElseThrow(() -> new RuntimeException("Produto com ID " + ps.ProdutoId + " não encontrado."));

                valorTotal = valorTotal.add(produto.getValor().multiply(new BigDecimal(ps.Quantidade)));

                produtosParaResponse.add(produto);

                OrcamentoProduto item = new OrcamentoProduto(orcamento, produto, ps.Quantidade);
                orcamentoProdutos.add(item);
            }

            valorTotal = valorTotal.subtract(orcamentoDTO.Desconto);
            if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                valorTotal = BigDecimal.ZERO;
            }

            orcamento.setValor(valorTotal);
            orcamento.setOrcamentoProdutos(orcamentoProdutos);

            Orcamento orcamentoSalvo = _orcamentoRepository.save(orcamento);

            return new OrcamentoResponse(
                    orcamentoSalvo.getId(),
                    orcamentoSalvo.getCliente().getId(),
                    orcamentoSalvo.getCliente(),
                    produtosParaResponse,
                    orcamentoSalvo.getDataCriacao(),
                    orcamentoSalvo.getDataValidade(),
                    orcamentoSalvo.getValor(),
                    orcamentoSalvo.getStatus(),
                    orcamentoSalvo.getDesconto()
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o orçamento: " + e.getMessage(), e);
        }
    }

    public Page<Orcamento> BuscaGeral(Integer pagina) {
        try {
            Pageable pageable = PageRequest.of(pagina - 1, 15, Sort.by("id").descending());

            return _orcamentoRepository.findAll(pageable);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao realizar a busca geral de orçamentos paginados: " + e.getMessage(), e);
        }
    }

    public Iterable<OrcamentoResponse> BuscaPorStatusAprovado() {
        try {
            List<Orcamento> orcamentos = _orcamentoRepository.findAllByStatus(Orcamento.StatusOrcamento.APROVADO.name());
            List<OrcamentoResponse> responses = new ArrayList<>();

            for (Orcamento o : orcamentos) {
                List<Produto> produtos = new ArrayList<>();

                if (o.getOrcamentoProdutos() != null) {
                    produtos = o.getOrcamentoProdutos().stream()
                            .map(OrcamentoProduto::getProduto)
                            .filter(Objects::nonNull)
                            .toList();
                }

                Integer idCliente = (o.getCliente() != null) ? o.getCliente().getId() : null;

                responses.add(new OrcamentoResponse(
                        o.getId(),
                        idCliente,
                        o.getCliente(),
                        produtos,
                        o.getDataCriacao(),
                        o.getDataValidade(),
                        o.getValor(),
                        o.getStatus(),
                        o.getDesconto()
                ));
            }

            return responses;

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

    static Date VerificarValidade(Date dataCriacao, Integer validade) {

        if (!dataCriacao.before(Date.valueOf(LocalDate.now().plusDays(validade))))
            throw new RuntimeException("Não foi possível criar o orçamento. Data de validade é menor que a data de criação. \nData de criação atual: " + dataCriacao);

        return Date.valueOf(LocalDate.now().plusDays(validade));
    }
}
