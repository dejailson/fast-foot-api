/**
 * 
 */
package br.ifma.ita.fastfood.api.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ifma.ita.fastfood.api.modelo.empacotador.IServicoEntregaEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.ServicoEntregaModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.ServicoEntregaModelo;
import br.ifma.ita.fastfood.core.excecao.CampoDuplicadoExcecao;
import br.ifma.ita.fastfood.core.excecao.EntidadeEmUsoException;
import br.ifma.ita.fastfood.core.excecao.NegocioExcecao;
import br.ifma.ita.fastfood.domain.db.modelo.ServicoEntrega;
import br.ifma.ita.fastfood.domain.repositorio.ServicoEntregaRepositorio;
import br.ifma.ita.fastfood.domain.servico.IServico;
import br.ifma.ita.fastfood.domain.servico.QualificadorDoServico;
import br.ifma.ita.fastfood.domain.servico.TipoDoServico;

/**
 * @author dejailson
 *
 */
@RestController
@RequestMapping(value = "/servico-entregas")
public class ServicoEntregaControlador {

	@QualificadorDoServico(TipoDoServico.TIPO_ENTREGA_SERVICO)
	private IServico<ServicoEntrega> servico;

	private IServicoEntregaEmpacotador empacotador;
	private ServicoEntregaRepositorio repositorio;

	public ServicoEntregaControlador(IServico<ServicoEntrega> servico, IServicoEntregaEmpacotador empacotador,
			ServicoEntregaRepositorio repositorio) {
		this.servico = servico;
		this.empacotador = empacotador;
		this.repositorio = repositorio;
	}

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<ServicoEntregaModelo> listar() {
		var lista = repositorio.listarTodos();
		return empacotador.empacotar(lista);
	}
	
	@GetMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicoEntregaModelo pesquisar(@PathVariable("codigo") Long codigo) {
		var se = repositorio.pesquisarPor(codigo);
		return empacotador.empacotar(se);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ServicoEntregaModelo salvar(@RequestBody @Valid ServicoEntregaModeloIn servicoEntrega) {
		System.out.println(servicoEntrega.toString());
		var entrega = this.empacotador.desempacotar(servicoEntrega);

		entrega = this.servico.salvar(entrega);

		return this.empacotador.empacotar(entrega);
	}

	@PutMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicoEntregaModelo atualizar(@PathVariable Long codigo,
			@RequestBody ServicoEntregaModeloIn tipoEntregaIn) {
		var tipoEntregaAtual = repositorio.pesquisarPor(codigo);

		try {
			this.empacotador.copiar(tipoEntregaIn, tipoEntregaAtual);

			return this.empacotador.empacotar(tipoEntregaAtual);
		} catch (CampoDuplicadoExcecao e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		try {
			this.servico.excluir(codigo);
		} catch (EntidadeEmUsoException e) {
			throw new NegocioExcecao(e.getMessage(), e);
		}

	}
}
