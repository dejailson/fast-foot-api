/**
 * 
 */
package br.ifma.ita.fastfood.api.excecao.controlador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.ifma.ita.fastfood.api.excecao.ProblemaExcecao;
import br.ifma.ita.fastfood.api.excecao.TipoProblema;
import br.ifma.ita.fastfood.core.excecao.CampoDuplicadoExcecao;
import br.ifma.ita.fastfood.core.excecao.EntidadeEmUsoException;
import br.ifma.ita.fastfood.core.excecao.EntidadeNaoEncontradaExcecao;
import br.ifma.ita.fastfood.core.excecao.NegocioExcecao;

/**
 * @author dejailson
 *
 */
@ControllerAdvice
public class ControladorExcecaoApi extends ResponseEntityExceptionHandler {

	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
			+ "o problema persistir, entre em contato com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExcecaoNaoCapturada(Exception ex, WebRequest request) {
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var tipoErro = TipoProblema.ERRO_DE_SISTEMA;
		ex.printStackTrace();
		var mensagemErro = this.construirMensagemErro(status, tipoErro, MSG_ERRO_GENERICA_USUARIO_FINAL)
				.detalhe(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

		return this.handleExceptionInternal(ex, mensagemErro, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaExcecao.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(Exception ex, WebRequest request) {

		var status = HttpStatus.NOT_FOUND;
		var tipoErro = TipoProblema.ENTIDADE_NAO_ENCONTRADA_ERRO;
		var detalhe = ex.getMessage();
		var mensagemErro = this.construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(detalhe)
				.build();

		return this.handleExceptionInternal(ex, mensagemErro, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<Object> handleEntidadeEmUso(Exception ex, WebRequest request) {

		var status = HttpStatus.CONFLICT;
		var tipoErro = TipoProblema.ENTIDADE_EM_USO_ERRO;
		var detalhe = ex.getMessage();
		var mensagemErro = this.construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(detalhe)
				.build();

		return this.handleExceptionInternal(ex, mensagemErro, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(CampoDuplicadoExcecao.class)
	public ResponseEntity<Object> handleCampoDuplicado(Exception ex, WebRequest request) {
		var status = HttpStatus.CONFLICT;
		var tipoErro = TipoProblema.CAMP0_DUPLICADO_ERRO;
		var detalhe = ex.getMessage();
		var mensagemErro = this.construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(detalhe)
				.build();
		
		return handleExceptionInternal(ex, mensagemErro, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioExcecao.class)
	public ResponseEntity<Object> handleErroNegocio(Exception ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var tipoErro = TipoProblema.ERRO_NO_NEGOCIO;
		var detalhe = ex.getMessage();
		var mensagemErro = this.construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(detalhe)
				.build();
		
		return handleExceptionInternal(ex, mensagemErro, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = ProblemaExcecao.builder()
					.timestamp(LocalDateTime.now())
					.status(status.value())
					.titulo(status.getReasonPhrase())
					.tipo(TipoProblema.ERRO_DE_SISTEMA.getUri())
					.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
		}

		if (body instanceof String) {
			body = ProblemaExcecao.builder()
					.timestamp(LocalDateTime.now())
					.status(status.value())
					.titulo(status.getReasonPhrase())
					.tipo(TipoProblema.ERRO_DE_SISTEMA.getUri())
					.mensagem(String.valueOf(body))
					.build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable causa = ExceptionUtils.getRootCause(ex);
		
		if (causa instanceof InvalidFormatException) {
			return this.handleInvalidFormat((InvalidFormatException) causa, headers, status, request);
		}

		if (causa instanceof PropertyBindingException) {
			return this.handlePropertyBinding((PropertyBindingException) causa, headers, status, request);
		}
	
		var detalhe = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		var tipoErro = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
		var mensagemErro = construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return this.handleExceptionInternal(ex, mensagemErro, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		var tipoErro = TipoProblema.RECURSO_NAO_ENCONTRADO_ERRO;
		String detalhe = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

		var mensagemErro = construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, mensagemErro, headers, status, request);
	}


	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var bindingResult = ex.getBindingResult();

	    return handleValidationInternal(ex, headers, status, request, bindingResult);
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		var bindingResult =  ex.getBindingResult();
		
		return handleValidationInternal(ex, headers, status, request, bindingResult);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, BindingResult bindingResult) {
		var tipoErro = TipoProblema.DADOS_INVALIDOS;
	    String detalhe = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    List<ProblemaExcecao.Objeto> problemaObjetos = bindingResult.getAllErrors().stream()
	    		.map(erro ->{
	    			
	    			String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
	    			String nome = erro.getObjectName();
	    			
	    			if (erro instanceof FieldError) {
	    				nome = ((FieldError) erro).getField();
	    			}
	    			
	    			return ProblemaExcecao.Objeto.builder()
	    					.nome(nome)
	    					.mensagem(mensagem)
	    					.build();
	    		})
	    		.collect(Collectors.toList());
	    
	    ProblemaExcecao problema = construirMensagemErro(status, tipoErro, detalhe)
	        .detalhe(detalhe)
	        .objetos(problemaObjetos)
	        .build();
	    
	    return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var tipoErro = TipoProblema.PARAMETRO_INVALIDO;

		String detalhe = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		var mensagemErro = construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, mensagemErro, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = montarCaminho(ex.getPath());

		var tipoErro = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
		String detalhe = String.format(
				"A propriedade '%s' não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path);

		var mensagemErro = construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, mensagemErro, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = montarCaminho(ex.getPath());

		var tipoErro = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
		String detalhe = String.format(
				"A propriedade '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		var mensagemErro = construirMensagemErro(status, tipoErro, detalhe)
				.mensagem(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, mensagemErro, headers, status, request);
	}

	private ProblemaExcecao.ProblemaExcecaoBuilder construirMensagemErro(HttpStatus status, TipoProblema tipo, String detalhe) {
		return ProblemaExcecao.builder()
				.status(status.value())
				.timestamp(LocalDateTime.now())
				.titulo(tipo.getUri())
				.tipo(tipo.getTitulo())
				.detalhe(detalhe);
	}

	private String montarCaminho(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

	}

}
