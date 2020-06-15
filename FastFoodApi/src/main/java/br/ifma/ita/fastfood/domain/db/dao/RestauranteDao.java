/**
 * 
 */
package br.ifma.ita.fastfood.domain.db.dao;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.domain.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
@Component
public interface RestauranteDao extends BaseDao<Restaurante>, IConsultaJpa<Restaurante> {
	
	

}
