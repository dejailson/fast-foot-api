/**
 * 
 */
package br.ifma.ita.fastfood.domain.db.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicoEntrega extends EntidadeBase {

	@Column(name = "nome", nullable = false, columnDefinition = "varchar(20)", unique = true)
	private String nome;

}
