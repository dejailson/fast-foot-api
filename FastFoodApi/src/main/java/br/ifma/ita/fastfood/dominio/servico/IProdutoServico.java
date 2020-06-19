/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico;

import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
public interface IProdutoServico extends IServico<Produto> {
	
	public void excluir(String sku,Restaurante restaurante);
	
	public Produto atualizar(Produto produto, Restaurante restaurante);

}
