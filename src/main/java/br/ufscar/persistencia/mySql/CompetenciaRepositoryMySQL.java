package br.ufscar.persistencia.mySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.ufscar.dao.ConnectionManager;
import br.ufscar.dominio.Competencia;
import br.ufscar.dominio.CompetenciaCategoria;
import br.ufscar.dominio.Responsavel;

@Repository
public class CompetenciaRepositoryMySQL {

	private static final String GRAVA_COMPETENCIA = "INSERT INTO Competencia (nome,estado,ts,idCategoria) VALUES (?,?,CURRENT_TIMESTAMP,?)";
	private static final String VERIFICA_EXISTENCIA_COMPETENCIA = "SELECT COUNT(*) FROM Competencia WHERE nome = ? AND idCategoria = ?";
	private static final String RECUPERA_PELO_NOME_CATEGORIA = "SELECT idCompetencia, aprovadoPor nome, estado, ts, idCategoria FROM Competencia C WHERE nome = ? AND idCategoria = ?";
	private static final String RECUPERA_COMPETENCIA_PELA_CATEGORIA = "SELECT idCompetencia, aprovadoPor nome, estado, ts, idCategoria FROM Competencia C INNER JOIN CompetenciaPorCategoria CPC WHERE CPC.idCategoria = ? AND C.estado = true";
	private static final String VERIFICA_EXISTENCIA_CATEGORIA = "SELECT COUNT(*) FROM CompetenciaCategoria WHERE nome = ?";

	public boolean verificaExostenciaCompetencia(Competencia competencia) {
		boolean existe = false;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(VERIFICA_EXISTENCIA_COMPETENCIA);
			ps.clearParameters();
			ps.setString(1, competencia.getNome());
			ps.setInt(2, competencia.getCompetenciaCategoria().getIdCategoria());
			rs = ps.executeQuery();
			if(rs.next()){
				int qtd = rs.getInt(1);
				if(qtd > 0){
					existe = true;
				}
			}
		} catch (SQLException e) {
			existe = false;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return existe;
	}

	public boolean gravaCompetencia(Competencia competencia){
		
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		boolean gravado = false; 

		try {

//			if(!verificaExostenciaCompetenciaCategoria(competencia.getCompetenciaCategoria())){
//				if(gravaCompetenciaCategoria(competencia.getCompetenciaCategoria())){
//					CompetenciaCategoria cat = recuperarCategoriaPeloNome(competencia.getNome());
//					competencia.setCompetenciaCategoria(cat);
//				}
//			}
			
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(GRAVA_COMPETENCIA);
			ps.clearParameters();

			ps.setString(1, competencia.getNome());
			ps.setBoolean(2, false);
			ps.setInt(3, competencia.getCompetenciaCategoria().getIdCategoria());

			ps.executeUpdate();

			gravado = true;

		} catch (SQLException e) {
			gravado = false;
			e.printStackTrace();
		}finally	{
			ConnectionManager.closeAll(ps);
		}
		return gravado;
	}

	public CompetenciaCategoria recuperarCategoriaPeloNome(String nome) {
		CompetenciaCategoria categoria = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(RECUPERA_PELO_NOME_CATEGORIA);
			ps.clearParameters();
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if(rs.next()){

				int idCategoria = rs.getInt("idCompetenciaCategoria");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel aprovadoPor = new Responsavel();
				aprovadoPor.setIdPessoa(rs.getInt("aprovadorPor"));
				
				categoria = new CompetenciaCategoria(idCategoria, nome, estado, ts, null, null, aprovadoPor);
				
				List<Competencia> competencias = recuperarCompetenciasPorCategoria(categoria);
				List<CompetenciaCategoria> subCategorias = recuperarSubCategoriasPorCategoria(categoria);
				categoria.setCompetencias(competencias);
				categoria.setSubCategorias(subCategorias);
			}
		} catch (SQLException e) {
			categoria = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return categoria;
	}

	public List<CompetenciaCategoria> recuperarSubCategoriasPorCategoria(
			CompetenciaCategoria categoria) {
		
		List<CompetenciaCategoria> subCategorias = new ArrayList<CompetenciaCategoria>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(RECUPERA_COMPETENCIA_PELA_CATEGORIA);
			ps.clearParameters();
			ps.setInt(1, categoria.getIdCategoria());
			rs = ps.executeQuery();
			while(rs.next()){

				int idCategoria = rs.getInt("idCategoria");
				String nome = rs.getString("nome");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel aprovadoPor = new Responsavel();
				aprovadoPor.setIdPessoa(rs.getInt("aprovadorPor"));
				
				CompetenciaCategoria subCategoria = new CompetenciaCategoria(idCategoria, nome, estado, ts, null, null, aprovadoPor);
				
				List<Competencia> competencias = recuperarCompetenciasPorCategoria(subCategoria);
				List<CompetenciaCategoria> subCat = recuperarSubCategoriasPorCategoria(subCategoria);
				subCategoria.setCompetencias(competencias);
				subCategoria.setSubCategorias(subCat);
				
				subCategorias.add(subCategoria);
			}
		} catch (SQLException e) {
			subCategorias = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return subCategorias;
	}

	public List<Competencia> recuperarCompetenciasPorCategoria(
			CompetenciaCategoria categoria) {
		
		List<Competencia> competencias = new ArrayList<Competencia>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(RECUPERA_COMPETENCIA_PELA_CATEGORIA);
			ps.clearParameters();
			ps.setInt(1, categoria.getIdCategoria());
			rs = ps.executeQuery();
			while(rs.next()){
				int idCompetencia = rs.getInt("idCompetencia");
				String nome = rs.getString("nome");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel aprovadorPor = new Responsavel();
				aprovadorPor.setIdPessoa(rs.getInt("aprovadoPor"));
				Competencia competencia = new Competencia(idCompetencia, nome, estado, ts, categoria, aprovadorPor);
				competencias.add(competencia);
			}
		} catch (SQLException e) {
			competencias = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return competencias;
	}

	public boolean verificaExostenciaCompetenciaCategoria(
			CompetenciaCategoria competenciaCategoria) {
		boolean existe = false;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(VERIFICA_EXISTENCIA_CATEGORIA);
			ps.clearParameters();
			ps.setString(1, competenciaCategoria.getNome());
			rs = ps.executeQuery();
			if(rs.next()){
				int qtd = rs.getInt(1);
				if(qtd > 0){
					existe = true;
				}
			}
		} catch (SQLException e) {
			existe = false;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return existe;
	}

	public Competencia recuperarPeloNomeECategoria(String nome,
			CompetenciaCategoria competenciaCategoria) {
		Competencia competencia = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(RECUPERA_PELO_NOME_CATEGORIA);
			ps.clearParameters();
			ps.setString(1, nome);
			ps.setInt(2, competenciaCategoria.getIdCategoria());
			rs = ps.executeQuery();
			if(rs.next()){
				int idCompetencia = rs.getInt("idCompetencia");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel aprovadorPor = new Responsavel();
				aprovadorPor.setIdPessoa(rs.getInt("aprovadoPor"));
				competencia = new Competencia(idCompetencia, nome, estado, ts, competenciaCategoria, aprovadorPor);
			}
		} catch (SQLException e) {
			competencia = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return competencia;
	}

}
