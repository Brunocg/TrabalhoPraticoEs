package br.ufscar.persistencia.mySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufscar.dao.ConnectionManager;
import br.ufscar.dominio.Competencia;
import br.ufscar.dominio.Feedback;
import br.ufscar.dominio.Projeto;
import br.ufscar.dominio.ProjetoAtividade;
import br.ufscar.dominio.Responsavel;
import br.ufscar.dominio.interfaces.ICompetenciaRepository;
import br.ufscar.dominio.interfaces.IPessoaRepository;
import br.ufscar.dominio.interfaces.IProjetoRepository;

public class ProjetoRepositoryMySQL implements IProjetoRepository {

	private IPessoaRepository _pessoaRepository = new PessoaRepositoryMySQL();
	private ICompetenciaRepository _competenciaRepository = new CompetenciaRepositoryMySQL();

	private static final String GRAVA_PROJETO = "INSERT INTO Projeto (nome, tipo, prazo, observacoes, status, estado, ts) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_ATIVIDADE = "INSERT INTO ProjetoAtividade (nome, descricao, tipo, prazo, status, estado, ts) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_ATIVIDADE_RESPONSAVEL = "INSERT INTO ProjetoAtividadeResponsaveis (idProjetoAtividade, idPessoa) VALUES (?,?)";
	private static final String GRAVAR_COMPETENCIA_ATIVIDADE = "INSERT INTO ProjetoAtividadeCompetencias (idProjetoAtividade, idCompetencia) VALUES (?,?)";
	private static final String GRAVAR_ATIVIDADES_DO_PROJETO = "INSERT INTO ProjetoAtividades (idProjeto, idAtividade) VALUES (?,?)";
	private static final String GRAVAR_PROJETO_RESPONSAVEL = "INSERT INTO ProjetoResponsaveis (idProjeto, idPessoa) VALUES (?,?)";
	private static final String GRAVAR_FEEDBACK = "INSERT INTO Feedback (feedbackDe, feedbackPara, idProjetoAtividade, avaliacao, tpFeedback, observacoes, estado, ts) VALUES (?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";

	private static final String EXCLUIR_ATIVIDADES_DO_PROJETO = "DELETE FROM ProjetoAtividades WHERE idProjeto = ?";
	private static final String EXCLUIR_PROJETO_RESPONSAVEL = "DELETE FROM ProjetoResponsaveis WHERE idProjeto = ?";
	private static final String EXCLUIR_ATIVIDADE_RESPONSAVEL = "DELETE FROM ProjetoAtividadeResponsaveis WHERE idProjetoAtividade = ?";
	private static final String EXCLUIR_COMPETENCIA_ATIVIDADE = "DELETE FROM ProjetoAtividadeCompetencias WHERE idProjetoAtividade = ?";

	private static final String EDITAR_PROJETO = "UPDATE Projeto SET nome = ?, tipo = ?, prazo = ?, observacoes = ?, status = ?, estado = ? WHERE idProjeto = ?";
	private static final String EDITAR_ATIVIDADE = "UPDATE ProjetoAtividade SET nome = ?, descricao = ?, tipo = ?, prazo = ?, status = ?, estado = ? WHERE idProjetoAtividade = ?";
	private static final String EDITAR_FEEDBACK = "UPDATE Feedback SET feedbackDe = ?, feedbackPara = ?, idProjetoAtividade = ?, avaliacao = ?, tpFeedback = ?, observacoes = ?, estado = ? WHERE idFeedback = ?";

