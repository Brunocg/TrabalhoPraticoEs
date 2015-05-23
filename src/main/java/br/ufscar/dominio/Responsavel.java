package br.ufscar.dominio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Responsavel extends br.ufscar.dominio.Pessoa {

	private List<Projeto> projeto;
	private List<ProjetoAtividade> projetoAtividades;
	private List<Usuario> usuariosAprovados;
	private List<Competencia> competenciasAprovadas;
	private List<Feedback> feedbackCriados;
	private List<Feedback> feedbacksRecebidos;
	private List<CompetenciaCategoria> competenciasCategoriaAprovadas;

	
	
	public Responsavel() {
		super();
	}

	public Responsavel(int idPessoa, String nome, String sitCivil, String sexo,
			Date dataNascimento, String cpf, String rg, List<Endereco> enderecos,
			String telefone, String celular, String email, String pagPessoal,
			String msgInst, Usuario usuario, boolean estado, Date ts,
			List<CompetenciaExperiencia> competenciasExperiencia) {
		super(idPessoa, nome, sitCivil, sexo, dataNascimento, cpf, rg, enderecos,
				telefone, celular, email, pagPessoal, msgInst, usuario, estado, ts,
				competenciasExperiencia);
	}

	public Responsavel(List<Projeto> projeto,
			List<ProjetoAtividade> projetoAtividades,
			List<Usuario> usuariosAprovados,
			List<Competencia> competenciasAprovadas,
			List<Feedback> feedbackCriados, List<Feedback> feedbacksRecebidos,
			List<CompetenciaCategoria> competenciasCategoriaAprovadas) {
		super();
		this.projeto = projeto;
		this.projetoAtividades = projetoAtividades;
		this.usuariosAprovados = usuariosAprovados;
		this.competenciasAprovadas = competenciasAprovadas;
		this.feedbackCriados = feedbackCriados;
		this.feedbacksRecebidos = feedbacksRecebidos;
		this.competenciasCategoriaAprovadas = competenciasCategoriaAprovadas;
	}

	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	public boolean naoTemFeedbacksRecebidos() {
		return feedbacksRecebidos.isEmpty();
	}

	public boolean contemEsteFeedbacksRecebidos(Feedback Feedback) {
		return feedbacksRecebidos.contains(Feedback);
	}

	public boolean contemEstesFeedbacksRecebidos(Collection<? extends Feedback> feedbacksRecebidos) {
		return this.feedbacksRecebidos.containsAll(feedbacksRecebidos);
	}

	public int feedbacksRecebidosSize() {
		return feedbacksRecebidos.size();
	}

	public boolean adicionaFeedbacksRecebidos(Feedback Feedback) {
		return feedbacksRecebidos.add(Feedback);
	}

	public boolean removerFeedbacksRecebidos(Feedback Feedback) {
		return feedbacksRecebidos.remove(Feedback);
	}

	public void limparFeedbacksRecebidos() {
		feedbacksRecebidos.clear();
	}

	public boolean naoTemFeedbackCriados() {
		return feedbackCriados.isEmpty();
	}

	public boolean contemEsteFeedbackCriados(Feedback Feedback) {
		return feedbackCriados.contains(Feedback);
	}

	public boolean contemEstesAllFeedbackCriados(List<? extends Feedback> feedbackCriados) {
		return this.feedbackCriados.containsAll(feedbackCriados);
	}

	public int quantosFeedbackCriados() {
		return feedbackCriados.size();
	}

	public boolean adicionaFeedbackCriados(Feedback Feedback) {
		return feedbackCriados.add(Feedback);
	}

	public boolean removerFeedbackCriados(Feedback Feedback) {
		return feedbackCriados.remove(Feedback);
	}

	public void limparFeedbackCriados() {
		feedbackCriados.clear();
	}

	public boolean naoTemCompetenciasAprovadas() {
		return competenciasAprovadas.isEmpty();
	}

	public boolean contemEstaCompetenciasAprovadas(Competencia competenciasAprovadas) {
		return this.competenciasAprovadas.contains(competenciasAprovadas);
	}

	public boolean contemEstasCompetenciasAprovadas(List<Competencia> competenciasAprovadas) {
		return this.competenciasAprovadas.containsAll(competenciasAprovadas);
	}

	public int quantasCompetenciasAprovadas() {
		return competenciasAprovadas.size();
	}

	public boolean adicionaCompetenciasAprovadas(Competencia competenciasAprovadas) {
		return this.competenciasAprovadas.add(competenciasAprovadas);
	}

	public boolean removerCompetenciasAprovadas(Competencia competenciasAprovadas) {
		return this.competenciasAprovadas.remove(competenciasAprovadas);
	}

	public void limparCompetenciasAprovadas() {
		this.competenciasAprovadas.clear();
	}

	public boolean naoTemUsuariosAprovados() {
		return usuariosAprovados.isEmpty();
	}

	public boolean contemEsteUsuariosAprovados(Usuario Usuario) {
		return usuariosAprovados.contains(Usuario);
	}

	public boolean contemEstesUsuariosAprovados(List<? extends Usuario> usuariosAprovados) {
		return this.usuariosAprovados.containsAll(usuariosAprovados);
	}

	public int quantosUsuariosAprovados() {
		return usuariosAprovados.size();
	}

	public boolean adicionaUsuariosAprovados(Usuario Usuario) {
		return usuariosAprovados.add(Usuario);
	}

	public boolean removerUsuariosAprovados(Usuario Usuario) {
		return usuariosAprovados.remove(Usuario);
	}

	public void limparUsuariosAprovados() {
		usuariosAprovados.clear();
	}

	public boolean naoTemProjetoAtividades() {
		return projetoAtividades.isEmpty();
	}

	public boolean contemEsteProjetoAtividades(ProjetoAtividade ProjetoAtividade) {
		return projetoAtividades.contains(ProjetoAtividade);
	}

	public boolean contemEstesProjetoAtividades(List<? extends ProjetoAtividade> projetoAtividades) {
		return this.projetoAtividades.containsAll(projetoAtividades);
	}

	public int quantosProjetoAtividades() {
		return projetoAtividades.size();
	}

	public boolean adicionaProjetoAtividades(ProjetoAtividade ProjetoAtividade) {
		return projetoAtividades.add(ProjetoAtividade);
	}

	public boolean removerProjetoAtividades(ProjetoAtividade ProjetoAtividade) {
		return projetoAtividades.remove(ProjetoAtividade);
	}

	public void limparProjetoAtividades() {
		projetoAtividades.clear();
	}

	public boolean naoTemProjeto() {
		return projeto.isEmpty();
	}

	public boolean contemEsteProjeto(Projeto Projeto) {
		return projeto.contains(Projeto);
	}

	public boolean contemEstesProjeto(List<? extends Projeto> projeto) {
		return this.projeto.containsAll(projeto);
	}

	public int quantosProjeto() {
		return projeto.size();
	}

	public boolean adicionaProjeto(Projeto Projeto) {
		return projeto.add(Projeto);
	}

	public boolean removerProjeto(Projeto Projeto) {
		return projeto.remove(Projeto);
	}

	public void limparProjeto() {
		projeto.clear();
	}

	public boolean naoTemCompetenciasCategoriaAprovadas() {
		return competenciasCategoriaAprovadas.isEmpty();
	}

	public boolean contemEstaCompetenciasCategoriaAprovadas(CompetenciaCategoria CompetenciaCategoria) {
		return competenciasCategoriaAprovadas.contains(CompetenciaCategoria);
	}

	public boolean contemEstasCompetenciasCategoriaAprovadas(List<? extends CompetenciaCategoria> competenciasCategoriaAprovadas) {
		return this.competenciasCategoriaAprovadas.containsAll(competenciasCategoriaAprovadas);
	}

	public int quantasCompetenciasCategoriaAprovadas() {
		return competenciasCategoriaAprovadas.size();
	}

	public boolean adicionaCompetenciasCategoriaAprovadas(
			CompetenciaCategoria CompetenciaCategoria) {
		return competenciasCategoriaAprovadas.add(CompetenciaCategoria);
	}

	public boolean removerCompetenciasCategoriaAprovadas(
			CompetenciaCategoria CompetenciaCategoria) {
		return competenciasCategoriaAprovadas.remove(CompetenciaCategoria);
	}

	public void limparCompetenciasCategoriaAprovadas() {
		competenciasCategoriaAprovadas.clear();
	}



	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------

	public List<Feedback> getFeedbacksRecebidos() {
		return feedbacksRecebidos;
	}


	public void setFeedbacksRecebidos(List<Feedback> feedbacksRecebidos) {
		this.feedbacksRecebidos = feedbacksRecebidos;
	}

	public List<Feedback> getFeedbackCriados() {
		return feedbackCriados;
	}

	public void setFeedbackCriados(List<Feedback> feedbackCriados) {
		this.feedbackCriados = feedbackCriados;
	}

	public List<Competencia> getCompetenciasAprovadas() {
		return competenciasAprovadas;
	}

	public void setCompetenciasAprovadas(List<Competencia> competenciasAprovadas) {
		this.competenciasAprovadas = competenciasAprovadas;
	}

	public List<Usuario> getUsuariosAprovados() {
		return usuariosAprovados;
	}

	public void setUsuariosAprovados(List<Usuario> usuariosAprovados) {
		this.usuariosAprovados = usuariosAprovados;
	}

	public List<ProjetoAtividade> getProjetoAtividades() {
		return projetoAtividades;
	}

	public void setProjetoAtividades(List<ProjetoAtividade> projetoAtividades) {
		this.projetoAtividades = projetoAtividades;
	}

	public void setProjeto(List<Projeto> projeto) {
		this.projeto = projeto;
	}

	public void setCompetenciasCategoriaAprovadas(
			List<CompetenciaCategoria> competenciasCategoriaAprovadas) {
		this.competenciasCategoriaAprovadas = competenciasCategoriaAprovadas;
	}

	public List<CompetenciaCategoria> getCompetenciasCategoriaAprovadas() {
		return competenciasCategoriaAprovadas;
	}

	public List<Projeto> getProjeto() {
		return projeto;
	}

}
