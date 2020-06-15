/**
 * 
 */
package br.ifma.ita.fastfood.core.excecao;

/**
 * @author dejailson
 *
 */
public class ServicoEntregaNaoEncontradoExcecao extends EntidadeNaoEncontradaExcecao {
	
	private static final String MENSAGEM = "Não existe Serviço de Entrega cadastrada com o Código %d";
	private static final long serialVersionUID = 1L;
	
	public ServicoEntregaNaoEncontradoExcecao(Long id) {
		super(String.format(MENSAGEM, id));
	}

}
