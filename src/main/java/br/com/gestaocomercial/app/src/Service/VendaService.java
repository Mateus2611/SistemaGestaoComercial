package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.DTO.UpdateVendaDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Response.VendaResponse;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Repository.IOrcamentoRepository;
import br.com.gestaocomercial.app.src.Repository.IVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private IVendaRepository _vendaRepository;
    @Autowired
    private IOrcamentoRepository _orcamentoRepository;


    public Venda Criar(Venda venda) {

        if (venda == null)
            throw new RuntimeException("Dados de venda vazio. Preencha as informações");

        if (venda.getOrcamento() == null || venda.getOrcamento().getId() == null)
            throw new RuntimeException("Dados de venda orçamento. Preencha as informações");

        try {
            Orcamento orcamento = _orcamentoRepository.findById(venda.getOrcamento().getId()).orElseThrow(
                    () -> new RuntimeException("Orçamento não encontrado"));

            venda.setOrcamento(orcamento);
            venda.setStatusPagamento(Venda.StatusPagamento.PENDENTE.name());
            venda.setDataCriacao(new Date(System.currentTimeMillis()));
            venda = _vendaRepository.save(venda);

            return venda;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao criar venda" + ex.getMessage());
        }
    }

    public Page<Venda> BuscaGeral(Integer pagina) {
        try {
            Pageable pageable = PageRequest.of(pagina - 1, 15, Sort.by("id").descending());

            return _vendaRepository.findAll(pageable);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Venda BuscaPorId(Integer id) {
        try {
            Venda venda = _vendaRepository.findById(id).get();

            venda.setOrcamento(_orcamentoRepository.findById(venda.getOrcamento().getId()).get());

            return venda;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Iterable<VendaResponse> BuscaPorStatusAprovado() {
        try {
            Iterable<Venda> vendas = _vendaRepository.findAllByStatus("APROVADO");

            List<VendaResponse> responses = new ArrayList<>();

            vendas.forEach(venda -> {

                responses.add(
                        new VendaResponse(
                                venda.getId(),
                                venda.getOrcamento(),
                                venda.getDataCriacao(),
                                venda.getPrazoPagamento(),
                                venda.getDataConclusao(),
                                venda.getStatusPagamento()
                        )
                );
            });

            return responses;

        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar vendas aprovadas: " + e.getMessage());
        }
    }

    public Venda Atualizar(UpdateVendaDTO vendaDTO) {
        Venda venda = _vendaRepository.findById(vendaDTO.id).get();

        if (vendaDTO.statusPagamento != null) {
            venda.setStatusPagamento(vendaDTO.statusPagamento.toString());

            if (vendaDTO.statusPagamento != Venda.StatusPagamento.APROVADO) {
                venda.setDataConclusao(null);
            } else {
                venda.setDataConclusao(new Date(System.currentTimeMillis()));
            }
        }

        return _vendaRepository.save(venda);
    }
}
