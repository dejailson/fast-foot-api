/**
 * 
 */
package br.ifma.ita.fastfood.core.validacao;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author dejailson
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = VerificarNomeEnumValidador.class)
public @interface VerificarNomeEnum {
	
	String message() default "{0}---{1}----{3}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	Class<? extends Enum<?>> value();

}
