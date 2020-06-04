/**
 * 
 */
package br.ifma.ita.fastfood.domain.db.dao;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.domain.db.modelo.Cidade;

/**
 * @author dejailson
 *
 */
@Component
public interface CidadeDao extends BaseDao<Cidade>, IConsultaJpa<Cidade> {
}
