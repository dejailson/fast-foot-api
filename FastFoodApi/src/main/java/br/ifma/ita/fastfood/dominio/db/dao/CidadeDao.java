/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.dao;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.dominio.db.modelo.Cidade;

/**
 * @author dejailson
 *
 */
@Component
public interface CidadeDao extends BaseDao<Cidade>, IConsultaJpa<Cidade> {
}
