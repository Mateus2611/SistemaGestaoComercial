package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Orcamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrcamentoRepository extends CrudRepository<Orcamento, Integer> {
}
