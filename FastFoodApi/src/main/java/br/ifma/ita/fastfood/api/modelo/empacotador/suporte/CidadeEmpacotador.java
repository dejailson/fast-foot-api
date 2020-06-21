/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador.suporte;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.api.modelo.empacotador.ICidadeEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.CidadeModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.CidadeModelo;
import br.ifma.ita.fastfood.dominio.db.modelo.Cidade;

/**
 * @author dejailson
 *
 */
@Component
public class CidadeEmpacotador extends EmpacotadorSuporte<CidadeModeloIn, CidadeModelo, Cidade>
implements ICidadeEmpacotador{

	public CidadeEmpacotador() {
		super(CidadeModelo.class, Cidade.class);
	}
}
