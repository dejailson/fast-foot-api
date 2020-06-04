/**
 * 
 */
package br.ifma.ita.fastfood.api.controlador;

import static br.ifma.ita.fastfood.util.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.http.ContentType;

/**
 * @author dejailson
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(value = "/db/inserts-dados-mock.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/db/limpar-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CidadesIT {

	private final int QUANTIDADE_ITENS = 2;

	private final String CAMPO_NOME = "nome";

	private final String BASE_PATH = "/cidades";

	// private static final String NOME_CIDADE = "São Luís";

	private final int ID_CIDADE = 1;
	private final int ID_CIDADE_SALVO = 3;
	private final int ID_CIDADE_NAO_EXISTE = 10;

	private String cidadeSaoLuisJson;
	private String cidadeFortelzaAtualizarJson;
	private String cidadeCampoInexistenteAtulizarJson;
	private String cidadeCampoInexistenteJson;
	private String cidadeCampoNuloJson;
	private String cidadeCampoNuloAtualizarJson;
	private String cidadeCampoDuplicadoJson;

	@LocalServerPort
	private int porta;

	@BeforeEach
	public void initialize() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		port = porta;
		basePath = BASE_PATH;

		cidadeSaoLuisJson = getContentFromResource("/json/correto/cadastrar-cidade-saoluis.json");
		cidadeFortelzaAtualizarJson = getContentFromResource("/json/correto/atualizar-cidade-fortaleza.json");
		cidadeCampoInexistenteJson = getContentFromResource("/json/incorreto/cadastrar-cidade-campo-inexistente.json");
		cidadeCampoNuloJson = getContentFromResource("/json/incorreto/cadastrar-cidade-campo-nulo.json");
		cidadeCampoDuplicadoJson = getContentFromResource("/json/incorreto/cadastrar-cidade-nome-duplicado.json");
		cidadeCampoNuloAtualizarJson = getContentFromResource("/json/incorreto/atualizar-cidade-campo-nulo.json");
		cidadeCampoInexistenteAtulizarJson = getContentFromResource("/json/incorreto/atualizar-cidade-campo-inexistente.json");
	}

	@Test
	public void deveRetornarStatus201_QuandoSalvarCidade() {
		given()
			.body(cidadeSaoLuisJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("codigo",equalTo(ID_CIDADE_SALVO))
			.log().all();
	}

	@Test
	public void deveRetornarStatus400_QuandoSalvarCidadeCampoInexistente() {
		given()
			.body(cidadeCampoInexistenteJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoSalvarCidadeCampoNulo() {
		given()
			.body(cidadeCampoNuloJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log()
			.all();
	}
	
	@Test
	public void deveRetornarStatus409_QuandoSalvarCidadeNomeDuplicado() {
		given()
			.body(cidadeCampoDuplicadoJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CONFLICT.value())
			.log().all();
	}

	@Test
	public void deveRetornarStatus200_QuandoAtualizarCidade() {
		given().contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_CIDADE)
			.body(cidadeFortelzaAtualizarJson)
		.when()
			.put("/{codigo}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoAtualizarCidadeNaoExiste() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_CIDADE_NAO_EXISTE)
			.body(cidadeFortelzaAtualizarJson)
		.when()
			.put("/{codigo}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoAtualizarCidadeCampoNulo() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_CIDADE)
			.body(cidadeCampoNuloAtualizarJson)
		.when()
			.put("/{codigo}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoAtualizarCidadeCampoInexistente() {
		given()
			.body(cidadeCampoInexistenteAtulizarJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_CIDADE)
		.when()
			.put("/{codigo}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}

	@Test
	@SqlMergeMode(MergeMode.OVERRIDE)
	@Sql("/db/cidades-mock.sql")
	public void deveRetornarStatus204_QuandoExcluirCidade() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_CIDADE)
		.when()
			.delete("/{codigo}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value())
			.log().all();
	}

	@Test
	public void deveRetornarStatus404_QuandoExcluirCidadeNaoExiste() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_CIDADE_NAO_EXISTE)
		.when()
			.delete("/{codigo}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.log().all();
	}

	@Test
	public void deveRetornarStatus400_QuandoExcluirCidadeEmUso() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_CIDADE)
		.when()
			.delete("/{codigo}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}

	@Test
	public void deveRetornarStatus415_QuandoSalvarCidade() {
		given()
			.body("")
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
			.log().all();
	}
	
	@Test
	public void deveRetornar200EDuasCidades_QuandoListar() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body(CAMPO_NOME, hasSize(QUANTIDADE_ITENS))
			.log().all();
	}
	
	@Test
	public void deveRetornar200EDoisServicos_QuandoListar() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.basePath("/servico-entregas")
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body(CAMPO_NOME, hasSize(QUANTIDADE_ITENS))
			.log().all();
	}
}
