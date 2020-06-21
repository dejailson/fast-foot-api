/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.visualizacao;

/**
 * @author dejailson
 *
 */
public interface VisualizacaoJson {
	
	
	public interface Imagem{}
	
	public interface ProdutoResumo{}
	
	public interface ProdutoCompleto extends ProdutoResumo{};

	public interface Completo extends Resumo{}

	public interface Resumo{}
}
