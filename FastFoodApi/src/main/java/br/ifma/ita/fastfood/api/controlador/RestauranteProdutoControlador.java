/**
 * 
 */
package br.ifma.ita.fastfood.api.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.ifma.ita.fastfood.api.modelo.empacotador.IProdutoEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.ProdutoModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.ProdutoModelo;
import br.ifma.ita.fastfood.api.modelo.visualizacao.VisualizacaoJson;
import br.ifma.ita.fastfood.core.excecao.NegocioExcecao;
import br.ifma.ita.fastfood.core.excecao.RestauranteNaoEncontradoExcecao;
import br.ifma.ita.fastfood.dominio.repositorio.ProdutoRepositorio;
import br.ifma.ita.fastfood.dominio.repositorio.RestauranteRepositorio;
import br.ifma.ita.fastfood.dominio.repositorio.filtro.ProdutoFiltro;
import br.ifma.ita.fastfood.dominio.servico.suporte.ProdutoServico;

/**
 * @author dejailson
 *
 */
@RestController
@RequestMapping(value = "/restaurantes/{codigo}/produtos")
public class RestauranteProdutoControlador {

	private ProdutoServico servico;
	private ProdutoRepositorio repositorio;
	private RestauranteRepositorio restauranteRepositorio;
	private IProdutoEmpacotador empacotador;

	@Autowired
	public RestauranteProdutoControlador(ProdutoServico servico, ProdutoRepositorio repositorio,
			RestauranteRepositorio restauranteRepositorio, IProdutoEmpacotador empacotador) {
		super();
		this.servico = servico;
		this.repositorio = repositorio;
		this.restauranteRepositorio = restauranteRepositorio;
		this.empacotador = empacotador;
	}

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public MappingJacksonValue pesquisar(@PathVariable("codigo") Long codigo,
			@RequestParam(value = "completo", required = false,defaultValue = "false") Boolean completo, @Valid ProdutoFiltro filtro) {

		try {
			var restaurante = restauranteRepositorio.pesquisarPor(codigo);

			var produtos = repositorio.pesquisarPor(restaurante, filtro);

			MappingJacksonValue produtoView = new MappingJacksonValue(empacotador.empacotar(produtos));

			produtoView.setSerializationView(VisualizacaoJson.ProdutoResumo.class);

			if (completo) {
				produtoView.setSerializationView(VisualizacaoJson.ProdutoCompleto.class);
			}

			return produtoView;
		} catch (RestauranteNaoEncontradoExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}

	@GetMapping("/{sku}")
	@ResponseStatus(code = HttpStatus.OK)
	@JsonView(VisualizacaoJson.ProdutoCompleto.class)
	public ProdutoModelo pesquisar(@PathVariable("codigo") Long codigo, @PathVariable("sku") String sku) {

		try {
			var restaurante = restauranteRepositorio.pesquisarPor(codigo);
			var resturante = repositorio.pesquisarProdutoPor(sku, restaurante);

			return empacotador.empacotar(resturante);
		} catch (RestauranteNaoEncontradoExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@JsonView(VisualizacaoJson.ProdutoCompleto.class)
	public ProdutoModelo salvar(@PathVariable("codigo") Long codigo, @Valid @RequestBody ProdutoModeloIn produtoIn) {
		try {
			var restaurante = restauranteRepositorio.pesquisarPor(codigo);

			var produto = empacotador.desempacotar(produtoIn);

			produto.setRestaurante(restaurante);

			produto = servico.salvar(produto);

			return empacotador.empacotar(produto);
		} catch (RestauranteNaoEncontradoExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}

	@PutMapping("/{sku}")
	@ResponseStatus(code = HttpStatus.OK)
	@JsonView(VisualizacaoJson.ProdutoCompleto.class)
	public ProdutoModelo atualizar(@PathVariable("codigo") Long codigo, @PathVariable("sku") String sku,
			@Valid @RequestBody ProdutoModeloIn produtoIn) {
		try {

			var restaurante = restauranteRepositorio.pesquisarPor(codigo);
			var produtoPersistido = repositorio.pesquisarProdutoPor(sku, restaurante);

			empacotador.copiar(produtoIn, produtoPersistido);

			produtoPersistido = servico.atualizar(produtoPersistido, restaurante);

			return empacotador.empacotar(produtoPersistido);
		} catch (RestauranteNaoEncontradoExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{sku}")
	@ResponseStatus(code = HttpStatus.OK)
	public void excluir(@PathVariable("codigo") Long codigo, @PathVariable("sku") String sku) {
		try {
			var restaurante = restauranteRepositorio.pesquisarPor(codigo);

			servico.excluir(sku, restaurante);
		} catch (DataIntegrityViolationException e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}

}
