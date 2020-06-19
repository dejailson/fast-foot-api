/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.dao;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
@Component
public interface RestauranteDao extends BaseDao<Restaurante>, IConsultaJpa<Restaurante> {
	
	

}
