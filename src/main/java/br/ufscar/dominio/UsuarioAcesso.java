package br.ufscar.dominio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufscar.dominio.interfaces.IPessoaRepository;

public class UsuarioAcesso {

	private String descricao;
	private int[] niveisDeAcesso;// codigo de quem tem acesso
	
	@Autowired
	private IPessoaRepository iPessoaRepository;

	public UsuarioAcesso() {
		super();
	}

	public UsuarioAcesso(String descricao, int[] niveisDeAcesso) {
		super();
		this.descricao = descricao;
		this.niveisDeAcesso = niveisDeAcesso;
	}

	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public List<UsuarioAcesso> getAcessos(UsuarioTipo usuarioTipo) {
		return iPessoaRepository.recuperarUsuarioAcessosPorTipo(usuarioTipo);
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
