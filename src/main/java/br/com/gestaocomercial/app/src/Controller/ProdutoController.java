package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.PrimitiveIterator;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService _produtoService;

    @RequestMapping("/produto")
    public Iterable<Produto> get() { return _produtoService.BuscaGeral(); }

    public Produto getById(Integer id) { return _produtoService.BuscaPorId(id); }

    public Produto create(Produto produto) { return _produtoService.Criar(produto); }

    public Produto update(Produto produto) { return _produtoService.Atualizar(produto); }

    public void delete(Integer id) { _produtoService.Excluir(id); }
}
