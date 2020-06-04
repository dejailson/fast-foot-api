/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.saida;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
public class EnderecoModelo{
	
	@JsonView(VisualizacaoJson.Completo.class)
	private String logradouro;

	@JsonView(VisualizacaoJson.Completo.class)
	private String numero;

	@JsonView(VisualizacaoJson.Completo.class)
	private String cep;

	@JsonView(VisualizacaoJson.Completo.class)
	private String complemento;

	@JsonView(VisualizacaoJson.Completo.class)
	private String bairro;
}
