/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
public interface ProdutoDao extends BaseDao<Produto>, ProdutoDaoQueries{
	
	public Integer contarPor(@Param("sku") String sku);
	
	public List<Produto> pesquisarProdutosPor(Restaurante restaurante);

	public Optional<Produto> pesquisarProdutoPor(@Param("sku") String sku, @Param("restaurante") Restaurante restaurante);
	

}
