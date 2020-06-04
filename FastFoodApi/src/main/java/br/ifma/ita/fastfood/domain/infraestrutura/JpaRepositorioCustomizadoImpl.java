/**
 * 
 */
package br.ifma.ita.fastfood.domain.infraestrutura;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * @author dejailson
 *
 */
public class JpaRepositorioCustomizadoImpl<T,ID> extends SimpleJpaRepository<T, ID> implements JpaRepositorioCustomizado<T, ID> {

	private EntityManager manager;
	
	public JpaRepositorioCustomizadoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.manager = entityManager;
	}

	@Override
	public void separar(T entidade) {
		this.manager.detach(entidade);
	}

}
