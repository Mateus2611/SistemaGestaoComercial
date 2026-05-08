package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends CrudRepository<Cliente, Integer> {
}
