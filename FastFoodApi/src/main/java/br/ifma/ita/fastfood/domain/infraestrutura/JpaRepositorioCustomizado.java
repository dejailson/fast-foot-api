/**
 * 
 */
package br.ifma.ita.fastfood.domain.infraestrutura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author dejailson
 *
 */
@NoRepositoryBean
public interface JpaRepositorioCustomizado<T,ID> extends JpaRepository<T, ID>{

	public void separar(T entidade);
}
