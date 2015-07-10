package br.ufscar.dominio.interfaces;

import java.util.List;

import br.ufscar.dominio.Competencia;
import br.ufscar.dominio.Feedback;
import br.ufscar.dominio.Projeto;
import br.ufscar.dominio.ProjetoAtividade;
import br.ufscar.dominio.Responsavel;

public interface IProjetoRepository {

	public abstract boolean gravaProjeto(Projeto projeto);
	public abstract boolean gravaResponsaveisProjeto(Projeto projeto,List<Responsavel> responsaveis);
	public abstract boolean gravaResponsaveisProjeto(Projeto projeto,Responsavel responsavel);
	
	public abstract boolean gravaAtividadesProjeto(Projeto projeto,List<ProjetoAtividade> atividades);
	public abstract boolean gravaAtividadesProjeto(Projeto projeto,ProjetoAtividade atividade);
	public abstract List<ProjetoAtividade> gravaAtividade(List<ProjetoAtividade> projetoAtividade);
	public abstract boolean gravaCompetenciaAtividades(ProjetoAtividade atividade);
	public abstract boolean gravaResponsaveisAtividade(ProjetoAtividade atividade);
	public abstract boolean gravaResponsaveisAtividade(ProjetoAtividade atividade, Responsavel responsavel);
	public abstract int gravaAtividade(ProjetoAtividade atividade);
	public abstract boolean gravaCompetenciaAtividades(ProjetoAtividade atividade,Competencia competencia);

	public abstract boolean gravaFeedBack(Feedback feedback);

	public abstract boolean alterarProjeto(Projeto projeto);

	public abstract List<ProjetoAtividade> alterarAtividade(List<ProjetoAtividade> projetoAtividade);
	public abstract boolean alterarAtividade(ProjetoAtividade atividade);

	public abstract boolean alterarFeedBack(Feedback feedback);

	public abstract boolean excluirAtividadesProjeto(Projeto projeto);
	public abstract boolean excluirResponsaveisAtividade(ProjetoAtividade atividade);
	public abstract boolean excluirCompetenciaAtividades(ProjetoAtividade atividade);
	
	public abstract boolean excluirResponsaveisProjeto(Projeto projeto);

	public abstract List<Projeto> listarProjetos();
	public abstract List<Responsavel> listarResponsaveisProjeto(Projeto projeto);
	public abstract List<Projeto> listarProjetosPorResponsavel(int idPessoa);

	public abstract List<Feedback> listarFeedbacksProjeto(Projeto projeto);
	
	public abstract List<ProjetoAtividade> listarAtividadesProjeto(Projeto projeto);
	public abstract List<Competencia> listarCompetenciasProjetoAtividade(ProjetoAtividade atividade);
	public abstract List<Responsavel> listarResponsaveisProjetoAtividade(ProjetoAtividade atividade);
	public abstract List<ProjetoAtividade> listarProjetosAtividadesPorResponsavel(int idPessoa);

	public abstract Projeto recuperarProjetoPorId(int idProjeto);
	public abstract Projeto recuperarProjetoPorAtividade(ProjetoAtividade atividade);

	public abstract Feedback recuperarFeedbackPorId(int idFeedBack);
	
	public abstract ProjetoAtividade recuperarAtividadeProjetoPorId(int idAtividade);
	public abstract Feedback recuperaFeedbackAtividade(ProjetoAtividade atividade);

	public abstract List<Feedback> recuperarFeedbacksCriadosPorResponsavel(int idPessoa);
	public abstract List<Feedback> recuperarFeedbacksRecebidosPorResponsavel(int idPessoa);

	public abstract boolean alterarStatusProjeto(Projeto projeto);
	public abstract boolean alterarStatusProjetoAtividade(ProjetoAtividade atividade);
}