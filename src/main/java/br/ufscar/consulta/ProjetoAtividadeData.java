package br.ufscar.consulta;

import java.util.Date;
import java.util.List;

public class ProjetoAtividadeData {

	private int idAtividade;
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

	public ProjetoAtividadeData(int idAtividade, String nome, String descricao,
			int tipo, Date prazo, int status, boolean estado, Date ts,
			ProjetoData projeto, List<ResponsavelData> responsaveis,
			List<CompetenciaData> competencia,
			FeedbackData feedbackAtividade) {
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
		this.competencia = competencia;
		this.feedbackAtividade = feedbackAtividade;
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public boolean finalizaAtividade(){
		return true;
	}
	
	public boolean naoTemCompetenciaCategoria() {
		return competencia.isEmpty();
	}

	public boolean contemEstaCompetencia(CompetenciaData Competencia) {
		return competencia.contains(Competencia);
	}

	public boolean contemEstasCompetencia(List<? extends CompetenciaCategoriaData> competencia) {
		return this.competencia.containsAll(competencia);
	}

	public int quantasCompetenciaCategoria() {
		return competencia.size();
	}

	public boolean adicionaCompetencia(CompetenciaData Competencia) {
		return competencia.add(Competencia);
	}

	public boolean removerCompetencia(CompetenciaData Competencia) {
		return competencia.remove(Competencia);
	}

	public void limparCompetencia() {
		competencia.clear();
	}

	public boolean naoTemResponsaveis() {
		return responsaveis.isEmpty();
	}

	public boolean contemEsteResponsaveis(ResponsavelData Responsavel) {
		return responsaveis.contains(Responsavel);
	}

	public boolean contemEstesResponsaveis(List<? extends ResponsavelData> responsaveis) {
		return this.responsaveis.containsAll(responsaveis);
	}

	public int quantosResponsaveis() {
		return responsaveis.size();
	}

	public boolean adicionaResponsaveis(ResponsavelData Responsavel) {
		return responsaveis.add(Responsavel);
	}

	public boolean removerResponsaveis(ResponsavelData Responsavel) {
		return responsaveis.remove(Responsavel);
	}

	public void limparResponsaveis() {
		responsaveis.clear();
	}



	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public ProjetoData getProjeto() {
		return projeto;
	}

	public void setProjeto(ProjetoData projeto) {
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

	public FeedbackData getFeedbackAtividade() {
		return feedbackAtividade;
	}

	public void setFeedbackAtividade(FeedbackData feedbackAtividade) {
		this.feedbackAtividade = feedbackAtividade;
	}

	public List<CompetenciaData> getCompetencia() {
		return competencia;
	}

	public void setCompetencia(List<CompetenciaData> competencia) {
		this.competencia = competencia;
	}

	public List<ResponsavelData> getResponsaveis() {
		return responsaveis;
	}

	public ResponsavelData getResponsaveis(int i) {
		return (ResponsavelData) responsaveis.get(i);
	}	

	public void setResponsaveis(List<ResponsavelData> responsaveis) {
		this.responsaveis = responsaveis;
	}

}
