/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico.suporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.core.armazenamento.Armazenamento;
import br.ifma.ita.fastfood.core.armazenamento.ArmazenamentoAdicionarEvento;
import br.ifma.ita.fastfood.core.armazenamento.ArmazenamentoRemoverEvento;
import br.ifma.ita.fastfood.core.armazenamento.ArmazenamentoSubstituirEvento;
import br.ifma.ita.fastfood.dominio.servico.ArmazenamentoServico;

/**
 * @author dejailson
 *
 */
@Component
public class ArmazenamentoServicoEvento implements ArmazenamentoServico{
	
	private Armazenamento armazenamento;
	
	@Autowired
	public ArmazenamentoServicoEvento(Armazenamento armazenamento) {
		this.armazenamento = armazenamento;
	}

	@Override
	@EventListener
	public void adicionar(ArmazenamentoAdicionarEvento adicionar) {
		this.armazenamento.adicionar(null, adicionar.getArquivo());
	}
	
	@Override
	@EventListener
	public void substituir(ArmazenamentoSubstituirEvento substituir) {
		this.armazenamento.adicionar(substituir.getNomeArquivo(), substituir.getArquivo());
	}
	
	@Override
	@EventListener
	public void excluir(ArmazenamentoRemoverEvento remover) {
		this.armazenamento.remover(remover.getNomeArquivo());
	}
}
