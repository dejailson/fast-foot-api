/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.entrada;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
public class ServicoEntregaModeloIn{
	
	@NotBlank
	private String nome;

}
