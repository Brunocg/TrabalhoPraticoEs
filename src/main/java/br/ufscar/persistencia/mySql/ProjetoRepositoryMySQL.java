package br.ufscar.persistencia.mySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dao.ConnectionManager;
import br.ufscar.dominio.Competencia;
import br.ufscar.dominio.CompetenciaCategoria;
import br.ufscar.dominio.Projeto;
import br.ufscar.dominio.ProjetoAtividade;
import br.ufscar.dominio.Responsavel;

public class ProjetoRepositoryMySQL {

	private static final String GRAVA_PROJETO = "INSERT INTO Projeto (nome, tipo, prazo, observacoes, status, estado, ts) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_ATIVIDADE = "INSERT INTO ProjetoAtividade (nome, descricao, tipo, prazo, status, estado, ts) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_ATIVIDADE_RESPONSAVEL = "INSERT INTO ProjetoAtividadeResponsaveis (idProjetoAtividade, idPessoa) VALUES (?,?)";
	private static final String GRAVAR_COMPETENCIA_ATIVIDADE = "INSERT INTO ProjetoAtividadeCompetencias (idProjetoAtividade, idCompetencia) VALUES (?,?)";
	private static final String GRAVAR_ATIVIDADES_DO_PROJETO = "INSERT INTO ProjetoAtividades (idProjeto, idAtividade) VALUES (?,?)";
	private static final String GRAVAR_PROJETO_RESPONSAVEL = "INSERT INTO ProjetoResponsaveis (idProjeto, idPessoa) VALUES (?,?)";
	
	private static final String EXCLUIR_ATIVIDADES_DO_PROJETO = "DELETE FROM ProjetoAtividades WHERE idProjeto = ?";
	private static final String EXCLUIR_PROJETO_RESPONSAVEL = "DELETE FROM ProjetoResponsaveis WHERE idProjeto = ?";
	private static final String EXCLUIR_ATIVIDADE_RESPONSAVEL = "DELETE FROM ProjetoAtividadeResponsaveis WHERE idProjetoAtividade = ?";
	private static final String EXCLUIR_COMPETENCIA_ATIVIDADE = "DELETE FROM ProjetoAtividadeCompetencias WHERE idProjetoAtividade = ?";

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

	public boolean gravaCompetenciaAtividades(ProjetoAtividade atividade) {
		boolean gravado = false;

		for (CompetenciaCategoria categoria : atividade.getCompetenciaCategoria()) {
			for (Competencia competencia : categoria.getCompetencias()) {
				
				gravado = gravaCompetenciaAtividades(atividade,competencia);

				if(!gravado){
					break;
				}
			}
		}
		return gravado;
	}

	private boolean gravaCompetenciaAtividades(ProjetoAtividade atividade,
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
	
	public boolean alterarProjeto(Projeto projeto){
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean gravado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			//FIXME
			ps = mySQLConnection.prepareStatement(""/*EDITAR_PROJETO*/);
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
	
	public boolean alterarAtividade(ProjetoAtividade atividade) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;
		
		boolean alterado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);
			//FIXME
			ps = mySQLConnection.prepareStatement(""/*EDITAR_ATIVIDADE*/);
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
	
	private boolean excluirCompetenciaAtividades(ProjetoAtividade atividade) {
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
}
