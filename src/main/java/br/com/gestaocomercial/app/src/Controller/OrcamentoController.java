package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.*;
import br.com.gestaocomercial.app.src.Model.DTO.CreateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Service.ClienteService;
import br.com.gestaocomercial.app.src.Service.OrcamentoService;
import br.com.gestaocomercial.app.src.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orcamento")
public class OrcamentoController {

    @Autowired
    private OrcamentoService _orcamentoService;

    @Autowired
    private ProdutoService _produtoService;

    @Autowired
    private ClienteService _clienteService;

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

        List<Cliente> clientes = _clienteService.BuscaGeral(Cliente.StatusCliente.ATIVO.name());

        mv.addObject("clientes", clientes);
        mv.addObject("produtos", _produtoService.BuscaGeral());

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

    @PostMapping("/create")
    public String create(@ModelAttribute("novoOrcamento") CreateOrcamentoDTO dto, RedirectAttributes redirectAttributes) {
        try {
            Orcamento orcamento = _orcamentoService.criar(dto);

            redirectAttributes.addFlashAttribute("mensagemSucesso", "Orçamento" + orcamento.getId() + " criado com sucesso!");
            return "redirect:/orcamento/" + orcamento.getId();

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/orcamento";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao criar o orçamento: " + e.getMessage());
            return "redirect:/orcamento";
        }
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            _orcamentoService.Atualizar(new UpdateOrcamentoDTO(id, Orcamento.StatusOrcamento.APROVADO));

            redirectAttributes.addFlashAttribute("mensagemSucesso", "O orçamento com o ID " + id + " foi APROVADO com sucesso!.");

            return "redirect:/orcamento";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não foi possível aprovar o orçamento " + id + " ocorreu um erro inesperado");
            return "redirect:/orcamento";
        }
    }

    @PostMapping("/reprobate/{id}")
    public String reprobate(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            _orcamentoService.Atualizar(new UpdateOrcamentoDTO(id, Orcamento.StatusOrcamento.REPROVADO));

            redirectAttributes.addFlashAttribute("mensagemSucesso", "O orçamento com o ID " + id + " foi REPROVADO com sucesso!.");

            return "redirect:/orcamento";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não foi possível reprovar o orçamento " + id + " ocorreu um erro inesperado");
            return "redirect:/orcamento";
        }
    }
}
