/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.saida;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.ifma.ita.fastfood.api.modelo.visualizacao.VisualizacaoJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class ServicoEntregaModelo{
	
	@JsonProperty(value = "codigo",index = 1)
	private Long id;
	
	@JsonView(VisualizacaoJson.Completo.class)
	private String nome;

}
