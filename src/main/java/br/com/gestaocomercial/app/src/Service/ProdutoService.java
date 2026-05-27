package br.com.gestaocomercial.app.src.Service;

import br.com.gestaocomercial.app.src.Model.Produto;
import br.com.gestaocomercial.app.src.Repository.IProdutoRepository;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoRepository _produtoRepository;

    public Produto Criar(Produto produto) {
        if (produto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            return _produtoRepository.save(produto);
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

    public Produto BuscaPorId(Integer id) {
        try {
            return _produtoRepository.findById(id).orElse(null);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Produto Atualizar(Produto produto) {
        if (produto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            return _produtoRepository.save(produto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void Excluir(Integer id) {
        try {
            _produtoRepository.deleteById(id);
        }
        catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
