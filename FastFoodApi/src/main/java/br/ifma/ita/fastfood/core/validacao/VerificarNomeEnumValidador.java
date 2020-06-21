/**
 * 
 */
package br.ifma.ita.fastfood.core.validacao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

/**
 * @author dejailson
 *
 */
public class VerificarNomeEnumValidador implements ConstraintValidator<VerificarNomeEnum, String>{
	
	List<String> nomes=new ArrayList<String>();

	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(VerificarNomeEnum constraintAnnotation) {
		var enums = constraintAnnotation.value().getEnumConstants();
		for (Enum e: enums) {
			nomes.add(StringUtils.deleteAny(e.name(), "_").toUpperCase());
		}
		
	}

	@Override
	public boolean isValid(String tipo, ConstraintValidatorContext context) {
		
		if (StringUtils.hasText(tipo)) {
			return nomes.contains(StringUtils.trimAllWhitespace(tipo).toUpperCase());
		}
		
		return true;
	}
}
