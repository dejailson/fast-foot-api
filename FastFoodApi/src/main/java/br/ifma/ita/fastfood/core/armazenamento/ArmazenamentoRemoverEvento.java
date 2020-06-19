/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento;

import lombok.Getter;

/**
 * @author dejailson
 *
 */
@Getter
public class ArmazenamentoRemoverEvento {
	
	private String nomeArquivo;
	
	public ArmazenamentoRemoverEvento(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}
