package br.ufscar.dominio.interfaces;

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

	public abstract boolean editarPessoa(Pessoa pessoa);
	
	public abstract boolean editarExperiencias(Pessoa pessoa, List<CompetenciaExperiencia> competenciasExperiencia);
	public abstract boolean editaExperiencias(Pessoa pessoa, CompetenciaExperiencia competenciaExperiencia);

	public abstract boolean editaUsuario(Pessoa pessoa, Usuario usuario) ;

	public abstract boolean excluirPessoa(Pessoa pessoa);
	
	public abstract Page<Pessoa> listarPessoas(Pageable pageable);
	
	public abstract boolean gravaPessoa(Pessoa pessoa);
	public abstract boolean gravaPessoaBasico(Pessoa pessoa);
	
	public abstract boolean gravaUsuario(Pessoa pessoa, Usuario usuario);
	
	public abstract boolean gravaExperiencias(Pessoa pessoa, List<CompetenciaExperiencia> competenciasExperiencia);
	public abstract boolean gravaExperiencias(Pessoa pessoa, CompetenciaExperiencia competenciaExperiencia);
	
	public abstract boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos);
	public abstract boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco);
	public abstract List<Endereco> gravaEndereco(List<Endereco> enderecos);
	public abstract int gravaEndereco(Endereco endereco);
	
	public abstract boolean excluiEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos);
	public abstract boolean excluiEnderecoPessoa(Pessoa pessoa, Endereco endereco);
	
}
