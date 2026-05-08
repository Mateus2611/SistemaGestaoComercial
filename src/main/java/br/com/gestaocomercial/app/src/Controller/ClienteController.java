package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService _clienteService;

    @RequestMapping("/cliente")
    public Iterable<Cliente> get() {
        return _clienteService.BuscaGeral();
    }
}
