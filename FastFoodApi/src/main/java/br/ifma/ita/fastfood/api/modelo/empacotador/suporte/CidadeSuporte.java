/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador.suporte;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.api.modelo.empacotador.ICidadeEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.CidadeModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.CidadeModelo;
import br.ifma.ita.fastfood.domain.db.modelo.Cidade;

/**
 * @author dejailson
 *
 */
@Component
public class CidadeSuporte extends EmpacotadorSuporte<CidadeModeloIn, CidadeModelo, Cidade>
implements ICidadeEmpacotador{

	public CidadeSuporte() {
		super(CidadeModelo.class, Cidade.class);
	}
	

}
