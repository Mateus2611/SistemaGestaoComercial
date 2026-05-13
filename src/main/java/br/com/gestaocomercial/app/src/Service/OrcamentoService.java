package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.OrcamentoProduto;
import br.com.gestaocomercial.app.src.Model.Produto;
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

    public Orcamento Criar(Orcamento orcamento, Integer validade) {
        if (orcamento == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            Date dataCriacao = Date.valueOf(LocalDate.now());
            Date dataValidade = VerificarValidade(dataCriacao, validade);

            orcamento.setDataCriacao(dataCriacao);
            orcamento.setDataValidade(dataValidade);

            return _orcamentoRepository.save(orcamento);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Iterable<Orcamento> BuscaGeral() {
        try {
            Iterable<Orcamento> orcamentos = _orcamentoRepository.findAll();

            for (Orcamento orcamento : orcamentos) {

                try {

                    Cliente cliente = _clienteRepository.findById(orcamento.getIdCliente()).get();
                    orcamento.setNomeCliente(cliente.getNome());

                } catch (RuntimeException e) {
                    orcamento.setNomeCliente("Nenhum cliente encontrado");
                }

                List<OrcamentoProduto> produtos = _orcamentoProdutoDao.BuscaOrcamentoId(orcamento.getId());
                List<String> nomeProdutos = new ArrayList<>();
                BigDecimal valorTotal = BigDecimal.ZERO;

                for (OrcamentoProduto produto : produtos) {
                    try {
                        Produto p = _produtoRepository.findById(produto.getIdProduto());
                        if (p != null) {
                            nomeProdutos.add(p.getNome());
                            BigDecimal valor = p.getValor().multiply(new BigDecimal(produto.getQuantidade()));
                            valorTotal = valorTotal.add(valor);
                        }
                    } catch (RuntimeException e) {
                        nomeProdutos.add("Nenhum produto encontrado");
                    }
                }
                orcamento.setNomeProdutos(nomeProdutos);

                if (orcamento.getDesconto() != null) {
                    valorTotal = valorTotal.subtract(orcamento.getDesconto());
                }

                if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                    valorTotal = BigDecimal.ZERO;
                    orcamento.setDesconto(BigDecimal.ZERO);
                }

                orcamento.setValor(valorTotal);
            }
            return orcamentos;

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Orcamento BuscaPorId(Integer id) {
        try {
            Orcamento orcamento = _orcamentoRepository.findById(id).get();

            if (orcamento != null) {

                try {
                    Cliente cliente = _clienteRepository.findById(orcamento.getIdCliente()).get();
                    if (cliente != null) {
                        orcamento.setNomeCliente(cliente.getNome());
                    }
                } catch (RuntimeException e) {
                    orcamento.setNomeCliente("Nenhum cliente encontrado");
                }

                List<OrcamentoProduto> produtos = _orcamentoProdutoDao.BuscaOrcamentoId(orcamento.getId());
                List<String> nomeProdutos = new ArrayList<>();
                BigDecimal valorTotal = BigDecimal.ZERO;

                for (OrcamentoProduto produto : produtos) {
                    try {
                        Produto p = _produtoRepository.findById(produto.getIdProduto());
                        if (p != null) {
                            nomeProdutos.add(p.getNome());

                            BigDecimal valor = p.getValor().multiply(new BigDecimal(produto.getQuantidade()));
                            valorTotal = valorTotal.add(valor);
                        }
                    } catch (RuntimeException e) {
                        nomeProdutos.add("Nenhum produto encontrado");
                    }
                }
                orcamento.setNomeProdutos(nomeProdutos);

                if (orcamento.getDesconto() != null) {
                    valorTotal = valorTotal.subtract(orcamento.getDesconto());
                }

                if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                    valorTotal = BigDecimal.ZERO;
                    orcamento.setDesconto(BigDecimal.ZERO);
                }

                orcamento.setValor(valorTotal);
            }

            return orcamento;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Orcamento Atualizar(UpdateOrcamentoDTO orcamentoDTO) {
        if (orcamentoDTO == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            Orcamento orcamento = _orcamentoRepository.findById(orcamentoDTO.Id).get();

            if (orcamento.getStatus().toString().equalsIgnoreCase("EXPIRADO"))
                throw new RuntimeException("Atualização recusada. Orçamento expirado no dia: " + orcamento.getDataValidade());

            orcamento.setStatus(orcamentoDTO.Status);

            return _orcamentoRepository.save(orcamento);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static Date VerificarValidade(Date dataCriacao, Integer validade) {

        if (!dataCriacao.before(Date.valueOf(LocalDate.now().plusDays(validade))))
            throw new RuntimeException("Não foi possível criar o orçamento. Data de validade é menor que a data de criação. \nData de criação atual: " + dataCriacao);

        return Date.valueOf(LocalDate.now().plusDays(validade));
    }
}
