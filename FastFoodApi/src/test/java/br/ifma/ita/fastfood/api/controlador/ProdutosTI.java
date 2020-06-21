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
public class ProdutosTI {
	
	private String PRODUTO_PRATO_PRINCIPAL;
	private String PRODUTO_CAMPO_NULO;
	private String PRODUTO_ATUALIZAR;
	private String PRODUTO_TIPO_PRODUTO_INEXISTENTE;
	private final String BASE_PATH = "/restaurantes/{codigo}/produtos";
	
	private int ID_RESTAURANTE= 1;
	private int ID_RESTAURANTE_PRODUTO= 2;
	private int ID_RESTAURANTE_INEXISTENTE= 3;
	
	private String SKU_PRODUTO_SALVO = "PTPCAMACABA14";
	private String SKU_PRODUTO = "PTPPEIXCHEI15";
	private String SKU_PRODUTO_INEXISTENTE = "PTPPEIXCHEI15XSSXSXS";
	
	@LocalServerPort
	private int porta;

	@BeforeEach
	public void initialize() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		port = porta;
		basePath = BASE_PATH;
		PRODUTO_PRATO_PRINCIPAL = getContentFromResource("/json/correto/cadastrar-produto-prato-principal.json");
		PRODUTO_CAMPO_NULO = getContentFromResource("/json/incorreto/cadastrar-produto-campo-nulo.json");
		PRODUTO_TIPO_PRODUTO_INEXISTENTE = getContentFromResource("/json/incorreto/cadastrar-produto-tipo-inexistente.json");
		PRODUTO_ATUALIZAR = getContentFromResource("/json/correto/atualizar-produto.json");
	}
	
	@Test
	public void deveRetornarStatus201_QuandoSalvarProduto() {
		given()
			.body(PRODUTO_PRATO_PRINCIPAL)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("sku",equalTo(SKU_PRODUTO_SALVO))
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoSalvarProdutoComRestauranteInexistente() {
		given()
			.body(PRODUTO_PRATO_PRINCIPAL)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_INEXISTENTE)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoSalvarProdutoComCampoNulo() {
		given()
			.body(PRODUTO_CAMPO_NULO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoSalvarProdutoComTipoInexistente() {
		given()
			.body(PRODUTO_TIPO_PRODUTO_INEXISTENTE)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}

	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus200_QuandoAtualizarProduto() {
		given()
			.body(PRODUTO_ATUALIZAR)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.pathParam("sku", SKU_PRODUTO)
		.when()
			.put("/{sku}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoAtualizarProdutoComSKUInexistente() {
		given()
			.body(PRODUTO_ATUALIZAR)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.pathParam("sku", SKU_PRODUTO_INEXISTENTE)
		.when()
			.put("/{sku}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoAtualizarProdutoComRestauranteInexistente() {
		given()
			.body(PRODUTO_ATUALIZAR)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_INEXISTENTE)
			.pathParam("sku", SKU_PRODUTO)
		.when()
			.put("/{sku}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus200_QuandoConsultarProduto() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.pathParam("sku", SKU_PRODUTO)
		.when()
			.get("/{sku}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus404_QuandoConsultarProdutoComSKUInexistente() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.pathParam("sku", SKU_PRODUTO_INEXISTENTE)
		.when()
			.get("/{sku}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus400_QuandoConsultarProdutoComRestauranteInexistente() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_INEXISTENTE)
			.pathParam("sku", SKU_PRODUTO)
		.when()
			.get("/{sku}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus200_QuandoConsultarProdutos() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus200_QuandoConsultarProdutosComParam() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.queryParam("completo", true)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus200_QuandoConsultarProdutosComParamEFiltro() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.queryParam("completo", true)
			.queryParam("nome", "Peix")
			.queryParam("valorInicial", 100)
			.queryParam("valorFinal", 500)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus400_QuandoConsultarProdutosComParamEFiltroErro() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.queryParam("completo", true)
			.queryParam("nome", "Peix")
			.queryParam("valorInicial", 100)
			.queryParam("valorFinal", 0)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus200_QuandoExcluirProduto() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.pathParam("sku", SKU_PRODUTO)
		.when()
			.delete("/{sku}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/produto-mock.sql")
	public void deveRetornarStatus200_QuandoExcluirProdutoComSKUInexistente() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_PRODUTO)
			.pathParam("sku", SKU_PRODUTO_INEXISTENTE)
		.when()
			.delete("/{sku}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.log().all();
	}
}
