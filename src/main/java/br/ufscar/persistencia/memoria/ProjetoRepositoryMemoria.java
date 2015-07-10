package br.ufscar.persistencia.memoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.ufscar.dominio.Competencia;
import br.ufscar.dominio.Feedback;
import br.ufscar.dominio.Projeto;
import br.ufscar.dominio.ProjetoAtividade;
import br.ufscar.dominio.Responsavel;
import br.ufscar.dominio.interfaces.IProjetoRepository;

@Repository
public class ProjetoRepositoryMemoria implements IProjetoRepository {
	HashMap<Integer, Projeto> projetos = new HashMap<Integer, Projeto>();
	
	@Override
	public boolean gravaProjeto(Projeto projeto) {
		projetos.put(projeto.getIdProjeto(), projeto);
		return true;
	}

	@Override
	public boolean gravaResponsaveisProjeto(Projeto projeto,
			List<Responsavel> responsaveis) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gravaResponsaveisProjeto(Projeto projeto,
			Responsavel responsavel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gravaAtividadesProjeto(Projeto projeto,
			List<ProjetoAtividade> atividades) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gravaAtividadesProjeto(Projeto projeto,
			ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProjetoAtividade> gravaAtividade(
			List<ProjetoAtividade> projetoAtividade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gravaCompetenciaAtividades(ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gravaResponsaveisAtividade(ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gravaResponsaveisAtividade(ProjetoAtividade atividade,
			Responsavel responsavel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int gravaAtividade(ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean alterarProjeto(Projeto projeto) {
		projetos.put(projeto.getIdProjeto(), projeto);
		return true;
	}

	@Override
	public List<ProjetoAtividade> alterarAtividade(
			List<ProjetoAtividade> projetoAtividade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean excluirAtividadesProjeto(Projeto projeto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluirResponsaveisProjeto(Projeto projeto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alterarAtividade(ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluirResponsaveisAtividade(ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Projeto> listarProjetos() {
		return new ArrayList<Projeto>(projetos.values());
	}

	@Override
	public Projeto recuperarProjetoPorId(int idProjeto) {
		return projetos.get(idProjeto);
	}

	@Override
	public List<Feedback> listarFeedbacksProjeto(Projeto projeto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feedback recuperarFeedbackPorId(int idFeedBack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjetoAtividade> listarAtividadesProjeto(Projeto projeto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjetoAtividade recuperarAtividadeProjetoPorId(int idAtividade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feedback recuperaFeedbackAtividade(ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projeto recuperarProjetoPorAtividade(ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Responsavel> listarResponsaveisProjeto(Projeto projeto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Competencia> listarCompetenciasProjetoAtividade(
			ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Responsavel> listarResponsaveisProjetoAtividade(
			ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Projeto> listarProjetosPorResponsavel(int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjetoAtividade> listarProjetosAtividadesPorResponsavel(
			int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Feedback> recuperarFeedbacksCriadosPorResponsavel(int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Feedback> recuperarFeedbacksRecebidosPorResponsavel(int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gravaCompetenciaAtividades(ProjetoAtividade atividade,
			Competencia competencia) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluirCompetenciaAtividades(ProjetoAtividade atividade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gravaFeedBack(Feedback feedback) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
