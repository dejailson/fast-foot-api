/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico;

import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

/**
 * @author dejailson
 *
 */
public interface IRestauranteServico extends IServico<Restaurante>{
	
	public void associarServicoEntrega(Long idRestaurante, Long idServicoEntrega);
	
	public void desassociarServicoEntrega(Long idRestaurante, Long idServicoEntrega);
	
	public void abrir(Long id);
	
	public void fechar(Long id);

}
