/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento.infraestrutura;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;

import br.ifma.ita.fastfood.core.armazenamento.Armazenamento;
import br.ifma.ita.fastfood.core.armazenamento.ArmazenamentoExcecao;

/**
 * @author dejailson
 *
 */
public class ArmazenamentoLocalServico implements Armazenamento {

	private String ERRO_INESPERADO = "Erro interno ocorreu ao criar a estrutura de diretório. Entre em contato com o Administrador do Sistema.";

	private Path diretorio;

	@Autowired
	public ArmazenamentoLocalServico(String diretorio) {

		try {
			var diretorioLocal = System.getProperty("user.home");

			this.diretorio = montar(diretorioLocal, diretorio);

			if (Files.notExists(this.diretorio, LinkOption.NOFOLLOW_LINKS)) {
				Files.createDirectory(this.diretorio);
			}
		} catch (IOException e) {
			throw new ArmazenamentoExcecao(ERRO_INESPERADO, e);
		}
	}

	@Override
	public void armazenar(Arquivo novoArquivo) {
		try {
			var caminhoArquivo = montar(this.diretorio, novoArquivo.getNomeArquivo());
			Files.copy(novoArquivo.getInputStream(), caminhoArquivo, opcoesGravacao());

		} catch (IOException e) {
			throw new ArmazenamentoExcecao("Armazenar", novoArquivo.getNomeArquivo(), e);
		}
	}

	private CopyOption[] opcoesGravacao() {
		var opcoes = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
		return opcoes;
	}

	@Override
	public void remover(String nomeArquivo) {

		var caminhoArquivo = montar(diretorio, nomeArquivo);
		try {
			Files.deleteIfExists(caminhoArquivo);
		} catch (IOException e) {
			throw new ArmazenamentoExcecao("Remover", nomeArquivo, e);
		}

	}

	@Override
	public ArquivoRecuperado recuperar(String nomeArquivo) {

		try {
			Path caminhoArquivo = montar(diretorio, nomeArquivo);
			var arquivo = Files.newInputStream(caminhoArquivo);

			var recuperado = ArquivoRecuperado.builder().inputStream(arquivo).build();
			return recuperado;

		} catch (IOException e) {
			throw new ArmazenamentoExcecao("Carregar", nomeArquivo, e);
		}
	}

	private Path montar(Path diretorio, String nomeArquivo) {
		return diretorio.resolve(Path.of(nomeArquivo));
	}

	private Path montar(String diretorioLocal, String nome) {
		return Path.of(diretorioLocal, nome);
	}
}
