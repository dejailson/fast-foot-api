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
@Table(name = "Cidade")
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true,onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cidade extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "nome", nullable = false, length = 20, columnDefinition = "varchar(100)")
	private String nome;
	@Column(name = "uf", nullable = false, length = 2, columnDefinition = "char(2)")
	private String uf;

}
