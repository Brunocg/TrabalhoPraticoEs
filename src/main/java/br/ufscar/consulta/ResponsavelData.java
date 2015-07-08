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

	public ResponsavelData(int idPessoa, String nome, String sitCivil, String sexo,
			Date dataNascimento, String cpf, String rg, List<EnderecoData> enderecos,
			String telefone, String celular, String email, String pagPessoal,
			String msgInst, UsuarioData usuario, boolean estado, Date ts,
			List<CompetenciaExperienciaData> competenciasExperiencia) {
		super(idPessoa, nome, sitCivil, sexo, dataNascimento, cpf, rg, enderecos,
				telefone, celular, email, pagPessoal, msgInst, usuario, estado, ts,
				competenciasExperiencia);
	}

	public ResponsavelData(List<ProjetoData> projeto,
			List<ProjetoAtividadeData> projetoAtividades,
			List<UsuarioData> usuariosAprovados,
			List<CompetenciaData> competenciasAprovadas,
			List<FeedbackData> feedbackCriados, List<FeedbackData> feedbacksRecebidos,
			List<CompetenciaCategoriaData> competenciasCategoriaAprovadas) {
		super();
		this.projeto = projeto;
		this.projetoAtividades = projetoAtividades;
		this.usuariosAprovados = usuariosAprovados;
		this.competenciasAprovadas = competenciasAprovadas;
		this.feedbackCriados = feedbackCriados;
		this.feedbacksRecebidos = feedbacksRecebidos;
		this.competenciasCategoriaAprovadas = competenciasCategoriaAprovadas;
	}
	
	public ResponsavelData(PessoaData pessoa, List<ProjetoData> projeto,
			List<ProjetoAtividadeData> projetoAtividades,
			List<UsuarioData> usuariosAprovados,
			List<CompetenciaData> competenciasAprovadas,
			List<FeedbackData> feedbackCriados, List<FeedbackData> feedbacksRecebidos,
			List<CompetenciaCategoriaData> competenciasCategoriaAprovadas) {
		super(pessoa.getIdPessoa(), pessoa.getNome(), pessoa.getSitCivil(), pessoa.getSexo(), pessoa.getDataNascimento(), pessoa.getCpf(), pessoa.getRg(), pessoa.getEndereco(),
				pessoa.getTelefone(), pessoa.getCelular(), pessoa.getEmail(), pessoa.getPagPessoal(), pessoa.getMsgInst(), pessoa.getUsuario(), pessoa.isEstado(), pessoa.getTs(),
				pessoa.getCompetenciasExperiencia());
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

	public boolean contemEsteFeedbacksRecebidos(FeedbackData Feedback) {
		return feedbacksRecebidos.contains(Feedback);
	}

	public boolean contemEstesFeedbacksRecebidos(Collection<? extends FeedbackData> feedbacksRecebidos) {
		return this.feedbacksRecebidos.containsAll(feedbacksRecebidos);
	}

	public int feedbacksRecebidosSize() {
		return feedbacksRecebidos.size();
	}

	public boolean adicionaFeedbacksRecebidos(FeedbackData Feedback) {
		return feedbacksRecebidos.add(Feedback);
	}

	public boolean removerFeedbacksRecebidos(FeedbackData Feedback) {
		return feedbacksRecebidos.remove(Feedback);
	}

	public void limparFeedbacksRecebidos() {
		feedbacksRecebidos.clear();
	}

	public boolean naoTemFeedbackCriados() {
		return feedbackCriados.isEmpty();
	}

	public boolean contemEsteFeedbackCriados(FeedbackData Feedback) {
		return feedbackCriados.contains(Feedback);
	}

	public boolean contemEstesAllFeedbackCriados(List<? extends FeedbackData> feedbackCriados) {
		return this.feedbackCriados.containsAll(feedbackCriados);
	}

	public int quantosFeedbackCriados() {
		return feedbackCriados.size();
	}

	public boolean adicionaFeedbackCriados(FeedbackData Feedback) {
		return feedbackCriados.add(Feedback);
	}

	public boolean removerFeedbackCriados(FeedbackData Feedback) {
		return feedbackCriados.remove(Feedback);
	}

	public void limparFeedbackCriados() {
		feedbackCriados.clear();
	}

	public boolean naoTemCompetenciasAprovadas() {
		return competenciasAprovadas.isEmpty();
	}

	public boolean contemEstaCompetenciasAprovadas(CompetenciaData competenciasAprovadas) {
		return this.competenciasAprovadas.contains(competenciasAprovadas);
	}

	public boolean contemEstasCompetenciasAprovadas(List<CompetenciaData> competenciasAprovadas) {
		return this.competenciasAprovadas.containsAll(competenciasAprovadas);
	}

	public int quantasCompetenciasAprovadas() {
		return competenciasAprovadas.size();
	}

	public boolean adicionaCompetenciasAprovadas(CompetenciaData competenciasAprovadas) {
		return this.competenciasAprovadas.add(competenciasAprovadas);
	}

	public boolean removerCompetenciasAprovadas(CompetenciaData competenciasAprovadas) {
		return this.competenciasAprovadas.remove(competenciasAprovadas);
	}

	public void limparCompetenciasAprovadas() {
		this.competenciasAprovadas.clear();
	}

	public boolean naoTemUsuariosAprovados() {
		return usuariosAprovados.isEmpty();
	}

	public boolean contemEsteUsuariosAprovados(UsuarioData Usuario) {
		return usuariosAprovados.contains(Usuario);
	}

	public boolean contemEstesUsuariosAprovados(List<? extends UsuarioData> usuariosAprovados) {
		return this.usuariosAprovados.containsAll(usuariosAprovados);
	}

	public int quantosUsuariosAprovados() {
		return usuariosAprovados.size();
	}

	public boolean adicionaUsuariosAprovados(UsuarioData Usuario) {
		return usuariosAprovados.add(Usuario);
	}

	public boolean removerUsuariosAprovados(UsuarioData Usuario) {
		return usuariosAprovados.remove(Usuario);
	}

	public void limparUsuariosAprovados() {
		usuariosAprovados.clear();
	}

	public boolean naoTemProjetoAtividades() {
		return projetoAtividades.isEmpty();
	}

	public boolean contemEsteProjetoAtividades(ProjetoAtividadeData ProjetoAtividade) {
		return projetoAtividades.contains(ProjetoAtividade);
	}

	public boolean contemEstesProjetoAtividades(List<? extends ProjetoAtividadeData> projetoAtividades) {
		return this.projetoAtividades.containsAll(projetoAtividades);
	}

	public int quantosProjetoAtividades() {
		return projetoAtividades.size();
	}

	public boolean adicionaProjetoAtividades(ProjetoAtividadeData ProjetoAtividade) {
		return projetoAtividades.add(ProjetoAtividade);
	}

	public boolean removerProjetoAtividades(ProjetoAtividadeData ProjetoAtividade) {
		return projetoAtividades.remove(ProjetoAtividade);
	}

	public void limparProjetoAtividades() {
		projetoAtividades.clear();
	}

	public boolean naoTemProjeto() {
		return projeto.isEmpty();
	}

	public boolean contemEsteProjeto(ProjetoData Projeto) {
		return projeto.contains(Projeto);
	}

	public boolean contemEstesProjeto(List<? extends ProjetoData> projeto) {
		return this.projeto.containsAll(projeto);
	}

	public int quantosProjeto() {
		return projeto.size();
	}

	public boolean adicionaProjeto(ProjetoData Projeto) {
		return projeto.add(Projeto);
	}

	public boolean removerProjeto(ProjetoData Projeto) {
		return projeto.remove(Projeto);
	}

	public void limparProjeto() {
		projeto.clear();
	}

	public boolean naoTemCompetenciasCategoriaAprovadas() {
		return competenciasCategoriaAprovadas.isEmpty();
	}

	public boolean contemEstaCompetenciasCategoriaAprovadas(CompetenciaCategoriaData CompetenciaCategoria) {
		return competenciasCategoriaAprovadas.contains(CompetenciaCategoria);
	}

	public boolean contemEstasCompetenciasCategoriaAprovadas(List<? extends CompetenciaCategoriaData> competenciasCategoriaAprovadas) {
		return this.competenciasCategoriaAprovadas.containsAll(competenciasCategoriaAprovadas);
	}

	public int quantasCompetenciasCategoriaAprovadas() {
		return competenciasCategoriaAprovadas.size();
	}

	public boolean adicionaCompetenciasCategoriaAprovadas(
			CompetenciaCategoriaData CompetenciaCategoria) {
		return competenciasCategoriaAprovadas.add(CompetenciaCategoria);
	}

	public boolean removerCompetenciasCategoriaAprovadas(
			CompetenciaCategoriaData CompetenciaCategoria) {
		return competenciasCategoriaAprovadas.remove(CompetenciaCategoria);
	}

	public void limparCompetenciasCategoriaAprovadas() {
		competenciasCategoriaAprovadas.clear();
	}



	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------

	public List<FeedbackData> getFeedbacksRecebidos() {
		return feedbacksRecebidos;
	}


	public void setFeedbacksRecebidos(List<FeedbackData> feedbacksRecebidos) {
		this.feedbacksRecebidos = feedbacksRecebidos;
	}

	public List<FeedbackData> getFeedbackCriados() {
		return feedbackCriados;
	}

	public void setFeedbackCriados(List<FeedbackData> feedbackCriados) {
		this.feedbackCriados = feedbackCriados;
	}

	public List<CompetenciaData> getCompetenciasAprovadas() {
		return competenciasAprovadas;
	}

	public void setCompetenciasAprovadas(List<CompetenciaData> competenciasAprovadas) {
		this.competenciasAprovadas = competenciasAprovadas;
	}

	public List<UsuarioData> getUsuariosAprovados() {
		return usuariosAprovados;
	}

	public void setUsuariosAprovados(List<UsuarioData> usuariosAprovados) {
		this.usuariosAprovados = usuariosAprovados;
	}

	public List<ProjetoAtividadeData> getProjetoAtividades() {
		return projetoAtividades;
	}

	public void setProjetoAtividades(List<ProjetoAtividadeData> projetoAtividades) {
		this.projetoAtividades = projetoAtividades;
	}

	public void setProjeto(List<ProjetoData> projeto) {
		this.projeto = projeto;
	}

	public void setCompetenciasCategoriaAprovadas(
			List<CompetenciaCategoriaData> competenciasCategoriaAprovadas) {
		this.competenciasCategoriaAprovadas = competenciasCategoriaAprovadas;
	}

	public List<CompetenciaCategoriaData> getCompetenciasCategoriaAprovadas() {
		return competenciasCategoriaAprovadas;
	}

	public List<ProjetoData> getProjeto() {
		return projeto;
	}

}
