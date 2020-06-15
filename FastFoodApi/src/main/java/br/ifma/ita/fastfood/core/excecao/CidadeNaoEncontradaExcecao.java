/**
 * 
 */
package br.ifma.ita.fastfood.core.excecao;

/**
 * @author dejailson
 *
 */
public class CidadeNaoEncontradaExcecao extends EntidadeNaoEncontradaExcecao {
	
	private static final String MENSAGEM = "Não existe Cidade cadastrada com o Código %d";
	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncontradaExcecao(Long id) {
		super(String.format(MENSAGEM, id));
	}

}
