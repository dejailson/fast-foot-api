/**
 * 
 */
package br.ifma.ita.fastfood.dominio.repositorio.filtro;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import br.ifma.ita.fastfood.core.validacao.NuloOuMaiorQue;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
public class ProdutoFiltro {
	
	private String nome;
	private BigDecimal valorInicial;
	@NuloOuMaiorQue(valor = 0)
	private BigDecimal valorFinal;
	
	
	public Boolean temParametro() {
		return (StringUtils.hasLength(nome)) || (valorInicial !=null) || (valorFinal !=null);
	}

}
