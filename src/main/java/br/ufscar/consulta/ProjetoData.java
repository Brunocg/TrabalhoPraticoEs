package br.ufscar.consulta;

import java.util.Date;
import java.util.List;

public class ProjetoData {

	private int idProjeto;
	private String nome;
	private int tipo;
	private Date prazo;
	private String observacoes;
	private int status;
	private boolean estado;
	private Date ts;
	private List<ProjetoAtividadeData> projetoAtividade = null;
	private List<ResponsavelData> responsaveis;
	private List<FeedbackData> feedbacksResponsaveisAtividades;


	public ProjetoData() {
		super();
	}

	public ProjetoData(int idProjeto, String nome, int tipo, Date prazo,
			String observacoes, int status, boolean estado, Date ts,
			List<ProjetoAtividadeData> projetoAtividade,
			List<ResponsavelData> responsaveis,
			List<FeedbackData> feedbacksResponsaveisAtividades) {
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

	public boolean finalizaProjeto() {
		return true;
	}
	
	public boolean naoTemFeedbacksResponsaveisAtividades() {
		return feedbacksResponsaveisAtividades.isEmpty();
	}

	public boolean contemEsteFeedbackResponsavelAtividade(FeedbackData Feedback) {
		return feedbacksResponsaveisAtividades.contains(Feedback);
	}

	public boolean contemEstesFeedbacksResponsaveisAtividades(List<? extends FeedbackData> feedbacksResponsaveisAtividades) {
		return this.feedbacksResponsaveisAtividades.containsAll(feedbacksResponsaveisAtividades);
	}

	public int quantosFeedbacksResponsaveisAtividades() {
		return feedbacksResponsaveisAtividades.size();
	}

	public boolean adicionarFeedbacksResponsaveisAtividades(FeedbackData Feedback) {
		return feedbacksResponsaveisAtividades.add(Feedback);
	}

	public boolean removerFeedbacksResponsaveisAtividades(FeedbackData Feedback) {
		return feedbacksResponsaveisAtividades.remove(Feedback);
	}

	public void limparFeedbacksResponsaveisAtividades() {
		feedbacksResponsaveisAtividades.clear();
	}

	public boolean naoTemResponsaveis() {
		return responsaveis.isEmpty();
	}

	public boolean contemEsteResponsavel(ResponsavelData Responsavel) {
		return responsaveis.contains(Responsavel);
	}

	public boolean contemEstesResponsaveis(List<? extends ResponsavelData> responsavel) {
		return this.responsaveis.containsAll(responsavel);
	}

	public int quantosResponsaveis() {
		return responsaveis.size();
	}

	public boolean adicionarResponsavel(ResponsavelData Responsavel) {
		return responsaveis.add(Responsavel);
	}

	public boolean removerResponsavel(ResponsavelData Responsavel) {
		return responsaveis.remove(Responsavel);
	}

	public void limparResponsaveis() {
		responsaveis.clear();
	}

	public boolean naoTemProjetoAtividade() {
		return projetoAtividade.isEmpty();
	}

	public boolean contemEsteProjetoAtividade(ProjetoAtividadeData projetoAtividade) {
		return this.projetoAtividade.contains(projetoAtividade);
	}

	public boolean contemEstesProjetoAtividade(List<ProjetoAtividadeData> projetoAtividade) {
		return this.projetoAtividade.containsAll(projetoAtividade);
	}

	public int quantosProjetoAtividade() {
		return projetoAtividade.size();
	}

	public boolean adicionarProjetoAtividade(ProjetoAtividadeData projetoAtividade) {
		return this.projetoAtividade.add(projetoAtividade);
	}

	public boolean removerProjetoAtividade(ProjetoAtividadeData projetoAtividade) {
		return this.projetoAtividade.remove(projetoAtividade);
	}

	public void limparProjetoAtividade() {
		this.projetoAtividade.clear();
	}


	
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public List<FeedbackData> getFeedbacksResponsaveisAtividades() {
		return feedbacksResponsaveisAtividades;
	}

	public void setProjetoAtividade(List<ProjetoAtividadeData> projetoAtividade) {
		this.projetoAtividade = projetoAtividade;
	}

	public List<ResponsavelData> getResponsaveis() {
		return responsaveis;
	}

	public void setFeedbacksResponsaveisAtividades(List<FeedbackData> feedbacksResponsaveisAtividades) {
		this.feedbacksResponsaveisAtividades = feedbacksResponsaveisAtividades;
	}

	public void setResponsavel(List<ResponsavelData> responsavel) {
		this.responsaveis = responsavel;
	}

	public List<ProjetoAtividadeData> getProjetoAtividade() {
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

	public void setResponsaveis(List<ResponsavelData> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public FeedbackData getFeedbacksResponsaveisAtividades(int i) {
		return (FeedbackData) feedbacksResponsaveisAtividades.get(i);
	}

}

