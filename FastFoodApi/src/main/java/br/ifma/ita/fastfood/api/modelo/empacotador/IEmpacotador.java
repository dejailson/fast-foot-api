/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador;

import java.util.Collection;
import java.util.List;

import br.ifma.ita.fastfood.dominio.db.modelo.EntidadeBase;

/**
 * @author dejailson
 *
 */
public interface IEmpacotador<I extends Object,O extends Object, E extends EntidadeBase>{
	
	public void copiar(I modeloIn, E entidade);
	
	public O empacotar(E entidade);
	
	public List<O> empacotar(Collection<E> colecao);
	
	public E desempacotar(I modeloIn);
}
