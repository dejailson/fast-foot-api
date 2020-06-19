/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import br.ifma.ita.fastfood.dominio.db.modelo.Imagem;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
public interface ProdutoDaoQueries{
	
	public List<Produto> pesquisarProdutosPor(Restaurante restaurante,Specification<Produto> espc);	
	
	public Optional<Imagem> pesquisarImagemPorId(Restaurante restaurante, Produto produto);
	
	public Imagem salvarImagem(Imagem imagem);
	
	public void excluirImagem(Imagem imagem);
	
}
