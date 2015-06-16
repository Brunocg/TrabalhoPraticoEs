package br.ufscar.dominio.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ufscar.dominio.CompetenciaExperiencia;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Usuario;

public interface IPessoaRepository {

	public abstract Pessoa recuperarPessoaPorId(int pessoaId);
	public abstract Pessoa recuperarPessoaPorLogin(String login);

	public abstract Usuario recuperarUsuarioPorPessoa(int idPessoa);
	
	public abstract List<Endereco> recuperarEnderecosPorPessoa(int idPessoa);
	public abstract List<Pessoa> recuperarMoradoresEndereco(int idEndereco);
	
	public abstract List<CompetenciaExperiencia> recuperarExperienciaPorPessoa(int idPessoa);

	public abstract void editar(Pessoa pessoa);
	
	public abstract boolean excluir(Pessoa pessoa);
	
	public abstract Page<Pessoa> listarPessoas(Pageable pageable);
	
	public abstract boolean gravaPessoa(Pessoa pessoa);
	public abstract boolean gravaPessoaBasico(Pessoa pessoa);
	
	public abstract boolean gravaUsuario(Pessoa pessoa, Usuario usuario) throws SQLException;
	
	public abstract boolean gravaExperiencias(Pessoa pessoa, List<CompetenciaExperiencia> competenciasExperiencia) throws SQLException;
	public abstract boolean gravaExperiencias(Pessoa pessoa, CompetenciaExperiencia competenciaExperiencia) throws SQLException;
	
	public abstract boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos) throws SQLException;
	public abstract boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco) throws SQLException;
	public abstract List<Endereco> gravaEndereco(List<Endereco> enderecos) throws SQLException;
	public abstract int gravaEndereco(Endereco endereco) throws SQLException;
	
}
