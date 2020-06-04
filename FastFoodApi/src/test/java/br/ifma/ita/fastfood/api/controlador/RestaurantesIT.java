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
public class RestaurantesIT {
	

	private String CAMPO_NOME = "nome";
	private String FILTRO_SITUACAO_VALOR = "ABERTO";
	private String FILTRO_SITUACAO = "situacao";
	private String FILTRO_CODIGO_SERVICO_ENTREGA = "codigoServicoEntrega";
	private String FILTRO_CODIGO_CIDADE = "codigoCidade";
	private String FILTRO_NOME_VALOR = "Cabana do Sol";
	private String FILTRO_NOME = "nome";
	private String QUERY_PARAM_COMPLETO = "completo";
	private String BASE_PATH = "/restaurantes";
	private String BASE_PATH_ASSOCIAR_SERVICO = BASE_PATH.concat("/{codigo}/servico-entregas");
	private String RESTAURANTE_COCO_JSON;
	private String RESTAURANTE_CABANA_ATUALIZAR;
	private String RESTAURANTE_CABANA_ATUALIZAR_NOME_DUPLICADO;
	private String RESTAURANTE_CIDADE_INEXISTENTE;
	private String RESTAURANTE_CIDADE_NULO;
	private String RESTAURANTE_CAMPO_NULO;
	private String RESTAURANTE_NOME_DUPLICADO;

	private boolean COMPLETO = true;
	
