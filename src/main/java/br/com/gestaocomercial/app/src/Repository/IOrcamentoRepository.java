package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrcamentoRepository extends CrudRepository<Orcamento, Integer> {
    @Query(value = "SELECT * FROM Orcamento WHERE Status_Orcamento = :status", nativeQuery = true)
    Iterable<Orcamento> findAllByStatus(@Param("status") String Status);
}
