/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador;

import br.ifma.ita.fastfood.api.modelo.entrada.ProdutoModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.ProdutoModelo;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;

/**
 * @author dejailson
 *
 */
public interface IProdutoEmpacotador extends IEmpacotador<ProdutoModeloIn, ProdutoModelo, Produto>{

}
