package br.ufscar.dominio;

import java.util.Date;

public class Feedback {

	private int idFeedback;
	private int avaliacao;
	private int tpFeedback;// finalizacao de projeto ou finalizacao de atividade
	private String observacoes;
	private boolean estado;
	private Date ts;
	private Responsavel feedbackDe = null;
	private Responsavel feedbackPara = null;
	private ProjetoAtividade projetoAtividade = null;

	public Feedback() {
		super();
	}

	public Feedback(int idFeedback, int avaliacao, int tpFeedback,
			String observacoes, boolean estado, Date ts,
			Responsavel feedbackDe, Responsavel feedbackPara,
			ProjetoAtividade projetoAtividade) {
		super();
		this.idFeedback = idFeedback;
		this.avaliacao = avaliacao;
		this.tpFeedback = tpFeedback;
		this.observacoes = observacoes;
		this.estado = estado;
		this.ts = ts;
		this.feedbackDe = feedbackDe;
		this.feedbackPara = feedbackPara;
		this.projetoAtividade = projetoAtividade;
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public Responsavel getFeedbackPara() {
		return feedbackPara;
	}

	public void setFeedbackPara(Responsavel feedbackPara) {
		this.feedbackPara = feedbackPara;
	}

	public Responsavel getFeedbackDe() {
		return feedbackDe;
	}

	public void setFeedbackDe(Responsavel feedbackDe) {
		this.feedbackDe = feedbackDe;
	}

	public ProjetoAtividade getProjetoAtividade() {
		return projetoAtividade;
	}

	public void setProjetoAtividade(ProjetoAtividade projetoAtividade) {
		this.projetoAtividade = projetoAtividade;
	}

	public int getIdFeedback() {
		return idFeedback;
	}

	public void setIdFeedback(int idFeedback) {
		this.idFeedback = idFeedback;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}

	public int getTpFeedback() {
		return tpFeedback;
	}

	public void setTpFeedback(int tpFeedback) {
		this.tpFeedback = tpFeedback;
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

}

