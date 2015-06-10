package br.ufscar.dominio;

import java.util.Date;
import java.util.List;

public class ProjetoAtividade {

	private int idAtividade;
	private String nome;
	private String descricao;
	private int tipo;
	private Date prazo;
	private int status;
	private boolean estado;
	private Date ts;
	private Projeto projeto = null;
	private List<Responsavel> responsaveis = null;
	private List<CompetenciaCategoria> competenciaCategoria = null;
	private Feedback feedbackAtividade = null;


	public ProjetoAtividade() {
		super();
	}

	public ProjetoAtividade(int idAtividade, String nome, String descricao,
			int tipo, Date prazo, int status, boolean estado, Date ts,
			Projeto projeto, List<Responsavel> responsaveis,
			List<CompetenciaCategoria> competenciaCategoria,
			Feedback feedbackAtividade) {
		super();
		this.idAtividade = idAtividade;
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
		this.prazo = prazo;
		this.status = status;
		this.estado = estado;
		this.ts = ts;
		this.projeto = projeto;
		this.responsaveis = responsaveis;
		this.competenciaCategoria = competenciaCategoria;
		this.feedbackAtividade = feedbackAtividade;
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public boolean finalizaAtividade(){
		return true;
	}
	
	public boolean naoTemCompetenciaCategoria() {
		return competenciaCategoria.isEmpty();
	}

	public boolean contemEstaCompetenciaCategoria(CompetenciaCategoria CompetenciaCategoria) {
		return competenciaCategoria.contains(CompetenciaCategoria);
	}

	public boolean contemEstasCompetenciaCategoria(List<? extends CompetenciaCategoria> competenciaCategoria) {
		return this.competenciaCategoria.containsAll(competenciaCategoria);
	}

	public int quantasCompetenciaCategoria() {
		return competenciaCategoria.size();
	}

	public boolean adicionaCompetenciaCategoria(CompetenciaCategoria CompetenciaCategoria) {
		return competenciaCategoria.add(CompetenciaCategoria);
	}

	public boolean removerCompetenciaCategoria(CompetenciaCategoria CompetenciaCategoria) {
		return competenciaCategoria.remove(CompetenciaCategoria);
	}

	public void limparCompetenciaCategoria() {
		competenciaCategoria.clear();
	}

	public boolean naoTemResponsaveis() {
		return responsaveis.isEmpty();
	}

	public boolean contemEsteResponsaveis(Responsavel Responsavel) {
		return responsaveis.contains(Responsavel);
	}

	public boolean contemEstesResponsaveis(List<? extends Responsavel> responsaveis) {
		return this.responsaveis.containsAll(responsaveis);
	}

	public int quantosResponsaveis() {
		return responsaveis.size();
	}

	public boolean adicionaResponsaveis(Responsavel Responsavel) {
		return responsaveis.add(Responsavel);
	}

	public boolean removerResponsaveis(Responsavel Responsavel) {
		return responsaveis.remove(Responsavel);
	}

	public void limparResponsaveis() {
		responsaveis.clear();
	}



	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public int getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(int idAtividade) {
		this.idAtividade = idAtividade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Date getPrazo() {
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Feedback getFeedbackAtividade() {
		return feedbackAtividade;
	}

	public void setFeedbackAtividade(Feedback feedbackAtividade) {
		this.feedbackAtividade = feedbackAtividade;
	}

	public List<CompetenciaCategoria> getCompetenciaCategoria() {
		return competenciaCategoria;
	}

	public void setCompetenciaCategoria(List<CompetenciaCategoria> competenciaCategoria) {
		this.competenciaCategoria = competenciaCategoria;
	}

	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}

	public Responsavel getResponsaveis(int i) {
		return (Responsavel) responsaveis.get(i);
	}	

	public void setResponsaveis(List<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}

}
