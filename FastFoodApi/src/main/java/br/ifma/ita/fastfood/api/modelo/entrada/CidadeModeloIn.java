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
public class CidadeModeloIn{
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String uf;
	

}
