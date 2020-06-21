/**
 * 
 */
package br.ifma.ita.fastfood.api.controlador;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Sql(value = {"/db/inserts-dados-mock.sql","/db/produto-mock.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/db/limpar-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ImagensTI {
	
	private String SKU_PRODUTO_CHEIRO = "PTPPEIXCHEI15";
	private String SKU_PRODUTO_CABANA = "PTPATUMCABA10";
	private String SKU_PRODUTO_CABANA_SALADA = "ENTSALACABA15";
	private String SKU_PRODUTO_INEXISTENTE= "PTPATUMCABA1012334444";
	private final String BASE_PATH = "/restaurantes/{codigo}/produtos/{sku}/imagens";
	
	private int ID_RESTAURANTE_CABANA = 1;
	private int ID_RESTAURANTE_CHEIRO = 2;
	private int ID_RESTAURANTE_INEXISTENTE = 10;
	@LocalServerPort
	private int porta;
	
	private File arquivo;
	private File arquivoPDF;
	
	@BeforeEach
	public void initialize() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		port = porta;
		basePath = BASE_PATH;
		try {
			arquivo =new File(ImagensTI.class.getResource("/foto/bife.jpg").toURI());
			arquivoPDF =new File(ImagensTI.class.getResource("/foto/arquivo.pdf").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/imagem-mock.sql")
	public void deveRetornarStatus200_QuandoPesquisarImagemJSON() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CABANA)
			.pathParam("sku", SKU_PRODUTO_CABANA_SALADA)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/imagem-mock.sql")
	public void deveRetornarStatus200_QuandoPesquisarImagem() {
		given()
			.contentType(ContentType.JSON)
			.accept(MediaType.IMAGE_JPEG_VALUE)
			.pathParam("codigo", ID_RESTAURANTE_CABANA)
			.pathParam("sku", SKU_PRODUTO_CABANA_SALADA)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.contentType(MediaType.IMAGE_JPEG_VALUE);
	}
	
	@Test
	public void deveRetornarStatus200_QuandoEnviarImagem() {
		given()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CHEIRO)
			.pathParam("sku", SKU_PRODUTO_CHEIRO)
			.multiPart("arquivo", arquivo,MediaType.IMAGE_JPEG_VALUE)
			.multiPart("descricao", "Nova foto")
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/imagem-mock.sql")
	public void deveRetornarStatus200_QuandoAtualizarImagem() {
		given()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CABANA)
			.pathParam("sku", SKU_PRODUTO_CABANA)
			.multiPart("arquivo", arquivo, MediaType.IMAGE_JPEG_VALUE)
			.multiPart("descricao", "Nova foto")
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
	}
	
	
	@Test
	@SqlMergeMode(MergeMode.MERGE)
	@Sql("/db/imagem-mock.sql")
	public void deveRetornarStatus204_QuandoExcluirImagem() {
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CHEIRO)
			.pathParam("sku", SKU_PRODUTO_CHEIRO)
		.when()
			.delete()
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoEnviarComRestauranteInexistente() {
		given()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_INEXISTENTE)
			.pathParam("sku", SKU_PRODUTO_CHEIRO)
			.multiPart("arquivo", arquivo, MediaType.IMAGE_JPEG_VALUE)
			.multiPart("descricao", "Nova foto")
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoEnviarComSKUInexistente() {
		given()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CHEIRO)
			.pathParam("sku", SKU_PRODUTO_INEXISTENTE)
			.multiPart("arquivo", arquivo, MediaType.IMAGE_JPEG_VALUE)
			.multiPart("descricao", "Nova foto")
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoEnviarImagemSemDescricao() {
		given()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CHEIRO)
			.pathParam("sku", SKU_PRODUTO_CHEIRO)
			.multiPart("arquivo", arquivo, MediaType.IMAGE_JPEG_VALUE)
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoEnviarImagemSemArquivo() {
		given()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CHEIRO)
			.pathParam("sku", SKU_PRODUTO_CHEIRO)
			.multiPart("descricao", "Nova foto")
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoEnviarImagemMaior500() {
		given()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CHEIRO)
			.pathParam("sku", SKU_PRODUTO_CHEIRO)
			.multiPart("arquivo", arquivoPDF, MediaType.IMAGE_JPEG_VALUE)
			.multiPart("descricao", "Nova foto")
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus400_QuandoEnviarImagemIncompativel() {
		given()
			.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
			.accept(ContentType.JSON)
			.pathParam("codigo", ID_RESTAURANTE_CHEIRO)
			.pathParam("sku", SKU_PRODUTO_CHEIRO)
			.multiPart("arquivo", arquivoPDF, MediaType.APPLICATION_PDF_VALUE)
			.multiPart("descricao", "Nova foto")
		.when()
			.put()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus404_QuandoPesquisarImagemSKUInxistente() {
		given()
			.contentType(ContentType.JSON)
			.accept(MediaType.IMAGE_JPEG_VALUE)
			.pathParam("codigo", ID_RESTAURANTE_CABANA)
			.pathParam("sku", SKU_PRODUTO_INEXISTENTE)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
