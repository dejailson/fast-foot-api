/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento.servico;

import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoAdicionarEvento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoRecuperarEvento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoRemoverEvento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoSubstituirEvento;

/**
 * @author dejailson
 *
 */
public interface IArmazenamentoServico {

	public void adicionar(ArmazenamentoAdicionarEvento adicionar);

	public void substituir(ArmazenamentoSubstituirEvento substituir);

	public void excluir(ArmazenamentoRemoverEvento remover);
	
	public void recuperar(ArmazenamentoRecuperarEvento recupepar);

}
