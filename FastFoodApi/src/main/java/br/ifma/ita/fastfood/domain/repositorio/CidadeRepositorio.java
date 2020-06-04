/**
 * 
 */
package br.ifma.ita.fastfood.domain.repositorio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ifma.ita.fastfood.core.excecao.CidadeNaoEncontradaExcecao;
import br.ifma.ita.fastfood.domain.db.dao.CidadeDao;
import br.ifma.ita.fastfood.domain.db.modelo.Cidade;

/**
 * @author dejailson
 *
 */
@Repository
public class CidadeRepositorio {
	
	private CidadeDao cidadeDao;
	
	@Autowired
	public CidadeRepositorio(CidadeDao cidadeDao) {
		this.cidadeDao = cidadeDao;
	}

	public Cidade pesquisarPor(Long id) {
		return cidadeDao.findById(id).orElseThrow(()-> new CidadeNaoEncontradaExcecao(id));
	}
	
	public List<Cidade> listarTodos(){
		return this.cidadeDao.pesquisarTodos();
	}

}
