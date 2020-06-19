/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Entity
@Getter
@Setter
@Table(name = "usuario")
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class Usuario extends Cliente {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "matricula", nullable = false,unique=true)
	private String matricula;

}
