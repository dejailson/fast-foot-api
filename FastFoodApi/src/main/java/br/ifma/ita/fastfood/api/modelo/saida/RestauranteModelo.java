/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.saida;

import java.util.Set;

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
public class RestauranteModelo{
	
	@JsonView(VisualizacaoJson.Resumo.class)
	@JsonProperty(value = "codigo", index = 1)
	private Long id;
	
	@JsonView(VisualizacaoJson.Resumo.class)
	private String nome;
	@JsonView(VisualizacaoJson.Resumo.class)
	private String descricao;
	
	@JsonView(VisualizacaoJson.Resumo.class)
	private Boolean aberto;
	
	@JsonView(VisualizacaoJson.Completo.class)
	private EnderecoModelo endereco;
	
	@JsonView(VisualizacaoJson.Resumo.class)
	private CidadeModelo cidade;
	
	@JsonView(VisualizacaoJson.Completo.class)
	private Set<ServicoEntregaModelo> servicoEntregas;
}
