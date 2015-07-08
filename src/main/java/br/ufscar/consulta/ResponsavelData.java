package br.ufscar.consulta;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ResponsavelData extends PessoaData {

	private List<ProjetoData> projeto;
	private List<ProjetoAtividadeData> projetoAtividades;
	private List<UsuarioData> usuariosAprovados;
	private List<CompetenciaData> competenciasAprovadas;
	private List<FeedbackData> feedbackCriados;
	private List<FeedbackData> feedbacksRecebidos;
	private List<CompetenciaCategoriaData> competenciasCategoriaAprovadas;

	
	
	public ResponsavelData() {
		super();
	}



	public List<ProjetoData> getProjeto() {
		return projeto;
	}



	public void setProjeto(List<ProjetoData> projeto) {
		this.projeto = projeto;
	}



	public List<ProjetoAtividadeData> getProjetoAtividades() {
		return projetoAtividades;
	}



	public void setProjetoAtividades(List<ProjetoAtividadeData> projetoAtividades) {
		this.projetoAtividades = projetoAtividades;
	}



	public List<UsuarioData> getUsuariosAprovados() {
		return usuariosAprovados;
	}



	public void setUsuariosAprovados(List<UsuarioData> usuariosAprovados) {
		this.usuariosAprovados = usuariosAprovados;
	}



	public List<CompetenciaData> getCompetenciasAprovadas() {
		return competenciasAprovadas;
	}



	public void setCompetenciasAprovadas(List<CompetenciaData> competenciasAprovadas) {
		this.competenciasAprovadas = competenciasAprovadas;
	}



	public List<FeedbackData> getFeedbackCriados() {
		return feedbackCriados;
	}



	public void setFeedbackCriados(List<FeedbackData> feedbackCriados) {
		this.feedbackCriados = feedbackCriados;
	}



	public List<FeedbackData> getFeedbacksRecebidos() {
		return feedbacksRecebidos;
	}



	public void setFeedbacksRecebidos(List<FeedbackData> feedbacksRecebidos) {
		this.feedbacksRecebidos = feedbacksRecebidos;
	}



	public List<CompetenciaCategoriaData> getCompetenciasCategoriaAprovadas() {
		return competenciasCategoriaAprovadas;
	}



	public void setCompetenciasCategoriaAprovadas(
			List<CompetenciaCategoriaData> competenciasCategoriaAprovadas) {
		this.competenciasCategoriaAprovadas = competenciasCategoriaAprovadas;
	}
}
