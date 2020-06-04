/**
 * 
 */
package br.ifma.ita.fastfood.domain.especificacao;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.ifma.ita.fastfood.domain.db.modelo.Cidade;
import br.ifma.ita.fastfood.domain.db.modelo.Cidade_;
import br.ifma.ita.fastfood.domain.db.modelo.Endereco;
import br.ifma.ita.fastfood.domain.db.modelo.Endereco_;
import br.ifma.ita.fastfood.domain.db.modelo.Restaurante;
import br.ifma.ita.fastfood.domain.db.modelo.Restaurante_;
import br.ifma.ita.fastfood.domain.db.modelo.ServicoEntrega;
import br.ifma.ita.fastfood.domain.db.modelo.ServicoEntrega_;
import br.ifma.ita.fastfood.domain.repositorio.filtro.RestauranteFiltro;
import br.ifma.ita.fastfood.domain.util.OperadorLogico;

/**
 * @author dejailson
 *
 */
public class RestauranteEspec {

	private static Specification<Restaurante> contemNome(String nome) {
		return (root, query, criteria) -> {
			root.fetch(Restaurante_.endereco).fetch(Endereco_.cidade);
			root.fetch(Restaurante_.servicoEntregas);
			Expression<String> campoNome = criteria.lower(root.get(Restaurante_.nome));
			
			query.distinct(true);

			return criteria.like(campoNome, MessageFormat.format("%{0}%", nome).toLowerCase());
		};
	}

	private static Specification<Restaurante> temCidadeIdIgual(Long cidadeId) {
		return (root, query, criteria) -> {
			root.fetch(Restaurante_.servicoEntregas);
			Join<Restaurante, Endereco> joinEndereco = root.join(Restaurante_.endereco, JoinType.LEFT);
			Join<Endereco, Cidade> joinCidade = joinEndereco.join(Endereco_.cidade,JoinType.LEFT);

			Expression<Long> campoCidadeId = joinCidade.get(Cidade_.id);

			query.distinct(true);

			return criteria.equal(campoCidadeId, cidadeId);
		};
	}

	private static Specification<Restaurante> temServicoEntregaIdIgual(Long servicoEntregaId) {
		return (root, query, criteria) -> {
			root.fetch(Restaurante_.endereco).fetch(Endereco_.cidade);
			Join<Restaurante, ServicoEntrega> joinServEntrega = root.join(Restaurante_.servicoEntregas,JoinType.LEFT);

			Expression<Long> campoServicoId = joinServEntrega.get(ServicoEntrega_.id);

			query.distinct(true);
			
			return criteria.equal(campoServicoId, servicoEntregaId);
		};
	}

	private static Specification<Restaurante> aberto() {
		return (root, query, criteria) -> {
			root.fetch(Restaurante_.endereco).fetch(Endereco_.cidade);
			root.fetch(Restaurante_.servicoEntregas);
			Expression<Boolean> campoAberto = root.get(Restaurante_.aberto);

			query.distinct(true);

			return criteria.equal(campoAberto, Boolean.TRUE);
		};
	}

	private static Specification<Restaurante> fechado() {
		return (root, query, criteria) -> {
			root.fetch(Restaurante_.endereco).fetch(Endereco_.cidade);
			root.fetch(Restaurante_.servicoEntregas);
			Expression<Boolean> campoAberto = root.get(Restaurante_.aberto);

			query.distinct(true);

			return criteria.equal(campoAberto, Boolean.FALSE);
		};
	}
	
	
	public static class Construtor extends OperadorLogico<Restaurante>{
		List<Specification<Restaurante>> especificacoes= new ArrayList<>();
		private RestauranteFiltro filtro;
		
		@Override
		protected List<Specification<Restaurante>> getSpecifications() {
			return especificacoes;
		}
		
		public Construtor nome() {
			if (StringUtils.hasText(filtro.getNome())) {
				especificacoes.add(contemNome(filtro.getNome()));
			}	
			return this;
		}
		
		public Construtor cidade() {
			if (filtro.getCodigoCidade() !=null) {
				especificacoes.add(temCidadeIdIgual(filtro.getCodigoCidade()));
			}
			return this;
		}
		
		public Construtor servicoEntrega() {
			if (filtro.getCodigoServicoEntrega() !=null) {
				especificacoes.add(temServicoEntregaIdIgual(filtro.getCodigoServicoEntrega()));
			}
			return this;
		}
		
		public Construtor estaAberto() {
			
			if (filtro.getSituacao() !=null) {
				if (filtro.getSituacao().estaAberto()) {
					especificacoes.add(aberto());
				}else {
					especificacoes.add(fechado());
				}
			}
			
			return this;
		}
		public Construtor(RestauranteFiltro filtro) {
			this.filtro = filtro;
		}
	}
}
