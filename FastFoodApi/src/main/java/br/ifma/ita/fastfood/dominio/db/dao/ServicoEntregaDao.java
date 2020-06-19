/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.dao;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.dominio.db.modelo.ServicoEntrega;

/**
 * @author dejailson
 *
 */
@Component
public interface ServicoEntregaDao extends BaseDao<ServicoEntrega> , IConsultaJpa<ServicoEntrega>{
	
}
