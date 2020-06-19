/**
 * 
 */
package br.ifma.ita.fastfood.dominio.repositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ifma.ita.fastfood.core.excecao.ProdutoImagemNaoEncontradoExcecao;
import br.ifma.ita.fastfood.dominio.db.dao.ProdutoDao;
import br.ifma.ita.fastfood.dominio.db.modelo.Imagem;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
@Repository
public class ProdutoImagemRepositorio {
	
	private ProdutoDao dao;
	
	@Autowired
	public ProdutoImagemRepositorio(ProdutoDao dao) {
		this.dao = dao;
	};
		
	public Imagem pesquisarPor(Produto produto, Restaurante restaurante) {
		return dao.pesquisarImagemPorId(restaurante, produto).orElseThrow(() -> 
			new ProdutoImagemNaoEncontradoExcecao(produto.getId(),restaurante.getId()));
	}

}
