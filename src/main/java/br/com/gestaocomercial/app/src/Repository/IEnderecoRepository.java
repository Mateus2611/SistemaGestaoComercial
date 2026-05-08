package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnderecoRepository extends CrudRepository<Endereco, Integer> {
}
