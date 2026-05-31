package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IVendaRepository extends JpaRepository<Venda, Integer> {
    @Query(value = "SELECT * FROM Venda WHERE Status_Pagamento =:status", nativeQuery = true)
    Iterable<Venda> findAllByStatus(@Param("status") String status);
}
