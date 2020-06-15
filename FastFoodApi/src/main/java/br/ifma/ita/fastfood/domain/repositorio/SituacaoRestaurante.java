/**
 * 
 */
package br.ifma.ita.fastfood.domain.repositorio;

/**
 * @author dejailson
 *
 */
public enum SituacaoRestaurante {
	
	ABERTO,FECHADO;
	
	public Boolean estaAberto() {
		return ABERTO.equals(this);
	}

}
