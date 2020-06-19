/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador.suporte;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.ifma.ita.fastfood.api.modelo.empacotador.IEmpacotador;
import br.ifma.ita.fastfood.dominio.db.modelo.EntidadeBase;

/**
 * @author dejailson
 *
 */
public abstract class EmpacotadorSuporte<I extends Object, O extends Object, E extends EntidadeBase> implements 
	IEmpacotador<I, O, E> {
	
	@Autowired
	private ModelMapper mapper;
	
	private Class<O> modeloClasse;
	private Class<E> entidadeClasse;
	
	public EmpacotadorSuporte(Class<O> modeloClasse, Class<E> entidadeClasse) {
		this.modeloClasse = modeloClasse;
		this.entidadeClasse = entidadeClasse;
	}
	
	protected ModelMapper getModelMapper() {
		return this.mapper;
	}

	@Override
	public void copiar(I modeloIn, E entidade) {
		this.mapper.map(modeloIn, entidade);
	}

	@Override
	public O empacotar(E entidade) {
		return this.mapper.map(entidade, modeloClasse);
	}
	
	@Override
	public List<O> empacotar(Collection<E> colecao){
		return colecao.stream().map(entidade ->empacotar(entidade))
				.collect(Collectors.toList());
	}

	@Override
	public E desempacotar(I modeloIn) {
		return this.mapper.map(modeloIn, entidadeClasse);
	}
	

}
