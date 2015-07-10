package br.ufscar.persistencia.mySql;

import java.sql.Connection;
import java.sql.Date;
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
import br.ufscar.dominio.CompetenciaExperiencia;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Feedback;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Projeto;
import br.ufscar.dominio.ProjetoAtividade;
import br.ufscar.dominio.Responsavel;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.UsuarioAcesso;
import br.ufscar.dominio.UsuarioTipo;
import br.ufscar.dominio.interfaces.ICompetenciaRepository;
import br.ufscar.dominio.interfaces.IPessoaRepository;
//import org.springframework.stereotype.Repository;
import br.ufscar.dominio.interfaces.IProjetoRepository;


//@Repository
public class PessoaRepositoryMySQL implements IPessoaRepository  {
	
	private IProjetoRepository _repositorioDeProjetos = new ProjetoRepositoryMySQL();
	private ICompetenciaRepository _repositorioDeCompetencia = new CompetenciaRepositoryMySQL();

	private static final String GRAVAR_PESSOA = "INSERT INTO Pessoa (nome,sitCivil,sexo,dataNascimento,CPF,RG,telefone,celular,email,pagPessoal,msgInst,estado,ts) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_PESSOA_BASICO = "INSERT INTO Pessoa (nome,sitCivil,sexo,dataNascimento,CPF,RG,telefone,celular,email,pagPessoal,msgInst,estado,ts) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
//	private static final String GRAVAR_PESSOA_BASICO_OLD = "INSERT INTO Pessoa (nome,dataNascimento,CPF,RG,email,estado,ts) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_ENDERECO = "INSERT INTO Endereco (rua,bairro,numero,cidade,uf,pais,cep,estado,ts) VALUES (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_ENDERECO_PESSOA = "INSERT INTO EnderecoPessoa (idEndereco,idPessoa,ts) VALUES (?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_EXPERIENCIA = "INSERT INTO CompetenciaExperiencia (idPessoa, idCompetencia, nivel, tempoExp,observacoes,estado,ts) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_USUARIO = "INSERT INTO Usuario (usuarioDe,login,senha,usuarioTipo,estado,ts) VALUES (?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_USUARIO_ACESSO = "INSERT INTO UsuarioAcesso (descricao, niveisDeAcesso) VALUES (?,?)";
	
	private static final String BUSCAR_PESSOA_POR_LOGIN = "SELECT usuarioDe FROM Usuario U WHERE login = ? AND estado = TRUE AND aprovadoPor != 0";
	private static final String BUSCAR_PESSOA_POR_ID = "SELECT idPessoa, nome, sitCivil, sexo, dataNascimento, CPF, RG, telefone, celular, email, pagPessoal, msgInst, estado, ts FROM Pessoa P WHERE idPessoa = ? AND estado = TRUE";
	private static final String BUSCAR_PESSOAS_PARA_LISTAR = "SELECT idPessoa FROM Pessoa P WHERE estado = true";
	private static final String BUSCAR_ENDERECOS_POR_PESSOA = "SELECT E.idEndereco, E.rua, E.bairro, E.numero, E.cidade, E.uf, E.pais, E.cep, E.estado, E.ts FROM Endereco E INNER JOIN EnderecoPessoa EP ON EP.idEndereco = E.idEndereco WHERE EP.idPessoa = ? AND estado = TRUE";
	private static final String BUSCAR_MORADORES_POR_ENDERECO = "SELECT EP.idPessoa, P.nome FROM EnderecoPessoa EP INNER JOIN Pessoa P ON P.idPessoa = EP.idPessoa WHERE idEndereco = ?";
	private static final String BUSCAR_USUARIO_POR_PESSOA = "SELECT usuarioDe, aprovadoPor, login, senha, usuarioTipo, ultimoLogin, estado, ts FROM Usuario U WHERE usuarioDe = ? AND estado = TRUE AND aprovadoPor != 0";
	private static final String BUSCAR_EXPERIENCIA_POR_PESSOA = "SELECT idCompetenciaExperiencia, idPessoa, idCompetencia, nivel, tempoExp, observacoes, estado, ts FROM CompetenciaExperiencia C WHERE idPessoa = ?  AND estado = TRUE";

