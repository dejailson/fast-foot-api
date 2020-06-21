/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.entrada;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import br.ifma.ita.fastfood.core.validacao.TamanhoArquivo;
import br.ifma.ita.fastfood.core.validacao.TipoConteudoArquivo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dejailson
 *
 */
@Getter
@Setter
public class ImagemModeloIn {
	
	@NotBlank
	private String descricao;
	
	
	@NotNull
	@TamanhoArquivo(maximo = "500KB")
	@TipoConteudoArquivo(permitido = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE})
	private MultipartFile arquivo;

}
