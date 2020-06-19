/**
 * 
 */
package br.ifma.ita.fastfood.infraestrutura.repositorio;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.ifma.ita.fastfood.dominio.db.dao.JpaRepositorioCustomizado;

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
