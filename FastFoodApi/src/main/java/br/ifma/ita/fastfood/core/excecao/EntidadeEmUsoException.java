package br.ifma.ita.fastfood.core.excecao;

public class EntidadeEmUsoException extends NegocioExcecao {
	
	private static final String MENSAGEM = "%s de código %d não pode ser removida, pois está em uso";
	private static final long serialVersionUID = 1L;

	public EntidadeEmUsoException(String entidade, Long id) {
		super(String.format(MENSAGEM, entidade, id));
	}

}
