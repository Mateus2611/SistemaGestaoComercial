package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Avaliacao;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Repository.IAvaliacaoRepository;
import br.com.gestaocomercial.app.src.Repository.IVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    @Autowired
    private IAvaliacaoRepository _avaliacaoRepository;
    @Autowired
    private IVendaRepository _vendaRepository;

    public Avaliacao Criar(Avaliacao avaliacao, Venda venda) {

        if (avaliacao == null)
            throw new RuntimeException("Dados da avaliação vazios. Preencha as informações");
        if (venda == null)
            throw new RuntimeException("Dados da venda vazios. Preencha as informações");

        try {
            venda = _vendaRepository.save(venda);
            avaliacao.getVenda().setId(venda.getId());
            avaliacao = _avaliacaoRepository.save(avaliacao);
            avaliacao.getVenda().setId(venda.getId());

            return avaliacao;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Avaliacao BuscaPorId(Integer id) {
        try {
            Avaliacao avaliacao = _avaliacaoRepository.findById(id).get();

            avaliacao.getVenda().setId(_vendaRepository.findById(avaliacao.getVenda().getId()).get().getId());

            return avaliacao;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Page<Avaliacao> BuscaGeral(Integer pagina) {
        try {
            Pageable pageable = PageRequest.of(pagina - 1, 15, Sort.by("id").descending());

            return _avaliacaoRepository.findAll(pageable);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage()) {
            };
        }
    }

    public void Excluir(Integer id) {
        try {
            _avaliacaoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
