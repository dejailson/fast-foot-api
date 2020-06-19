/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico;

import java.io.InputStream;

import br.ifma.ita.fastfood.dominio.db.modelo.Imagem;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
public interface IProdutoImagemServico {
	
	public Imagem salvar(Imagem imagem,InputStream stream);
	
	public void excluir(Produto produto, Restaurante restaurante);

}
