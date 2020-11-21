package br.com.enade.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.enade.model.Tbusuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	EntityManager em;
	
	private DAO<Tbusuario>dao;
	
	 @PostConstruct
	    void init() {
	        this.dao = new DAO<Tbusuario>(this.em, Tbusuario.class);
	    }
	 
	 public Tbusuario buscarPorId(Long usuario) {
		return this.dao.buscaPorId(usuario);
	}
	 
	 public void adiciona(Tbusuario usuario) {
		 this.dao.adiciona(usuario);
	 }
	 
	 public void atualiza(Tbusuario usuario) {
		this.dao.atualiza(usuario);
	}
	 
	 public void remove(Tbusuario usuario) {
		 this.dao.remove(usuario);
	 }
	 
	 public List<Tbusuario> listarTodos() {
		return this.dao.listaTodos();
	}	 
	 

	public Tbusuario existe(Tbusuario usuario) {

		TypedQuery<Tbusuario> query = em.createQuery(
				" select u from Tbusuario u  where u.emailUsuario = :pEmail and u.senhaUsuario = :pSenha",
				Tbusuario.class);

		query.setParameter("pEmail", usuario.getEmailUsuario());
		query.setParameter("pSenha", usuario.getSenhaUsuario());
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public Tbusuario recuperarUsuario(Tbusuario usuario) {

		TypedQuery<Tbusuario> query = em.createQuery(
				"select u from Tbusuario u where  u.emailUsuario = :pEmail and u.senhaUsuario = :pSenha",
				Tbusuario.class);
		query.setParameter("pEmail", usuario.getEmailUsuario());
		query.setParameter("pSenha", usuario.getSenhaUsuario());
		return query.getSingleResult();
	}

	
	
}
