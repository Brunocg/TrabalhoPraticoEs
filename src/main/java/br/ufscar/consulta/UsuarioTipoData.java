package br.ufscar.consulta;

import java.util.List;

public enum UsuarioTipoData {

	ADMINISTRADOR(1,"Administrador"),
	GERENTE(2,"Gerente"),
	MODERADOR(3, "Moderador"),
	COLABORADOR(4, "Colaborador");

	private int usuarioTipo;
	private String descricao;
	private List<UsuarioAcessoData> acessos;

	private UsuarioTipoData(int cod, String descricao){
		this.setUsuarioTipo(cod);
		this.setDescricao(descricao);
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public boolean temAcesso(String descricaoAcesso){
		boolean garantido = false;
		for (UsuarioAcessoData acesso : acessos) {
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

	public static UsuarioTipoData recuperaTipo(String tipoString) {
		UsuarioTipoData tipo = null;
		
		for (UsuarioTipoData tp : UsuarioTipoData.values()) {
			if(tp.getDescricao().equalsIgnoreCase(tipoString)){
				tipo = tp;
				break;
			}
		}
		return tipo;
	}

}
