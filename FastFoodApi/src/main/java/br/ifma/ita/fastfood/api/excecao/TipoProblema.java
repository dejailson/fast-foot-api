/**
 * 
 */
package br.ifma.ita.fastfood.api.excecao;

import lombok.Getter;

/**
 * @author dejailson
 *
 */
@Getter
public enum TipoProblema {
	
	ENTIDADE_NAO_ENCONTRADA_ERRO("/entidade-nao-encontrada","Entidade não encontrada."),
	RECURSO_NAO_ENCONTRADO_ERRO("/recurso-nao-encontrado","Recurso não encontrado."),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel","Mensagem Incompreensível."),
	ENTIDADE_EM_USO_ERRO("/entidade-em-uso","Entidade em uso."),
	CAMP0_DUPLICADO_ERRO("/campo-duplicado","Campo duplicado."),
	PARAMETRO_INVALIDO("/paramentro-invalido","Parâmetro inválido."),
	ERRO_DE_SISTEMA("/erro-sistema","Erro no sistema"),
	ERRO_NO_NEGOCIO("/erro-no-negocio","Violação de regra de negócio"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");
	
	private String uri;
	private String titulo;
	
	private TipoProblema(String uri, String titulo) {
		this.uri = String.format("fast.food.api.local%s", uri);
		this.titulo = titulo;
	}
	
	
	

}
