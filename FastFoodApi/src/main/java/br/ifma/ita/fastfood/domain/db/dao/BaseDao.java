/**
 * 
 */
package br.ifma.ita.fastfood.domain.db.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import br.ifma.ita.fastfood.domain.db.modelo.EntidadeBase;

/**
 * @author dejailson
 *
 */
@NoRepositoryBean
public interface BaseDao<T extends EntidadeBase> extends JpaRepositorioCustomizado<T, Long>, JpaSpecificationExecutor<T> {

	public List<T> pesquisarTodos();

}
