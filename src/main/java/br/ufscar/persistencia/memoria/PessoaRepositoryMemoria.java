package br.ufscar.persistencia.memoria;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.ufscar.dominio.CompetenciaExperiencia;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.interfaces.IPessoaRepository;


@Repository
public class PessoaRepositoryMemoria implements IPessoaRepository  {

	@Override
	public boolean gravaPessoa(Pessoa pessoa){
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaUsuario(Pessoa pessoa, Usuario usuario) throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			List<CompetenciaExperiencia> competenciasExperiencia) throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			CompetenciaExperiencia competenciaExperiencia) throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos) throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco) throws SQLException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<Endereco> gravaEndereco(List<Endereco> enderecos) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int gravaEndereco(Endereco endereco) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int novoId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Pessoa buscarPorId(int pessoaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pessoa buscarPorLogin(String login) {
		// TODO Auto-generated method stub
		return new Pessoa(0, login, login, login, null, login, login, null, login, login, login, login, login, new Usuario(0, "Bruno", "teste", null, false, null, null, null), false, null, null);
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
