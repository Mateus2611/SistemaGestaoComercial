package br.com.gestaocomercial.app.src.Repository;

import br.com.gestaocomercial.app.src.Model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Integer> {}
