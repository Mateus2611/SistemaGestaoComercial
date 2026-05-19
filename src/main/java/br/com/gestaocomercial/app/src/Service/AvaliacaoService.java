package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Avaliacao;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateAvaliacaoDTO;
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
    private IVendaRepository _vendaRepository;

    public Avaliacao Criar(Avaliacao avaliacao) {

        try {
            if (avaliacao == null) {
                throw new RuntimeException("Objeto Avaliação vazio. Preencha as informações.");
            }

            if (!_vendaRepository.existsById(avaliacao.getIdVenda()))
                throw new RuntimeException("Venda não encontrada.");

            return _avaliacaoRepository.save(avaliacao);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Iterable<Avaliacao> BuscaGeral() {
        try {
            return _avaliacaoRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Avaliacao BuscaPorId(Integer id) {
        try {
            return _avaliacaoRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Avaliacao BuscaPorIdVenda(Integer idVenda) {
        try {
            return _avaliacaoRepository.findBySaleId(idVenda);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Avaliacao Atualizar(UpdateAvaliacaoDTO avaliacaoDTO) {
        try {

            if (avaliacaoDTO == null) {
                throw new RuntimeException("Objeto vazio. Preencha as informações.");
            }

            Avaliacao avaliacao = _avaliacaoRepository.findById(avaliacaoDTO.id).get();

            if (avaliacaoDTO.Titulo != null ) avaliacao.setTitulo(avaliacaoDTO.Titulo);
            if (avaliacaoDTO.Descricao != null) avaliacao.setDescricao(avaliacaoDTO.Descricao);
            if (avaliacaoDTO.Nota != null) avaliacao.setNota(avaliacaoDTO.Nota);

            return _avaliacaoRepository.save(avaliacao);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
