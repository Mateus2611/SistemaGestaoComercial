package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IClienteRepository extends CrudRepository<Cliente, Integer> {
    @Query(value = "UPDATE Cliente SET Status_Cliente = 'INATIVO', Data_Inativacao = :date WHERE Id = :id", nativeQuery = true)
    void Disable(@Param("id") Integer id, @Param("data") Date date);

    @Query(value = "UPDATE Cliente SET Status_Cliente = 'ATIVO' WHERE Id = :id", nativeQuery = true)
    void Activate(@Param("id") Integer id);
}
