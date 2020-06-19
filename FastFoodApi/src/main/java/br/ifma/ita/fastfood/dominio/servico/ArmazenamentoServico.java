/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico;

import br.ifma.ita.fastfood.core.armazenamento.ArmazenamentoAdicionarEvento;
import br.ifma.ita.fastfood.core.armazenamento.ArmazenamentoRemoverEvento;
import br.ifma.ita.fastfood.core.armazenamento.ArmazenamentoSubstituirEvento;

/**
 * @author dejailson
 *
 */
public interface ArmazenamentoServico {

	public void adicionar(ArmazenamentoAdicionarEvento adicionar);

	public void substituir(ArmazenamentoSubstituirEvento substituir);

	public void excluir(ArmazenamentoRemoverEvento remover);

}
