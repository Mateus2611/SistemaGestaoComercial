package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Service.OrcamentoService;
import br.com.gestaocomercial.app.src.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VendaController {
    @Autowired
    private VendaService _vendaService;
    @Autowired
    private OrcamentoService _orcamentoService;

    @RequestMapping("/venda")
    public Iterable<Venda> get() { return _vendaService.BuscaGeral(); }

    public Venda getById(Integer id) { return _vendaService.BuscaPorId(id); }

    public Venda create(Integer idCliente, Integer prazo) {
        Orcamento orcamento = _orcamentoService.BuscaPorId(idCliente);
        return _vendaService.Criar(orcamento, prazo);
    }

    public Venda update(Integer id, String status) { return _vendaService.Atualizar(id, status); }
}
