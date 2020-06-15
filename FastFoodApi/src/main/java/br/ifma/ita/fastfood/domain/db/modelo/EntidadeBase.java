/**
 * 
 */
package br.ifma.ita.fastfood.domain.db.modelo;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@NoArgsConstructor
@MappedSuperclass
public abstract class EntidadeBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@EqualsAndHashCode.Include
	private Long id;

	@Transient
	public Boolean eNovo() {
		return (this.id == null || this.id == 0) ? Boolean.TRUE : Boolean.FALSE;
	}
}
