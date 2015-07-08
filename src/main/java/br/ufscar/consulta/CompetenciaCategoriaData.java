package br.ufscar.consulta;

import java.util.Date;
import java.util.List;

public class CompetenciaCategoriaData {

	private int idCategoria;
	private String nome;
	private boolean estado;
	private Date ts;
	private List<CompetenciaData> competencias;
	private List<CompetenciaCategoriaData> subCategorias;
	private ResponsavelData aprovadoPor = null;

	public int getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
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


	public List<CompetenciaData> getCompetencias() {
		return competencias;
	}


	public void setCompetencias(List<CompetenciaData> competencias) {
		this.competencias = competencias;
	}


	public List<CompetenciaCategoriaData> getSubCategorias() {
		return subCategorias;
	}


	public void setSubCategorias(List<CompetenciaCategoriaData> subCategorias) {
		this.subCategorias = subCategorias;
	}


	public ResponsavelData getAprovadoPor() {
		return aprovadoPor;
	}


	public void setAprovadoPor(ResponsavelData aprovadoPor) {
		this.aprovadoPor = aprovadoPor;
	}


	public CompetenciaCategoriaData() {
		super();
	}
}

