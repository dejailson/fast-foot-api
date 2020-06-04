/**
 * 
 */
package br.ifma.ita.fastfood.domain.util;

import org.springframework.data.jpa.domain.Specification;

import br.ifma.ita.fastfood.domain.db.modelo.EntidadeBase;

/**
 * @author dejailson
 *
 */
public interface IOperadorLogico<T extends EntidadeBase> {
	
	Specification<T> and();
	Specification<T> or();
}
