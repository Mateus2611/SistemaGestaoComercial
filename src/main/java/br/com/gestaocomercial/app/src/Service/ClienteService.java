package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Cliente;
import br.com.gestaocomercial.app.src.Model.Email;
import br.com.gestaocomercial.app.src.Model.Endereco;
import br.com.gestaocomercial.app.src.Repository.IClienteRepository;
import br.com.gestaocomercial.app.src.Repository.IEmailRepository;
import br.com.gestaocomercial.app.src.Repository.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                cliente.Emails.add(_emailRepository.save(email));
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

//    public Cliente BuscaPorId(Integer id) {
//        try {
//            Cliente cliente = _clienteDAO.BuscaPorId(id);
//
//            cliente.setEndereco(_enderecoDAO.BuscaPorId(cliente.getIdEndereco()));
//
//            cliente.setEmails(_emailDAO.BuscaGeral(cliente.getId()));
//
//            return cliente;
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
//
//    public List<Email> ExibirEmailsCliente(Integer idCliente) {
//        try {
//            return _emailDAO.BuscaGeral(idCliente);
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
//
//    public Cliente AtualizarTipoCliente(Integer id, String tipoCliente) {
//        try {
//
//            _clienteDAO.AtualizarTipoCliente(id, tipoCliente);
//
//            return BuscaPorId(id);
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
//
//    public Cliente AtualizarNomeCliente(Integer id, String nome) {
//        try {
//
//            _clienteDAO.AtualizarNomeCliente(id, nome);
//
//            return BuscaPorId(id);
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
//
//    public Cliente AtualizarEnderecoCliente(Integer idCliente, Endereco endereco) {
//        try {
//            Cliente cliente = _clienteDAO.BuscaPorId(idCliente);
//            Integer idEnderecoDesatualizado = cliente.IdEndereco;
//
//            endereco = _enderecoDAO.Criar(endereco);
//            _clienteDAO.AtualizarEnderecoCliente(idCliente, endereco.getId());
//
//            _enderecoDAO.Excluir(idEnderecoDesatualizado);
//
//            return BuscaPorId(idCliente);
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
//
//    public List<Email> CriarEmailCliente(Integer idCliente, List<Email> emails) {
//        try {
//            List<Email> novosEmails = new ArrayList<Email>();
//
//            for (Email email : emails) {
//                email.setIdCliente(idCliente);
//                novosEmails.add(_emailDAO.Criar(email));
//            }
//
//            return novosEmails;
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
//
//    public void ExcluirEmailCliente(Integer idEmail) {
//        try {
//            _emailDAO.Excluir(idEmail);
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
//
//    public Cliente InativarCliente(int id) {
//        try {
//
//            _clienteDAO.InativarCliente(id);
//
//            return BuscaPorId(id);
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
//
//    public Cliente AtivarCliente(int id) {
//        try {
//
//            _clienteDAO.AtivarCliente(id);
//
//            return BuscaPorId(id);
//        } catch (RuntimeException ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//    }
}
