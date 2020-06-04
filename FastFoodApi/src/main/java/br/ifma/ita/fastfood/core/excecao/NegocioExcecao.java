/**
 * 
 */
package br.ifma.ita.fastfood.core.excecao;

/**
 * @author dejailson
 *
 */
public class NegocioExcecao extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NegocioExcecao(String mensagem) {
		super(mensagem);
	}
	
	public NegocioExcecao(String mensagem,Throwable e) {
		super(mensagem,e);
	}

}
