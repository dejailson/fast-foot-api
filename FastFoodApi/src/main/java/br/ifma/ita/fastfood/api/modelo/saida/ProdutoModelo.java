/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.saida;

import java.math.BigDecimal;

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
public class ProdutoModelo {
	
	@JsonView(VisualizacaoJson.ProdutoResumo.class)
	private String nome;
	@JsonView(VisualizacaoJson.ProdutoResumo.class)
	private String descricao;
	@JsonView({VisualizacaoJson.ProdutoResumo.class,VisualizacaoJson.Imagem.class})
	private String sku;
	@JsonView(VisualizacaoJson.ProdutoResumo.class)
	private String tipo;
	
	@JsonView(VisualizacaoJson.ProdutoResumo.class)
	private Long peso;
	
	@JsonView(VisualizacaoJson.ProdutoResumo.class)
	private BigDecimal precoUnitario;
	
	@JsonView(VisualizacaoJson.ProdutoCompleto.class)
	private RestauranteModelo restaurante;

}
