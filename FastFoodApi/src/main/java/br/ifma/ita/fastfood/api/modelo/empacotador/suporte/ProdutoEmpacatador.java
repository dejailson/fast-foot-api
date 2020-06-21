/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador.suporte;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.api.modelo.empacotador.IProdutoEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.ProdutoModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.ProdutoModelo;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;

/**
 * @author dejailson
 *
 */
@Component
public class ProdutoEmpacatador extends EmpacotadorSuporte<ProdutoModeloIn, ProdutoModelo, Produto> implements IProdutoEmpacotador {

	public ProdutoEmpacatador() {
		super(ProdutoModelo.class, Produto.class);
	}
}