	private int FILTRO_CODIGO_CIDADE_VALOR = 1;
	private int FILTRO_CODIGO_SERVICO_ENTREGA_VALOR = 1;
	private int ID_RESTAURANTE = 1;
	private int ID_RESTAURANTE_SALVO = 3;
	private int ID_RESTAURANTE_INEXISTENTE = 10;
	private int ID_RESTAURANTE_ATUALIZADO = 2;
	private int ID_SERVICO_ENTREGA = 1;
	private int ID_SERVICO_ENTREGA_INSERIDO = 2;
	private int ID_SERVICO_ENTREGA_INEXISTENTE = 10;
	private int QUANTIDADE_ITENS = 2;
	@LocalServerPort
	private int porta;

	
	@BeforeEach
	public void initialize() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		port = porta;
		basePath = BASE_PATH;
		RESTAURANTE_COCO_JSON = getContentFromResource("/json/correto/cadastrar-restaurante-coco-bambum.json");
		RESTAURANTE_CABANA_ATUALIZAR = getContentFromResource("/json/correto/atualizar-restaurante-cabana.json");
		RESTAURANTE_CIDADE_INEXISTENTE = getContentFromResource("/json/incorreto/cadastrar-restaurante-com-cidade-inexistente.json");
		RESTAURANTE_CIDADE_NULO = getContentFromResource("/json/incorreto/cadastrar-restaurante-com-cidade-nulo.json");
		RESTAURANTE_CAMPO_NULO = getContentFromResource("/json/incorreto/cadastrar-restaurante-com-campo-nulo.json");
		RESTAURANTE_NOME_DUPLICADO = getContentFromResource("/json/incorreto/cadastrar-restaurante-nome-duplicado.json");
		RESTAURANTE_CABANA_ATUALIZAR_NOME_DUPLICADO = getContentFromResource("/json/incorreto/atualizar-restaurante-nome-duplicado.json");
	}
	
	@Test
	public void deveRetornar201_QuandoSalvarRestaurante() {
		given()
			.body(RESTAURANTE_COCO_JSON)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("codigo", equalTo(ID_RESTAURANTE_SALVO))
			.log()
			.all();
	}
	
	@Test
	public void deveRetornar400_QuandoSalvarRestauranteComCidadeInexistente() {
		given()
			.body(RESTAURANTE_CIDADE_INEXISTENTE)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornar400_QuandoSalvarRestauranteCampoNulo() {
		given()
			.body(RESTAURANTE_CAMPO_NULO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornar400_QuandoSalvarRestauranteCidadeNulo() {
		given()
			.body(RESTAURANTE_CIDADE_NULO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornar409_QuandoSalvarRestauranteNomeDuplicado() {
		given()
			.body(RESTAURANTE_NOME_DUPLICADO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CONFLICT.value());
	}
	
	@Test
	public void deveRetornar200_QuandoAtualizarRestaurante() {
		given()
			.body(RESTAURANTE_CABANA_ATUALIZAR)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
		.when()
			.put("/{codigo}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar404_QuandoAtualizarRestauranteInexistente() {
		given()
			.body(RESTAURANTE_CABANA_ATUALIZAR)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_INEXISTENTE)
		.when()
			.put("/{codigo}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornar400_QuandoAtualizarRestauranteNomeDuplicado() {
		given()
			.body(RESTAURANTE_CABANA_ATUALIZAR_NOME_DUPLICADO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_ATUALIZADO)
		.when()
			.put("/{codigo}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql(value = {"/db/servico-entrega-mock.sql"})
	public void deveRetornar204_QuandoAssociarServicoEntrega() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
			.pathParam("codigoServico", ID_SERVICO_ENTREGA_INSERIDO)
			.basePath(BASE_PATH_ASSOCIAR_SERVICO)
		.when()
			.put("/{codigoServico}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());	
	}
	
	@Test
	public void deveRetornar204_QuandoDesassociarServicoEntrega() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
			.pathParam("codigoServico", ID_SERVICO_ENTREGA)
			.basePath(BASE_PATH_ASSOCIAR_SERVICO)
		.when()
			.delete("/{codigoServico}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());	
	}
	
	@Test
	public void deveRetornar404_QuandoAssociarServicoEntregaComRestauranteInexistente() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_INEXISTENTE)
			.pathParam("codigoServico", ID_SERVICO_ENTREGA)
			.basePath(BASE_PATH_ASSOCIAR_SERVICO)
		.when()
			.put("/{codigoServico}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());	
	}
	
	@Test
	public void deveRetornar400_QuandoDesassociarServicoEntregaComServicoInexistente() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
			.pathParam("codigoServico", ID_SERVICO_ENTREGA_INEXISTENTE)
			.basePath(BASE_PATH_ASSOCIAR_SERVICO)
		.when()
			.delete("/{codigoServico}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());	
	}
	
	@Test
	public void deveRetornar204_QuandoFecharRestaurante() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
		.when()
			.delete("/{codigo}/aberto")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornar204_QuandoAbrirRestaurante() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
		.when()
			.put("/{codigo}/aberto")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornar404_QuandoFecharRestauranteInexistente() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_INEXISTENTE)
		.when()
			.delete("/{codigo}/aberto")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornar200EDoisRestaurante_QuandoListar() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.queryParam(QUERY_PARAM_COMPLETO, COMPLETO)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body(CAMPO_NOME, hasSize(QUANTIDADE_ITENS))
			.log().all();
	}
	
	@Test
	public void deveRetornar200_QuandoPesquisarRestauranteFiltro() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.queryParam(QUERY_PARAM_COMPLETO, COMPLETO)
			.queryParam(FILTRO_NOME, FILTRO_NOME_VALOR)
			.queryParam(FILTRO_CODIGO_CIDADE, FILTRO_CODIGO_CIDADE_VALOR)
			.queryParam(FILTRO_CODIGO_SERVICO_ENTREGA, FILTRO_CODIGO_SERVICO_ENTREGA_VALOR)
			.queryParam(FILTRO_SITUACAO, FILTRO_SITUACAO_VALOR)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.log()
			.all();
	}
	
	@Test
	public void deveRetornar200_QuandoPesquisarEndereco() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
		.when()
			.get("/{codigo}/endereco")
		.then()
			.statusCode(HttpStatus.OK.value())
			.log()
			.all();
	}
	
	@Test
	public void deveRetornar200_QuandoPesquisarRestaurante() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
		.when()
			.get("/{codigo}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}

}
