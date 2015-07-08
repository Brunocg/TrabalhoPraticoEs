package br.ufscar.consulta;

import java.util.Date;

public class CompetenciaExperienciaData {

	private int idExperiencia;
	private int nivel;
	private int tempoExperiencia;// meses
	private String observacoes;
	private boolean estado;
	private Date ts;
	private CompetenciaData competencia = null;
	private PessoaData pessoa = null;

	public CompetenciaExperienciaData() {
		super();
	}

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

	public CompetenciaData getCompetencia() {
		return competencia;
	}

	public void setCompetencia(CompetenciaData competencia) {
		this.competencia = competencia;
	}

	public PessoaData getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaData pessoa) {
		this.pessoa = pessoa;
	}
}

