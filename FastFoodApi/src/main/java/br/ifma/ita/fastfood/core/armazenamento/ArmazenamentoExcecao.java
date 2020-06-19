/**
 * 
 */
package br.ifma.ita.fastfood.core.armazenamento;

/**
 * @author dejailson
 *
 */
public class ArmazenamentoExcecao extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private static String MENSAGEM = "Erro interno ocorreu ao %s o arquivo %s. Entre em contato com o Administrador do Sistema.";
	
	
	public ArmazenamentoExcecao(String mensagem, Throwable e) {
		super(mensagem,e);
	}
	
	public ArmazenamentoExcecao(String acao, String nomeArquivo, Throwable e) {
		super(String.format(MENSAGEM, acao,nomeArquivo), e);
	}

}
