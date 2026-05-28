package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.DTO.UpdateClienteDTO;
import br.com.gestaocomercial.app.src.Model.Email;
import br.com.gestaocomercial.app.src.Model.Endereco;
import br.com.gestaocomercial.app.src.Repository.IClienteRepository;
import br.com.gestaocomercial.app.src.Repository.IEmailRepository;
import br.com.gestaocomercial.app.src.Repository.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Cliente Criar(Cliente cliente) {

        try {

            if (cliente == null)
                throw new RuntimeException("Dados do cliente vazio. Preencha as informações");
            if (cliente.getEndereco() == null)
                throw new RuntimeException("Dados de endereço vazio. Preencha as informações");
            if (cliente.getEmails() == null)
                throw new RuntimeException("Endereço de email vazio. Preencha a informação");

            cliente.setDataCadastro(Date.valueOf(LocalDate.now()));
            cliente.setStatus(Cliente.StatusCliente.ATIVO);
            return _clienteRepository.save(cliente);

        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Page<Cliente> BuscaGeral(Integer pagina) {
        try {
            Pageable pageable = PageRequest.of(pagina - 1, 15, Sort.by("id").descending());

            return _clienteRepository.findAll(pageable);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente BuscaPorId(Integer id) {
        try {
            return _clienteRepository.findById(id).get();
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente Atualizar(Cliente clienteUpdate) {
        Cliente cliente = _clienteRepository.findById(clienteUpdate.getId()).get();
        List<Email> emailsAtualizados = new ArrayList<>();

        if (clienteUpdate.getNome() != null && clienteUpdate.getNome() != cliente.getNome()) cliente.setNome(clienteUpdate.getNome());
        if (clienteUpdate.getTipo() != null && clienteUpdate.getTipo() != cliente.getTipo()) cliente.setTipo(clienteUpdate.getTipo());

        if (clienteUpdate.getStatus() != null && clienteUpdate.getStatus() != cliente.getStatus()) {
            if (clienteUpdate.getStatus() == Cliente.StatusCliente.ATIVO) cliente.setStatus(clienteUpdate.getStatus());
            else {
                cliente.setStatus(clienteUpdate.getStatus());
                cliente.setDataInativacao(Date.valueOf(LocalDate.now()));
            }
        }

        clienteUpdate.getEmails().forEach(eu -> {
            if (!cliente.getEmails().contains(eu)) emailsAtualizados.add(eu);
            if (cliente.getEmails().contains(eu)) emailsAtualizados.addAll(cliente.getEmails());
        });

        cliente.setEmails(emailsAtualizados);

        if (clienteUpdate.getEndereco() != null) {

            Endereco enviaEnd = clienteUpdate.getEndereco();
            Endereco bancoEnd = cliente.getEndereco();

            if (enviaEnd.getLogradouro() != null && !enviaEnd.getLogradouro().trim().isEmpty()
                    && !enviaEnd.getLogradouro().equals(bancoEnd.getLogradouro())) {
                bancoEnd.setLogradouro(enviaEnd.getLogradouro().trim());
            }

            if (enviaEnd.getBairro() != null && !enviaEnd.getBairro().trim().isEmpty()
                    && !enviaEnd.getBairro().equals(bancoEnd.getBairro())) {
                bancoEnd.setBairro(enviaEnd.getBairro().trim());
            }

            if (enviaEnd.getCidade() != null && !enviaEnd.getCidade().trim().isEmpty()
                    && !enviaEnd.getCidade().equals(bancoEnd.getCidade())) {
                bancoEnd.setCidade(enviaEnd.getCidade().trim());
            }

            if (enviaEnd.getEstado() != null && !enviaEnd.getEstado().trim().isEmpty()
                    && !enviaEnd.getEstado().equals(bancoEnd.getEstado())) {
                bancoEnd.setEstado(enviaEnd.getEstado().trim());
            }

            if (enviaEnd.getCep() != null && !enviaEnd.getCep().trim().isEmpty()
                    && !enviaEnd.getCep().equals(bancoEnd.getCep())) {
                bancoEnd.setCep(enviaEnd.getCep().trim());
            }
        }

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
                email.getCliente().setId(idCliente);
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
