/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.id;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
public class InCidadeId{
	@JsonProperty(value = "codigo")
	@NotNull
	private Long id;
}
