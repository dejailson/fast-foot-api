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
import br.ifma.ita.fastfood.core.excecao.EntidadeEmUsoException;
import br.ifma.ita.fastfood.core.excecao.ServicoEntregaNaoEncontradoExcecao;
import br.ifma.ita.fastfood.dominio.db.dao.ServicoEntregaDao;
import br.ifma.ita.fastfood.dominio.db.modelo.ServicoEntrega;
import br.ifma.ita.fastfood.dominio.db.modelo.ServicoEntrega_;
import br.ifma.ita.fastfood.dominio.servico.IServico;
import br.ifma.ita.fastfood.dominio.servico.QualificadorDoServico;
import br.ifma.ita.fastfood.dominio.servico.TipoDoServico;

/**
 * @author dejailson
 *
 */
@Service
@QualificadorDoServico(TipoDoServico.TIPO_ENTREGA_SERVICO)
public class ServicoEntregaServico implements IServico<ServicoEntrega> {

	private ServicoEntregaDao servicoEntregaDao;

	@Autowired
	public ServicoEntregaServico(ServicoEntregaDao tipoEntregaDao) {
		this.servicoEntregaDao = tipoEntregaDao;
	}

	@Transactional
	@Override
	public ServicoEntrega salvar(ServicoEntrega servico) {
		if (this.servicoEntregaDao.pesquisarPor(servico.getNome()).isPresent()) {
			throw new CampoDuplicadoExcecao(ServicoEntrega.class.getSimpleName(), ServicoEntrega_.NOME, servico.getNome());
		}
		return servicoEntregaDao.save(servico);
	}

	@Transactional
	@Override
	public ServicoEntrega atualizar(ServicoEntrega servico) {
		if (!this.servicoEntregaDao.existsById(servico.getId())) {
			throw new ServicoEntregaNaoEncontradoExcecao(servico.getId());
		}
		
		var servicoExistente = servicoEntregaDao.pesquisarPor(servico.getNome());
		if (servicoExistente.isPresent() && !servicoExistente.get().equals(servico)) {
			throw new CampoDuplicadoExcecao(ServicoEntrega.class.getSimpleName(), ServicoEntrega_.NOME, servico.getNome());
		}
		
		return this.salvar(servico);
	}

	@Transactional
	@Override
	public void excluir(Long tipoId) {
		try {
			this.servicoEntregaDao.deleteById(tipoId);;
			this.servicoEntregaDao.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new ServicoEntregaNaoEncontradoExcecao(tipoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(ServicoEntrega.class.getSimpleName(), tipoId);
		}
	}

}
