/**
 * 
 */
package br.ifma.ita.fastfood.domain.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import br.ifma.ita.fastfood.core.excecao.RestauranteNaoEncontradoExcecao;
import br.ifma.ita.fastfood.domain.db.dao.RestauranteDao;
import br.ifma.ita.fastfood.domain.db.modelo.Endereco;
import br.ifma.ita.fastfood.domain.db.modelo.Restaurante;
import br.ifma.ita.fastfood.domain.especificacao.RestauranteEspec;
import br.ifma.ita.fastfood.domain.repositorio.filtro.RestauranteFiltro;

/**
 * @author dejailson
 *
 */
@Repository
public class RestauranteRepositorio {

	private RestauranteDao dao;

	@Autowired
	public RestauranteRepositorio(RestauranteDao dao) {
		this.dao = dao;
	}

	public Restaurante pesquisarPor(Long id) {
		return this.dao.findById(id).orElseThrow(()-> new RestauranteNaoEncontradoExcecao(id));
	}
	
	public Optional<Restaurante> pesquisarPor(String nome) {
		return dao.pesquisarPor(nome);
	}
	
	public List<Restaurante> pesquisarTodos(@Nullable RestauranteFiltro filtro){
		if (filtro !=null) {
			var especificacao = new RestauranteEspec.Construtor(filtro)
					.cidade()
					.servicoEntrega()
					.nome()
					.estaAberto()
					.and();
			return dao.findAll(especificacao);
		}
		return dao.pesquisarTodos();
	}
	
	public Endereco pesquisarEnderecoPor(Long idRestaurante){
		return this.dao.findById(idRestaurante).orElseThrow(()-> new RestauranteNaoEncontradoExcecao(idRestaurante)).getEndereco();
	}

}
