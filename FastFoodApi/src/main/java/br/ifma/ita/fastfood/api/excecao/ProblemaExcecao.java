package br.ifma.ita.fastfood.api.excecao;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class ProblemaExcecao {
	
	private Integer status;
	private LocalDateTime timestamp;
	private String tipo;
	private String titulo;
	private String detalhe;
	//mensagem para usuario final
	private String mensagem;
	
	private List<Objeto> objetos;
	
	@Getter
	@Builder
	public static class Objeto {
		
		private String nome;
		private String mensagem;
		
	}

}
