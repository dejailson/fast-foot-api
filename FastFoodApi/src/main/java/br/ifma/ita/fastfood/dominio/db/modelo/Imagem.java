package br.ifma.ita.fastfood.dominio.db.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.ifma.ita.fastfood.dominio.db.ouvinte.ImagemOuvinte;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "imagem")
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true, onlyExplicitlyIncluded = true)
@EntityListeners(ImagemOuvinte.class)
public class Imagem extends EntidadeBase{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "descricao", nullable = false, columnDefinition = "varchar(150)",length = 150)
	private String descricao;
	
	@Column(name = "tipo_conteudo", nullable = false)
	private String tipoConteudo;
	
	@OneToOne(fetch = FetchType.EAGER,targetEntity = Produto.class)
	@JoinColumn(name = "produto_id",foreignKey = @ForeignKey(name ="PRODUTO_ID_FK"))
	private Produto produto;

	public void clonar(Imagem imagem) {
		this.nome = imagem.getNome();
		this.descricao = imagem.getDescricao();
		this.tipoConteudo = imagem.getTipoConteudo();
	}
}
