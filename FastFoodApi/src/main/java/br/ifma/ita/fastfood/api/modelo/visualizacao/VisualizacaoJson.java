/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.visualizacao;

/**
 * @author dejailson
 *
 */
public interface VisualizacaoJson {
	
	public interface Resumo{}
	
	public interface Completo extends Resumo{}
	
	public interface ProdutoResumo{}
	
	public interface ProdutoCompleto extends ProdutoResumo{};

}
