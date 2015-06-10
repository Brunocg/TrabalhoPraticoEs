package br.ufscar.dominio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CompetenciaCategoria {

	private int idCategoria;
	private String nome;
	private boolean estado;
	private Date ts;
	private List<Competencia> competencias;
	private List<CompetenciaCategoria> subCategorias;
	private Responsavel aprovadoPor = null;


	public CompetenciaCategoria() {
		super();
	}

	public CompetenciaCategoria(int idCategoria, String nome,
			boolean estado, Date ts, List<Competencia> competencias, 
			List<CompetenciaCategoria> subCategorias, Responsavel aprovadoPor) {
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
	
	public boolean contemEssaCompetencias(Competencia Competencia) {
		return competencias.contains(Competencia);
	}
	
	public boolean contemEssasCompetencias(Collection<? extends Competencia> competencias) {
		return this.competencias.containsAll(competencias);
	}
	
	public int quantasCompetencias() {
		return competencias.size();
	}
	
	
	public boolean adicionarCompetencias(Competencia Competencia) {
		return competencias.add(Competencia);
	}
	
	public boolean removerCompetencias(Competencia Competencia) {
		return competencias.remove(Competencia);
	}
	
	public void limparCompetencias() {
		competencias.clear();
	}
	
	public boolean naoTemSubCategoria() {
		return subCategorias.isEmpty();
	}

	public boolean contemEssaSubCategoria(CompetenciaCategoria subCategoria) {
		return subCategorias.contains(subCategoria);
	}

	public boolean contemEssasSubCategoria(Collection<? extends CompetenciaCategoria> subCategoria) {
		return this.subCategorias.containsAll(competencias);
	}

	public int quantasSubCategoria() {
		return subCategorias.size();
	}


	public boolean adicionarSubCategoria(CompetenciaCategoria subCategoria) {
		return subCategorias.add(subCategoria);
	}

	public boolean removerSubCategoria(CompetenciaCategoria subCategoria) {
		return subCategorias.remove(subCategoria);
	}

	public void limparSubCategoria() {
		subCategorias.clear();
	}
	
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	

	public List<Competencia> getCompetencias() {
		return competencias;
	}

	public void setCompetencias(List<Competencia> competencias) {
		this.competencias = competencias;
	}

	public Responsavel getAprovadoPor() {
		return aprovadoPor;
	}

	public void setAprovadoPor(Responsavel aprovadoPor) {
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

	public List<CompetenciaCategoria> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<CompetenciaCategoria> subCategorias) {
		this.subCategorias = subCategorias;
	}

}

