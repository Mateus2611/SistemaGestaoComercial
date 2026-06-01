package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.DTO.CreateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.OrcamentoProduto;
import br.com.gestaocomercial.app.src.Model.Produto;
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

@Controller
@RequestMapping("/orcamento")
public class OrcamentoController {

    @Autowired
    private OrcamentoService _orcamentoService;

    @GetMapping
    public ModelAndView orcamento(@RequestParam(value = "page", defaultValue = "1") Integer page) { return carregarTelaBase(null, page); }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Orcamento orcamento = _orcamentoService.BuscaPorId(id);

        if (orcamento == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O produto com o ID " + id + " não foi encontrado.");

            return new ModelAndView("redirect:/orcamento");
        }

        return carregarTelaBase(id, 1);
    }

    private ModelAndView carregarTelaBase(Integer id, Integer page) {
        ModelAndView mv = new ModelAndView("orcamento");

        Page<Orcamento> orcamentos = _orcamentoService.BuscaGeral(page);

        mv.addObject("orcamentos", orcamentos.getContent());
        mv.addObject("paginaAtual", page);
        mv.addObject("totalPaginas", orcamentos.getTotalPages());
        mv.addObject("totalItens", orcamentos.getTotalElements());

        mv.addObject("novoOrcamento", new Orcamento());

        if (id != null) {
            Orcamento orcamento = _orcamentoService.BuscaPorId(id);

            mv.addObject("orcamento", orcamento);
            mv.addObject("mostrarDropdown", true);
        }

        return mv;
    }

    public OrcamentoResponse create(CreateOrcamentoDTO orcamentoDTO) { return _orcamentoService.Criar(orcamentoDTO); }

    public Orcamento update(UpdateOrcamentoDTO orcamentoDTO) { return _orcamentoService.Atualizar(orcamentoDTO); }
}
