package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateClienteDTO;
import br.com.gestaocomercial.app.src.Model.Email;
import br.com.gestaocomercial.app.src.Model.Endereco;
import br.com.gestaocomercial.app.src.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService _clienteService;

    @RequestMapping("/cliente")
    public String cliente() {
        return "cliente";
    }

    public Iterable<Cliente> get() { return _clienteService.BuscaGeral(); }

    @RequestMapping("/cliente/{id}")
    public Cliente getById(@Param("id") Integer id) { return _clienteService.BuscaPorId(id); }

    public Cliente create(Cliente cliente, Endereco endereco, List<Email> emails) { return _clienteService.Criar(cliente, endereco, emails); }

    public Cliente update(UpdateClienteDTO clienteDTO) { return _clienteService.Atualizar(clienteDTO); }

    public Cliente disable(Integer id) { return _clienteService.InativarCliente(id); }

    public Cliente activate(Integer id) { return _clienteService.AtivarCliente(id); }

    public List<Email> addEmails(Integer idCliente, List<Email> emails) { return _clienteService.CriarEmailCliente(idCliente, emails); }

    public void deleteEmail(Integer emailId) { _clienteService.ExcluirEmailCliente(emailId); }

    public List<Email> getClientEmails(Integer clientId) { return _clienteService.ExibirEmailsCliente(clientId); }


}
