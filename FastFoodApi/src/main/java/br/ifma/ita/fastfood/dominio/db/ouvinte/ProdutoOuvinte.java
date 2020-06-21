/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.ouvinte;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import br.ifma.ita.fastfood.dominio.db.dao.ProdutoDao;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;

/**
 * @author dejailson
 *
 */
public class ProdutoOuvinte {
	
	private ProdutoDao dao;

	@Autowired
	public ProdutoOuvinte(@Lazy ProdutoDao dao) {
		this.dao = dao;
	}
	
	@PrePersist
	public void gerarSku(Produto produto) {
		var prefixoRestaurante = produto.getRestaurante().getPrefixoNome();
		var prefixoProduto = produto.getPrefixoNome();
		var valor= produto.getPrecoUnitario().toBigInteger();
		
		var novoSKU = produto.getTipo().getPrefixo()
				.concat(prefixoProduto)
				.concat(prefixoRestaurante)
				.concat(valor.toString());
		var contador = dao.contarPor(novoSKU);
		novoSKU = contador>0?novoSKU.concat(contador.toString()):novoSKU;
		
		produto.setSku(novoSKU.toUpperCase());
	}
	
	@Transactional
	@PreRemove
	public void exluirImagem(Produto produto) {
		
		var optional = dao.pesquisarImagemPorId(produto.getRestaurante(), produto);
		if (optional.isPresent()) {
			var imagem = optional.get();
			dao.excluirImagem(imagem);
		}
	}
}
