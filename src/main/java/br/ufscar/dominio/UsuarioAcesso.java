package br.ufscar.dominio;

import java.util.List;

import br.ufscar.dominio.interfaces.IPessoaRepository;
import br.ufscar.persistencia.mySql.PessoaRepositoryMySQL;

public class UsuarioAcesso {

	private String descricao;
	private int[] niveisDeAcesso;// codigo de quem tem acesso
	
	//FIXME bruno tentei usar o autowire mas nao funcionou
	private IPessoaRepository iPessoaRepository = new PessoaRepositoryMySQL();

	public UsuarioAcesso() {
		super();
	}

	public UsuarioAcesso(String descricao, int[] niveisDeAcesso) {
		super();
		this.descricao = descricao;
		this.niveisDeAcesso = niveisDeAcesso;
	}

	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public List<UsuarioAcesso> getAcessos(int codigoTipoUsuario) {
		return iPessoaRepository.recuperarUsuarioAcessosPorTipo(codigoTipoUsuario);
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
