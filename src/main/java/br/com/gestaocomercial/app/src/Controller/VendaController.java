package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.DTO.UpdateVendaDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Model.Response.OrcamentoResponse;
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

    @GetMapping
    public ModelAndView venda(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return carregarTelaBase(null, page);
    }

    public Venda getById(Integer id) {
        return _vendaService.BuscaPorId(id);
    }

    private ModelAndView carregarTelaBase(Integer id, Integer page) {
        ModelAndView mv = new ModelAndView("venda");
        Page<Venda> vendas = _vendaService.BuscaGeral(page);
        Iterable<OrcamentoResponse> orcamentos = _orcamentoService.BuscaPorStatusAprovado();

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

        Venda novaVenda = _vendaService.Criar(venda);

        return "redirect:/venda";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("venda") Venda venda, Date prazoPagamento,
                         Venda.StatusPagamento statusPagamento, RedirectAttributes redirectAttributes) {

        UpdateVendaDTO vendaDTO = new UpdateVendaDTO();

        vendaDTO.id = id;
        vendaDTO.prazoPagamento = prazoPagamento;
        vendaDTO.statusPagamento = statusPagamento;

        _vendaService.Atualizar(vendaDTO);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Venda atualizada com sucesso!");

        return "redirect:/venda";
    }
}
