package br.ufscar.consulta;

public class UsuarioAcessoData {

	private String descricao;
	private int[] niveisDeAcesso;// codigo de quem tem acesso

	public UsuarioAcessoData() {
		super();
	}

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
