/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.ifma.ita.fastfood.api.modelo.entrada.RestauranteModeloIn;
import br.ifma.ita.fastfood.api.modelo.id.InCidadeId;
import br.ifma.ita.fastfood.dominio.db.dao.RestauranteDao;
import br.ifma.ita.fastfood.dominio.db.modelo.Cidade;
import br.ifma.ita.fastfood.dominio.db.modelo.Endereco;
import br.ifma.ita.fastfood.dominio.db.modelo.Restaurante;
import br.ifma.ita.fastfood.dominio.db.modelo.ServicoEntrega;
import br.ifma.ita.fastfood.dominio.repositorio.RestauranteRepositorio;

/**
 * @author dejailson
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestauranteEmpacotadorUT {

	//private final long ID_RESTAURANTE = 1l;

	@MockBean
	private RestauranteDao dao;

	private IRestauranteEmpacotador empacotador;

	private RestauranteRepositorio repo;

//	private ValidatorFactory validatorFactory;

	private Validator validator;
	
	@BeforeEach
	public void init() {
//		empacotador = new RestauranteSuporte(new ModelMapper());
//		this.repo = new RestauranteRepositorio(dao);
//		when(dao.findById(ID_RESTAURANTE)).thenReturn(Optional.of(getEntidade()));
//		
//		validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
	}

	@Test
	public void deveRetonarEntidade_QuandoRececerModeloIn() {

		var restauranteIn = getModeloIn();

		var entidade = empacotador.desempacotar(restauranteIn);

	//	assertThat(entidade.getTipoEntrega().getId()).isEqualTo(1l);
		assertThat(entidade.getEndereco().getCidade().getId()).isEqualTo(1l);
	}

	@Test
	public void devereRetornarModelo_QuandoReceberEntidade() {
		when(dao.findById(1l)).thenReturn(Optional.of(getEntidade()));

		var restaurante = repo.pesquisarPor(1l);

		var modelo = empacotador.empacotar(restaurante);

		assertThat(modelo.getId()).isEqualTo(1l);
	}

	@Test
	public void deveCopiarParaEntidade_QuandoReceberModeloIn() {
		var restaurante = getEntidade();
		var restauranteIn = getModeloIn();

		empacotador.copiar(restauranteIn, restaurante);

		//assertThat(restaurante.getTipoEntrega().getNome()).isBlank();
		assertThat(restaurante.getEndereco().getCidade().getNome()).isBlank();
	}
	
	@Test
	public void deveRetornarViolacao_QuandoReceberModeloIn() {
		var modelo = getModeloIn();
		modelo.getEndereco().setCidade(new InCidadeId());
		
		var violacoes = validator.validate(modelo);
		
		assertThat(violacoes.size()).isGreaterThan(1);
	}

	private Restaurante getEntidade() {
		var restaurante = new Restaurante();

		restaurante.setId(1l);
		restaurante.setDescricao("Frutos do mar");
		restaurante.setNome("Cabana do SOL");
		var endereco = new Endereco();
		endereco.setLogradouro("Av. Litorânea");
		endereco.setBairro("Calhau");
		endereco.setNumero("1000");
		endereco.setCep("650.000-00");
		endereco.setComplemento("Próximo do retorno");

		var cidade = new Cidade();
		cidade.setId(1L);
		cidade.setNome("São Luis");
		cidade.setUf("MA");

		endereco.setCidade(cidade);

		var tipo = new ServicoEntrega();
		tipo.setId(1l);
		tipo.setNome("Local");

		//restaurante.setTipoEntrega(tipo);
		restaurante.setEndereco(endereco);
		return restaurante;
	}

	private RestauranteModeloIn getModeloIn() {

//		var cidade = new InCidadeId();
//		cidade.setId(1L);
//
//		var endereco = EnderecoModeloIn.builder().logradouro("Av. Neiva moreira").numero("100").cep("650.000-00")
//				.cidade(cidade).complemento("Próximo do retorno").bairro("Turu").build();
//
//		var restauranteIn = RestauranteModeloIn.builder().nome("Cheiro Verde").descricao("Frutos do mar")
//				.endereco(endereco).build();
		return null;
	}

}
