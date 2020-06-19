/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissao")
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class Permissao extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "nome", nullable = false,columnDefinition = "varchar(100)",unique=true)
	private String nome;
	
	@Column(name = "nome", nullable = false,columnDefinition = "varchar(150)")
	private String descricao;

}
