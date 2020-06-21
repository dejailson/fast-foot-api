/**
 * 
 */
package br.ifma.ita.fastfood.dominio.db.ouvinte;

import java.util.UUID;

import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import br.ifma.ita.fastfood.core.armazenamento.evento.ArmazenamentoRemoverEvento;
import br.ifma.ita.fastfood.dominio.db.modelo.Imagem;


/**
 * @author dejailson
 *
 */
public class ImagemOuvinte {
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@PrePersist
	@PreUpdate
	public void adicionarUUID(Imagem imagem) {
		var nomeUUID = UUID.randomUUID().toString().concat("_").concat(imagem.getNome());
		imagem.setNome(nomeUUID);
	}
	
	@PostRemove
	public void postExcluir(Imagem imagem) {
		this.eventPublisher.publishEvent(new ArmazenamentoRemoverEvento(imagem.getNome()));
	}
}
