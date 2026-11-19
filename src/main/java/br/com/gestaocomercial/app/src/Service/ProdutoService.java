package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.DTO.ProdutoSyncDTO;
import br.com.gestaocomercial.app.src.Model.DTO.SyncEvent;
import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Repository.IProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoRepository _produtoRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public Produto Criar(Produto produto) {
        if (produto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            Produto salvo = _produtoRepository.save(produto);
            publicarSyncProduto(salvo, true);
            return salvo;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Page<Produto> BuscaGeral(Integer pagina) {
        try {
            Pageable pageable = PageRequest.of(pagina - 1, 15, Sort.by("id").descending());

            return _produtoRepository.findAll(pageable);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Produto> BuscaGeral() {
        try {
            return _produtoRepository.findAll();
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Produto BuscaPorId(Integer id) {
        try {
            return _produtoRepository.findById(id).orElse(null);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Produto> BuscaPorNome(String nome) {
        List<Produto> produtos = _produtoRepository.findAll();

        return produtos.stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome))
                .toList();
    }

    @Transactional
    public Produto Atualizar(Produto produto) {
        if (produto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            Produto salvo = _produtoRepository.save(produto);
            publicarSyncProduto(salvo, true);
            return salvo;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void Excluir(Integer id) {
        try {
            Produto produto = _produtoRepository.findById(id).orElse(null);
            _produtoRepository.deleteById(id);

            if (produto != null) {
                publicarSyncProduto(produto, false);
            }
        }
        catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void publicarSyncProduto(Produto produto, boolean ativo) {
        BigDecimal valorAtual = produto.getValor() != null ? produto.getValor() : BigDecimal.ZERO;

        ProdutoSyncDTO syncDTO = new ProdutoSyncDTO(
                produto.getId(),
                produto.getNome(),
                valorAtual,
                ativo
        );

        eventPublisher.publishEvent(new SyncEvent("/sync/produto", syncDTO));
    }
}