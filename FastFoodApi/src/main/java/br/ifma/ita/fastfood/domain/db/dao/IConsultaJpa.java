/**
 * 
 */
package br.ifma.ita.fastfood.domain.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import br.ifma.ita.fastfood.domain.db.modelo.EntidadeBase;

/**
 * @author dejailson
 *
 */
@NoRepositoryBean
public interface IConsultaJpa<T extends EntidadeBase> {
	
	public Optional<T> pesquisarPor(@Param("nome") String nome);
	
	public List<T> contem(@Param("nome") String nome);

}
