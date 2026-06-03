package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.DTO.UpdateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateVendaDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Service.OrcamentoService;
import br.com.gestaocomercial.app.src.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService _vendaService;
    @Autowired
    private OrcamentoService _orcamentoService;

    @RequestMapping
    public ModelAndView venda(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return carregarTelaBase(null, page);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        try {
            ModelAndView mv = new ModelAndView("venda");

            Venda vendaUnica = _vendaService.BuscaPorId(id);

            List<Venda> listaFiltrada = new ArrayList<>();
            listaFiltrada.add(vendaUnica);

            mv.addObject("vendas", listaFiltrada);

            Venda novaVenda = new Venda();
            novaVenda.setOrcamento(new Orcamento());
            mv.addObject("novaVenda", novaVenda);

            return mv;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "A venda de ID " + id + " não foi encontrada");

            return new ModelAndView("redirect:/venda");
        }
    }

    private ModelAndView carregarTelaBase(Integer id, Integer page) {
        ModelAndView mv = new ModelAndView("venda");
        Page<Venda> vendas = _vendaService.BuscaGeral(page);
        Iterable<Orcamento> orcamentos = _orcamentoService.BuscaPorStatusAprovado();

        mv.addObject("vendas", vendas.getContent());

        mv.addObject("paginaAtual", page);
        mv.addObject("totalPaginas", vendas.getTotalPages());
        mv.addObject("totalItens", vendas.getTotalElements());
        mv.addObject("orcamentos", orcamentos);

        mv.addObject("novaVenda", new Venda());

        if (id != null) {
            Venda venda = _vendaService.BuscaPorId(id);
            mv.addObject("venda", venda);
            mv.addObject("mostrarDropdown", true);
        }

        return mv;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("novaVenda") Venda venda) {
        _vendaService.Criar(venda);

        return "redirect:/venda";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            _vendaService.Atualizar(new UpdateVendaDTO(id, Venda.StatusPagamento.APROVADO));

            redirectAttributes.addFlashAttribute("mensagemSucesso", "O pagamento com o ID " + id + " foi APROVADO com sucesso!.");

            return "redirect:/venda";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não foi possível aprovar o pagamento " + id + " ocorreu um erro inesperado");
            return "redirect:/venda";
        }
    }

    @PostMapping("/cancel/{id}")
    public String reprobate(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            _vendaService.Atualizar(new UpdateVendaDTO(id, Venda.StatusPagamento.CANCELADO));

            redirectAttributes.addFlashAttribute("mensagemSucesso", "O pagamento com o ID " + id + " foi CANCELADO com sucesso!.");

            return "redirect:/venda";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não foi possível reprovar o cancelar " + id + " ocorreu um erro inesperado");
            return "redirect:/venda";
        }
    }
}
