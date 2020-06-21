/**
 * 
 */
package br.ifma.ita.fastfood.dominio.repositorio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ifma.ita.fastfood.core.excecao.ProdutoNaoEncontradoExcecao;
import br.ifma.ita.fastfood.dominio.db.dao.ProdutoDao;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;
import br.ifma.ita.fastfood.dominio.repositorio.filtro.ProdutoFiltro;
import br.ifma.ita.fastfood.infraestrutura.especificacao.ProdutoEspec;

/**
 * @author dejailson
 *
 */
@Repository
public class ProdutoRepositorio {
	
	private ProdutoDao dao;

	@Autowired
	public ProdutoRepositorio(ProdutoDao dao) {
		this.dao = dao;
	}
	
	public List<Produto> pesquisarPor(Restaurante restaurante, ProdutoFiltro filtro) {
		if (filtro.temParametro()) {
			var spec = new ProdutoEspec.Construtor(filtro)
					.nome()
					.precoUnitario()
					.and();
			return dao.pesquisarProdutosPor(restaurante,spec);
		}
		return dao.pesquisarProdutosPor(restaurante);
	}
	
	public Produto pesquisarProdutoPor(String sku, Restaurante restaurante) {
		return dao.pesquisarProdutoPor(sku,restaurante).orElseThrow(()-> new ProdutoNaoEncontradoExcecao(sku,restaurante.getId()));
	}
}
