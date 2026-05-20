package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.DTO.UpdateVendaDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Repository.IOrcamentoRepository;
import br.com.gestaocomercial.app.src.Repository.IVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    @Autowired
    private IVendaRepository _vendaRepository;
    @Autowired
    private IOrcamentoRepository _orcamentoRepository;


    public Venda Criar(Venda venda, Orcamento orcamento) {

        if (venda == null)
            throw new RuntimeException("Dados de venda vazio. Preencha as informações");
        if (orcamento == null)
            throw new RuntimeException("Dados de venda vazio. Preencha as informações");

        try {
            orcamento = _orcamentoRepository.save(orcamento);
            venda.setIdOrcamento(orcamento.getId());
            venda = _vendaRepository.save(venda);
            venda.setOrcamento(orcamento);

            return venda;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Iterable<Venda> BuscaGeral() {
        try {
            Iterable<Venda> vendas = _vendaRepository.findAll();

            for (Venda venda : vendas) {
                venda.setOrcamento(_orcamentoRepository.findById(venda.getId()).get());
            }

            return vendas;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Venda BuscaPorId(Integer id) {
        try {
            Venda venda = _vendaRepository.findById(id).get();

            venda.setOrcamento(_orcamentoRepository.findById(venda.getIdOrcamento()).get());

            return venda;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Venda Atualizar(UpdateVendaDTO vendaDTO) {
        Venda venda = _vendaRepository.findById(vendaDTO.id).get();

        if (vendaDTO.statusPagamento != null) venda.setStatusPagamento(vendaDTO.statusPagamento.toString());

        if (vendaDTO.orcamento != null) {
            Orcamento orcamento = _orcamentoRepository.save(vendaDTO.orcamento);

            _orcamentoRepository.deleteById(venda.getIdOrcamento());

            venda.setIdOrcamento(orcamento.getId());
        }

        return _vendaRepository.save(venda);
    }
}
