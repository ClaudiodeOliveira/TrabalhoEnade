package br.com.enade.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.enade.dao.TipoUsuarioDao;
import br.com.enade.dao.UsuarioDao;
import br.com.enade.model.Tbtipousuario;
import br.com.enade.model.Tbusuario;
import br.com.enade.tx.Transacional;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Tbusuario usuario;

	@Inject
	private UsuarioDao dao;

	@Inject
	private TipoUsuarioDao tipoUsuarioDao;

	@Inject
	private FacesContext context;
	
	@Inject
	private LoginBean login;

	private Long usuarioId;

	private Long tipoUsuarioId;

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public List<Tbtipousuario> getTipoUsuarios() {
		return this.tipoUsuarioDao.listaTodos();
	}

	public void carregarUsuarioId() {
		System.out.println("Carregando usuario");
		this.usuario = this.dao.buscarPorId(usuarioId);
	}

	public void gravarTipoUsuario() {
		Tbtipousuario tbtipousuario = this.tipoUsuarioDao.buscarPorId(this.tipoUsuarioId);
		this.usuario.setTbTipoUsuarioidTipoUsuario(tbtipousuario);
	}

	public Tbtipousuario getTipoUsuarioDeUsuario() {
		return this.usuario.getTbTipoUsuarioidTipoUsuario();
	}

	@Transacional
	public String gravar() {
		System.out.println("Gravando usuario " + this.usuario.getNomeUsuario());
		this.gravarTipoUsuario();
		if (this.usuario.getIdUsuario() == null && login.getUsuarioLogado().getTbTipoUsuarioidTipoUsuario().getIdTipoUsuario() != 2) {
			this.dao.adiciona(this.usuario);
		} else if ( this.usuario.getTbTipoUsuarioidTipoUsuario().getIdTipoUsuario() != 2) {
			this.dao.atualiza(this.usuario);
		}
		this.usuario = new Tbusuario();

		return "usuarios?faces-redirect=true";

	}

	@Transacional
	public void remover(Tbusuario usuario) {
		System.out.println("Removendo usuario" + usuario.getNomeUsuario());

		this.dao.remove(usuario);
	}

	public List<Tbusuario> getUsuarios() {
		return this.dao.listarTodos();
	}

	public Tbusuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Tbusuario usuario) {
		this.usuario = usuario;
	}

	public void carregar(Tbusuario ususario) {
		this.usuario = ususario;
	}

	public void novo() {
		this.usuario = new Tbusuario();

	}

	public Long getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(Long tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
	}

}
