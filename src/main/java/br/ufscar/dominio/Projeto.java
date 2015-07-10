package br.ufscar.dominio;

import java.util.Date;
import java.util.List;

public class Projeto {

	private int idProjeto;
	private String nome;
	private int tipo;
	private Date prazo;
	private String observacoes;
	private int status;
	private boolean estado;
	private Date ts;
	private List<ProjetoAtividade> projetoAtividade = null;
	private List<Responsavel> responsaveis;
	private List<Feedback> feedbacksResponsaveisAtividades;


	public Projeto() {
		super();
	}

	public Projeto(int idProjeto, String nome, int tipo, Date prazo,
			String observacoes, int status, boolean estado, Date ts,
			List<ProjetoAtividade> projetoAtividade,
			List<Responsavel> responsaveis,
			List<Feedback> feedbacksResponsaveisAtividades) {
		super();
		this.idProjeto = idProjeto;
		this.nome = nome;
		this.tipo = tipo;
		this.prazo = prazo;
		this.observacoes = observacoes;
		this.status = status;
		this.estado = estado;
		this.ts = ts;
		this.projetoAtividade = projetoAtividade;
		this.responsaveis = responsaveis;
		this.feedbacksResponsaveisAtividades = feedbacksResponsaveisAtividades;
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------

	public boolean naoTemFeedbacksResponsaveisAtividades() {
		return feedbacksResponsaveisAtividades.isEmpty();
	}

	public boolean contemEsteFeedbackResponsavelAtividade(Feedback Feedback) {
		return feedbacksResponsaveisAtividades.contains(Feedback);
	}

	public boolean contemEstesFeedbacksResponsaveisAtividades(List<? extends Feedback> feedbacksResponsaveisAtividades) {
		return this.feedbacksResponsaveisAtividades.containsAll(feedbacksResponsaveisAtividades);
	}

	public int quantosFeedbacksResponsaveisAtividades() {
		return feedbacksResponsaveisAtividades.size();
	}

	public boolean adicionarFeedbacksResponsaveisAtividades(Feedback Feedback) {
		return feedbacksResponsaveisAtividades.add(Feedback);
	}

	public boolean removerFeedbacksResponsaveisAtividades(Feedback Feedback) {
		return feedbacksResponsaveisAtividades.remove(Feedback);
	}

	public void limparFeedbacksResponsaveisAtividades() {
		feedbacksResponsaveisAtividades.clear();
	}

	public boolean naoTemResponsaveis() {
		return responsaveis.isEmpty();
	}

	public boolean contemEsteResponsavel(Responsavel Responsavel) {
		return responsaveis.contains(Responsavel);
	}

	public boolean contemEstesResponsaveis(List<? extends Responsavel> responsavel) {
		return this.responsaveis.containsAll(responsavel);
	}

	public int quantosResponsaveis() {
		return responsaveis.size();
	}

	public boolean adicionarResponsavel(Responsavel Responsavel) {
		return responsaveis.add(Responsavel);
	}

	public boolean removerResponsavel(Responsavel Responsavel) {
		return responsaveis.remove(Responsavel);
	}

	public void limparResponsaveis() {
		responsaveis.clear();
	}

	public boolean naoTemProjetoAtividade() {
		return projetoAtividade.isEmpty();
	}

	public boolean contemEsteProjetoAtividade(ProjetoAtividade projetoAtividade) {
		return this.projetoAtividade.contains(projetoAtividade);
	}

	public boolean contemEstesProjetoAtividade(List<ProjetoAtividade> projetoAtividade) {
		return this.projetoAtividade.containsAll(projetoAtividade);
	}

	public int quantosProjetoAtividade() {
		return projetoAtividade.size();
	}

	public boolean adicionarProjetoAtividade(ProjetoAtividade projetoAtividade) {
		return this.projetoAtividade.add(projetoAtividade);
	}

	public boolean removerProjetoAtividade(ProjetoAtividade projetoAtividade) {
		return this.projetoAtividade.remove(projetoAtividade);
	}

	public void limparProjetoAtividade() {
		this.projetoAtividade.clear();
	}


	
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public List<Feedback> getFeedbacksResponsaveisAtividades() {
		return feedbacksResponsaveisAtividades;
	}

	public void setProjetoAtividade(List<ProjetoAtividade> projetoAtividade) {
		this.projetoAtividade = projetoAtividade;
	}

	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}

	public void setFeedbacksResponsaveisAtividades(List<Feedback> feedbacksResponsaveisAtividades) {
		this.feedbacksResponsaveisAtividades = feedbacksResponsaveisAtividades;
	}

	public void setResponsavel(List<Responsavel> responsavel) {
		this.responsaveis = responsavel;
	}

	public List<ProjetoAtividade> getProjetoAtividade() {
		return projetoAtividade;
	}

	public int getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(int idProjeto) {
		this.idProjeto = idProjeto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
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

	public void setResponsaveis(List<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public Feedback getFeedbacksResponsaveisAtividades(int i) {
		return (Feedback) feedbacksResponsaveisAtividades.get(i);
	}

}

