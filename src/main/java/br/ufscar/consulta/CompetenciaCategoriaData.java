package br.ufscar.consulta;

import java.util.Collection;
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


	public CompetenciaCategoriaData() {
		super();
	}

	public CompetenciaCategoriaData(int idCategoria, String nome,
			boolean estado, Date ts, List<CompetenciaData> competencias, 
			List<CompetenciaCategoriaData> subCategorias, ResponsavelData aprovadoPor) {
		super();
		this.idCategoria = idCategoria;
		this.nome = nome;
		this.estado = estado;
		this.ts = ts;
		this.competencias = competencias;
		this.subCategorias = subCategorias;
		this.aprovadoPor = aprovadoPor;
	}

	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------

	public boolean aprovar(){
		return true;
	}
	
	public boolean naoTemCompetencias() {
		return competencias.isEmpty();
	}
	
	public boolean contemEssaCompetencias(CompetenciaData Competencia) {
		return competencias.contains(Competencia);
	}
	
	public boolean contemEssasCompetencias(Collection<? extends CompetenciaData> competencias) {
		return this.competencias.containsAll(competencias);
	}
	
	public int quantasCompetencias() {
		return competencias.size();
	}
	
	
	public boolean adicionarCompetencias(CompetenciaData Competencia) {
		return competencias.add(Competencia);
	}
	
	public boolean removerCompetencias(CompetenciaData Competencia) {
		return competencias.remove(Competencia);
	}
	
	public void limparCompetencias() {
		competencias.clear();
	}
	
	public boolean naoTemSubCategoria() {
		return subCategorias.isEmpty();
	}

	public boolean contemEssaSubCategoria(CompetenciaCategoriaData subCategoria) {
		return subCategorias.contains(subCategoria);
	}

	public boolean contemEssasSubCategoria(Collection<? extends CompetenciaCategoriaData> subCategoria) {
		return this.subCategorias.containsAll(competencias);
	}

	public int quantasSubCategoria() {
		return subCategorias.size();
	}


	public boolean adicionarSubCategoria(CompetenciaCategoriaData subCategoria) {
		return subCategorias.add(subCategoria);
	}

	public boolean removerSubCategoria(CompetenciaCategoriaData subCategoria) {
		return subCategorias.remove(subCategoria);
	}

	public void limparSubCategoria() {
		subCategorias.clear();
	}
	
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	

	public List<CompetenciaData> getCompetencias() {
		return competencias;
	}

	public void setCompetencias(List<CompetenciaData> competencias) {
		this.competencias = competencias;
	}

	public ResponsavelData getAprovadoPor() {
		return aprovadoPor;
	}

	public void setAprovadoPor(ResponsavelData aprovadoPor) {
		this.aprovadoPor = aprovadoPor;
	}

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

	public List<CompetenciaCategoriaData> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<CompetenciaCategoriaData> subCategorias) {
		this.subCategorias = subCategorias;
	}

}

