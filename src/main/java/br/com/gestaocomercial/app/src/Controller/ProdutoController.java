package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.Email;
import br.com.gestaocomercial.app.src.Model.Endereco;
import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService _produtoService;

    @GetMapping
    public ModelAndView produto(@RequestParam(value = "page", defaultValue = "1") Integer page) { return carregarTelaBase(null, page); }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            return carregarTelaBase(id, 1);
        } catch (RuntimeException exception) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O produto com o ID " + id + " não foi encontrado.");
            return new ModelAndView("redirect:/produto");
        }
    }

    private ModelAndView carregarTelaBase(Integer id, Integer page) {
        ModelAndView mv = new ModelAndView("produto");

        mv.addObject("novoProduto", new Produto());

        if (id != null) {
            Produto produtoUnico = _produtoService.BuscaPorId(id);

            if (produtoUnico == null)
                throw new RuntimeException("Produto com ID " + id + " não foi encontrado.");

            List<Produto> listaFiltrada = java.util.Collections.singletonList(produtoUnico);

            mv.addObject("produtos", listaFiltrada);
            mv.addObject("paginaAtual", 1);
            mv.addObject("totalPaginas", 1);
            mv.addObject("totalItens", 1);

            mv.addObject("produto", produtoUnico);
            mv.addObject("isFiltrado", true);
            mv.addObject("mostrarDropdown", true);
        } else {
            Page<Produto> produtos = _produtoService.BuscaGeral(page);

            mv.addObject("produtos", produtos.getContent());
            mv.addObject("paginaAtual", page);
            mv.addObject("totalPaginas", produtos.getTotalPages());
            mv.addObject("totalItens", produtos.getTotalElements());

            mv.addObject("isFiltrado", false);
        }

        return mv;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("novoProduto") Produto produto) {

        Produto novoProduto = _produtoService.Criar(produto);

        return "redirect:/produto/" + novoProduto.getId();
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("produto") Produto produto, RedirectAttributes redirectAttributes) {
        produto.setId(id);

        _produtoService.Atualizar(produto);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "O produto foi atualizado com sucesso!");

        return "redirect:/produto";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            _produtoService.Excluir(id);

            redirectAttributes.addFlashAttribute("mensagemSucesso", "O produto foi removido com sucesso!");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemAlerta", "Não foi possível excluir o produto, pois ele está vinculado a outros orçamentos.");
        }

        return "redirect:/produto";
    }
}
