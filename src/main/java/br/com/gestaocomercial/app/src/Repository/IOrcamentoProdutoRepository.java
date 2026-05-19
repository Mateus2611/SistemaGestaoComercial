package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.OrcamentoProduto;
import org.springframework.data.repository.CrudRepository;

public interface IOrcamentoProdutoRepository extends CrudRepository<OrcamentoProduto, Integer> {

    Iterable<OrcamentoProduto> findAllById(Integer Id);
}
