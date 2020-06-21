/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento.evento;

import br.ifma.ita.fastfood.core.armazenamento.servico.ArmazenamentoServicoOuvinte;
import lombok.Getter;

/**
 * @author dejailson
 *
 */
@Getter
public class ArmazenamentoRecuperarEvento {
	
	private String nomeArquivo;
	private ArmazenamentoServicoOuvinte notificador;

	public ArmazenamentoRecuperarEvento(String nomeArquivo,ArmazenamentoServicoOuvinte notificador) {
		this.nomeArquivo = nomeArquivo;
		this.notificador = notificador;
	}

}
