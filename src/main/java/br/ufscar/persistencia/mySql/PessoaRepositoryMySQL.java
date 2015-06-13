package br.ufscar.persistencia.mySql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;

import br.ufscar.dao.ConnectionManager;
import br.ufscar.dominio.CompetenciaExperiencia;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.interfaces.IPessoaRepository;


//@Repository
public class PessoaRepositoryMySQL implements IPessoaRepository  {

	private static final String GRAVAR_PESSOA = "INSERT INTO Pessoa (nome,sitCivil,sexo,dataNascimento,CPF,RG,telefone,celular,email,pagPessoal,msgInst,estado,ts) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_PESSOA_BASICO = "INSERT INTO Pessoa (nome,dataNascimento,CPF,RG,email,estado,ts) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_ENDERECO = "INSERT INTO Endereco (rua,bairro,numero,cidade,uf,pais,cep,estado,ts) VALUES (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_ENDERECO_PESSOA = "INSERT INTO EnderecoPessoa (idEndereco,idPessoa,ts) VALUES (?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_EXPERIENCIA = "INSERT INTO CompetenciaExperiencia (idPessoa, idCompetencia, nivel, tempoExp,observacoes,estado,ts) VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	private static final String GRAVAR_USUARIO = "INSERT INTO Usuario (usuarioDe,login,senha,usuarioTipo,estado,ts) VALUES (?,?,?,?,?,CURRENT_TIMESTAMP)";

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
			ps.setDate(2,(Date) pessoa.getDataNascimento());
			ps.setString(3,pessoa.getCpf());
			ps.setString(4,pessoa.getRg());
			ps.setString(5,pessoa.getEmail());
			ps.setBoolean(6,true);

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
			ps.setDate(4,(Date) pessoa.getDataNascimento());
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
	public boolean gravaUsuario(Pessoa pessoa, Usuario usuario) throws SQLException {
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
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			List<CompetenciaExperiencia> competenciasExperiencia) throws SQLException {

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
			CompetenciaExperiencia competenciaExperiencia) throws SQLException {
		
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
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos) throws SQLException {
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
	public boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco) throws SQLException {

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
		}finally{
			ConnectionManager.closeAll(ps);
		}

		return gravado;
	}

	@Override
	public List<Endereco> gravaEndereco(List<Endereco> enderecos) throws SQLException {

		List<Endereco> enderecosComId = new ArrayList<Endereco>();

		for (Endereco endereco : enderecos) {
			int idEndereco = gravaEndereco(endereco);

			if(idEndereco != 0){
				endereco.setIdEndereco(idEndereco);
				enderecosComId.add(endereco);
			}else{
				enderecosComId = null;
				break;
			}
		}


		return enderecosComId;
	}

	@Override
	public int gravaEndereco(Endereco endereco) throws SQLException {

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
		}finally{
			ConnectionManager.closeAll(ps,rs);
		}

		return idEndereco;
	}

	@Override
	public Pessoa buscarPorId(int pessoaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pessoa buscarPorLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editar(Pessoa pessoa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean excluir(Pessoa pessoa) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<Pessoa> listar(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
