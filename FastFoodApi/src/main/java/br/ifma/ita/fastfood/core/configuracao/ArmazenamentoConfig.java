/**
 * 
 */
package br.ifma.ita.fastfood.core.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.ifma.ita.fastfood.core.armazenamento.ArmazenamentoPropriedades;
import br.ifma.ita.fastfood.core.armazenamento.infraestrutura.ArmazenamentoLocalServico;

/**
 * @author dejailson
 *
 */
@Configuration
public class ArmazenamentoConfig {
	
	@Autowired
	private ArmazenamentoPropriedades propriedades;
	
	@Bean
	@ConditionalOnProperty(name = "fastfood.api.armazenamento.tipo",havingValue = "local")
	public ArmazenamentoLocalServico armazenamentoLocalServico() {
		return new ArmazenamentoLocalServico(propriedades.getLocal().getNomeDiretorio());
	}

}
