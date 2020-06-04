/**
 * 
 */
package br.ifma.ita.fastfood.domain.db.dao;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.domain.db.modelo.ServicoEntrega;

/**
 * @author dejailson
 *
 */
@Component
public interface ServicoEntregaDao extends BaseDao<ServicoEntrega> , IConsultaJpa<ServicoEntrega>{
	
}
