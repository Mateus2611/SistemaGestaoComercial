package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Repository.IOrcamentoRepository;
import br.com.gestaocomercial.app.src.Repository.IVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class VendaService {

    @Autowired
    private IVendaRepository _vendaRepository;
    @Autowired
    private IOrcamentoRepository _orcamentoRepository;

    public Venda Criar(Orcamento orcamento, Integer prazo) {
        try {
            if (!orcamento.getStatus().toString().equalsIgnoreCase("APROVADO"))
                throw new RuntimeException("Não foi possível gerar a venda, o orçamento não foi aprovado. Status atual do orçamento: "
                        + orcamento.getStatus());

            Venda venda = new Venda(orcamento.getId());
            venda.setDataCriacao(Date.valueOf(LocalDate.now()));
            venda.setPrazoPagamento(VerificarValidade(venda.getDataCriacao(), prazo));
            venda.setStatusPagamento("PENDENTE");
            venda = _vendaRepository.save(venda);
            venda.setOrcamento(orcamento);

            return venda;

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Iterable<Venda> BuscaGeral() {
        try {

            Iterable<Venda> vendas = _vendaRepository.findAll();
            vendas.forEach( v -> v.setOrcamento(_orcamentoRepository.findById(v.getIdOrcamento()).get()));

            return vendas;

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Venda BuscaPorId(Integer id) {
        try {
            Venda venda = _vendaRepository.findById(id).get();
            venda.setOrcamento(_orcamentoRepository.findById(venda.getIdOrcamento()).get());

            return venda;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Venda Atualizar(Integer id, String statusPagamento) {
        try {
            Venda venda = _vendaRepository.findById(id).get();

            if (venda.getStatusPagamento().equals(Venda.StatusPagamento.EXPIRADO))
                throw new RuntimeException("Atualização recusada. Venda EXPIRADO no dia:" + venda.getPrazoPagamento());

            if (venda.getStatusPagamento().toString().equalsIgnoreCase("CANCELADO")) {
                throw new RuntimeException("Atualização recusada. Orçamento está com status CANCELADO.");
            }

            if (LocalDate.now().isAfter(venda.getPrazoPagamento().toLocalDate())) {
                venda.setStatusPagamento(Venda.StatusPagamento.EXPIRADO.toString());
                _vendaRepository.save(venda);
                throw new RuntimeException("Atualização recusada. Venda expirado no dia: " + venda.getPrazoPagamento());
            }

            venda.setStatusPagamento(statusPagamento);

            if (venda.getStatusPagamento().toString().equalsIgnoreCase("APROVADO"))
                venda.setDataConclusao(Date.valueOf(LocalDate.now()));

            return _vendaRepository.save(venda);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static Date VerificarValidade(Date dataCriacao, Integer prazo) {

        if (!dataCriacao.before(Date.valueOf(LocalDate.now().plusDays(prazo))))
            throw new RuntimeException("Não foi possível criar a venda. Data de prazo é menor que a data de criação. \nData de criação atual: " + dataCriacao);

        return Date.valueOf(LocalDate.now().plusDays(prazo));
    }

}
