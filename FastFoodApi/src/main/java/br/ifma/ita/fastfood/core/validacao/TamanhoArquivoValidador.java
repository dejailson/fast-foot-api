/**
 * 
 */
package br.ifma.ita.fastfood.core.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dejailson
 *
 */
public class TamanhoArquivoValidador implements ConstraintValidator<TamanhoArquivo, MultipartFile>{

	private DataSize tamanhoMaximo;

	@Override
	public void initialize(TamanhoArquivo constraintAnnotation) {
		this.tamanhoMaximo = DataSize.parse(constraintAnnotation.maximo());
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || value.getSize() <= this.tamanhoMaximo.toBytes();
	}

}
