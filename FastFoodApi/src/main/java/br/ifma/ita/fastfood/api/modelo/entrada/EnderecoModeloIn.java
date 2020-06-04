/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.entrada;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.ifma.ita.fastfood.api.modelo.id.InCidadeId;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
public class EnderecoModeloIn{

	@NotBlank
	private String logradouro;

	@NotBlank
	private String numero;

	@NotBlank
	private String cep;

	private String complemento;

	@NotBlank
	private String bairro;

	@Valid
	@NotNull
	private InCidadeId cidade;
}
