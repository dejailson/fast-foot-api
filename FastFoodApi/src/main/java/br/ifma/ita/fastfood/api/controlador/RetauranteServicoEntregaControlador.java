/**
 * 
 */
package br.ifma.ita.fastfood.api.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ifma.ita.fastfood.api.modelo.empacotador.IServicoEntregaEmpacotador;
import br.ifma.ita.fastfood.api.modelo.saida.ServicoEntregaModelo;
import br.ifma.ita.fastfood.core.excecao.NegocioExcecao;
import br.ifma.ita.fastfood.core.excecao.ServicoEntregaNaoEncontradoExcecao;
import br.ifma.ita.fastfood.dominio.repositorio.ServicoEntregaRepositorio;
import br.ifma.ita.fastfood.dominio.servico.IRestauranteServico;

/**
 * @author dejailson
 *
 */
@RestController
@RequestMapping("/restaurantes/{codigo}/servico-entregas")
public class RetauranteServicoEntregaControlador {

	
	private IRestauranteServico servico;
	private ServicoEntregaRepositorio repositorio;
	private IServicoEntregaEmpacotador empacotador;
	
	@Autowired
	public RetauranteServicoEntregaControlador(IRestauranteServico servico,
			ServicoEntregaRepositorio repositorio, IServicoEntregaEmpacotador empacotador) {
		this.servico = servico;
		this.repositorio = repositorio;
		this.empacotador = empacotador;
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<ServicoEntregaModelo> pesquisar(@PathVariable("codigo") Long codigo) {
		var servicos = repositorio.pesquisarPorRestaurante(codigo);
		return empacotador.empacotar(servicos);
	}

	@PutMapping("/{codigoServico}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable("codigo") Long codigo,
			@PathVariable("codigoServico") Long codigoServico) {
		try {
			servico.associarServicoEntrega(codigo, codigoServico);
		} catch (ServicoEntregaNaoEncontradoExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{codigoServico}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable("codigo") Long codigo,
			@PathVariable("codigoServico") Long codigoServico) {
		try {
			servico.desassociarServicoEntrega(codigo, codigoServico);
		} catch (ServicoEntregaNaoEncontradoExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}

	}

}
