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
public class ArmazenamentoAdicionarEvento {
	
	private Armazenamento.Arquivo arquivo;

	public ArmazenamentoAdicionarEvento(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

}
