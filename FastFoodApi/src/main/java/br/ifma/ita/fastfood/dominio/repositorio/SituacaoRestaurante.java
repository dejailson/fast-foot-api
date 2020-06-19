/**
 * 
 */
package br.ifma.ita.fastfood.dominio.repositorio;

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
