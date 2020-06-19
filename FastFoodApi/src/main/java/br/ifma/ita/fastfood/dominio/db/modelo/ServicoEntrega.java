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
@Table(name = "servico_entrega")
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true,onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicoEntrega extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "nome", nullable = false, columnDefinition = "varchar(100)", unique = true)
	private String nome;

}
