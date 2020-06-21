/**
 * 
 */
package br.ifma.ita.fastfood.api.controlador;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.ifma.ita.fastfood.api.modelo.empacotador.IImagemEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.ImagemModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.ImagemModelo;
import br.ifma.ita.fastfood.api.modelo.visualizacao.VisualizacaoJson;
import br.ifma.ita.fastfood.core.armazenamento.Armazenamento;
import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoRecuperarEvento;
import br.ifma.ita.fastfood.core.excecao.EntidadeNaoEncontradaExcecao;
import br.ifma.ita.fastfood.core.excecao.NegocioExcecao;
import br.ifma.ita.fastfood.dominio.repositorio.ProdutoImagemRepositorio;
import br.ifma.ita.fastfood.dominio.repositorio.ProdutoRepositorio;
import br.ifma.ita.fastfood.dominio.repositorio.RestauranteRepositorio;
import br.ifma.ita.fastfood.dominio.servico.IProdutoImagemServico;

/**
 * @author dejailson
 *
 */
@RestController
@RequestMapping(value = "/restaurantes/{codigo}/produtos/{sku}/imagens")
public class RestauranteProdutoImagemControlador {

	private IProdutoImagemServico servico;
	private RestauranteRepositorio restauranteRepositorio;
	private ProdutoRepositorio produtoRepositorio;
	private IImagemEmpacotador empacotador;
	private ProdutoImagemRepositorio repositorio;
	private ApplicationEventPublisher eventPublisher;
	private Armazenamento.ArquivoRecuperado arquivo;
	private MediaType mediaTypeFoto;

	@Autowired
	public RestauranteProdutoImagemControlador(IProdutoImagemServico servico,
			RestauranteRepositorio restauranteRepositorio, ProdutoRepositorio produtoRepositorio,
			IImagemEmpacotador empacotador, ProdutoImagemRepositorio repositorio,
			ApplicationEventPublisher eventPublisher) {
		this.servico = servico;
		this.restauranteRepositorio = restauranteRepositorio;
		this.produtoRepositorio = produtoRepositorio;
		this.empacotador = empacotador;
		this.repositorio = repositorio;
		this.eventPublisher = eventPublisher;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(VisualizacaoJson.Imagem.class)
	@ResponseStatus(code = HttpStatus.OK)
	public ImagemModelo pesquisar(@PathVariable("codigo") Long codigo, @PathVariable("sku") String sku){

		try {
			var restaurante = restauranteRepositorio.pesquisarPor(codigo);

			var produto = produtoRepositorio.pesquisarProdutoPor(sku, restaurante);
			
			var imagem = repositorio.pesquisarPor(produto, restaurante);

			return empacotador.empacotar(imagem);

		} catch (EntidadeNaoEncontradaExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> pesquisarImagem(@PathVariable("codigo") Long codigo, @PathVariable("sku") String sku,
			@RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException{

		try {
			var restaurante = restauranteRepositorio.pesquisarPor(codigo);

			var produto = produtoRepositorio.pesquisarProdutoPor(sku, restaurante);
			
			var imagem = repositorio.pesquisarPor(produto, restaurante);
			
			mediaTypeFoto = MediaType.parseMediaType(imagem.getTipoConteudo());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			
			this.eventPublisher.publishEvent(new ArmazenamentoRecuperarEvento(imagem.getNome(), recuperado-> arquivo = recuperado));
			
			return enviarArquivoRecuperado();

		} catch (EntidadeNaoEncontradaExcecao e) {
			return ResponseEntity
					.notFound()
					.build();
		}
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@JsonView(VisualizacaoJson.Imagem.class)
	@ResponseStatus(code = HttpStatus.OK)
	public ImagemModelo adicionar(@PathVariable("codigo") Long codigo, @PathVariable("sku") String sku,
			@Valid ImagemModeloIn imagem) throws IOException {

		try {
			var restaurante = restauranteRepositorio.pesquisarPor(codigo);

			var produto = produtoRepositorio.pesquisarProdutoPor(sku, restaurante);

			var im = empacotador.desempacotar(imagem);
			im.setProduto(produto);

			im = servico.salvar(im, imagem.getArquivo().getInputStream());

			return empacotador.empacotar(im);

		} catch (EntidadeNaoEncontradaExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}

	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable("codigo") Long codigo, @PathVariable("sku") String sku) {
		try {

			var restaurante = restauranteRepositorio.pesquisarPor(codigo);

			var produto = produtoRepositorio.pesquisarProdutoPor(sku, restaurante);
			
			servico.excluir(produto, restaurante);
			
		} catch (EntidadeNaoEncontradaExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}
	
	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}
	
	private ResponseEntity<?> enviarArquivoRecuperado() {
		if (arquivo.temUrl()) {
			return ResponseEntity
					.status(HttpStatus.FOUND)
					.header(HttpHeaders.LOCATION, arquivo.getUrl())
					.build();
		} else {
			return ResponseEntity.ok()
					.contentType(mediaTypeFoto)
					.body(new InputStreamResource(arquivo.getInputStream()));
		}
	}

}
