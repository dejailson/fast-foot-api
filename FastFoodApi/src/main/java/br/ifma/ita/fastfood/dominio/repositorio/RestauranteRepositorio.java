/**
 * 
 */
package br.ifma.ita.fastfood.dominio.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import br.ifma.ita.fastfood.core.excecao.RestauranteNaoEncontradoExcecao;
import br.ifma.ita.fastfood.dominio.db.dao.RestauranteDao;
import br.ifma.ita.fastfood.dominio.db.modelo.Endereco;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;
import br.ifma.ita.fastfood.dominio.repositorio.filtro.RestauranteFiltro;
import br.ifma.ita.fastfood.infraestrutura.especificacao.RestauranteEspec;

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
