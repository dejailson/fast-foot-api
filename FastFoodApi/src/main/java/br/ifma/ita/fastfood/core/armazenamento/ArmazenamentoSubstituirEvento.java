/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento;

import br.ifma.ita.fastfood.core.armazenamento.Armazenamento.Arquivo;
import lombok.Getter;

/**
 * @author dejailson
 *
 */
@Getter
public class ArmazenamentoSubstituirEvento {
	
	private Armazenamento.Arquivo arquivo;
	private String nomeArquivo;
	
	public ArmazenamentoSubstituirEvento(Arquivo arquivo, String nomeArquivo) {
		this.arquivo = arquivo;
		this.nomeArquivo = nomeArquivo;
	}

}
