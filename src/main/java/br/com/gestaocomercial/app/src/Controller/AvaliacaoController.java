package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Avaliacao;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService _avaliacaoService;

    @RequestMapping
    public ModelAndView avaliacao(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return carregarTelaBase(null, page);
    }

    public Avaliacao getById(Integer id) {
        return _avaliacaoService.BuscaPorId(id);
    }

    private ModelAndView carregarTelaBase(Integer idNovo, Integer page) {
        ModelAndView mv = new ModelAndView("avaliacao");
        Page<Avaliacao> avaliacoes = _avaliacaoService.BuscaGeral(page);

        mv.addObject("avaliacoes", avaliacoes.getContent());

        mv.addObject("paginaAtual", page);
        mv.addObject("totalPaginas", avaliacoes.getTotalPages());
        mv.addObject("totalItens", avaliacoes.getTotalElements());

        mv.addObject("novaVenda", new Venda());

        if (idNovo != null) {
            Avaliacao avaliacao = _avaliacaoService.BuscaPorId(idNovo);
            mv.addObject("avaliacao", avaliacao);
            mv.addObject("mostrarDropdown", true);
        }

        return mv;
    }

    public Avaliacao create(Avaliacao avaliacao, Venda venda) {
        return _avaliacaoService.Criar(avaliacao, venda);
    }

    public void delete(Integer id) {
        _avaliacaoService.Excluir(id);
    }
}
