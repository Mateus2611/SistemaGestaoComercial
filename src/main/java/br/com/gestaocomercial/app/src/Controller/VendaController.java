package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.DTO.UpdateVendaDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Model.Venda;
import br.com.gestaocomercial.app.src.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VendaController {

    @Autowired
    private VendaService _vendaService;

    @RequestMapping("/venda")
    public Iterable<Venda> get() {
        return _vendaService.BuscaGeral();
    }

    public Venda getById(Integer id) {
        return _vendaService.BuscaPorId(id);
    }

    public Venda create(Venda venda, Orcamento orcamento) {
        return _vendaService.Criar(venda, orcamento);
    }

    public Venda update(UpdateVendaDTO vendaDTO) {
        return _vendaService.Atualizar(vendaDTO);
    }
}
