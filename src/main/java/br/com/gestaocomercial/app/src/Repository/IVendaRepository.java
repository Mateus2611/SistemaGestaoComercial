package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVendaRepository extends JpaRepository<Venda, Integer> {
}
