package br.ufscar.dominio;

import java.util.Date;

public class Competencia {

	private int idCompetencia;
	private String nome;
	private boolean estado;
	private Date ts;
	private CompetenciaCategoria competenciaCategoria = null;
	private Responsavel aprovadorPor = null;

	public Competencia() {
		super();
	}

	public Competencia(int idCompetencia, String nome, boolean estado,
			Date ts, CompetenciaCategoria competenciaCategoria,
			Responsavel aprovadorPor) {
		super();
		this.idCompetencia = idCompetencia;
		this.nome = nome;
		this.estado = estado;
		this.ts = ts;
		this.competenciaCategoria = competenciaCategoria;
		this.aprovadorPor = aprovadorPor;
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------

	public boolean aprovar(){
		return true;
	}
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public int getIdCompetencia() {
		return idCompetencia;
	}

	public void setIdCompetencia(int idCompetencia) {
		this.idCompetencia = idCompetencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Responsavel getAprovadorPor() {
		return aprovadorPor;
	}

	public void setAprovadorPor(Responsavel aprovadorPor) {
		this.aprovadorPor = aprovadorPor;
	}

	public CompetenciaCategoria getCompetenciaCategoria() {
		return competenciaCategoria;
	}

	public void setCompetenciaCategoria(CompetenciaCategoria competenciaCategoria) {
		this.competenciaCategoria = competenciaCategoria;
	}

}
