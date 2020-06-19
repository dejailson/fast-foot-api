/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico.suporte;

import org.springframework.transaction.annotation.Transactional;

import br.ifma.ita.fastfood.dominio.db.dao.BaseDao;
import br.ifma.ita.fastfood.dominio.db.modelo.EntidadeBase;
import br.ifma.ita.fastfood.dominio.servico.IServico;

/**
 * @author dejailson
 *
 */
public abstract class ServicoSuporte<T extends EntidadeBase> implements IServico<T> {
	
	protected abstract BaseDao<T> getInstanciaDAO();

	@Transactional
	@Override
	public T salvar(T t) {
		return getInstanciaDAO().save(t);
	}

	@Transactional
	@Override
	public T atualizar(T t) {
		return this.salvar(t);
	}

	@Transactional
	@Override
	public void excluir(Long id) {
		getInstanciaDAO().deleteById(id);
	}
	
	
	

}
