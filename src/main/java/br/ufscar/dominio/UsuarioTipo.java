package br.ufscar.dominio;

import java.util.List;

public enum UsuarioTipo {

	ADMINISTRADOR(1,"Administrador"),
	GERENTE(2,"Gerente"),
	MODERADOR(3, "Moderador"),
	COLABORADOR(4, "Colaborador");

	private int usuarioTipo;
	private String descricao;
	private List<UsuarioAcesso> acessos;

	private UsuarioTipo(int cod, String descricao){
		this.setUsuarioTipo(cod);
		this.setDescricao(descricao);
		this.acessos = new UsuarioAcesso().getAcessos(this);
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public boolean temAcesso(String descricaoAcesso){
		boolean garantido = false;
		for (UsuarioAcesso acesso : acessos) {
			if(acesso.getDescricao().equalsIgnoreCase(descricaoAcesso)){
				garantido = true;
				break;
			}
		}
		return garantido;
	}

	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public void setUsuarioTipo(int usuarioTipo) {
		this.usuarioTipo = usuarioTipo;
	}

	public int getUsuarioTipo() {
		return usuarioTipo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static UsuarioTipo recuperaTipo(String tipoString) {
		UsuarioTipo tipo = null;
		
		for (UsuarioTipo tp : UsuarioTipo.values()) {
			if(tp.getDescricao().equalsIgnoreCase(tipoString)){
				tipo = tp;
				break;
			}
		}
		return tipo;
	}

}
