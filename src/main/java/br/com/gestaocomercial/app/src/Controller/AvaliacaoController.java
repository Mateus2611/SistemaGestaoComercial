package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Avaliacao;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateAvaliacaoDTO;
import br.com.gestaocomercial.app.src.Service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService _avaliacaoService;

    @RequestMapping("/avaliacao")
    public Iterable<Avaliacao> get() { return _avaliacaoService.BuscaGeral(); }

    public Avaliacao getById(Integer id) { return _avaliacaoService.BuscaPorId(id); }

    public Avaliacao getBySaleId(Integer idVenda) { return _avaliacaoService.BuscaPorIdVenda(idVenda); }

    public Avaliacao create(Avaliacao avaliacao) { return _avaliacaoService.Criar(avaliacao); }

    public Avaliacao update(UpdateAvaliacaoDTO avaliacaoDTO) { return _avaliacaoService.Atualizar(avaliacaoDTO); }

    public void delete(Integer id) { _avaliacaoService.Excluir(id); }
}
