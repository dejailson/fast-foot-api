/**
 * 
 */
package br.ifma.ita.fastfood.infraestrutura.repositorio;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ifma.ita.fastfood.dominio.db.dao.ProdutoDaoQueries;
import br.ifma.ita.fastfood.dominio.db.modelo.Imagem;
import br.ifma.ita.fastfood.dominio.db.modelo.Imagem_;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto_;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
@Repository
public class ProdutoDaoImpl implements ProdutoDaoQueries {

	@Autowired
	private EntityManager manager;

	@Override
	public List<Produto> pesquisarProdutosPor(Restaurante restaurante, Specification<Produto> specs) {

		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Produto.class);

		var root = criteria.from(Produto.class);
		root.fetch(Produto_.restaurante);

		Expression<Restaurante> campoRestaurante = root.get(Produto_.restaurante);

		var predicado = builder.equal(campoRestaurante, restaurante);

		var criterios = specs.toPredicate(root, criteria, builder);

		criteria.where(predicado, criterios);
		var query = manager.createQuery(criteria);

		return query.getResultList();
	}

	@Transactional
	@Override
	public Imagem salvarImagem(Imagem imagem) {
	
		return manager.merge(imagem);
	}

	@Transactional
	@Override
	public void excluirImagem(Imagem imagem) {
		manager.remove(imagem);
	}

	@Override
	public Optional<Imagem> pesquisarImagemPorId(Restaurante restaurante, Produto produto) {
		try {
			var builder = manager.getCriteriaBuilder();
			var criteria = builder.createQuery(Imagem.class);

			var root = criteria.from(Imagem.class);

			Join<Imagem, Produto> joinProduto = root.join(Imagem_.produto, JoinType.LEFT);

			Expression<Produto> campoProduto = root.get(Imagem_.produto);

			Expression<Restaurante> campoRestaurante = joinProduto.get(Produto_.restaurante);

			var predicadoProduto = builder.equal(campoProduto, produto);
			var predicadoRestaurante = builder.equal(campoRestaurante, restaurante);

			criteria.where(predicadoProduto, predicadoRestaurante);

			var query = manager.createQuery(criteria);

			return Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
