/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico.suporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifma.ita.fastfood.core.excecao.CampoDuplicadoExcecao;
import br.ifma.ita.fastfood.core.excecao.CidadeNaoEncontradaExcecao;
import br.ifma.ita.fastfood.core.excecao.EntidadeEmUsoException;
import br.ifma.ita.fastfood.dominio.db.dao.CidadeDao;
import br.ifma.ita.fastfood.dominio.db.modelo.Cidade;
import br.ifma.ita.fastfood.dominio.db.modelo.Cidade_;
import br.ifma.ita.fastfood.dominio.servico.IServico;
import br.ifma.ita.fastfood.dominio.servico.QualificadorDoServico;
import br.ifma.ita.fastfood.dominio.servico.TipoDoServico;

/**
 * @author dejailson
 *
 */
@Service
@QualificadorDoServico(TipoDoServico.CIDADE_SERVICO)
public class CidadeServico implements IServico<Cidade> {

	private CidadeDao cidadeDao;

	@Autowired
	public CidadeServico(CidadeDao cidade) {
		this.cidadeDao = cidade;
	}

	@Transactional
	@Override
	public Cidade salvar(Cidade cidade) {

		if (this.cidadeDao.pesquisarPor(cidade.getNome()).isPresent()) {
			throw new CampoDuplicadoExcecao(Cidade.class.getSimpleName(), Cidade_.NOME, cidade.getNome());
		}

		return this.cidadeDao.save(cidade);
	}

	@Transactional
	@Override
	public Cidade atualizar(Cidade cidade) {
		if (!this.cidadeDao.existsById(cidade.getId())) {
			throw new CidadeNaoEncontradaExcecao(cidade.getId());
		}
		
		var cidadeExistente = cidadeDao.pesquisarPor(cidade.getNome());
		if (cidadeExistente.isPresent() && !cidadeExistente.get().equals(cidade)) {
			throw new CampoDuplicadoExcecao(Cidade.class.getSimpleName(), Cidade_.NOME, cidade.getNome());
		}
		return this.cidadeDao.save(cidade);
	}

	@Transactional
	@Override
	public void excluir(Long idCidade) {
		try {
			this.cidadeDao.deleteById(idCidade);
			this.cidadeDao.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaExcecao(idCidade);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(Cidade.class.getSimpleName(), idCidade);
		}
	}
}
