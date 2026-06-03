package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.DTO.CreateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Service.ClienteService;
import br.com.gestaocomercial.app.src.Service.OrcamentoService;
import br.com.gestaocomercial.app.src.Service.ProdutoService;
import br.com.gestaocomercial.app.src.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private OrcamentoService _orcamentoService;
    @Autowired
    private ClienteService _clienteService;
    @Autowired
    private ProdutoService _produtoService;
    @Autowired
    private VendaService _vendaService;

    @GetMapping
    public ModelAndView index() {
        return carregarTelaBase();
    }

    private ModelAndView carregarTelaBase() {
        ModelAndView mv = new ModelAndView("index");


        List<Orcamento> orcamentos = _orcamentoService.BuscaGeral();
        long qtdOrcamentosPendentes = orcamentos.stream()
                .filter(o -> o.getStatus() != null && o.getStatus().name().equals("PENDENTE"))
                .count();

        List<Venda> vendas = _vendaService.BuscaGeral();
        YearMonth mesAtual = YearMonth.now();

        long qtdVendasMes = vendas.stream()
                .filter(v -> v.getDataCriacao() != null)
                .filter(v -> {
                    LocalDate dataConvertida = v.getDataCriacao().toLocalDate();
                    return YearMonth.from(dataConvertida).equals(mesAtual);
                })
                .count();

        List<Venda> ultimasVendas = vendas.stream()
                .filter(v -> v.getDataCriacao() != null && v.getId() != null)
                .sorted((v1, v2) -> v2.getId().compareTo(v1.getId()))
                .limit(3)
                .toList();

        List<Cliente> clientes = _clienteService.BuscaGeral();
        long qtdClientesAtivos = clientes.stream()
                .filter(c -> c.getStatus() != null && c.getStatus().name().equals("ATIVO"))
                .count();

        List<Produto> produtos = _produtoService.BuscaGeral();
        long qtdProdutos = produtos.size();

        mv.addObject("qtdOrcamentosPendentes", qtdOrcamentosPendentes);
        mv.addObject("qtdVendasMes", qtdVendasMes);
        mv.addObject("qtdClientesAtivos", qtdClientesAtivos);
        mv.addObject("qtdProdutos", qtdProdutos);
        mv.addObject("ultimasVendas", ultimasVendas);

        mv.addObject("clientes", _clienteService.BuscaGeral());
        mv.addObject("produtos", _produtoService.BuscaGeral());
        mv.addObject("orcamentos", _orcamentoService.BuscaPorStatusAprovado());

        mv.addObject("novoOrcamento", new CreateOrcamentoDTO());
        mv.addObject("novoCliente", new Cliente());
        mv.addObject("novaVenda", new Venda());

        return mv;
    }
}