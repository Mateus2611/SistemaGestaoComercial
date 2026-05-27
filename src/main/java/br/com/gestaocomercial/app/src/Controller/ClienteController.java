package br.com.gestaocomercial.app.src.Controller;

import br.com.gestaocomercial.app.src.Model.*;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateClienteDTO;
import br.com.gestaocomercial.app.src.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService _clienteService;

    @RequestMapping
    public ModelAndView cliente(@RequestParam(name = "page", defaultValue = "1") Integer page) { return carregarTelaBase(null, page); }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Cliente cliente = _clienteService.BuscaPorId(id);

        if (cliente == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "O cliente com o ID " + id + " não foi encontrado.");

            return new ModelAndView("redirect:/cliente");
        }

        return carregarTelaBase(id, 1);
    }

    private ModelAndView carregarTelaBase(Integer id, Integer page) {
        ModelAndView mv = new ModelAndView("cliente");
        Page<Cliente> clientes = _clienteService.BuscaGeral(page);
        Cliente novoCliente = new Cliente();
        novoCliente.setEndereco(new Endereco());

        List<Email> emailsIniciais = new ArrayList<>();
        emailsIniciais.add(new Email());
        novoCliente.setEmails(emailsIniciais);

        mv.addObject("clientes", clientes.getContent());

        mv.addObject("paginaAtual", page);
        mv.addObject("totalPaginas", clientes.getTotalPages());
        mv.addObject("totalItens", clientes.getTotalElements());

        mv.addObject("novoCliente", novoCliente);

        if (id != null) {
            Cliente cliente = _clienteService.BuscaPorId(id);
            mv.addObject("cliente", cliente);
            mv.addObject("mostrarDropdown", true);
        }

        return mv;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("novoCliente") Cliente cliente) {

        Cliente novoCliente = _clienteService.Criar(cliente);

        return "redirect:/cliente/" + novoCliente.getId();
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("cliente") Cliente cliente, @RequestParam(value = "emailsInput", required = false) List<String> emails, RedirectAttributes redirectAttributes) {
        cliente.setId(id);

        List<Email> listaDeEmailsNovos = new ArrayList<>();

        if (emails != null && !emails.isEmpty()) {
            for (String emailTexto : emails) {
                String emailLimpo = emailTexto.trim();
                if (!emailLimpo.isEmpty()) {
                    Email novoEmailObj = new Email();

                    novoEmailObj.setEndereco(emailLimpo);

                    novoEmailObj.setCliente(cliente);

                    listaDeEmailsNovos.add(novoEmailObj);
                }
            }
        }

        cliente.setEmails(listaDeEmailsNovos);

        _clienteService.Atualizar(cliente);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cliente atualizado com sucesso!");

        return "redirect:/cliente/" + id;
    }

    public Cliente disable(Integer id) { return _clienteService.InativarCliente(id); }

    public Cliente activate(Integer id) { return _clienteService.AtivarCliente(id); }

    public List<Email> addEmails(Integer idCliente, List<Email> emails) { return _clienteService.CriarEmailCliente(idCliente, emails); }

    public void deleteEmail(Integer emailId) { _clienteService.ExcluirEmailCliente(emailId); }

    public List<Email> getClientEmails(Integer clientId) { return _clienteService.ExibirEmailsCliente(clientId); }


}
