/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador;

import br.ifma.ita.fastfood.api.modelo.entrada.RestauranteModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.RestauranteModelo;
import br.ifma.ita.fastfood.domain.db.modelo.Endereco;
import br.ifma.ita.fastfood.domain.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
public interface IRestauranteEmpacotador extends IEmpacotador<RestauranteModeloIn, RestauranteModelo, Restaurante> {
	
	public RestauranteModelo empacotar(Endereco endereco);
}