	private static final String BUSCAR_COMPETENCIAS_ATIVIDADE_PARA_LISTAR = "SELECT idCompetencia FROM ProjetoAtividadeCompetencias P WHERE idProjetoAtividade = ?";
	private static final String BUSCAR_RESPONSAVEIS_PROJETO_PARA_LISTAR = "SELECT idPessoa FROM ProjetoResponsaveis P WHERE idProjeto = ?";
	private static final String BUSCAR_RESPONSAVEIS_ATIVIDADE_PARA_LISTAR = "SELECT idPessoa FROM ProjetoAtividadeResponsaveis P WHERE idProjetoAtividade = ?";
	private static final String BUSCAR_PROJETOS_PARA_LISTAR = "SELECT idProjeto FROM Projeto P WHERE estado = TRUE";
	private static final String BUSCAR_ATIVIDADES_PROJETO_PARA_LISTAR = "SELECT idAtividade FROM ProjetoAtividades P WHERE idProjeto = ?";
	private static final String BUSCAR_ATIVIDADE_PROJETO_POR_ID = "SELECT nome, descricao, tipo, prazo, status, estado, ts FROM ProjetoAtividade P WHERE idProjetoAtividade = ?";
	private static final String BUSCAR_PROJETO_POR_ATIVIDADE = "SELECT PA.idProjeto, P.nome, P.nome, P.tipo, P.prazo, P.observacoes, P.status, P.estado, P.ts FROM ProjetoAtividades PA INNER JOIN Projeto P ON P.idProjeto = PA.idProjeto WHERE idAtividade = ?";
	private static final String BUSCAR_FEEDBACK_ATIVIDADE_POR_ATIVIDADE = "SELECT idFeedback, feedbackDe, feedbackPara, avaliacao, tpFeedback, observacoes, estado, ts FROM Feedback F WHERE idProjetoAtividade = ?";
	private static final String BUSCAR_PROJETO_POR_ID = "SELECT nome, tipo, prazo, observacoes, status, estado, ts FROM Projeto P WHERE idProjeto = ?";
	private static final String BUSCAR_FEEDBACKS_PROJETO_PARA_LISTAR = "SELECT idFeedback FROM ProjetoFeedbacks P WHERE idProjeto = ?";
	private static final String BUSCAR_FEEDBACK_POR_ID = "SELECT idProjetoAtividade, feedbackDe, feedbackPara, avaliacao, tpFeedback, observacoes, estado, ts FROM Feedback F WHERE idFeedback = ?";
	private static final String BUSCAR_ATIVIDADES_POR_RESPONSAVEL_PARA_LISTAR = "SELECT idProjetoAtividade FROM ProjetoAtividadeResponsaveis P WHERE idPessoa = ?";
	private static final String BUSCAR_PROJETOS_POR_RESPNSAVEL_PARA_LISTAR = "SELECT idProjeto FROM ProjetoResponsaveis P WHERE idPessoa = ?";
	private static final String BUSCAR_FEEDBACK_CRIADO_POR_RESPONSAVEL_PARA_LISTAR = "SELECT idFeedback FROM Feedback F WHERE feedbackDe = ?";
	private static final String BUSCAR_FEEDBACK_RECEBIDO_POR_RESPONSAVEL_PARA_LISTAR = "SELECT idFeedback FROM Feedback F WHERE feedbackPara = ?";

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaProjeto(br.ufscar.dominio.Projeto)
	 */
	@Override
	public boolean gravaProjeto(Projeto projeto){
		Connection mySQLConnection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int idProjeto = 0;
		boolean gravado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVA_PROJETO, Statement.RETURN_GENERATED_KEYS);
			ps.clearParameters();

