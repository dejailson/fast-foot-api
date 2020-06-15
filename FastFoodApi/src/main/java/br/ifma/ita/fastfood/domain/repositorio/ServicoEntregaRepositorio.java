/**
 * 
 */
package br.ifma.ita.fastfood.domain.repositorio;

import java.util.List;
import java.util.Set;

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
	private RestauranteRepositorio restauranteRepositorio;
	
	@Autowired
	public ServicoEntregaRepositorio(ServicoEntregaDao servicoEntregaDao, RestauranteRepositorio restauranteRepositorio) {
		this.servicoEntregaDao = servicoEntregaDao;
		this.restauranteRepositorio = restauranteRepositorio;
	}

	public ServicoEntrega pesquisarPor(Long id) {
		return servicoEntregaDao.findById(id).orElseThrow(()-> new ServicoEntregaNaoEncontradoExcecao(id));
	}
	
	public Set<ServicoEntrega> pesquisarPorRestaurante(Long codigo) {
		
		return restauranteRepositorio.pesquisarPor(codigo).getServicoEntregas();
	}
	
	public List<ServicoEntrega> listarTodos(){
		return this.servicoEntregaDao.pesquisarTodos(); 
	}

}
