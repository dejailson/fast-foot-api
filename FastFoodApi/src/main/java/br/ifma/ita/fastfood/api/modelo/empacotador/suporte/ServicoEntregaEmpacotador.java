/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador.suporte;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.api.modelo.empacotador.IServicoEntregaEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.ServicoEntregaModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.ServicoEntregaModelo;
import br.ifma.ita.fastfood.dominio.db.modelo.ServicoEntrega;

/**
 * @author dejailson
 *
 */
@Component
public class ServicoEntregaEmpacotador extends EmpacotadorSuporte<ServicoEntregaModeloIn, ServicoEntregaModelo, ServicoEntrega>
implements IServicoEntregaEmpacotador{
	
	public ServicoEntregaEmpacotador() {
		super(ServicoEntregaModelo.class, ServicoEntrega.class);
	}
}
