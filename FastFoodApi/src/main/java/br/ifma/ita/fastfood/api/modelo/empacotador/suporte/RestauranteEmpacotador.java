/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador.suporte;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.api.modelo.empacotador.IRestauranteEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.RestauranteModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.RestauranteModelo;
import br.ifma.ita.fastfood.dominio.db.modelo.Cidade;
import br.ifma.ita.fastfood.dominio.db.modelo.Endereco;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
@Component
public class RestauranteEmpacotador extends EmpacotadorSuporte<RestauranteModeloIn, RestauranteModelo, Restaurante>
implements IRestauranteEmpacotador{

	public RestauranteEmpacotador() {
		super(RestauranteModelo.class, Restaurante.class);
	}

	@Override
	public void copiar(RestauranteModeloIn modeloIn, Restaurante entidade) {
		entidade.getEndereco().setCidade(new Cidade());
		super.copiar(modeloIn, entidade);
	}

	@Override
	public RestauranteModelo empacotar(Endereco endereco) {
		var entidade = new Restaurante();
		entidade.setEndereco(endereco);
		
		return super.empacotar(entidade);
	}

}
