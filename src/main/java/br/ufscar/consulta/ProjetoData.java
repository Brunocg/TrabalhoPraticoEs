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


	public List<ProjetoAtividadeData> getProjetoAtividade() {
		return projetoAtividade;
	}


	public void setProjetoAtividade(List<ProjetoAtividadeData> projetoAtividade) {
		this.projetoAtividade = projetoAtividade;
	}


	public List<ResponsavelData> getResponsaveis() {
		return responsaveis;
	}


	public void setResponsaveis(List<ResponsavelData> responsaveis) {
		this.responsaveis = responsaveis;
	}


	public List<FeedbackData> getFeedbacksResponsaveisAtividades() {
		return feedbacksResponsaveisAtividades;
	}


	public void setFeedbacksResponsaveisAtividades(
			List<FeedbackData> feedbacksResponsaveisAtividades) {
		this.feedbacksResponsaveisAtividades = feedbacksResponsaveisAtividades;
	}
}

