package br.ufscar.dominio;

import java.util.Date;

public class Usuario {

	private int idUsuario;
	private String login;
	private String senha;
	private Date ultimoLogin;
	private boolean estado;
	private Date ts;
	private Responsavel aprovadoPor = null;
	private UsuarioTipo tipo = null;


	public Usuario() {
		super();
	}

	public Usuario(int idUsuario, String login, String senha,
			Date ultimoLogin, boolean estado, Date ts,
			Responsavel aprovadoPor, UsuarioTipo tipo) {
		super();
		this.idUsuario = idUsuario;
		this.login = login;
		this.senha = senha;
		this.ultimoLogin = ultimoLogin;
		this.estado = estado;
		this.ts = ts;
		this.aprovadoPor = aprovadoPor;
		this.tipo = tipo;
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public boolean validaLogin(String senha){
		return getSenha().equals(senha);
	}
	
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
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

	public UsuarioTipo getTipo() {
		return tipo;
	}

	public void setTipo(UsuarioTipo tipo) {
		this.tipo = tipo;
	}


	public Responsavel getAprovadoPor() {
		return aprovadoPor;
	}

	public void setAprovadoPor(Responsavel aprovadoPor) {
		this.aprovadoPor = aprovadoPor;
	}

}
