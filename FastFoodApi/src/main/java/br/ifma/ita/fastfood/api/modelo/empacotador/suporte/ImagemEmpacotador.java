/**
 * 
 */
package br.ifma.ita.fastfood.api.modelo.empacotador.suporte;

import org.springframework.stereotype.Component;

import br.ifma.ita.fastfood.api.modelo.empacotador.IImagemEmpacotador;
import br.ifma.ita.fastfood.api.modelo.entrada.ImagemModeloIn;
import br.ifma.ita.fastfood.api.modelo.saida.ImagemModelo;
import br.ifma.ita.fastfood.dominio.db.modelo.Imagem;

/**
 * @author dejailson
 *
 */
@Component
public class ImagemEmpacotador extends EmpacotadorSuporte<ImagemModeloIn, ImagemModelo, Imagem> implements IImagemEmpacotador{

	public ImagemEmpacotador() {
		super(ImagemModelo.class, Imagem.class);
	}

	@Override
	public Imagem desempacotar(ImagemModeloIn modeloIn) {
		var imagem = new Imagem();
		imagem.setDescricao(modeloIn.getDescricao());
		imagem.setNome(modeloIn.getArquivo().getOriginalFilename());
		imagem.setTipoConteudo(modeloIn.getArquivo().getContentType());
		return imagem;
	}
}
