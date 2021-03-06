/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.modelo;

import static br.ifma.ita.fastfood.core.util.GerenciadorPrefixoUtil.gerarPrefixo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Table(name = "restaurante")
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class Restaurante extends EntidadeBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private final int TAMANHO_PREFIXO = 4;

	@Column(name = "nome", nullable = false, columnDefinition = "varchar(100)", unique = true, length = 30)
	private String nome;
	@Column(name = "descricao", nullable = false, columnDefinition = "varchar(150)", length = 150)
	private String descricao;
	
	@Column(name = "aberto", nullable = false)
	private Boolean aberto;

	@Embedded
	private Endereco endereco;
	
	@ManyToMany(fetch = FetchType.LAZY,targetEntity = ServicoEntrega.class)
	@JoinTable(name = "restaurante_servico_entrega",joinColumns = @JoinColumn( name ="restaurante_id",
		foreignKey = @ForeignKey(name = "RESTAURANTE_ID_FK")),
		inverseJoinColumns = @JoinColumn(name="servico_entrega_id", foreignKey = @ForeignKey(name = "SERVICO_ENTREGA_ID_FK")))
	@Fetch(FetchMode.JOIN)
	private Set<ServicoEntrega> servicoEntregas;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "restaurante",targetEntity = Produto.class)
	@Fetch(FetchMode.JOIN)
	private Set<Produto> produtos;
	
	public Restaurante() {
		this.servicoEntregas = new HashSet<ServicoEntrega>();
		this.produtos = new HashSet<Produto>();
	}	
	
	@Transient
	public void adicionarServicoEntrega(ServicoEntrega servicoEntrega) {
		this.servicoEntregas.add(servicoEntrega);
	}
	
	@Transient
	public void removerServicoEntrega(ServicoEntrega servicoEntrega) {
		this.servicoEntregas.remove(servicoEntrega);
	}
	
	@Transient
	public void abrir() {
		this.setAberto(Boolean.TRUE);
	}
	
	@Transient
	public void fechar() {
		this.setAberto(Boolean.FALSE);
	}
	
	private void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}
	
	@PrePersist
	private void initialize() {
		this.abrir();
	}
	
	@Transient
	public String getPrefixoNome() {
		return gerarPrefixo(this.nome,TAMANHO_PREFIXO);
	}
}
