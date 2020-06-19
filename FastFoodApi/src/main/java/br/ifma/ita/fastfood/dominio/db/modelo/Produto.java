/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.modelo;

import static br.ifma.ita.fastfood.core.util.GerenciadorPrefixoUtil.gerarPrefixo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.ifma.ita.fastfood.dominio.db.ouvinte.ProdutoOuvinte;
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
@Table(name = "produto")
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(ProdutoOuvinte.class)
public class Produto extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private final int TAMANHO_PREFIXO = 4;
	
	@Column(name = "nome", nullable = false,columnDefinition = "varchar(100)")
	private String nome;
	@Column(name = "descricao", nullable = false,columnDefinition = "varchar(150)",length = 150)
	private String descricao;
	@Column(name = "sku", nullable = false,columnDefinition = "varchar(255)",unique = true)
	private String sku;
	
	@Column(name = "peso", nullable = false)
	private Long peso;
	
	@Column(name = "preco_unitario", nullable = false)
	private BigDecimal precoUnitario;
	
	@Column(name = "disponivel", nullable = false, columnDefinition = "boolean default true")
	private Boolean disponivel = Boolean.TRUE;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity = Restaurante.class)
	@JoinColumn(name = "restaurante_id",foreignKey = @ForeignKey(name ="RESTAURANTE_ID_FK"))
	private Restaurante restaurante;
	
	@Transient
	public String getPrefixoNome() {
		return gerarPrefixo(this.nome,TAMANHO_PREFIXO);
	}

}
