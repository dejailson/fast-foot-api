/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.saida;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import br.ifma.ita.fastfood.api.modelo.visualizacao.VisualizacaoJson;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class CidadeModelo{
	

	@JsonProperty(value = "codigo",index = 1)
	private Long id;
	
	@JsonView(VisualizacaoJson.Resumo.class)
	private String nome;
	@JsonView(VisualizacaoJson.Resumo.class)
	private String uf;

}