			ps.setString(1,projeto.getNome());
			ps.setInt(2,projeto.getTipo());
			ps.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(projeto.getPrazo()));
			ps.setString(4,projeto.getObservacoes());
			ps.setInt(5,projeto.getStatus());
			ps.setBoolean(6,true);

			ps.executeUpdate();

			//parte que pega o que foi inclu�do no bd... no caso o campo id
			rs = ps.getGeneratedKeys(); 
			if(rs.next()){  
				idProjeto = rs.getInt(1);  
			}

			projeto.setIdProjeto(idProjeto);

			List<ProjetoAtividade> atividades = gravaAtividade(projeto.getProjetoAtividade());
			if(atividades == null){
				gravado = false;
			}else{

				if(gravaAtividadesProjeto(projeto, atividades)){

					gravado = gravaResponsaveisProjeto(projeto, projeto.getResponsaveis());

				}else{
					gravado = false;
				}
			}

			if(gravado){
				//Chama commit no final do processo
				mySQLConnection.commit();
				//Habilita auto comit novamente
				mySQLConnection.setAutoCommit(true);
			}else{
				ConnectionManager.rollBack();
			}
		}catch(SQLException e){
			e.printStackTrace();
			gravado = false;
			ConnectionManager.rollBack();		
		}finally{
			ConnectionManager.closeAll(ps,rs);
		}

		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaResponsaveisProjeto(br.ufscar.dominio.Projeto, java.util.List)
	 */
	@Override
	public boolean gravaResponsaveisProjeto(Projeto projeto,
			List<Responsavel> responsaveis) {
		boolean gravado = false;

		for (Responsavel responsavel : responsaveis) {
			gravado = gravaResponsaveisProjeto(projeto,responsavel);

			if(!gravado){
				break;
			}
		}
		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaResponsaveisProjeto(br.ufscar.dominio.Projeto, br.ufscar.dominio.Responsavel)
	 */
	@Override
	public boolean gravaResponsaveisProjeto(Projeto projeto,
			Responsavel responsavel) {
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_PROJETO_RESPONSAVEL);
			ps.clearParameters();

			ps.setInt(1,projeto.getIdProjeto());
			ps.setInt(2,responsavel.getIdPessoa());

			ps.executeUpdate();

			gravado = true;

		}catch(SQLException e){
			e.printStackTrace();
			gravado = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaAtividadesProjeto(br.ufscar.dominio.Projeto, java.util.List)
	 */
	@Override
	public boolean gravaAtividadesProjeto(Projeto projeto,
			List<ProjetoAtividade> atividades) {
		boolean gravado = false;

		for (ProjetoAtividade atividade : atividades) {

			gravado = gravaAtividadesProjeto(projeto,atividade);

			if(!gravado){
				break;
			}
		}
		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaAtividadesProjeto(br.ufscar.dominio.Projeto, br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public boolean gravaAtividadesProjeto(Projeto projeto,
			ProjetoAtividade atividade) {
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_ATIVIDADES_DO_PROJETO);
			ps.clearParameters();

			ps.setInt(1,projeto.getIdProjeto());
			ps.setInt(2,atividade.getIdAtividade());

			ps.executeUpdate();

			gravado = true;

		}catch(SQLException e){
			e.printStackTrace();
			gravado = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaAtividade(java.util.List)
	 */
	@Override
	public List<ProjetoAtividade> gravaAtividade(
			List<ProjetoAtividade> projetoAtividade) {
		List<ProjetoAtividade> atividadesComId = new ArrayList<ProjetoAtividade>();

		for (ProjetoAtividade atividade : projetoAtividade) {
			if(atividade.getIdAtividade() > 0){//ja existe
				atividadesComId.add(atividade);
			}else{

				int idAtividade = gravaAtividade(atividade);

				if(idAtividade != 0){

					atividade.setIdAtividade(idAtividade);

					if(gravaResponsaveisAtividade(atividade)){

						if(gravaCompetenciaAtividades(atividade)){

							atividadesComId.add(atividade);

						}else{
							atividadesComId = null;
							break;
						}

					}else{
						atividadesComId = null;
						break;
					}

				}else{
					atividadesComId = null;
					break;
				}
			}
		}


		return atividadesComId;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaCompetenciaAtividades(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public boolean gravaCompetenciaAtividades(ProjetoAtividade atividade) {
		boolean gravado = false;

		for (Competencia competencia : atividade.getCompetencia()) {

			gravado = gravaCompetenciaAtividades(atividade,competencia);

			if(!gravado){
				break;
			}
		}
		return gravado;
	}

	@Override
	public boolean gravaCompetenciaAtividades(ProjetoAtividade atividade,
			Competencia competencia) {
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_COMPETENCIA_ATIVIDADE);
			ps.clearParameters();

			ps.setInt(1,atividade.getIdAtividade());
			ps.setInt(2,competencia.getIdCompetencia());

			ps.executeUpdate();

			gravado = true;

		}catch(SQLException e){
			e.printStackTrace();
			gravado = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaResponsaveisAtividade(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public boolean gravaResponsaveisAtividade(ProjetoAtividade atividade) {
		boolean gravado = false;

		for (Responsavel responsavel : atividade.getResponsaveis()) {
			gravado = gravaResponsaveisAtividade(atividade,responsavel);

			if(!gravado){
				break;
			}
		}
		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaResponsaveisAtividade(br.ufscar.dominio.ProjetoAtividade, br.ufscar.dominio.Responsavel)
	 */
	@Override
	public boolean gravaResponsaveisAtividade(ProjetoAtividade atividade,
			Responsavel responsavel) {
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_ATIVIDADE_RESPONSAVEL);
			ps.clearParameters();

			ps.setInt(1,atividade.getIdAtividade());
			ps.setInt(2,responsavel.getIdPessoa());

			ps.executeUpdate();

			gravado = true;

		}catch(SQLException e){
			e.printStackTrace();
			gravado = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#gravaAtividade(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public int gravaAtividade(ProjetoAtividade atividade) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int idAtividade = 0;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_ATIVIDADE, Statement.RETURN_GENERATED_KEYS);
			ps.clearParameters();

			ps.setString(1,atividade.getNome());
			ps.setString(2,atividade.getDescricao());
			ps.setInt(3,atividade.getTipo());
			ps.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(atividade.getPrazo()));
			ps.setInt(5,atividade.getStatus());
			ps.setBoolean(6,true);

			ps.executeUpdate();

			//parte que pega o que foi inclu�do no bd... no caso o campo id
			rs = ps.getGeneratedKeys(); 
			if(rs.next()){  
				idAtividade = rs.getInt(1);  
			}

		}catch(SQLException e){
			e.printStackTrace();
			idAtividade = 0;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps,rs);
		}

		return idAtividade;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#alterarProjeto(br.ufscar.dominio.Projeto)
	 */
	@Override
	public boolean alterarProjeto(Projeto projeto){
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean gravado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EDITAR_PROJETO);
			ps.clearParameters();

			ps.setString(1,projeto.getNome());
			ps.setInt(2,projeto.getTipo());
			ps.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(projeto.getPrazo()));
			ps.setString(4,projeto.getObservacoes());
			ps.setInt(5,projeto.getStatus());
			ps.setBoolean(6,true);
			ps.setInt(7, projeto.getIdProjeto());

			ps.executeUpdate();

			List<ProjetoAtividade> atividades = alterarAtividade(projeto.getProjetoAtividade());
			if(atividades == null){
				gravado = false;
			}else{

				if(excluirAtividadesProjeto(projeto) && gravaAtividadesProjeto(projeto, atividades)){

					if(excluirResponsaveisProjeto(projeto) && gravaResponsaveisProjeto(projeto, projeto.getResponsaveis())){
						gravado = true;
					}

				}else{
					gravado = false;
				}
			}

			if(gravado){
				//Chama commit no final do processo
				mySQLConnection.commit();
				//Habilita auto comit novamente
				mySQLConnection.setAutoCommit(true);
			}else{
				ConnectionManager.rollBack();
			}
		}catch(SQLException e){
			e.printStackTrace();
			gravado = false;
			ConnectionManager.rollBack();		
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#alterarAtividade(java.util.List)
	 */
	@Override
	public List<ProjetoAtividade> alterarAtividade(
			List<ProjetoAtividade> projetoAtividade) {
		List<ProjetoAtividade> atividadesAlteradas = new ArrayList<ProjetoAtividade>();

		for (ProjetoAtividade atividade : projetoAtividade) {
			if(atividade.getIdAtividade() > 0){//ja existe
				if(!alterarAtividade(atividade)){
					atividadesAlteradas = null;
					break;
				}

				if(excluirResponsaveisAtividade(atividade) && gravaResponsaveisAtividade(atividade)){

					if(excluirCompetenciaAtividades(atividade) && gravaCompetenciaAtividades(atividade)){

						atividadesAlteradas.add(atividade);

					}else{
						atividadesAlteradas = null;
						break;
					}

				}else{
					atividadesAlteradas = null;
					break;
				}
			}else{

				int idAtividade = gravaAtividade(atividade);

				if(idAtividade != 0){

					atividade.setIdAtividade(idAtividade);

					if(gravaResponsaveisAtividade(atividade)){

						if(gravaCompetenciaAtividades(atividade)){

							atividadesAlteradas.add(atividade);

						}else{
							atividadesAlteradas = null;
							break;
						}

					}else{
						atividadesAlteradas = null;
						break;
					}

				}else{
					atividadesAlteradas = null;
					break;
				}
			}
		}

		return atividadesAlteradas;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#excluirAtividadesProjeto(br.ufscar.dominio.Projeto)
	 */
	@Override
	public boolean excluirAtividadesProjeto(Projeto projeto) {
		boolean excluido = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EXCLUIR_ATIVIDADES_DO_PROJETO);
			ps.clearParameters();

			ps.setInt(1,projeto.getIdProjeto());

			ps.executeUpdate();

			excluido = true;

		}catch(SQLException e){
			e.printStackTrace();
			excluido = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return excluido;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#excluirResponsaveisProjeto(br.ufscar.dominio.Projeto)
	 */
	@Override
	public boolean excluirResponsaveisProjeto(Projeto projeto) {
		boolean excluido = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EXCLUIR_PROJETO_RESPONSAVEL);
			ps.clearParameters();

			ps.setInt(1,projeto.getIdProjeto());

			ps.executeUpdate();

			excluido = true;

		}catch(SQLException e){
			e.printStackTrace();
			excluido = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return excluido;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#alterarAtividade(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public boolean alterarAtividade(ProjetoAtividade atividade) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean alterado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);
			ps = mySQLConnection.prepareStatement(EDITAR_ATIVIDADE);
			ps.clearParameters();

			ps.setString(1,atividade.getNome());
			ps.setString(2,atividade.getDescricao());
			ps.setInt(3,atividade.getTipo());
			ps.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(atividade.getPrazo()));
			ps.setInt(5,atividade.getStatus());
			ps.setBoolean(6,true);
			ps.setInt(7, atividade.getIdAtividade());

			ps.executeUpdate();

			alterado = true;

		}catch(SQLException e){
			e.printStackTrace();
			alterado = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return alterado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#excluirResponsaveisAtividade(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public boolean excluirResponsaveisAtividade(ProjetoAtividade atividade) {
		boolean excluido = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EXCLUIR_ATIVIDADE_RESPONSAVEL);
			ps.clearParameters();

			ps.setInt(1,atividade.getIdAtividade());

			ps.executeUpdate();

			excluido = true;

		}catch(SQLException e){
			e.printStackTrace();
			excluido = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return excluido;
	}

	@Override
	public boolean excluirCompetenciaAtividades(ProjetoAtividade atividade) {
		boolean excluido = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EXCLUIR_COMPETENCIA_ATIVIDADE);
			ps.clearParameters();

			ps.setInt(1,atividade.getIdAtividade());

			ps.executeUpdate();

			excluido = true;

		}catch(SQLException e){
			e.printStackTrace();
			excluido = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return excluido;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#listarProjetos()
	 */
	@Override
	public List<Projeto> listarProjetos() {
		List<Projeto> projetosList = new ArrayList<Projeto>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_PROJETOS_PARA_LISTAR);
			ps.clearParameters();
			rs = ps.executeQuery();
			while(rs.next()){

				projetosList.add(recuperarProjetoPorId(rs.getInt("idProjeto")));

			}
		} catch (SQLException e) {
			projetosList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return projetosList;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#recuperarProjetoPorId(int)
	 */
	@Override
	public Projeto recuperarProjetoPorId(int idProjeto) {
		Projeto projeto = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_PROJETO_POR_ID);
			ps.clearParameters();
			rs = ps.executeQuery();
			if(rs.next()){

				String nome = rs.getString("nome");
				int tipo = rs.getInt("tipo");
				Date prazo = rs.getDate("prazo");
				String observacoes = rs.getString("observacoes");
				int status = rs.getInt("status");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				
				projeto = new Projeto(idProjeto, nome, tipo, prazo, observacoes, status, estado, ts, null, null, null);

				List<ProjetoAtividade> projetoAtividade = listarAtividadesProjeto(projeto);
				projeto.setProjetoAtividade(projetoAtividade);
				
				List<Responsavel> responsaveis = listarResponsaveisProjeto(projeto);
				projeto.setResponsaveis(responsaveis);
				
				List<Feedback> feedbacksResponsaveisAtividades = listarFeedbacksProjeto(projeto);
				projeto.setFeedbacksResponsaveisAtividades(feedbacksResponsaveisAtividades);
				
			}
		} catch (SQLException e) {
			projeto = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return projeto;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#listarFeedbacksProjeto(br.ufscar.dominio.Projeto)
	 */
	@Override
	public List<Feedback> listarFeedbacksProjeto(Projeto projeto) {
		List<Feedback> feedbackList = new ArrayList<Feedback>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_FEEDBACKS_PROJETO_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, projeto.getIdProjeto());
			rs = ps.executeQuery();
			while(rs.next()){

				feedbackList.add(recuperarFeedbackPorId(rs.getInt("idFeedback")));

			}
		} catch (SQLException e) {
			feedbackList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return feedbackList;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#recuperarFeedbackPorId(int)
	 */
	@Override
	public Feedback recuperarFeedbackPorId(int idFeedBack) {
		Feedback feedback = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_FEEDBACK_POR_ID);
			ps.clearParameters();
			ps.setInt(1, idFeedBack);
			rs = ps.executeQuery();
			if(rs.next()){

				int idFeedback = rs.getInt("idFeedback");
				int avaliacao = rs.getInt("avaliacao");
				int tpFeedback = rs.getInt("tpFeedback");
				String observacoes = rs.getString("observacoes");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel feedbackDe = _pessoaRepository.recuperarResponsavelSimplesPorId(rs.getInt("feedbackDe"));
				Responsavel feedbackPara = _pessoaRepository.recuperarResponsavelSimplesPorId(rs.getInt("feedbackPara"));
				ProjetoAtividade atividade = recuperarAtividadeProjetoPorId(rs.getInt("idProjetoAtividade"));
				feedback = new Feedback(idFeedback, avaliacao, tpFeedback, observacoes, estado, ts, feedbackDe, feedbackPara, atividade);

			}
		} catch (SQLException e) {
			feedback = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return feedback;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#listarAtividadesProjeto(br.ufscar.dominio.Projeto)
	 */
	@Override
	public List<ProjetoAtividade> listarAtividadesProjeto(Projeto projeto) {
		List<ProjetoAtividade> atividadesList = new ArrayList<ProjetoAtividade>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_ATIVIDADES_PROJETO_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, projeto.getIdProjeto());
			rs = ps.executeQuery();
			while(rs.next()){

				atividadesList.add(recuperarAtividadeProjetoPorId(rs.getInt("idAtividade")));

			}
		} catch (SQLException e) {
			atividadesList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return atividadesList;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#recuperarAtividadeProjetoPorId(int)
	 */
	@Override
	public ProjetoAtividade recuperarAtividadeProjetoPorId(int idAtividade) {
		ProjetoAtividade atividade = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_ATIVIDADE_PROJETO_POR_ID);
			ps.clearParameters();
			ps.setInt(1, idAtividade);
			rs = ps.executeQuery();
			if(rs.next()){

				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				int tipo = rs.getInt("tipo");
				Date prazo = rs.getDate("prazo");
				int status = rs.getInt("status");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				atividade =  new ProjetoAtividade(idAtividade, nome, descricao, tipo, prazo, status, estado, ts, null, null, null, null);

				Projeto projeto = recuperarProjetoPorAtividade(atividade);
				atividade.setProjeto(projeto);

				List<Responsavel> responsaveis = listarResponsaveisProjetoAtividade(atividade);
				atividade.setResponsaveis(responsaveis);

				List<Competencia> competencias = listarCompetenciasProjetoAtividade(atividade);
				atividade.setCompetencia(competencias);

				Feedback feedbackAtividade = recuperaFeedbackAtividade(atividade);
				atividade.setFeedbackAtividade(feedbackAtividade);

			}
		} catch (SQLException e) {
			atividade = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return atividade;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#recuperaFeedbackAtividade(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public Feedback recuperaFeedbackAtividade(ProjetoAtividade atividade) {
		Feedback feedback = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_FEEDBACK_ATIVIDADE_POR_ATIVIDADE);
			ps.clearParameters();
			ps.setInt(1, atividade.getIdAtividade());
			rs = ps.executeQuery();
			if(rs.next()){

				int idFeedback = rs.getInt("idFeedback");
				int avaliacao = rs.getInt("avaliacao");
				int tpFeedback = rs.getInt("tpFeedback");
				String observacoes = rs.getString("observacoes");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel feedbackDe = _pessoaRepository.recuperarResponsavelSimplesPorId(rs.getInt("feedbackDe"));
				Responsavel feedbackPara = _pessoaRepository.recuperarResponsavelSimplesPorId(rs.getInt("feedbackPara"));
				feedback = new Feedback(idFeedback, avaliacao, tpFeedback, observacoes, estado, ts, feedbackDe, feedbackPara, atividade);

			}
		} catch (SQLException e) {
			feedback = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return feedback;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#recuperarProjetoPorAtividade(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public Projeto recuperarProjetoPorAtividade(ProjetoAtividade atividade) {
		Projeto projeto = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_PROJETO_POR_ATIVIDADE);
			ps.clearParameters();
			ps.setInt(1, atividade.getIdAtividade());
			rs = ps.executeQuery();
			if(rs.next()){

				int idProjeto = rs.getInt("PA.idProjeto");
				String nome = rs.getString("P.nome");
				int tipo = rs.getInt("P.tipo");
				Date prazo = rs.getDate("P.prazo");
				String observacoes = rs.getString("P.observacoes");
				int status = rs.getInt("P.status");
				boolean estado = rs.getBoolean("P.estado");
				Date ts = rs.getDate("P.ts");
				projeto = new Projeto(idProjeto, nome, tipo, prazo, observacoes, status, estado, ts, null, null, null);

			}
		} catch (SQLException e) {
			projeto = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return projeto;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#listarResponsaveisProjeto(br.ufscar.dominio.Projeto)
	 */
	@Override
	public List<Responsavel> listarResponsaveisProjeto(Projeto projeto) {
		List<Responsavel> responsaveisList = new ArrayList<Responsavel>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_RESPONSAVEIS_PROJETO_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, projeto.getIdProjeto());
			rs = ps.executeQuery();
			while(rs.next()){

				responsaveisList.add(_pessoaRepository.recuperarResponsavelSimplesPorId(rs.getInt("idPessoa")));

			}
		} catch (SQLException e) {
			responsaveisList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return responsaveisList;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#listarCompetenciasProjetoAtividade(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public List<Competencia> listarCompetenciasProjetoAtividade(ProjetoAtividade atividade) {
		List<Competencia> competenciasList = new ArrayList<Competencia>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_COMPETENCIAS_ATIVIDADE_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, atividade.getIdAtividade());
			rs = ps.executeQuery();
			while(rs.next()){

				competenciasList.add(_competenciaRepository.recuperarCompetenciaPeloId(rs.getInt("idCompetencia")));

			}
		} catch (SQLException e) {
			competenciasList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return competenciasList;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.IProjetoRepository#listarResponsaveisProjetoAtividade(br.ufscar.dominio.ProjetoAtividade)
	 */
	@Override
	public List<Responsavel> listarResponsaveisProjetoAtividade(ProjetoAtividade atividade) {
		List<Responsavel> responsaveisList = new ArrayList<Responsavel>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_RESPONSAVEIS_ATIVIDADE_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, atividade.getIdAtividade());
			rs = ps.executeQuery();
			while(rs.next()){

				responsaveisList.add(_pessoaRepository.recuperarResponsavelSimplesPorId(rs.getInt("idPessoa")));

			}
		} catch (SQLException e) {
			responsaveisList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return responsaveisList;
	}

	@Override
	public List<Projeto> listarProjetosPorResponsavel(int idPessoa) {
		List<Projeto> projetosList = new ArrayList<Projeto>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_PROJETOS_POR_RESPNSAVEL_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				projetosList.add(recuperarProjetoPorId(rs.getInt("idProjeto")));

			}
		} catch (SQLException e) {
			projetosList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return projetosList;
	}

	@Override
	public List<ProjetoAtividade> listarProjetosAtividadesPorResponsavel(
			int idPessoa) {
		List<ProjetoAtividade> atividadesList = new ArrayList<ProjetoAtividade>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_ATIVIDADES_POR_RESPONSAVEL_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				atividadesList.add(recuperarAtividadeProjetoPorId(rs.getInt("idProjetoAtividade")));

			}
		} catch (SQLException e) {
			atividadesList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return atividadesList;
	}

	@Override
	public List<Feedback> recuperarFeedbacksCriadosPorResponsavel(int idPessoa) {
		List<Feedback> feedbacksList = new ArrayList<Feedback>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_FEEDBACK_CRIADO_POR_RESPONSAVEL_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				feedbacksList.add(recuperarFeedbackPorId(rs.getInt("idFeedback")));

			}
		} catch (SQLException e) {
			feedbacksList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return feedbacksList;
	}

	@Override
	public List<Feedback> recuperarFeedbacksRecebidosPorResponsavel(int idPessoa) {
		List<Feedback> feedbacksList = new ArrayList<Feedback>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_FEEDBACK_RECEBIDO_POR_RESPONSAVEL_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				feedbacksList.add(recuperarFeedbackPorId(rs.getInt("feedbackPara")));

			}
		} catch (SQLException e) {
			feedbacksList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return feedbacksList;
	}

	@Override
	public boolean gravaFeedBack(Feedback feedback) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean gravado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(GRAVAR_FEEDBACK);
			ps.clearParameters();
			ps.setInt(1,feedback.getFeedbackDe().getIdPessoa());
			ps.setInt(2,feedback.getFeedbackPara().getIdPessoa());
			ps.setInt(3,feedback.getProjetoAtividade().getIdAtividade());
			ps.setInt(4, feedback.getAvaliacao());
			ps.setInt(5,feedback.getTpFeedback());
			ps.setString(6,feedback.getObservacoes());
			ps.setBoolean(7,true);

			ps.executeUpdate();

			gravado = true;

		}catch(SQLException e){
			e.printStackTrace();
			gravado = false;
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}
	
	@Override
	public boolean alterarFeedBack(Feedback feedback) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean alterado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(EDITAR_FEEDBACK);
			ps.clearParameters();

			ps.setInt(1,feedback.getFeedbackDe().getIdPessoa());
			ps.setInt(2,feedback.getFeedbackPara().getIdPessoa());
			ps.setInt(3,feedback.getProjetoAtividade().getIdAtividade());
			ps.setInt(4, feedback.getAvaliacao());
			ps.setInt(5,feedback.getTpFeedback());
			ps.setString(6,feedback.getObservacoes());
			ps.setBoolean(7,true);
			ps.setInt(8, feedback.getIdFeedback());

			ps.executeUpdate();

			alterado = true;

		}catch(SQLException e){
			e.printStackTrace();
			alterado = false;
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return alterado;
	}
	
}
