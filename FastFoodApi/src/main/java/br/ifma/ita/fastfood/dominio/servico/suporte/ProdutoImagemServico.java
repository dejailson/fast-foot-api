/**
 * 
 */
package br.ifma.ita.fastfood.dominio.servico.suporte;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.ifma.ita.fastfood.core.armazenamento.Armazenamento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoAdicionarEvento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoSubstituirEvento;
import br.ifma.ita.fastfood.core.excecao.ProdutoImagemNaoEncontradoExcecao;
import br.ifma.ita.fastfood.dominio.db.dao.ProdutoDao;
import br.ifma.ita.fastfood.dominio.db.modelo.Imagem;
import br.ifma.ita.fastfood.dominio.db.modelo.Produto;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;
import br.ifma.ita.fastfood.dominio.servico.IProdutoImagemServico;

/**
 * @author dejailson
 *
 */
@Service
public class ProdutoImagemServico implements IProdutoImagemServico {

	private ProdutoDao dao;
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	public ProdutoImagemServico(ProdutoDao dao, ApplicationEventPublisher eventPublisher) {
		this.dao = dao;
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	@Override
	public Imagem salvar(Imagem imagem, InputStream stream) {
		Imagem imagemPersistida = null;
		var optional = dao.pesquisarImagemPorId(imagem.getProduto().getRestaurante(), imagem.getProduto());

		String nomeAnterior = null;

		if (optional.isPresent()) {
			imagemPersistida = optional.get();
			nomeAnterior = imagemPersistida.getNome();

			imagemPersistida.clonar(imagem);

		} else {
			imagemPersistida = dao.salvarImagem(imagem);
		}

		dao.flush();

		salvarOuAtualizarImagem(imagemPersistida, stream, nomeAnterior);

		return imagemPersistida;
	}

	@Transactional
	@Override
	public void excluir(Produto produto, Restaurante restaurante) {

		var imagem = dao.pesquisarImagemPorId(restaurante, produto)
				.orElseThrow(() -> new ProdutoImagemNaoEncontradoExcecao(produto.getId(), restaurante.getId()));

		dao.excluirImagem(imagem);
		dao.flush();
	}

	private void salvarOuAtualizarImagem(Imagem imagem, InputStream stream, String nomeAnterior) {
		if (StringUtils.hasText(nomeAnterior)) {
			this.eventPublisher
					.publishEvent(new ArmazenamentoSubstituirEvento(getNovoArquivo(stream, imagem), nomeAnterior));
		} else {
			this.eventPublisher.publishEvent(new ArmazenamentoAdicionarEvento(getNovoArquivo(stream, imagem)));
		}
	}

	private Armazenamento.Arquivo getNovoArquivo(InputStream stream, Imagem imagemPersistida) {
		return Armazenamento.Arquivo.builder()
				.tipoConteudo(imagemPersistida.getTipoConteudo())
				.nomeArquivo(imagemPersistida.getNome())
				.inputStream(stream)
				.build();
	}

}
