package br.ufscar.consulta;

import java.util.Date;

import br.ufscar.dominio.UsuarioTipo;

public class UsuarioData {

	private int idUsuario;
	private String login;
	private String senha;
	private Date ultimoLogin;
	private boolean estado;
	private Date ts;


	public UsuarioData() {
		super();
	}


	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public Date getUltimoLogin() {
		return ultimoLogin;
	}


	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}


	public boolean isEstado() {
		return estado;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public Date getTs() {
		return ts;
	}


	public void setTs(Date ts) {
		this.ts = ts;
	}
}
