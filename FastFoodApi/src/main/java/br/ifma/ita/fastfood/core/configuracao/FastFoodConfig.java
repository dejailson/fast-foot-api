/**
 * 
 */
package br.ifma.ita.fastfood.core.configuracao;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.ifma.ita.fastfood.api.modelo.saida.CidadeModelo;
import br.ifma.ita.fastfood.api.modelo.saida.RestauranteModelo;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;
import br.ifma.ita.fastfood.dominio.db.modelo.TipoProduto;

/**
 * @author dejailson
 *
 */
@Configuration
public class FastFoodConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		var restauranteParaRestauranteResumo = modelMapper.createTypeMap(Restaurante.class, RestauranteModelo.class);
		
		restauranteParaRestauranteResumo.<CidadeModelo>addMapping(entidade -> entidade.getEndereco().getCidade(), 
				(resumo,cidade) -> resumo.setCidade(cidade));
		
		var produtoModeloConverter = new AbstractConverter<String, TipoProduto>() {

			@Override
			protected TipoProduto convert(String entrada) {
				
				return TipoProduto.getTipo(entrada);
			}
		};
		
		var tipoProdutoConverter = new AbstractConverter<TipoProduto, String>() {

			@Override
			protected String convert(TipoProduto tipo) {
				return tipo.getDescricao();
			}
		};
		
		modelMapper.addConverter(produtoModeloConverter);
		modelMapper.addConverter(tipoProdutoConverter);
		
		return modelMapper;
		
	}

}
