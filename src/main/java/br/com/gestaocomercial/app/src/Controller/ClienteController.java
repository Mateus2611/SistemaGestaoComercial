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
    public ModelAndView getById(@PathVariable("id") Integer id) {

        ModelAndView mv = new ModelAndView("cliente");

        Cliente clienteUnico = _clienteService.BuscaPorId(id);

        List<Cliente> listaFiltrada = new ArrayList<>();
        if (clienteUnico != null) {
            listaFiltrada.add(clienteUnico);
        }

        mv.addObject("clientes", listaFiltrada);

        Cliente novoCliente = new Cliente();
        novoCliente.setEndereco(new Endereco());
        novoCliente.setEmails(new ArrayList<>(List.of(new Email())));
        mv.addObject("novoCliente", novoCliente);

        return mv;
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
                    listaDeEmailsNovos.add(new Email(cliente, emailTexto));

            }
        }

        cliente.setEmails(listaDeEmailsNovos);

        _clienteService.Atualizar(cliente);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cliente atualizado com sucesso!");

        return "redirect:/cliente/" + id;
    }

    @PostMapping("/disable/{id}")
    public String disable(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        _clienteService.InativarCliente(id);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cliente INATIVADO com sucesso!");

        return "redirect:/cliente/" + id;
    }

    @PostMapping("/activate/{id}")
    public String activate(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        _clienteService.AtivarCliente(id);

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Cliente ATIVADO com sucesso!");

        return "redirect:/cliente/" + id;
    }


}
