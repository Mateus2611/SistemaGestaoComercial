package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Avaliacao;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Repository.IAvaliacaoRepository;
import br.com.gestaocomercial.app.src.Repository.IVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    @Autowired
    private IAvaliacaoRepository _avaliacaoRepository;
    @Autowired
    IVendaRepository _vendaRepository;

    public Avaliacao Criar(Avaliacao avaliacao, Venda venda) {

        if (avaliacao == null)
            throw new RuntimeException("Dados da avaliação vazios. Preencha as informações");
        if (venda == null)
            throw new RuntimeException("Dados da venda vazios. Preencha as informações");

        try {
            venda = _vendaRepository.save(venda);
            avaliacao.setIdVenda(venda);
            avaliacao = _avaliacaoRepository.save(avaliacao);
            avaliacao.setIdVenda(venda);

            return avaliacao;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Avaliacao BuscaPorId(Integer id) {
        try {
            Avaliacao avaliacao = _avaliacaoRepository.findById(id).get();

            avaliacao.setIdVenda(_vendaRepository.findById(avaliacao.getIdVenda()).get());

            return avaliacao;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Iterable<Avaliacao> BuscaGeral() {
        try {
            Iterable<Avaliacao> avalicoes = _avaliacaoRepository.findAll();

            for (Avaliacao avaliacao : avalicoes) {
                avaliacao.setIdVenda(_vendaRepository.findById(avaliacao.getId()).get());
            }

            return avalicoes;
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
