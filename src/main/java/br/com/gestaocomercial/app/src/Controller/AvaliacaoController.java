package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Avaliacao;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService _avaliacaoService;

    @RequestMapping("/avaliacao")
    public Iterable<Avaliacao> get() {
        return _avaliacaoService.BuscaGeral();
    }

    public Avaliacao getById(Integer id) {
        return _avaliacaoService.BuscaPorId(id);
    }

    public Avaliacao create(Avaliacao avaliacao, Venda venda) {
        return _avaliacaoService.Criar(avaliacao, venda);
    }

    public void delete(Integer id) {
        _avaliacaoService.Excluir(id);
    }
}
