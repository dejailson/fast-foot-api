/**
 * 
 */
package br.ifma.ita.fastfood.domain.repositorio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ifma.ita.fastfood.core.excecao.ServicoEntregaNaoEncontradoExcecao;
import br.ifma.ita.fastfood.domain.db.dao.ServicoEntregaDao;
import br.ifma.ita.fastfood.domain.db.modelo.ServicoEntrega;

/**
 * @author dejailson
 *
 */
@Repository
public class ServicoEntregaRepositorio {
	
	private ServicoEntregaDao servicoEntregaDao;
	
	@Autowired
	public ServicoEntregaRepositorio(ServicoEntregaDao servicoEntregaDao) {
		this.servicoEntregaDao = servicoEntregaDao;
	}

	public ServicoEntrega pesquisarPor(Long id) {
		return servicoEntregaDao.findById(id).orElseThrow(()-> new ServicoEntregaNaoEncontradoExcecao(id));
	}
	
	public List<ServicoEntrega> listarTodos(){
		return this.servicoEntregaDao.pesquisarTodos(); 
	}

}
