package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProdutoRepository extends CrudRepository<Produto, Integer> {}
