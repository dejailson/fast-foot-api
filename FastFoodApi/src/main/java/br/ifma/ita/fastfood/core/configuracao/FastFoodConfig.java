/**
 * 
 */
package br.ifma.ita.fastfood.core.configuracao;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.ifma.ita.fastfood.api.modelo.saida.CidadeModelo;
import br.ifma.ita.fastfood.api.modelo.saida.RestauranteModelo;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;

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
		
		return modelMapper;
		
	}

}
