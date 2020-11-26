package br.com.enade.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.enade.dao.QuestaoDao;
import br.com.enade.dao.TipoQuestaoDao;
import br.com.enade.model.Tbquestao;
import br.com.enade.model.Tbtipoquestao;
import br.com.enade.model.Tbusuario;
import br.com.enade.tx.Transacional;

@Named
@ViewScoped
public class QuestaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Tbquestao questao;

	@Inject
	private LoginBean login;

	@Inject
	private QuestaoDao questaoDao;

	@Inject
	private TipoQuestaoDao tipoQuestaoDao;

	private List<Tbquestao> questoes;

	private Long questaoId;

	private Long tipoQuestaoId;

	public void gravarTipoQuesta() {
		Tbtipoquestao tbtipoquestao = this.tipoQuestaoDao.buscarPorId(this.tipoQuestaoId);
		this.questao.setTbTipoQuestaoidTipoQuestao(tbtipoquestao);
	}

	@Transacional
	public void gravar() {
		System.out.println("Gravando usuario " + this.questao.getIdQuestao());

		if (this.questao.getIdQuestao() == null
				&& login.getUsuarioLogado().getTbTipoUsuarioidTipoUsuario().getIdTipoUsuario() != 2) {
			this.gravarTipoQuesta();
			this.questaoDao.adiciona(this.questao);
			this.questoes = this.questaoDao.listarTodos();
		} else if (login.getUsuarioLogado().getTbTipoUsuarioidTipoUsuario().getIdTipoUsuario() != 2) {
			this.gravarTipoQuesta();
			this.questaoDao.atualiza(this.questao);
			this.questoes = this.questaoDao.listarTodos();
		}
		this.questao = new Tbquestao();

	}

	@Transacional
	public void remover(Tbquestao questao) {
		System.out.println("Removendo questa " + questao.getIdQuestao());

		this.questaoDao.remove(questao);
	}

	public Tbquestao getQuestao() {
		return questao;
	}

	public void setQuestao(Tbquestao questao) {
		this.questao = questao;
	}

	public List<Tbtipoquestao> getTipoQuestoes() {
		return this.tipoQuestaoDao.listarTodos();
	}
	
	public Tbtipoquestao getTipoQuestaoDeQuestao() {
		return this.questao.getTbTipoQuestaoidTipoQuestao();
	}

	public List<Tbquestao> getQuestoes() {
		return this.questaoDao.listarTodos();
	}

	public Long getQuestaoId() {
		return questaoId;
	}

	public void setQuestaoId(Long questaoId) {
		this.questaoId = questaoId;
	}

	public Long getTipoQuestaoId() {
		return tipoQuestaoId;
	}

	public void setTipoQuestaoId(Long tipoQuestaoId) {
		this.tipoQuestaoId = tipoQuestaoId;
	}
	public void novo() {
		this.questao = new Tbquestao();

	}

}
