/**
 * 
 */
package br.ifma.ita.fastfood.core.excecao;

/**
 * @author dejailson
 *
 */
public class CampoDuplicadoExcecao extends NegocioExcecao {

	private static final String MENSAGEM = "Já existe %s cadastrada com o %s de %s";
	private static final long serialVersionUID = 1L;

	public CampoDuplicadoExcecao(String entidade, String campo, String valor) {
		super(String.format(MENSAGEM, entidade,campo,valor));
	}

}
