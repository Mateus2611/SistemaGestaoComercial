package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.OrcamentoProduto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IOrcamentoProdutoRepository extends CrudRepository<OrcamentoProduto, Integer> {

    @Query(value = "SELECT * FROM Orcamento_Produto WHERE Id_Orcamento = :id", nativeQuery = true)
    Iterable<OrcamentoProduto> findAllById(@Param("id") Integer Id);
}
