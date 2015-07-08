package br.ufscar.consulta;

import java.util.Date;

public class CompetenciaData {

	private int idCompetencia;
	private String nome;
	private boolean estado;
	private Date ts;
	private ResponsavelData aprovadorPor = null;

	public CompetenciaData() {
		super();
	}

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

	public ResponsavelData getAprovadorPor() {
		return aprovadorPor;
	}

	public void setAprovadorPor(ResponsavelData aprovadorPor) {
		this.aprovadorPor = aprovadorPor;
	}
}
