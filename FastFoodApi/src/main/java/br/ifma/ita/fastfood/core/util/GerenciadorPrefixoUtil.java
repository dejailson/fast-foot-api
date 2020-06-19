/**
 * 
 */
package br.ifma.ita.fastfood.core.util;

import org.springframework.util.StringUtils;

/**
 * @author dejailson
 *
 */
public class GerenciadorPrefixoUtil {

	public static String gerarPrefixo(String valor, int tamanhoMaximo) {
		var apenas_letras = StringUtils.trimAllWhitespace(valor);

		if (apenas_letras.length() <= 4) {
			return apenas_letras;
		}

		return valor.substring(0, 4);
	}

}
