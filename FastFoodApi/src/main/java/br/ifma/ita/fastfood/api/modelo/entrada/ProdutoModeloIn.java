/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.entrada;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.ifma.ita.fastfood.core.validacao.VerificarNomeEnum;
import br.ifma.ita.fastfood.dominio.db.modelo.TipoProduto;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
public class ProdutoModeloIn {

	@NotBlank
	private String nome;
	@NotBlank
	private String descricao;

	@NotNull
	private Long peso;

	@NotNull
	private BigDecimal precoUnitario;

	@NotNull
	@VerificarNomeEnum(value = TipoProduto.class)
	private String tipo;
}
