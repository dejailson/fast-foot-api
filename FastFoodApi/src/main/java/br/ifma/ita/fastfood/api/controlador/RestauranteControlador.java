/**
 * 
 */
package br.ifma.ita.fastfood.api.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.ifma.ita.fastfood.api.modelo.empacotador.IRestauranteEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.RestauranteModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.RestauranteModelo;
import br.ifma.ita.fastfood.api.modelo.visualizacao.VisualizacaoJson;
import br.ifma.ita.fastfood.core.excecao.CampoDuplicadoExcecao;
import br.ifma.ita.fastfood.core.excecao.CidadeNaoEncontradaExcecao;
import br.ifma.ita.fastfood.core.excecao.EntidadeEmUsoException;
import br.ifma.ita.fastfood.core.excecao.NegocioExcecao;
import br.ifma.ita.fastfood.dominio.repositorio.RestauranteRepositorio;
import br.ifma.ita.fastfood.dominio.repositorio.filtro.RestauranteFiltro;
import br.ifma.ita.fastfood.dominio.servico.IRestauranteServico;

/**
 * @author dejailson
 *
 */
@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteControlador {

	private IRestauranteServico servico;
	private IRestauranteEmpacotador empacotador;
	private RestauranteRepositorio repositorio;

	@Autowired
	public RestauranteControlador(IRestauranteServico servico, IRestauranteEmpacotador empacotador,
			RestauranteRepositorio repositorio) {
		this.servico = servico;
		this.empacotador = empacotador;
		this.repositorio = repositorio;
	}
	
	@GetMapping()
	@ResponseStatus(code = HttpStatus.OK)
	public MappingJacksonValue pesquisar(@RequestParam(name = "completo", required = false,defaultValue = "false") boolean completo,
			RestauranteFiltro filtro){
		var restaurantes = repositorio.pesquisarTodos(filtro);
		
		MappingJacksonValue resturanteView = new MappingJacksonValue(empacotador.empacotar(restaurantes));
		
		resturanteView.setSerializationView(VisualizacaoJson.Resumo.class);
		
		if (completo) {
			resturanteView.setSerializationView(VisualizacaoJson.Completo.class);
		}
		
		return resturanteView;
	}
	
	@GetMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteModelo pesquisar(@PathVariable("codigo") Long codigo){
		var resturante = repositorio.pesquisarPor(codigo);
		
		return empacotador.empacotar(resturante);
	}
	
	@GetMapping("/{codigo}/endereco")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteModelo pesquisarEndereco(@PathVariable("codigo") Long codigo) {
		var endereco = repositorio.pesquisarEnderecoPor(codigo);

		return empacotador.empacotar(endereco);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RestauranteModelo salvar(@Valid @RequestBody RestauranteModeloIn restauranteIn) {
		var restaurante = empacotador.desempacotar(restauranteIn);
		try {
			restaurante = this.servico.salvar(restaurante);
			return this.empacotador.empacotar(restaurante);
		} catch (CidadeNaoEncontradaExcecao e) {
			throw new NegocioExcecao(e.getMessage(),e);
		}

	}

	@PutMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.OK)
	public RestauranteModelo atualizar(@PathVariable Long codigo, @RequestBody RestauranteModeloIn restauranteIn) {

		var restauranteAtual = repositorio.pesquisarPor(codigo);
		this.empacotador.copiar(restauranteIn, restauranteAtual);
		try {
			restauranteAtual = this.servico.atualizar(restauranteAtual);
			return this.empacotador.empacotar(restauranteAtual);
		} catch (CampoDuplicadoExcecao e) {
			throw new NegocioExcecao(e.getMessage(),e);
		}
	}
	
	@PutMapping("/{codigo}/aberto")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void abrirRestaurante(@PathVariable Long codigo) {
		this.servico.abrir(codigo);
	}
	
	@DeleteMapping("/{codigo}/aberto")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void fecharRestaurante(@PathVariable Long codigo) {
		this.servico.fechar(codigo);
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		try {
			this.servico.excluir(codigo);
		} catch (EntidadeEmUsoException e) {
			throw new NegocioExcecao(e.getMessage(),e);
		}
	}

}
