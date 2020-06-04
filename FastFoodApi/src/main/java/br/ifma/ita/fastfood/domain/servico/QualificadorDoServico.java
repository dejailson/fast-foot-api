/**
 * 
 */
package br.ifma.ita.fastfood.domain.servico;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author dejailson
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Qualifier
public @interface QualificadorDoServico {
	TipoDoServico value();

}
