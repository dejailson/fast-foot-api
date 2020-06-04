/**
 * 
 */
package br.ifma.ita.fastfood.domain.db.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
@Embeddable
public class Endereco {

	@Column(name = "logradouro", columnDefinition = "varchar(150)", nullable = false, length = 150)
	private String logradouro;

	@Column(name = "numero", columnDefinition = "varchar(10)", nullable = false, length = 10)
	private String numero;

	@Column(name = "cep", columnDefinition = "varchar(10)", nullable = false, length = 10)
	private String cep;

	@Column(name = "complemento", columnDefinition = "varchar(50)", nullable = true, length = 150)
	private String complemento;

	@Column(name = "bairro", columnDefinition = "varchar(50)", nullable = true, length = 50)
	private String bairro;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Cidade.class)
	@JoinColumn(name = "cidade", nullable = false, foreignKey = @ForeignKey(name = "FK_CIDADE"))
	@Fetch(FetchMode.JOIN)
	private Cidade cidade;

}
