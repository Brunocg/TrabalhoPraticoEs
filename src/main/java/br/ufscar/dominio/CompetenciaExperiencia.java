package br.ufscar.dominio;

import java.util.Date;

public class CompetenciaExperiencia {

	private int idExperiencia;
	private int nivel;
	private int tempoExperiencia;// meses
	private String observacoes;
	private boolean estado;
	private Date ts;
	private Competencia competencia = null;
	private Pessoa pessoa = null;

	public CompetenciaExperiencia() {
		super();
	}

	public CompetenciaExperiencia(int idExperiencia, int nivel,
			int tempoExperiencia, String observacoes, boolean estado,
			Date ts, Competencia competencia, Pessoa pessoa) {
		super();
		this.idExperiencia = idExperiencia;
		this.nivel = nivel;
		this.tempoExperiencia = tempoExperiencia;
		this.observacoes = observacoes;
		this.estado = estado;
		this.ts = ts;
		this.competencia = competencia;
		this.pessoa = pessoa;
	}

	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public int getIdExperiencia() {
		return idExperiencia;
	}

	public void setIdExperiencia(int idExperiencia) {
		this.idExperiencia = idExperiencia;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getTempoExperiencia() {
		return tempoExperiencia;
	}

	public void setTempoExperiencia(int tempoExperiencia) {
		this.tempoExperiencia = tempoExperiencia;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
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

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}

}

