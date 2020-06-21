/**
 * 
 */
package br.ifma.ita.fastfood.core.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author dejailson
 *
 */
public class NuloOuMaioQueValidador implements ConstraintValidator<NuloOuMaiorQue, Number>{
	
	double valor;

	@Override
	public void initialize(NuloOuMaiorQue constraintAnnotation) {
		valor = constraintAnnotation.valor();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		var valido = true;
		
		if (value != null) {
			valido = Double.compare(value.doubleValue(),valor)==1;
		}
		
		return valido;
	}

}
