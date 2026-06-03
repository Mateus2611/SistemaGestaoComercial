package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Avaliacao;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Repository.IAvaliacaoRepository;
import br.com.gestaocomercial.app.src.Repository.IVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class AvaliacaoService {

    @Autowired
    private IAvaliacaoRepository _avaliacaoRepository;
    @Autowired
    private IVendaRepository _vendaRepository;

    public Avaliacao Criar(Avaliacao avaliacao) {

        if (avaliacao == null)
            throw new RuntimeException("Dados da avaliação vazios. Preencha as informações");
        if (avaliacao.getVenda() == null || avaliacao.getVenda().getId() == null)
            throw new RuntimeException("Dados da venda vazios. Preencha as informações");

        try {
            Venda venda = _vendaRepository.findById(avaliacao.getVenda().getId()).orElseThrow(
                    () -> new RuntimeException("Venda não encontrada"));

            avaliacao.setVenda(venda);
            avaliacao.setDataCriacao(Date.valueOf(LocalDate.now()));
            avaliacao = _avaliacaoRepository.save(avaliacao);

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

    public Avaliacao Atualizar(Avaliacao avaliacao) {
        if (avaliacao == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            return _avaliacaoRepository.save(avaliacao);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void Excluir(Integer id) {
        try {
            _avaliacaoRepository.deleteById(id);
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
