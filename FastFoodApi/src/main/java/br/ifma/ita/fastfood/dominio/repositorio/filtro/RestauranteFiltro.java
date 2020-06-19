/**
 * 
 */
package br.ifma.ita.fastfood.dominio.repositorio.filtro;

import br.ifma.ita.fastfood.dominio.repositorio.SituacaoRestaurante;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
public class RestauranteFiltro {

	private String nome;
	private Long codigoCidade;
	private Long codigoServicoEntrega;
	private SituacaoRestaurante situacao;

}
