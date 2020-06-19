/**
 * 
 */
package br.ifma.ita.fastfood.core.validacao;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author dejailson
 *
 */

public class TipoConteudoArquivoValidador implements ConstraintValidator<TipoConteudoArquivo, MultipartFile> {

	private List<String> tipoPermitidos;
	
	@Override
	public void initialize(TipoConteudoArquivo constraint) {
		this.tipoPermitidos = Arrays.asList(constraint.permitido());
	}
	
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		return multipartFile == null 
				|| this.tipoPermitidos.contains(multipartFile.getContentType());
	}

}
