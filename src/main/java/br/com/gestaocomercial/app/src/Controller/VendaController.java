package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.DTO.UpdateVendaDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService _vendaService;

    @GetMapping
    public ModelAndView venda(@RequestParam(value = "page", defaultValue = "1") Integer page) { return carregarTelaBase(null, page); }

    public Venda getById(Integer id) {
        return _vendaService.BuscaPorId(id);
    }

    private ModelAndView carregarTelaBase(Integer id, Integer page) {
        ModelAndView mv = new ModelAndView("venda");
        Page<Venda> vendas = _vendaService.BuscaGeral(page);

        mv.addObject("vendas", vendas.getContent());

        mv.addObject("paginaAtual", page);
        mv.addObject("totalPaginas", vendas.getTotalPages());
        mv.addObject("totalItens", vendas.getTotalElements());

        mv.addObject("novaVenda", new Venda());

        if (id != null) {
            Venda venda = _vendaService.BuscaPorId(id);
            mv.addObject("venda", venda);
            mv.addObject("mostrarDropdown", true);
        }

        return mv;
    }

    public Venda create(Venda venda, Orcamento orcamento) {
        return _vendaService.Criar(venda, orcamento);
    }

    public Venda update(UpdateVendaDTO vendaDTO) {
        return _vendaService.Atualizar(vendaDTO);
    }
}
