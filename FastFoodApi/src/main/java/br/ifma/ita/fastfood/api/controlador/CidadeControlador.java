/**
 * 
 */
package br.ifma.ita.fastfood.api.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.ifma.ita.fastfood.api.modelo.empacotador.ICidadeEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.CidadeModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.CidadeModelo;
import br.ifma.ita.fastfood.core.excecao.CampoDuplicadoExcecao;
import br.ifma.ita.fastfood.core.excecao.EntidadeEmUsoException;
import br.ifma.ita.fastfood.core.excecao.NegocioExcecao;
import br.ifma.ita.fastfood.domain.db.modelo.Cidade;
import br.ifma.ita.fastfood.domain.repositorio.CidadeRepositorio;
import br.ifma.ita.fastfood.domain.servico.IServico;
import br.ifma.ita.fastfood.domain.servico.QualificadorDoServico;
import br.ifma.ita.fastfood.domain.servico.TipoDoServico;

/**
 * @author dejailson
 *
 */
@RestController
@RequestMapping(value = "/cidades")
public class CidadeControlador {
	
	@QualificadorDoServico(TipoDoServico.CIDADE_SERVICO)
	private IServico<Cidade> servico;
	
	private ICidadeEmpacotador empacotador;
	private CidadeRepositorio repositorio;
	
	@Autowired
	public CidadeControlador(IServico<Cidade> servico, CidadeRepositorio repositorio, 
			ICidadeEmpacotador empacotador) {
		this.servico = servico;
		this.empacotador = empacotador;
		this.repositorio = repositorio;
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<CidadeModelo> listar() {
		var lista = repositorio.listarTodos();
		
		return empacotador.empacotar(lista);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CidadeModelo salvar(@RequestBody @Valid CidadeModeloIn cidadeIn) {
		
		var cidade = empacotador.desempacotar(cidadeIn);
		cidade = servico.salvar(cidade);
		
		return empacotador.empacotar(cidade);
		
	}
	
	@PutMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.OK)
	public CidadeModelo atualizar(@PathVariable Long codigo, @RequestBody @Valid CidadeModeloIn cidade) {
		
		var cidadeAtual = repositorio.pesquisarPor(codigo);
		
		this.empacotador.copiar(cidade, cidadeAtual);
		
		try {
			cidadeAtual = servico.atualizar(cidadeAtual);
			return this.empacotador.empacotar(cidadeAtual);
		}catch (CampoDuplicadoExcecao e ) {
			throw new NegocioExcecao(e.getMessage(),e);
		}
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		try {
			this.servico.excluir(codigo);
		}catch (EntidadeEmUsoException e) {
			throw new NegocioExcecao(e.getMessage(),e);
		}
		
	}

}
