package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Avaliacao;
import br.com.gestaocomercial.app.src.Model.Response.VendaResponse;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Service.AvaliacaoService;
import br.com.gestaocomercial.app.src.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService _avaliacaoService;

    @Autowired
    private VendaService _vendaService;

    @RequestMapping
    public ModelAndView avaliacao(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return carregarTelaBase(null, page);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            ModelAndView mv = new ModelAndView("avaliacao");

            Avaliacao avaliacaoUnica = _avaliacaoService.BuscaPorId(id);

            List<Avaliacao> listaFiltrada = new ArrayList<>();
            listaFiltrada.add(avaliacaoUnica);

            mv.addObject("avaliacoes", listaFiltrada);

            Avaliacao novaAvaliacao = new Avaliacao();
            novaAvaliacao.setVenda(new Venda());
            mv.addObject("novaAvaliacao", novaAvaliacao);

            return mv;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "A avaliação com o ID " + id + " não foi encontrada.");

            return new ModelAndView("redirect:/avaliacao");
        }
    }

    private ModelAndView carregarTelaBase(Integer id, Integer page) {
        ModelAndView mv = new ModelAndView("avaliacao");
        Page<Avaliacao> avaliacoes = _avaliacaoService.BuscaGeral(page);
        Iterable<VendaResponse> vendas = _vendaService.BuscaPorStatusAprovado();

        mv.addObject("avaliacoes", avaliacoes.getContent());

        mv.addObject("paginaAtual", page);
        mv.addObject("totalPaginas", avaliacoes.getTotalPages());
        mv.addObject("totalItens", avaliacoes.getTotalElements());
        mv.addObject("vendas", vendas);

        mv.addObject("novaAvaliacao", new Avaliacao());

        if (id != null) {
            Avaliacao avaliacao = _avaliacaoService.BuscaPorId(id);
            mv.addObject("avaliacao", avaliacao);
            mv.addObject("mostrarDropdown", true);
        }

        return mv;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("novaAvaliacao") Avaliacao avaliacao) {
        Avaliacao novaAvaliacao = _avaliacaoService.Criar(avaliacao);
        return "redirect:/avaliacao";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("avaliacao") Avaliacao avaliacao, RedirectAttributes redirectAttributes) {
        try {
            _avaliacaoService.Atualizar(avaliacao);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "A avaliação foi atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar avaliação.");
        }
        return "redirect:/avaliacao";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            _avaliacaoService.Excluir(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "A avaliação foi removida com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não foi possível excluir a avaliação.");
        }

        return "redirect:/avaliacao";
    }
}