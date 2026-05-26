package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        Produto produto = _produtoService.BuscaPorId(id);

        if (produto == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O produto com o ID " + id + " não foi encontrado.");

            return new ModelAndView("redirect:/produto");
        }

        return carregarTelaBase(id, 1);
    }

    private ModelAndView carregarTelaBase(Integer idNovo, Integer page) {
        ModelAndView mv = new ModelAndView("produto");
        Page<Produto> produtos = _produtoService.BuscaGeral(page);

        mv.addObject("produtos", produtos.getContent());

        mv.addObject("paginaAtual", page);
        mv.addObject("totalPaginas", produtos.getTotalPages());
        mv.addObject("totalItens", produtos.getTotalElements());

        mv.addObject("novoProduto", new Produto());

        if (idNovo != null) {
            Produto produto = _produtoService.BuscaPorId(idNovo);
            mv.addObject("produto", produto);
            mv.addObject("mostrarDropdown", true);
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