	private static final String EDITA_PESSOA = "UPDATE Pessoa SET nome = ?, sitCivil = ?, sexo = ?, dataNascimento = ?, CPF = ?, RG = ?, telefone = ?, celular = ?, email = ?, pagPessoal = ?, msgInst = ?, estado = ? WHERE idPessoa = ?";
	private static final String EDITA_EXPERIENCIA = "UPDATE CompetenciaExperiencia SET idPessoa = ?, idCompetencia = ?, nivel = ?, tempoExp = ?,observacoes = ?,estado = ? WHERE idCompetenciaExperiencia = ?";
	private static final String EDITA_USUARIO = "UPDATE Usuario SET usuarioDe = ?,login = ?,senha = ?,usuarioTipo = ?,estado = ? WHERE idUsuario = ? AND estado = TRUE";

	private static final String EXCLUIR_PESSOA = "UPDATE Pessoa SET estado = ? WHERE idPessoa = ?";
	private static final String EXCLUIR_ENDERECO_PESSOA = "DELETE FROM EnderecoPessoa E WHERE idEndereco = ? AND idPessoa = ?";

	private static final String BUSCAR_USUARIOS_APROVADOS_POR_RESPONSAVEL_PARA_LISTAR = "SELECT usuarioDe FROM Usuario U WHERE aprovadoPor = ?";
	
	private static final String ATUALIZAR_ULTIMO_LOGIN_USUARIO = "UPDATE Usuario SET ultimoLogin = ? WHERE idUsuario = ?";
	private static final String DESATIVA_USUARIO = "UPDATE Usuario SET estado = ? WHERE login = ?";
	private static final String VERIFICA_EXISTENCIA_LOGIN = "SELECT COUNT(*) FROM Usuario U WHERE login = ? AND estado = TRUE";
	private static final String ATUALIZAR_SENHA_POR_LOGIN = "UPDATE Usuario SET senha = ? WHERE login = ?";
	private static final String ATUALIZAR_TIPO_USUARIO = "UPDATE Usuario SET usuarioTipo = ? WHERE idUsuario = ?";
	private static final String APROVAR_USUARIO = "UPDATE Usuario SET aprovadoPor = ? WHERE idUsuario = ?";
	private static final String RECUPERAR_ACESSOS_POR_TIPO = "SELECT idUsuarioAcesso, descricao, niveisDeAcesso FROM UsuarioAcesso U WHERE niveisDeAcesso LIKE ?";

	@Override
	public boolean gravaPessoaBasico(Pessoa pessoa){
		Connection mySQLConnection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int idPessoa = 0;
		boolean gravado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_PESSOA_BASICO, Statement.RETURN_GENERATED_KEYS);
			ps.clearParameters();

