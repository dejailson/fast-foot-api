/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name = "cliente")
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente extends EntidadeBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "nome", nullable = false, columnDefinition = "varchar(100)")
	private String nome;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "login", nullable = false,unique = true)
	private String login;
	@Column(name = "cpf", nullable = false,unique = true)
	private String cpf;
	@Column(name = "senha", nullable = false)
	private String senha;

}
