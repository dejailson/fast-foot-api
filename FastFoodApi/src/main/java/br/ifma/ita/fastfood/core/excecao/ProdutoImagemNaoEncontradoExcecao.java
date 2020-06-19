/**
 * 
 */
package br.ifma.ita.fastfood.core.excecao;

/**
 * @author dejailson
 *
 */
public class ProdutoImagemNaoEncontradoExcecao extends EntidadeNaoEncontradaExcecao {
	
	private static final String MENSAGEM = "Não existe imagem associado ao Produto com códido %d do Restaurante de código %s";
	private static final long serialVersionUID = 1L;
	
	public ProdutoImagemNaoEncontradoExcecao(Long produtoId, Long restauranteId) {
		super(String.format(MENSAGEM, produtoId,restauranteId));
	}

}