			ps.setString(1,pessoa.getNome());
			ps.setString(2,pessoa.getSitCivil());
			ps.setString(3,pessoa.getSexo());
			ps.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(pessoa.getDataNascimento()));
			ps.setString(5,pessoa.getCpf());
			ps.setString(6,pessoa.getRg());
			ps.setString(7,pessoa.getTelefone());
			ps.setString(8,pessoa.getCelular());
			ps.setString(9,pessoa.getEmail());
			ps.setString(10,pessoa.getPagPessoal());
			ps.setString(11,pessoa.getMsgInst());
			ps.setBoolean(12,true);

			ps.executeUpdate();

			//parte que pega o que foi inclu�do no bd... no caso o campo id
			rs = ps.getGeneratedKeys(); 
			if(rs.next()){  
				idPessoa = rs.getInt(1);  
			}

			pessoa.setIdPessoa(idPessoa);

			gravado = gravaUsuario(pessoa,pessoa.getUsuario());
					

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
	
	@Override
	public boolean gravaPessoa(Pessoa pessoa){

		Connection mySQLConnection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int idPessoa = 0;
		boolean gravado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_PESSOA, Statement.RETURN_GENERATED_KEYS);
			ps.clearParameters();

			ps.setString(1,pessoa.getNome());
			ps.setString(2,pessoa.getSitCivil());
			ps.setString(3,pessoa.getSexo());
			ps.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(pessoa.getDataNascimento()));
			ps.setString(5,pessoa.getCpf());
			ps.setString(6,pessoa.getRg());
			ps.setString(7,pessoa.getTelefone());
			ps.setString(8,pessoa.getCelular());
			ps.setString(9,pessoa.getEmail());
			ps.setString(10,pessoa.getPagPessoal());
			ps.setString(11,pessoa.getMsgInst());
			ps.setBoolean(12,true);

			ps.executeUpdate();

			//parte que pega o que foi inclu�do no bd... no caso o campo id
			rs = ps.getGeneratedKeys(); 
			if(rs.next()){  
				idPessoa = rs.getInt(1);  
			}

			pessoa.setIdPessoa(idPessoa);

			List<Endereco> enderecos = gravaEndereco(pessoa.getEndereco());
			if(enderecos == null){
				gravado = false;
			}else{

				if(gravaEnderecosPessoa(pessoa, enderecos)){

					if(gravaExperiencias(pessoa, pessoa.getCompetenciasExperiencia())){
						
						gravado = gravaUsuario(pessoa,pessoa.getUsuario());
					
					}else{
						gravado = false;
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
			ConnectionManager.closeAll(ps,rs);
		}

		return gravado;
	}

	@Override
	public boolean gravaUsuario(Pessoa pessoa, Usuario usuario){
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_USUARIO);
			ps.clearParameters();

			ps.setInt(1,pessoa.getIdPessoa());
			ps.setString(2,usuario.getLogin());
			ps.setString(3,usuario.getSenha());
			ps.setString(4,usuario.getTipo().getDescricao());
			ps.setBoolean(5,true);

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

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			List<CompetenciaExperiencia> competenciasExperiencia){

		boolean gravado = false;

		for (CompetenciaExperiencia competenciaExperiencia : competenciasExperiencia) {

			if(!gravaExperiencias(pessoa, competenciaExperiencia)){
				gravado = false;
				break;
			}

			gravado = true;
		}

		return gravado;
	}

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			CompetenciaExperiencia competenciaExperiencia){
		
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_EXPERIENCIA);
			ps.clearParameters();

			ps.setInt(1,pessoa.getIdPessoa());
			ps.setInt(2,competenciaExperiencia.getCompetencia().getIdCompetencia());
			ps.setInt(3,competenciaExperiencia.getNivel());
			ps.setInt(4,competenciaExperiencia.getTempoExperiencia());
			ps.setString(5,competenciaExperiencia.getObservacoes());
			ps.setBoolean(6,true);

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

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos){
		boolean gravado = false;

		for (Endereco endereco : enderecos) {
			gravado = gravaEnderecosPessoa(pessoa,endereco);

			if(!gravado){
				break;
			}
		}
		return gravado;
	}

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco){

		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_ENDERECO_PESSOA);
			ps.clearParameters();

			ps.setInt(1,endereco.getIdEndereco());
			ps.setInt(2,pessoa.getIdPessoa());

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

	@Override
	public List<Endereco> gravaEndereco(List<Endereco> enderecos) {

		List<Endereco> enderecosComId = new ArrayList<Endereco>();

		for (Endereco endereco : enderecos) {
			if(endereco.getIdEndereco() > 0){//ja existe
				enderecosComId.add(endereco);
			}else{
				
				int idEndereco = gravaEndereco(endereco);
				
				if(idEndereco != 0){
					endereco.setIdEndereco(idEndereco);
					enderecosComId.add(endereco);
				}else{
					enderecosComId = null;
					break;
				}
			}
		}


		return enderecosComId;
	}

	@Override
	public int gravaEndereco(Endereco endereco) {

		Connection mySQLConnection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int idEndereco = 0;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(GRAVAR_ENDERECO, Statement.RETURN_GENERATED_KEYS);
			ps.clearParameters();

			ps.setString(1,endereco.getRua());
			ps.setString(2,endereco.getBairro());
			ps.setInt(3,endereco.getNumero());
			ps.setString(4,endereco.getCidade());
			ps.setString(5,endereco.getUf());
			ps.setString(6,endereco.getPais());
			ps.setString(7,endereco.getCep());
			ps.setBoolean(8,true);

			ps.executeUpdate();

			//parte que pega o que foi inclu�do no bd... no caso o campo id
			rs = ps.getGeneratedKeys(); 
			if(rs.next()){  
				idEndereco = rs.getInt(1);  
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			idEndereco = 0;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps,rs);
		}

		return idEndereco;
	}

	@Override
	public Pessoa recuperarPessoaPorId(int pessoaId) {
		Pessoa pessoa = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_PESSOA_POR_ID);
			ps.clearParameters();
			ps.setInt(1, pessoaId);
			rs = ps.executeQuery();
			if(rs.next()){

				int idPessoa = rs.getInt("idPessoa");
				String nome = rs.getString("nome");
				String sitCivil = rs.getString("sitCivil");
				String sexo = rs.getString("sexo");
				java.util.Date dataNascimento = rs.getDate("dataNascimento");
				String cpf = rs.getString("CPF");
				String rg = rs.getString("RG");
				List<Endereco> enderecos = recuperarEnderecosPorPessoa(idPessoa);
				String telefone = rs.getString("telefone");
				String celular = rs.getString("celular");
				String email = rs.getString("email");
				String pagPessoal = rs.getString("pagPessoal");
				String msgInst = rs.getString("msgInst");
				Usuario usuario = recuperarUsuarioPorPessoa(idPessoa);
				boolean estado = rs.getBoolean("estado");
				java.util.Date ts = rs.getDate("ts");
				List<CompetenciaExperiencia> competenciasExperiencia = recuperarExperienciaPorPessoa(idPessoa);
				pessoa = new Pessoa(idPessoa, nome, sitCivil, sexo, dataNascimento, cpf, rg, enderecos, telefone, celular, email, pagPessoal, msgInst, usuario, estado, ts, competenciasExperiencia);
				
			}
		} catch (SQLException e) {
			pessoa = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return pessoa;
	}

	@Override
	public List<CompetenciaExperiencia> recuperarExperienciaPorPessoa(
			int idPessoa) {
		List<CompetenciaExperiencia> experiencias = new ArrayList<CompetenciaExperiencia>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_EXPERIENCIA_POR_PESSOA);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				int idExperiencia = rs.getInt("idCompetenciaExperiencia");
				int nivel = rs.getInt("nivel");
				int tempoExperiencia = rs.getInt("tempExp");
				String observacoes = rs.getString("observacoes");
				boolean estado = rs.getBoolean("estado");
				java.util.Date ts = rs.getDate("ts");
				Competencia competencia = _repositorioDeCompetencia.recuperarCompetenciaPeloId(rs.getInt("idCompetencia"));
				Pessoa pessoa = new Pessoa();
				pessoa.setIdPessoa(idPessoa);
				CompetenciaExperiencia experiencia = new CompetenciaExperiencia(idExperiencia, nivel, tempoExperiencia, observacoes, estado, ts, competencia, pessoa);
				
				experiencias.add(experiencia);
				
			}
		} catch (SQLException e) {
			experiencias = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return experiencias;
	}

	@Override
	public Usuario recuperarUsuarioPorPessoa(int idPessoa) {
		Usuario usuario = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_USUARIO_POR_PESSOA);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			if(rs.next()){

				int idUsuario = rs.getInt("usuarioDe");
				String login = rs.getString("login");
				String senha = rs.getString("senha");
				java.util.Date ultimoLogin = rs.getDate("ultimoLogin");
				boolean estado = rs.getBoolean("estado");
				java.util.Date ts = rs.getDate("ts");
				Responsavel aprovadoPor = new Responsavel();
				aprovadoPor.setIdPessoa(rs.getInt("aprovadoPor"));
				UsuarioTipo tipo = UsuarioTipo.recuperaTipo(rs.getString("usuarioTipo"));
				usuario = new Usuario(idUsuario, login, senha, ultimoLogin, estado, ts, aprovadoPor, tipo);
				
			}
		} catch (SQLException e) {
			usuario = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return usuario;
	}

	@Override
	public List<Endereco> recuperarEnderecosPorPessoa(int idPessoa) {
		List<Endereco> enderecos = new ArrayList<Endereco>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_ENDERECOS_POR_PESSOA);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				int idEndereco = rs.getInt("E.idEndereco");
				String rua = rs.getString("E.rua");
				String bairro = rs.getString("E.bairro");
				int numero = rs.getInt("E.numero");
				String cidade = rs.getString("E.cidade");
				String uf = rs.getString("E.uf");
				String pais = rs.getString("E.pais");
				String cep = rs.getString("E.cep");
				List<Pessoa> moradores = recuperarMoradoresEndereco(idEndereco);
				Endereco endereco = new Endereco(idEndereco, rua, bairro, numero, cidade, uf, pais, cep, moradores );
				
				enderecos.add(endereco);
				
			}
		} catch (SQLException e) {
			enderecos = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return enderecos;
	}

	@Override
	public List<Pessoa> recuperarMoradoresEndereco(int idEndereco) {
		List<Pessoa> moradores = new ArrayList<Pessoa>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_MORADORES_POR_ENDERECO);
			ps.clearParameters();
			ps.setInt(1, idEndereco);
			rs = ps.executeQuery();
			while(rs.next()){

				Pessoa pessoa = new Pessoa();
				pessoa.setIdPessoa(rs.getInt("EP.idPessoa"));
				pessoa.setNome(rs.getString("P.nome"));
				
				moradores.add(pessoa);
				
			}
		} catch (SQLException e) {
			moradores = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return moradores;
	}

	@Override
	public Pessoa recuperarPessoaPorLogin(String login) {
		Pessoa pessoa = null;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_PESSOA_POR_LOGIN);
			ps.clearParameters();
			ps.setString(1, login);
			rs = ps.executeQuery();
			if(rs.next()){

				pessoa = recuperarPessoaPorId(rs.getInt("usuarioDe"));
				
			}
		} catch (SQLException e) {
			pessoa = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}
		return pessoa;
	}

	@Override
	public boolean editarPessoa(Pessoa pessoa) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean editado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EDITA_PESSOA);
			ps.clearParameters();

			ps.setString(1,pessoa.getNome());
			ps.setString(2,pessoa.getSitCivil());
			ps.setString(3,pessoa.getSexo());
			ps.setDate(4,(Date) pessoa.getDataNascimento());
			ps.setString(5,pessoa.getCpf());
			ps.setString(6,pessoa.getRg());
			ps.setString(7,pessoa.getTelefone());
			ps.setString(8,pessoa.getCelular());
			ps.setString(9,pessoa.getEmail());
			ps.setString(10,pessoa.getPagPessoal());
			ps.setString(11,pessoa.getMsgInst());
			ps.setBoolean(12,true);
			ps.setInt(13,pessoa.getIdPessoa());

			ps.executeUpdate();


			List<Endereco> enderecos = gravaEndereco(pessoa.getEndereco());
			if(enderecos == null){
				editado = false;
			}else{

				if(excluiEnderecosPessoa(pessoa, enderecos) && gravaEnderecosPessoa(pessoa, enderecos)){

					if(editarExperiencias(pessoa, pessoa.getCompetenciasExperiencia())){
						
						editado = editaUsuario(pessoa,pessoa.getUsuario());
					
					}else{
						editado = false;
					}
				}else{
					editado = false;
				}
			}

			if(editado){
				//Chama commit no final do processo
				mySQLConnection.commit();
				//Habilita auto comit novamente
				mySQLConnection.setAutoCommit(true);
			}else{
				ConnectionManager.rollBack();
			}
		}catch(SQLException e){
			e.printStackTrace();
			editado = false;
			ConnectionManager.rollBack();		
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return editado;
	}

	@Override
	public boolean editaUsuario(Pessoa pessoa, Usuario usuario) {
		boolean editado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EDITA_USUARIO);
			ps.clearParameters();

			ps.setInt(1,pessoa.getIdPessoa());
			ps.setString(2,usuario.getLogin());
			ps.setString(3,usuario.getSenha());
			ps.setString(4,usuario.getTipo().getDescricao());
			ps.setBoolean(5,true);
			ps.setInt(6,usuario.getIdUsuario());

			ps.executeUpdate();

			editado = true;
			
		}catch(SQLException e){
			e.printStackTrace();
			editado = false;
			ConnectionManager.rollBack();	
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return editado;
	}

	@Override
	public boolean editarExperiencias(Pessoa pessoa,
			List<CompetenciaExperiencia> competenciasExperiencia) {
		boolean editado = false;

		for (CompetenciaExperiencia competenciaExperiencia : competenciasExperiencia) {

			if(!editaExperiencias(pessoa, competenciaExperiencia)){
				editado = false;
				break;
			}

			editado = true;
		}

		return editado;
	}

	@Override
	public boolean editaExperiencias(Pessoa pessoa,
			CompetenciaExperiencia competenciaExperiencia) {
		boolean editado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EDITA_EXPERIENCIA);
			ps.clearParameters();

			ps.setInt(1,pessoa.getIdPessoa());
			ps.setInt(2,competenciaExperiencia.getCompetencia().getIdCompetencia());
			ps.setInt(3,competenciaExperiencia.getNivel());
			ps.setInt(4,competenciaExperiencia.getTempoExperiencia());
			ps.setString(5,competenciaExperiencia.getObservacoes());
			ps.setBoolean(6,true);
			ps.setInt(7,competenciaExperiencia.getIdExperiencia());

			ps.executeUpdate();

			editado = true;
			
		}catch(SQLException e){
			e.printStackTrace();
			editado = false;
			ConnectionManager.rollBack();
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return editado;
	}

	@Override
	public boolean excluiEnderecosPessoa(Pessoa pessoa,
			List<Endereco> enderecos) {
		boolean excluido = false;
		
		for (Endereco endereco : enderecos) {
			excluido = excluiEnderecoPessoa(pessoa, endereco);
			if(!excluido){
				break;
			}
		}
		
		return excluido;
	}

	@Override
	public boolean excluiEnderecoPessoa(Pessoa pessoa, Endereco endereco) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean excluido = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			//Desabilita auto-commit
			mySQLConnection.setAutoCommit(false);

			ps = mySQLConnection.prepareStatement(EXCLUIR_ENDERECO_PESSOA);
			ps.clearParameters();

			ps.setInt(1,endereco.getIdEndereco());
			ps.setInt(2,pessoa.getIdPessoa());

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
	public boolean excluirPessoa(Pessoa pessoa) {
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		boolean excluido = false; 

		try {

			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(EXCLUIR_PESSOA);
			ps.clearParameters();

			ps.setBoolean(1, false);
			ps.setInt(2, pessoa.getIdPessoa());

			ps.executeUpdate();

			excluido = true;

		} catch (SQLException e) {
			excluido = false;
			e.printStackTrace();
		}finally	{
			ConnectionManager.closeAll(ps);
		}
		return excluido;
	}

	@Override
	public List<Pessoa> listarPessoas() {
		List<Pessoa> pessoasList = new ArrayList<Pessoa>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_PESSOAS_PARA_LISTAR);
			ps.clearParameters();
			rs = ps.executeQuery();
			while(rs.next()){

				pessoasList.add(recuperarPessoaPorId(rs.getInt("idPessoa")));
				
			}
		} catch (SQLException e) {
			pessoasList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return pessoasList;
	}

	@Override
	public Responsavel recuperarResponsavelCompletoPorId(int idPessoa) {
		Responsavel responsavel = null;
		
		Pessoa pessoa = recuperarPessoaPorId(idPessoa);
		
		List<Projeto> projeto = _repositorioDeProjetos.listarProjetosPorResponsavel(idPessoa);
		
		List<ProjetoAtividade> projetoAtividades = _repositorioDeProjetos.listarProjetosAtividadesPorResponsavel(idPessoa);
		
		List<Usuario> usuariosAprovados = recuperarUsuariosAprovadosPorResponsavel(idPessoa);
		
		List<Competencia> competenciasAprovadas = _repositorioDeCompetencia.recuperarCompetenciasAprovadasPorResponsavel(idPessoa);
		
		List<Feedback> feedbackCriados = _repositorioDeProjetos.recuperarFeedbacksCriadosPorResponsavel(idPessoa);
		
		List<Feedback> feedbacksRecebidos = _repositorioDeProjetos.recuperarFeedbacksRecebidosPorResponsavel(idPessoa);
		
		List<CompetenciaCategoria> competenciasCategoriaAprovadas = _repositorioDeCompetencia.recuperarCompetenciaCategoriasAprovadasPorResponsavel(idPessoa);
		
		responsavel = new Responsavel(pessoa, projeto, projetoAtividades, usuariosAprovados, competenciasAprovadas, feedbackCriados, feedbacksRecebidos, competenciasCategoriaAprovadas);
		
		
		return responsavel;
	}
	
	@Override
	public List<Usuario> recuperarUsuariosAprovadosPorResponsavel(int idPessoa) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(BUSCAR_USUARIOS_APROVADOS_POR_RESPONSAVEL_PARA_LISTAR);
			ps.clearParameters();
			ps.setInt(1, idPessoa);
			rs = ps.executeQuery();
			while(rs.next()){

				usuarios.add(recuperarUsuarioPorPessoa(rs.getInt("usuarioDe")));

			}
		} catch (SQLException e) {
			usuarios = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return usuarios;
	}

	@Override
	public Responsavel recuperarResponsavelSimplesPorId(int idPessoa) {
		Responsavel responsavel = null;
		
		Pessoa pessoa = recuperarPessoaPorId(idPessoa);
		
		responsavel = new Responsavel(pessoa, null, null, null, null, null, null, null);
		
		
		return responsavel;
	}

	@Override
	public boolean atualizaUltimoLoginUsuario(Usuario usuario, java.util.Date novaData) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean atualizado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(ATUALIZAR_ULTIMO_LOGIN_USUARIO);
			ps.clearParameters();

			ps.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(novaData));
			ps.setInt(2,usuario.getIdUsuario());

			ps.executeUpdate();

			atualizado = true;

		}catch(SQLException e){
			e.printStackTrace();
			atualizado = false;
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return atualizado;
	}

	@Override
	public boolean trocarSenhaUsuario(String login, String senhaAntiga,
			String senhaNova) {
		
		Pessoa usuario = recuperarPessoaPorLogin(login);
		
		if(usuario.getUsuario().getSenha().equalsIgnoreCase(senhaAntiga)){
			return trocarSenhaUsuario(login, senhaNova);
		}else{
			return false;
		}
		
	}

	@Override
	public boolean desativaUsuario(String login) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean desativado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(DESATIVA_USUARIO);
			ps.clearParameters();

			ps.setBoolean(1, false);
			ps.setString(2,login);

			ps.executeUpdate();

			desativado = true;

		}catch(SQLException e){
			e.printStackTrace();
			desativado = false;
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return desativado;
	}

	@Override
	public boolean trocaTipoUsuario(Usuario usuario, UsuarioTipo novoTipo) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean atualizado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(ATUALIZAR_TIPO_USUARIO);
			ps.clearParameters();

			ps.setString(1, novoTipo.getDescricao());
			ps.setInt(2, usuario.getIdUsuario());

			ps.executeUpdate();

			atualizado = true;

		}catch(SQLException e){
			e.printStackTrace();
			atualizado = false;
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return atualizado;
	}
	
	@Override
	public boolean verificaLoginExiste(String login) {
		boolean existe = false;
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(VERIFICA_EXISTENCIA_LOGIN);
			ps.clearParameters();
			ps.setString(1, login);
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

	@Override
	public boolean trocarSenhaUsuario(String login, String senhaNova) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean atualizado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(ATUALIZAR_SENHA_POR_LOGIN);
			ps.clearParameters();

			ps.setString(1, senhaNova);
			ps.setString(2,login);

			ps.executeUpdate();

			atualizado = true;

		}catch(SQLException e){
			e.printStackTrace();
			atualizado = false;
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return atualizado;
	}

	
	@Override
	public boolean aprovarUsuario(Usuario usuario,
			Responsavel aprovador) {
		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		boolean aprovado = false;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(APROVAR_USUARIO);
			ps.clearParameters();

			ps.setInt(1,aprovador.getIdPessoa());
			ps.setInt(2,usuario.getIdUsuario());

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
	public List<UsuarioAcesso> recuperarUsuarioAcessosPorTipo(
			UsuarioTipo usuarioTipo) {
		List<UsuarioAcesso> acessosList = new ArrayList<UsuarioAcesso>();
		Connection 			mySQLConnection = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		try {
			mySQLConnection = ConnectionManager.getConexao();
			ps = mySQLConnection.prepareStatement(RECUPERAR_ACESSOS_POR_TIPO);
			ps.clearParameters();
			ps.setString(1, "%" + usuarioTipo.getUsuarioTipo() + "%");
			rs = ps.executeQuery();
			while(rs.next()){

				String descricao = rs.getString("descricao");
				String[] niveisDeAcessoString = rs.getString("niveisDeAcesso").split("-");
				int[] niveisDeAcesso = new int[niveisDeAcessoString.length];
				for (int i = 0; i < niveisDeAcessoString.length; i++) {
					niveisDeAcesso[i] = Integer.parseInt(niveisDeAcessoString[i]);
				}
				UsuarioAcesso usuarioAcesso = new UsuarioAcesso(descricao, niveisDeAcesso);
				acessosList.add(usuarioAcesso);
				
			}
		} catch (SQLException e) {
			acessosList = null;
			e.printStackTrace();
		}finally {
			ConnectionManager.closeAll(ps,rs);
		}

		return acessosList;
	}

	@Override
	public boolean gravaUsuarioAcesso(UsuarioAcesso acesso) {
		boolean gravado = false;

		Connection mySQLConnection = null;
		PreparedStatement ps = null;

		try{
			mySQLConnection = ConnectionManager.getConexao();

			ps = mySQLConnection.prepareStatement(GRAVAR_USUARIO_ACESSO);
			ps.clearParameters();

			ps.setString(1,acesso.getDescricao());
			String niveisDeAcesso = "";
			for (int nivel : acesso.getNiveisDeAcesso()) {
				if(nivel == acesso.getNiveisDeAcesso()[acesso.getNiveisDeAcesso().length-1]){
					niveisDeAcesso = niveisDeAcesso.concat(nivel + "");
				}else{
					niveisDeAcesso = niveisDeAcesso.concat(nivel + "-");
				}
			}
			ps.setString(2,niveisDeAcesso);

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
	
}
