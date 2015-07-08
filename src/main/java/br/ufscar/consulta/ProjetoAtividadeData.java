package br.ufscar.consulta;

import java.util.Date;
import java.util.List;

public class ProjetoAtividadeData {

	private int idAtividade;
	
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


	public ProjetoData getProjeto() {
		return projeto;
	}


	public void setProjeto(ProjetoData projeto) {
		this.projeto = projeto;
	}


	public List<ResponsavelData> getResponsaveis() {
		return responsaveis;
	}


	public void setResponsaveis(List<ResponsavelData> responsaveis) {
		this.responsaveis = responsaveis;
	}


	public List<CompetenciaData> getCompetencia() {
		return competencia;
	}


	public void setCompetencia(List<CompetenciaData> competencia) {
		this.competencia = competencia;
	}


	public FeedbackData getFeedbackAtividade() {
		return feedbackAtividade;
	}


	public void setFeedbackAtividade(FeedbackData feedbackAtividade) {
		this.feedbackAtividade = feedbackAtividade;
	}


	private String nome;
	private String descricao;
	private int tipo;
	private Date prazo;
	private int status;
	private boolean estado;
	private Date ts;
	private ProjetoData projeto = null;
	private List<ResponsavelData> responsaveis = null;
	private List<CompetenciaData> competencia = null;
	private FeedbackData feedbackAtividade = null;


	public ProjetoAtividadeData() {
		super();
	}
}
