/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento.infraestrutura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.core.armazenamento.Armazenamento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoAdicionarEvento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoRecuperarEvento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoRemoverEvento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoSubstituirEvento;
import br.ifma.ita.fastfood.core.armazenamento.servico.IArmazenamentoServico;

/**
 * @author dejailson
 *
 */
@Component
public class ArmazenamentoServico implements IArmazenamentoServico{
	
	private Armazenamento armazenamento;
	
	@Autowired
	public ArmazenamentoServico(Armazenamento armazenamento) {
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
	
	@Override
	@EventListener
	public void recuperar(ArmazenamentoRecuperarEvento recupepar) {
		var stream = this.armazenamento.recuperar(recupepar.getNomeArquivo());
		recupepar.getNotificador().notificar(stream);
	}
}
