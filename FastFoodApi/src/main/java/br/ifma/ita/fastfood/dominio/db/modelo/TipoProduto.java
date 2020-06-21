/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.modelo;

import java.util.Arrays;

/**
 * @author dejailson
 *
 */
public enum TipoProduto {
	
	ENTRADA("Entrada","ETD"),
	BEBIDA("Bebida","BBD"),
	LANCHE("Lanche","LCH"),
	PRATO_PRINCIPAL("Prato Principal","PTP"),
	SOBREMESA("Sobremesa", "SBM");
	
	private String descricao;
	private String prefixo;

	private TipoProduto(String descricao,String prefixo) {
		this.descricao = descricao;
		this.prefixo = prefixo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getPrefixo() {
		return this.prefixo;
	}
	
	public static TipoProduto getTipo(String descricao) {
		return Arrays.asList(TipoProduto.values())
				.stream()
				.filter(t -> t.getDescricao().equalsIgnoreCase(descricao)).findFirst().orElse(null);
	}

}
