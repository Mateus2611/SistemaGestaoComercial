package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmailRepository extends CrudRepository<Email, Integer> {
}
