/**
 * 
 */
package br.ifma.ita.fastfood.domain.servico.suporte;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.ifma.ita.fastfood.core.excecao.CampoDuplicadoExcecao;
import br.ifma.ita.fastfood.core.excecao.EntidadeEmUsoException;
import br.ifma.ita.fastfood.core.excecao.RestauranteNaoEncontradoExcecao;
import br.ifma.ita.fastfood.domain.db.dao.CidadeDao;
import br.ifma.ita.fastfood.domain.db.dao.RestauranteDao;
import br.ifma.ita.fastfood.domain.db.modelo.Restaurante;
import br.ifma.ita.fastfood.domain.db.modelo.Restaurante_;
import br.ifma.ita.fastfood.domain.repositorio.CidadeRepositorio;
import br.ifma.ita.fastfood.domain.repositorio.RestauranteRepositorio;
import br.ifma.ita.fastfood.domain.repositorio.ServicoEntregaRepositorio;
import br.ifma.ita.fastfood.domain.servico.IRestauranteServico;

/**
 * @author dejailson
 *
 */
@Service
public class RestauranteServico implements IRestauranteServico {

	private RestauranteRepositorio restauranteRepositorio;
	private CidadeRepositorio cidadeRepositorio;
	private ServicoEntregaRepositorio servicoRepositorio;
	private RestauranteDao restauranteDao;
	

	@Autowired
	public RestauranteServico(RestauranteRepositorio restauranteRepositorio, CidadeDao cidadeDao, CidadeRepositorio cidadeRepositorio,
			ServicoEntregaRepositorio servicoRepositorio, RestauranteDao restauranteDao) {
		this.restauranteRepositorio = restauranteRepositorio;
		this.servicoRepositorio = servicoRepositorio;
		this.cidadeRepositorio = cidadeRepositorio;
		this.restauranteDao = restauranteDao;
	}

	@Transactional
	@Override
	public Restaurante salvar(Restaurante restaurante) {

		var cidade = this.cidadeRepositorio.pesquisarPor(restaurante.getEndereco().getCidade().getId());

		if (restaurante.eNovo()) {
			if (this.restauranteRepositorio.pesquisarPor(restaurante.getNome()).isPresent()) {
				throw new CampoDuplicadoExcecao(Restaurante.class.getSimpleName(), Restaurante_.NOME, restaurante.getNome());
			}
		}
		
		restaurante.getEndereco().setCidade(cidade);

		return this.restauranteDao.save(restaurante);
	}
	
	@Transactional
	@Override
	public Restaurante atualizar(Restaurante restaurante) {

		restauranteDao.separar(restaurante);

		this.restauranteRepositorio.pesquisarPor(restaurante.getId());

		var restauranteExistente = restauranteDao.pesquisarPor(restaurante.getNome());
		
		if (restauranteExistente.isPresent() && !restauranteExistente.get().equals(restaurante)) {
			throw new CampoDuplicadoExcecao(Restaurante.class.getSimpleName(), Restaurante_.NOME,
					restaurante.getNome());

		}

		return this.salvar(restaurante);
	}

	@Transactional
	@Override
	public void excluir(Long restauranteId) {
		try {
			this.restauranteDao.deleteById(restauranteId);
			this.restauranteDao.flush();

		} catch (EmptyResultDataAccessException e) {

			throw new RestauranteNaoEncontradoExcecao(restauranteId);

		} catch (DataIntegrityViolationException e) {

			throw new EntidadeEmUsoException(Restaurante.class.getSimpleName(), restauranteId);
		}

	}
	
	@Override
	@Transactional
	public void associarServicoEntrega(Long idRestaurante, Long idServicoEntrega) {
		var restaurante = this.restauranteRepositorio.pesquisarPor(idRestaurante);
		
		var servico = this.servicoRepositorio.pesquisarPor(idServicoEntrega);
		
		restaurante.adicionarServicoEntrega(servico);
		
	}

	
	@Override
	@Transactional
	public void desassociarServicoEntrega(Long idRestaurante, Long idServicoEntrega) {
		var restaurante = restauranteRepositorio.pesquisarPor(idRestaurante);
		
		var servico = this.servicoRepositorio.pesquisarPor(idServicoEntrega);
		
		restaurante.removerServicoEntrega(servico);
		
	}
	
	@Override
	@Transactional
	public void abrir(Long id) {
		var restaurante = this.restauranteRepositorio.pesquisarPor(id);
		restaurante.abrir();
	}
	
	@Override
	@Transactional
	public void fechar(Long id) {
		var restaurante = this.restauranteRepositorio.pesquisarPor(id);
		restaurante.fechar();
	}
}
