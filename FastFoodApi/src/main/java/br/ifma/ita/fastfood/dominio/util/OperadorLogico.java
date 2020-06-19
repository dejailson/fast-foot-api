/**
 * 
 */
package br.ifma.ita.fastfood.dominio.util;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.ifma.ita.fastfood.dominio.db.modelo.EntidadeBase;

/**
 * @author dejailson
 *
 */
public abstract class OperadorLogico <T extends EntidadeBase> implements IOperadorLogico<T>{
	
	protected abstract List<Specification<T>> getSpecifications();
	
	@Override
	public Specification<T> and() {
		if (getSpecifications().isEmpty()) {
			return null;
		}
		return getSpecifications().stream().reduce(null, (proximo,atual) -> atual.and(proximo));
	}
	
	@Override
	public Specification<T> or() {
		if (getSpecifications().isEmpty()) {
			return null;
		}
		return getSpecifications().stream().reduce(null, (proximo,atual) -> atual.or(proximo));
	}

}
