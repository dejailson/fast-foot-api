/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.saida;

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
@JsonView(VisualizacaoJson.Imagem.class)
public class ImagemModelo {
	
	private String nome;
	private String descricao;
	private String tipoConteudo;
	private ProdutoModelo produto;
}
