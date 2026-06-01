package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.*;
import br.com.gestaocomercial.app.src.Model.DTO.CreateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.Response.OrcamentoResponse;
import br.com.gestaocomercial.app.src.Service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orcamento")
public class OrcamentoController {

    @Autowired
    private OrcamentoService _orcamentoService;

    @GetMapping
    public ModelAndView orcamento(@RequestParam(value = "page", defaultValue = "1") Integer page) { return carregarTelaBase(null, page); }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            _orcamentoService.BuscaPorId(id);

            return carregarTelaBase(id, 1);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O orçamento com o ID " + id + " não foi encontrado.");
            return new ModelAndView("redirect:/orcamento");
        }
    }

    private ModelAndView carregarTelaBase(Integer id, Integer page) {
        ModelAndView mv = new ModelAndView("orcamento");

        Orcamento novoOrcamento = new Orcamento();
        novoOrcamento.setCliente(new Cliente());
        novoOrcamento.setOrcamentoProdutos(new java.util.ArrayList<>());
        mv.addObject("novoOrcamento", novoOrcamento);

        if (id != null) {
            Orcamento orcamentoUnico = _orcamentoService.BuscaPorId(id);

            List<Orcamento> listaFiltrada = java.util.Collections.singletonList(orcamentoUnico);

            mv.addObject("orcamentos", listaFiltrada);
            mv.addObject("paginaAtual", 1);
            mv.addObject("totalPaginas", 1);
            mv.addObject("totalItens", 1);

            mv.addObject("orcamento", orcamentoUnico);
            mv.addObject("isFiltrado", true);
            mv.addObject("mostrarDropdown", true);
        } else {
            Page<Orcamento> orcamentos = _orcamentoService.BuscaGeral(page);

            mv.addObject("orcamentos", orcamentos.getContent());
            mv.addObject("paginaAtual", page);
            mv.addObject("totalPaginas", orcamentos.getTotalPages());
            mv.addObject("totalItens", orcamentos.getTotalElements());

            mv.addObject("isFiltrado", false);
        }

        return mv;
    }

    public Orcamento create(CreateOrcamentoDTO orcamentoDTO) { return _orcamentoService.Criar(orcamentoDTO); }

    public Orcamento update(UpdateOrcamentoDTO orcamentoDTO) { return _orcamentoService.Atualizar(orcamentoDTO); }
}
