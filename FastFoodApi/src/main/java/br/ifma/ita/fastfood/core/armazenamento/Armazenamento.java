/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento;

import java.io.InputStream;

import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

/**
 * @author dejailson
 *
 */
public interface Armazenamento {

	default void adicionar(String nomeArquivoAntigo, Arquivo novoArquivo) {

		if (StringUtils.hasText(nomeArquivoAntigo)) {
			remover(nomeArquivoAntigo);
		}
		armazenar(novoArquivo);
	}

	public void armazenar(Arquivo novoArquivo);

	public void remover(String nomeArquivo);

	public ArquivoRecuperado recuperar(String nomeArquivo);

	@Builder
	@Getter
	class Arquivo {

		private String nomeArquivo;
		private String tipoConteudo;
		private InputStream inputStream;

	}

	@Builder
	@Getter
	class ArquivoRecuperado {

		private InputStream inputStream;
		private String url;

		public boolean temUrl() {
			return url != null;
		}

		public boolean temInputStream() {
			return inputStream != null;
		}

	}

}
