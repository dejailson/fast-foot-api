/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico;

import br.ifma.ita.fastfood.dominio.db.modelo.EntidadeBase;

/**
 * @author dejailson
 *
 */
public interface IServico<T extends EntidadeBase> {

	T salvar(T t);

	T atualizar(T t);

	void excluir(Long id);

}
