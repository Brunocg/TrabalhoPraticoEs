package br.ufscar.dominio;

import java.util.List;

public class UsuarioAcesso {

	private String descricao;
	private int[] niveisDeAcesso;// codigo de quem tem acesso

	public UsuarioAcesso() {
		super();
	}

	public UsuarioAcesso(String descricao, int[] niveisDeAcesso) {
		super();
		this.descricao = descricao;
		this.niveisDeAcesso = niveisDeAcesso;
	}

	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public List<UsuarioAcesso> getAcessos(int cod) {
		// recupera do bd os acessos baseados no cod
		return null;
	}

	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int[] getNiveisDeAcesso() {
		return niveisDeAcesso;
	}
	public void setNiveisDeAcesso(int[] niveisDeAcesso) {
		this.niveisDeAcesso = niveisDeAcesso;
	}




}
