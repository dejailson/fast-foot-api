/**
 * 
 */
package br.ifma.ita.fastfood.infraestrutura.especificacao;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Expression;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto_;
import br.ifma.ita.fastfood.dominio.repositorio.filtro.ProdutoFiltro;
import br.ifma.ita.fastfood.dominio.util.OperadorLogico;

/**
 * @author dejailson
 *
 */
public class ProdutoEspec {

	private static Specification<Produto> contemNome(String nome) {
		return (root, query, criteria) -> {

			root.fetch(Produto_.restaurante);

			Expression<String> campoNome = criteria.lower(root.get(Produto_.nome));

			//query.distinct(true);

			return criteria.like(campoNome, MessageFormat.format("%{0}%", nome).toLowerCase());
		};
	}

	private static Specification<Produto> eIgual(BigDecimal precoUnitario) {
		return (root, query, criteria) -> {

			root.fetch(Produto_.restaurante);

			Expression<BigDecimal> campoPreco = root.get(Produto_.precoUnitario);

			//query.distinct(true);

			return criteria.equal(campoPreco, precoUnitario);
		};
	}
	
	private static Specification<Produto> entre(BigDecimal precoUnitarioInicial,BigDecimal precoUnitarioFinal) {
		return (root, query, criteria) -> {

			root.fetch(Produto_.restaurante);

			Expression<BigDecimal> campoPreco = root.get(Produto_.precoUnitario);

			//query.distinct(true);

			return criteria.between(campoPreco, precoUnitarioInicial, precoUnitarioFinal);
		};
	}
	
	public static class Construtor extends OperadorLogico<Produto>{
		
		List<Specification<Produto>> predicados = new ArrayList<Specification<Produto>>();
		private ProdutoFiltro filtro;

		public Construtor(ProdutoFiltro filtro) {
			this.filtro = filtro;
		}

		@Override
		protected List<Specification<Produto>> getSpecifications() {
			return predicados;
		}
		
		public Construtor nome() {
			if (StringUtils.hasText(filtro.getNome())) {
				predicados.add(contemNome(filtro.getNome()));
			}
			return this;
		}
		
		public Construtor precoUnitario() {
			
			if (filtro.getValorInicial() !=null) {
				
				if (filtro.getValorFinal() !=null) {
					predicados.add(entre(filtro.getValorInicial(), filtro.getValorFinal()));
				}else {
					predicados.add(eIgual(filtro.getValorInicial()));
				}
			}
			return this;
		}	
		
	}

}
