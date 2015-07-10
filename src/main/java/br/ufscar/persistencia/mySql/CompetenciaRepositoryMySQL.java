package br.ufscar.persistencia.mySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.ufscar.dao.ConnectionManager;
import br.ufscar.dominio.Competencia;
import br.ufscar.dominio.CompetenciaCategoria;
import br.ufscar.dominio.Responsavel;
import br.ufscar.dominio.interfaces.ICompetenciaRepository;

@Repository
public class CompetenciaRepositoryMySQL implements ICompetenciaRepository {

	private static final String VERIFICA_EXISTENCIA_COMPETENCIA = "SELECT COUNT(*) FROM Competencia WHERE nome = ? AND estado = TRUE";
	private static final String VERIFICA_EXISTENCIA_CATEGORIA = "SELECT COUNT(*) FROM CompetenciaCategoria WHERE nome = ? AND estado = TRUE";

	private static final String RECUPERA_CATEGORIA_PELO_NOME_CATEGORIA = "SELECT idCompetenciaCategoria, aprovadoPor, nome, estado, ts FROM CompetenciaCategoria C WHERE nome = ? AND estado = TRUE";
	private static final String RECUPERA_COMPETENCIA_PELO_NOME = "SELECT idCompetencia, aprovadoPor, nome, estado, ts FROM Competencia C WHERE nome = ? AND estado = TRUE";
	private static final String RECUPERA_COMPETENCIA_PELA_CATEGORIA = "SELECT C.idCompetencia, C.aprovadoPor, C.nome, C.estado, C.ts, C.idCategoria FROM Competencia C INNER JOIN CompetenciaPorCategoria CPC ON CPC.idCompetencia = C.idCompetencia WHERE CPC.idCategoria = ? AND C.estado = true";
	
