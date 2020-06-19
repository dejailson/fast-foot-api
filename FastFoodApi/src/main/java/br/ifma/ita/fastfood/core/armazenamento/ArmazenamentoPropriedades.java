/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties("fastfood.api.armazenamento")
public class ArmazenamentoPropriedades {
	
	private Local local;
	private TipoArmazenamento tipo;
	
	public enum TipoArmazenamento {
		
		LOCAL;

	}
	
	public ArmazenamentoPropriedades() {
		this.local = new Local();
		tipo = TipoArmazenamento.LOCAL;
	}

	@Getter
	@Setter
	public class Local{
		private String nomeDiretorio = "temporario";
	}

}
