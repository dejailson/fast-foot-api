/**
 * 
 */
package br.ifma.ita.fastfood.core.excecao;

/**
 * @author dejailson
 *
 */
public class RestauranteNaoEncontradoExcecao extends EntidadeNaoEncontradaExcecao {
	
	private static final String MENSAGEM = "Não existe Restaurante cadastrado com o Código %s";
	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncontradoExcecao(Long id) {
		super(String.format(MENSAGEM, Long.toString(id)));
	}

}
