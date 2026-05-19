package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.DTO.CreateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateOrcamentoDTO;
import br.com.gestaocomercial.app.src.Model.Orcamento;
import br.com.gestaocomercial.app.src.Service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrcamentoController {

    @Autowired
    private OrcamentoService _orcamentoService;

    @RequestMapping("/orcamento")
    public Iterable<Orcamento> get() { return _orcamentoService.BuscaGeral(); }

    public Orcamento getById(Integer id) { return _orcamentoService.BuscaPorId(id); }

    public Orcamento create(CreateOrcamentoDTO orcamentoDTO) { return _orcamentoService.Criar(orcamentoDTO); }

    public Orcamento update(UpdateOrcamentoDTO orcamentoDTO) { return _orcamentoService.Atualizar(orcamentoDTO); }
}
