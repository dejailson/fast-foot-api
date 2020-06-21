/**
 * 
 */
package br.ifma.ita.fastfood.core.excecao;

/**
 * @author dejailson
 *
 */
public class ProdutoNaoEncontradoExcecao extends EntidadeNaoEncontradaExcecao {
	
	private static final String MENSAGEM = "Não existe Produto cadastrado com SKU %s associado ao Restarurante de código %d";
	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoEncontradoExcecao(String sku, Long restauranteId) {
		super(String.format(MENSAGEM, sku,restauranteId));
	}

}
