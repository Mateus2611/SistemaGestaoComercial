package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateClienteDTO;
import br.com.gestaocomercial.app.src.Model.Email;
import br.com.gestaocomercial.app.src.Model.Endereco;
import br.com.gestaocomercial.app.src.Repository.IClienteRepository;
import br.com.gestaocomercial.app.src.Repository.IEmailRepository;
import br.com.gestaocomercial.app.src.Repository.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IEnderecoRepository _enderecoRepository;
    @Autowired
    private IEmailRepository _emailRepository;
    @Autowired
    private IClienteRepository _clienteRepository;

    public Cliente Criar(Cliente cliente, Endereco endereco, List<Email> emails) {

        if (cliente == null)
            throw new RuntimeException("Dados do cliente vazio. Preencha as informações");
        if (endereco == null)
            throw new RuntimeException("Dados de endereço vazio. Preencha as informações");
        if (emails== null)
            throw new RuntimeException("Endereço de email vazio. Preencha a informação");

        try {
            endereco = _enderecoRepository.save(endereco);
            cliente.setIdEndereco(endereco.getId());
            cliente = _clienteRepository.save(cliente);
            cliente.setEndereco(endereco);

            for (Email email :  emails) {
                email.setIdCliente(cliente.getId());
                cliente.setEmails(Collections.singletonList(_emailRepository.save(email)));
            }

            return cliente;

        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Iterable<Cliente> BuscaGeral() {
        try {
            Iterable<Cliente> clientes = _clienteRepository.findAll();

            for (Cliente cliente : clientes) {
                cliente.setEndereco(_enderecoRepository.findById(cliente.getId()).get());
            }

            return clientes;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente BuscaPorId(Integer id) {
        try {
            Cliente cliente = _clienteRepository.findById(id).get();

            cliente.setEndereco(_enderecoRepository.findById(cliente.getIdEndereco()).get());

            cliente.setEmails(_emailRepository.findAllById(cliente.getId()));

            return cliente;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente Atualizar(UpdateClienteDTO clienteDTO) {
        Cliente cliente = _clienteRepository.findById(clienteDTO.id).get();

        if (clienteDTO.Nome != null) cliente.setNome(clienteDTO.Nome);
        if (clienteDTO.Tipo != null) cliente.setTipo(Cliente.TipoCliente.valueOf(clienteDTO.Tipo));

        if (clienteDTO.Endereco != null) {
            Endereco endereco = _enderecoRepository.save(clienteDTO.Endereco);

            _enderecoRepository.deleteById(cliente.getIdEndereco());

            cliente.setIdEndereco(endereco.getId());
        };

        return _clienteRepository.save(cliente);
    }

    public Cliente InativarCliente(int id) {
        try {

            _clienteRepository.Disable(id, Date.valueOf(LocalDate.now()));

            return BuscaPorId(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente AtivarCliente(int id) {
        try {

            _clienteRepository.Activate(id);

            return BuscaPorId(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Email> ExibirEmailsCliente(Integer idCliente) {
        try {
            return _emailRepository.findAllById(idCliente);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Email> CriarEmailCliente(Integer idCliente, List<Email> emails) {
        try {
            List<Email> novosEmails = new ArrayList<Email>();

            for (Email email : emails) {
                email.setIdCliente(idCliente);
                novosEmails.add(_emailRepository.save(email));
            }

            return novosEmails;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void ExcluirEmailCliente(Integer idEmail) {
        try {
            _emailRepository.deleteById(idEmail);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
