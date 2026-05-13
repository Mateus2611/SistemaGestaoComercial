package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmailRepository extends CrudRepository<Email, Integer> {

    @Query("SELECT '*' FROM `Email` WHERE Id_Cliente = :id ")
    List<Email> findAllById(@Param("id") Integer id);
}
