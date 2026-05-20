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
import org.springframework.stereotype.Service;

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

    public OrcamentoResponse Criar(CreateOrcamentoDTO orcamentoDTO) {
        if (orcamentoDTO == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            Date dataCriacao = Date.valueOf(LocalDate.now());
            Date dataValidade = VerificarValidade(dataCriacao, orcamentoDTO.validade);
            Cliente cliente = _clienteRepository.findById(orcamentoDTO.idCliente).get();
            Orcamento orcamento = new Orcamento(orcamentoDTO.idCliente, dataCriacao, dataValidade, Orcamento.StatusOrcamento.PENDENTE ,orcamentoDTO.Desconto);
            List<Produto> produtos = new ArrayList<>();
            BigDecimal valorTotal = BigDecimal.ZERO;

            for (CreateOrcamentoProdutoDTO ps : orcamentoDTO.Produtos) {
                Produto produto = _produtoRepository.findById(ps.ProdutoId).get();
                valorTotal = valorTotal.add(produto.getValor().multiply(new BigDecimal(ps.Quantidade)));
                produtos.add(produto);
            }

            valorTotal = valorTotal.subtract(orcamentoDTO.Desconto);
            if (valorTotal.equals(BigDecimal.ZERO)) valorTotal = BigDecimal.ZERO;

            orcamento.setValor(valorTotal);

            Orcamento orcamentoCriado = _orcamentoRepository.save(orcamento);

            orcamentoDTO.Produtos.forEach( ps -> _orcamentoProdutoRepository.save(new OrcamentoProduto(orcamentoCriado.getId(), ps.ProdutoId, ps.Quantidade)));

            orcamentoCriado.setCliente(cliente);

            return new OrcamentoResponse(orcamentoCriado.getId(), orcamentoCriado.getIdCliente(), cliente, produtos, orcamentoCriado.getDataCriacao(), orcamentoCriado.getDataValidade(), orcamentoCriado.getValor(), orcamentoCriado.getStatus(), orcamentoCriado.getDesconto());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Iterable<OrcamentoResponse> BuscaGeral() {

        try {
            Iterable<Orcamento> orcamentos = _orcamentoRepository.findAll();
            List<OrcamentoResponse> responses = new ArrayList<>();

            orcamentos.forEach( o -> {
                List<Produto> produtos = new ArrayList<>();

                o.setCliente(_clienteRepository.findById(o.getIdCliente()).get());
                _orcamentoProdutoRepository.findAllById(o.getId()).forEach(op -> produtos.add(
                        _produtoRepository.findById(op.getIdProduto()).get()));

                responses.add(
                        new OrcamentoResponse(
                                o.getId(),
                                o.getIdCliente(),
                                o.getCliente(),
                                produtos,
                                o.getDataCriacao(),
                                o.getDataValidade(),
                                o.getValor(),
                                o.getStatus(),
                                o.getDesconto())
                );
            });

            return responses;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public OrcamentoResponse BuscaPorId(Integer id) {
        try {
            Orcamento orcamento = _orcamentoRepository.findById(id).get();
            orcamento.setCliente(_clienteRepository.findById(orcamento.getIdCliente()).get());
            List<Produto> produtos = new ArrayList<>();

            _orcamentoProdutoRepository.findAllById(orcamento.getId()).forEach(op -> produtos.add(
                    _produtoRepository.findById(op.getIdProduto()).get()));

            return new OrcamentoResponse(
                    orcamento.getId(),
                    orcamento.getIdCliente(),
                    orcamento.getCliente(),
                    produtos,
                    orcamento.getDataCriacao(),
                    orcamento.getDataValidade(),
                    orcamento.getValor(),
                    orcamento.getStatus(),
                    orcamento.getDesconto());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Orcamento Atualizar(UpdateOrcamentoDTO orcamentoDTO) {
        try {

            if (orcamentoDTO == null)
                throw new RuntimeException("Objeto vazio. Preencha as informações.");

            Orcamento orcamento = _orcamentoRepository.findById(orcamentoDTO.Id).get();


            if (orcamento.getStatus().toString().equalsIgnoreCase("EXPIRADO")) {
                throw new RuntimeException("Atualização recusada. Orçamento EXPIRADO no dia: " + orcamento.getDataValidade());
            }

            if (orcamento.getStatus().toString().equalsIgnoreCase("REPROVADO")) {
                throw new RuntimeException("Atualização recusada. Orçamento está com status REPROVADO.");
            }

            if (LocalDate.now().isAfter(orcamento.getDataValidade().toLocalDate())) {
                orcamento.setStatus(Orcamento.StatusOrcamento.EXPIRADO);
                _orcamentoRepository.save(orcamento);
                throw new RuntimeException("Atualização recusada. Orçamento expirado no dia: " + orcamento.getDataValidade());
            }

            orcamento.setStatus(orcamentoDTO.Status);

            return _orcamentoRepository.save(orcamento);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static Date VerificarValidade(Date dataCriacao, Integer validade) {

        if (!dataCriacao.before(Date.valueOf(LocalDate.now().plusDays(validade))))
            throw new RuntimeException("Não foi possível criar o orçamento. Data de validade é menor que a data de criação. \nData de criação atual: " + dataCriacao);

        return Date.valueOf(LocalDate.now().plusDays(validade));
    }
}