	private static final String GRAVA_COMPETENCIA = "INSERT INTO Competencia (aprovadoPor,nome,estado,ts) VALUES (?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVA_COMPETENCIA_CATEGORIA = "INSERT INTO CompetenciaCategoria (aprovadoPor,nome,estado,ts) VALUES (?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_RELACAO_CATEGORIA_SUB_CATEGORIA = "INSERT INTO CompetenciaSubCategoria (idCategoria,idSubCategoria) VALUES (?,?)";
	private static final String GRAVAR_RELACAO_CATEGORIA_COMPETENCIA = "INSERT INTO CompetenciaPorCategoria (idCategoria,idCompetencia) VALUES (?,?)";

	private static final String BUSCAR_COMPETENCIAS_APROVADAS_POR_RESPONSAVEL_PARA_LISTAR = "SELECT idCompetencia FROM Competencia C WHERE aprovadorPor = ? AND estado = TRUE";
	private static final String BUSCAR_COMPETENCIAS_CATEGORIA_APROVADAS_POR_RESPONSAVEL_PARA_LISTAR = "SELECT nome FROM CompetenciaCategoria C WHERE aprovadorPor = ? AND estado = TRUE";
	
	private static final String APROVAR_COMPETENCIA = "UPDATE Competencia SET aprovadoPor = ? WHERE idCompetencia = ? AND estado = TRUE";
	private static final String APROVAR_COMPETENCIA_CATEGORIA = "UPDATE CompetenciaCategoria SET aprovadoPor = ? WHERE idCompetenciaCategoria = ? AND estado = TRUE";

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#verificaExostenciaCompetencia(br.ufscar.dominio.Competencia)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#gravaCompetencia(br.ufscar.dominio.Competencia)
	 */
	@Override
	public boolean gravaCompetencia(Competencia competencia){

		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		boolean gravado = false; 

		try {

			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(GRAVA_COMPETENCIA);
			ps.clearParameters();

			ps.setInt(1, competencia.getAprovadorPor() == null ? 0 : competencia.getAprovadorPor().getIdPessoa());
			ps.setString(2, competencia.getNome());
			ps.setBoolean(3, false);

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

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#gravaCompetenciaCategoria(br.ufscar.dominio.CompetenciaCategoria)
	 */
	@Override
	public boolean gravaCompetenciaCategoria(
			CompetenciaCategoria competenciaCategoria) {
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet rs = null;
		int idCategoria = 0;
		boolean gravado = false; 

		try {

			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVA_COMPETENCIA_CATEGORIA, Statement.RETURN_GENERATED_KEYS);
			ps.clearParameters();

			ps.setInt(1, competenciaCategoria.getAprovadoPor() == null ? 0 : competenciaCategoria.getAprovadoPor().getIdPessoa());
			ps.setString(2, competenciaCategoria.getNome());
			ps.setBoolean(3, false);

			ps.executeUpdate();

			//parte que pega o que foi incluído no bd... no caso o campo id
			rs = ps.getGeneratedKeys(); 
			if(rs.next()){  
				idCategoria = rs.getInt(1);  
			}

			competenciaCategoria.setIdCategoria(idCategoria);

			if(gravaSubCategorias(competenciaCategoria, competenciaCategoria.getSubCategorias())){

				gravado = gravaCompetencias(competenciaCategoria, competenciaCategoria.getCompetencias());

			}else{
				gravado = false;
			}

			if(gravado){
				//Chama commit no final do processo
				mySQLConnection.commit();
				//Habilita auto comit novamente
				mySQLConnection.setAutoCommit(true);
			}else{
				ConnectionManager.rollBack();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			gravado = false;
			ConnectionManager.rollBack();	
		}finally	{
			ConnectionManager.closeAll(ps, rs);
		}
		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#gravaCompetencias(br.ufscar.dominio.CompetenciaCategoria, java.util.List)
	 */
	@Override
	public boolean gravaCompetencias(
			CompetenciaCategoria competenciaCategoria,
			List<Competencia> competencias) {
		boolean gravado = false;

		if(competencias == null || competencias.isEmpty()){
			gravado = true;
		}else{
			for (Competencia competencia : competencias) {
				if(verificaExostenciaCompetencia(competencia)){
					competencia = recuperarCompetenciaPeloNome(competencia.getNome());
					gravado = gravaRelacaoCategoriaCompetencia(competencia,competenciaCategoria);
					if(!gravado){
						gravado = false;
						break;
					}
				}else if(gravaCompetencia(competencia)){
					competencia = recuperarCompetenciaPeloNome(competencia.getNome());
					gravado = gravaRelacaoCategoriaCompetencia(competencia,competenciaCategoria);
					if(!gravado){
						gravado = false;
						break;
					}
				}else{
					gravado = false;
					break;
				}
			}
		}

		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#gravaRelacaoCategoriaCompetencia(br.ufscar.dominio.Competencia, br.ufscar.dominio.CompetenciaCategoria)
	 */
	@Override
	public boolean gravaRelacaoCategoriaCompetencia(Competencia competencia,
			CompetenciaCategoria competenciaCategoria){
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_RELACAO_CATEGORIA_COMPETENCIA);
			ps.clearParameters();

			ps.setInt(1,competenciaCategoria.getIdCategoria());
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
	 * @see br.ufscar.persistencia.mySql.teste#gravaSubCategorias(br.ufscar.dominio.CompetenciaCategoria, java.util.List)
	 */
	@Override
	public boolean gravaSubCategorias(
			CompetenciaCategoria competenciaCategoria,
			List<CompetenciaCategoria> subCategorias){
		boolean gravado = false;

		if(subCategorias == null || subCategorias.isEmpty()){
			gravado = true;
		}else{
			for (CompetenciaCategoria subCategoria : subCategorias) {
				if(verificaExostenciaCompetenciaCategoria(subCategoria)){
					subCategoria = recuperarCategoriaPeloNome(subCategoria.getNome());
					gravado = gravaRelacaoCategoriSubCategoria(competenciaCategoria,subCategoria);
					if(!gravado){
						gravado = false;
						break;
					}
				}else if(gravaCompetenciaCategoria(subCategoria)){
					subCategoria = recuperarCategoriaPeloNome(subCategoria.getNome());
					gravado = gravaRelacaoCategoriSubCategoria(competenciaCategoria,subCategoria);
					if(!gravado){
						gravado = false;
						break;
					}
				}else{
					gravado = false;
					break;
				}
			}
		}

		return gravado;
	}

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#gravaRelacaoCategoriSubCategoria(br.ufscar.dominio.CompetenciaCategoria, br.ufscar.dominio.CompetenciaCategoria)
	 */
	@Override
	public boolean gravaRelacaoCategoriSubCategoria(
			CompetenciaCategoria competenciaCategoria,
			CompetenciaCategoria subCategoria){
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_RELACAO_CATEGORIA_SUB_CATEGORIA);
			ps.clearParameters();

			ps.setInt(1,competenciaCategoria.getIdCategoria());
			ps.setInt(2,subCategoria.getIdCategoria());

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
	 * @see br.ufscar.persistencia.mySql.teste#recuperarCategoriaPeloNome(java.lang.String)
	 */
	@Override
	public CompetenciaCategoria recuperarCategoriaPeloNome(String nome) {
		CompetenciaCategoria categoria = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(RECUPERA_CATEGORIA_PELO_NOME_CATEGORIA);
			ps.clearParameters();
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if(rs.next()){

				int idCategoria = rs.getInt("idCompetenciaCategoria");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel aprovadoPor = new Responsavel();
				aprovadoPor.setIdPessoa(rs.getInt("aprovadoPor"));

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

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#recuperarSubCategoriasPorCategoria(br.ufscar.dominio.CompetenciaCategoria)
	 */
	@Override
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

				int idCategoria = rs.getInt("C.idCategoria");
				String nome = rs.getString("C.nome");
				boolean estado = rs.getBoolean("C.estado");
				Date ts = rs.getDate("C.ts");
				Responsavel aprovadoPor = new Responsavel();
				aprovadoPor.setIdPessoa(rs.getInt("C.aprovadorPor"));

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

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#recuperarCompetenciasPorCategoria(br.ufscar.dominio.CompetenciaCategoria)
	 */
	@Override
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
				Competencia competencia = new Competencia(idCompetencia, nome, estado, ts, aprovadorPor);
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

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#verificaExostenciaCompetenciaCategoria(br.ufscar.dominio.CompetenciaCategoria)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see br.ufscar.persistencia.mySql.teste#recuperarCompetenciaPeloNomeECategoria(java.lang.String, br.ufscar.dominio.CompetenciaCategoria)
	 */
	@Override
	public Competencia recuperarCompetenciaPeloNome(String nome) {
		Competencia competencia = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(RECUPERA_COMPETENCIA_PELO_NOME);
			ps.clearParameters();
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if(rs.next()){
				int idCompetencia = rs.getInt("idCompetencia");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel aprovadorPor = new Responsavel();
				aprovadorPor.setIdPessoa(rs.getInt("aprovadoPor"));
				competencia = new Competencia(idCompetencia, nome, estado, ts, aprovadorPor);
			}
		} catch (SQLException e) {
			competencia = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return competencia;
	}
	
	@Override
	public Competencia recuperarCompetenciaPeloId(int idCompetencia) {
		Competencia competencia = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(RECUPERA_COMPETENCIA_PELO_NOME);
			ps.clearParameters();
			ps.setInt(1, idCompetencia);
			rs = ps.executeQuery();
			if(rs.next()){
				String nome = rs.getString("nome");
				boolean estado = rs.getBoolean("estado");
				Date ts = rs.getDate("ts");
				Responsavel aprovadorPor = new Responsavel();
				aprovadorPor.setIdPessoa(rs.getInt("aprovadoPor"));
				competencia = new Competencia(idCompetencia, nome, estado, ts, aprovadorPor);
			}
		} catch (SQLException e) {
			competencia = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return competencia;
	}

	@Override
	public List<Competencia> recuperarCompetenciasAprovadasPorResponsavel(
			int idPessoa) {
		List<Competencia> competenciasList = new ArrayList<Competencia>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_COMPETENCIAS_APROVADAS_POR_RESPONSAVEL_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				competenciasList.add(recuperarCompetenciaPeloId(rs.getInt("idCompetencia")));

			}
		} catch (SQLException e) {
			competenciasList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return competenciasList;
	}

	@Override
	public List<CompetenciaCategoria> recuperarCompetenciaCategoriasAprovadasPorResponsavel(
			int idPessoa) {
		List<CompetenciaCategoria> competenciasList = new ArrayList<CompetenciaCategoria>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_COMPETENCIAS_CATEGORIA_APROVADAS_POR_RESPONSAVEL_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				competenciasList.add(recuperarCategoriaPeloNome(rs.getString("nome")));

			}
		} catch (SQLException e) {
			competenciasList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return competenciasList;
	}

	@Override
	public boolean aprovarCompetencia(Competencia competencia,
			Responsavel aprovador) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean aprovado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(APROVAR_COMPETENCIA);
			ps.clearParameters();

			ps.setInt(1,aprovador.getIdPessoa());
			ps.setInt(2,competencia.getIdCompetencia());

			ps.executeUpdate();

			aprovado = true;

		}catch(SQLException e){
			e.printStackTrace();
			aprovado = false;
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return aprovado;
	}

	@Override
	public boolean aprovarCompetenciaCategoria(
			CompetenciaCategoria competenciaCategoria, Responsavel aprovador) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean aprovado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(APROVAR_COMPETENCIA_CATEGORIA);
			ps.clearParameters();

			ps.setInt(1,aprovador.getIdPessoa());
			ps.setInt(2,competenciaCategoria.getIdCategoria());

			ps.executeUpdate();

			aprovado = true;

		}catch(SQLException e){
			e.printStackTrace();
			aprovado = false;
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return aprovado;
	}

}
