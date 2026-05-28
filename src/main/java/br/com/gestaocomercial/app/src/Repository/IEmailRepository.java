package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmailRepository extends JpaRepository<Email, Integer> {

    @Query(value = "SELECT * FROM Email WHERE Id_Cliente = :id", nativeQuery = true)
    List<Email> findAllById(@Param("id") Integer id);
}
