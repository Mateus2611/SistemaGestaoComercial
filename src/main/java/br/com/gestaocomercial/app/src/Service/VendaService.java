package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.DTO.UpdateVendaDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Repository.IOrcamentoRepository;
import br.com.gestaocomercial.app.src.Repository.IVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            venda.getOrcamento().setId(orcamento.getId());
            venda = _vendaRepository.save(venda);
            venda.setOrcamento(orcamento);

            return venda;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Page<Venda> BuscaGeral(Integer pagina) {
        try {
            Pageable pageable = PageRequest.of(pagina - 1, 15, Sort.by("id").descending());

            Page<Venda> vendas = _vendaRepository.findAll(pageable);

            for (Venda venda : vendas.getContent()) {
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

            venda.setOrcamento(_orcamentoRepository.findById(venda.getOrcamento().getId()).get());

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

            _orcamentoRepository.deleteById(venda.getOrcamento().getId());

            venda.getOrcamento().setId(orcamento.getId());
        }

        return _vendaRepository.save(venda);
    }
}
