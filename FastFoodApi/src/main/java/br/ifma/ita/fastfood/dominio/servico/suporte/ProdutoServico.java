/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico.suporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifma.ita.fastfood.core.excecao.EntidadeEmUsoException;
import br.ifma.ita.fastfood.core.excecao.ProdutoNaoEncontradoExcecao;
import br.ifma.ita.fastfood.dominio.db.dao.BaseDao;
import br.ifma.ita.fastfood.dominio.db.dao.ProdutoDao;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;
import br.ifma.ita.fastfood.dominio.repositorio.RestauranteRepositorio;
import br.ifma.ita.fastfood.dominio.servico.IProdutoServico;

/**
 * @author dejailson
 *
 */
@Service
public class ProdutoServico extends ServicoSuporte<Produto> implements IProdutoServico {
	private RestauranteRepositorio repositorio;
	private ProdutoDao dao;

	@Autowired
	public ProdutoServico(RestauranteRepositorio repositorio, ProdutoDao dao) {
		this.repositorio = repositorio;
		this.dao = dao;
	}

	@Override
	protected BaseDao<Produto> getInstanciaDAO() {
		return this.dao;
	}

	@Transactional
	@Override
	public Produto salvar(Produto produto) {
		incluirInstanciaRestaurante(produto);
		return super.salvar(produto);
	}

	@Transactional
	@Override
	public Produto atualizar(Produto produto, Restaurante restaurante) {
		dao.separar(produto);
		if (dao.pesquisarProdutoPor(produto.getSku(),restaurante).isEmpty()) {
			throw new ProdutoNaoEncontradoExcecao(produto.getSku(),restaurante.getId());
		}
		incluirInstanciaRestaurante(produto);
		return super.atualizar(produto);
	}

	@Transactional
	@Override
	public void excluir(String sku,Restaurante restaurante) {
		var produto = dao.pesquisarProdutoPor(sku,restaurante).orElseThrow(()-> new  ProdutoNaoEncontradoExcecao(sku,restaurante.getId()));
		try {
			super.excluir(produto.getId());
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(Produto.class.getSimpleName(), produto.getId());
		}
	}

	private void incluirInstanciaRestaurante(Produto produto) {
		var restaurante = repositorio.pesquisarPor(produto.getRestaurante().getId());
		produto.setRestaurante(restaurante);
	}
}
