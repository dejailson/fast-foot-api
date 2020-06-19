/**
 * 
 */
package br.ifma.ita.fastfood.core.validacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author dejailson
 *
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { TamanhoArquivoValidador.class })
public @interface TamanhoArquivo {

	String message() default "tamanho do arquivo inválido";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String maximo();

}
