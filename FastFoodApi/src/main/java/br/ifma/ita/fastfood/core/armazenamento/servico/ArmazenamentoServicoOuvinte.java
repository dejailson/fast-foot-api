/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento.servico;

import br.ifma.ita.fastfood.core.armazenamento.Armazenamento.ArquivoRecuperado;

/**
 * @author dejailson
 *
 */
@FunctionalInterface
public interface ArmazenamentoServicoOuvinte {
	
	public void notificar(ArquivoRecuperado recuperado);

}
