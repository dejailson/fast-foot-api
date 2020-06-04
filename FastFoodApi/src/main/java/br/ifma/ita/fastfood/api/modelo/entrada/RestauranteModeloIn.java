/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.entrada;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
public class RestauranteModeloIn{
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
	
	@Valid
	@NotNull
	private EnderecoModeloIn endereco;
}
