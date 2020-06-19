/**
 * 
 */
package br.ifma.ita.fastfood.dominio.util;

import org.springframework.data.jpa.domain.Specification;

import br.ifma.ita.fastfood.dominio.db.modelo.EntidadeBase;

/**
 * @author dejailson
 *
 */
public interface IOperadorLogico<T extends EntidadeBase> {
	
	Specification<T> and();
	Specification<T> or();
}
