package br.ufscar.persistencia.memoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.ufscar.dominio.CompetenciaExperiencia;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.interfaces.IPessoaRepository;


@Repository
public class PessoaRepositoryMemoria implements IPessoaRepository  {
	HashMap<Integer, Pessoa> pessoas = new HashMap<Integer, Pessoa>();
	
	@Override
	public boolean gravaPessoa(Pessoa pessoa){
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaUsuario(Pessoa pessoa, Usuario usuario) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			List<CompetenciaExperiencia> competenciasExperiencia) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			CompetenciaExperiencia competenciaExperiencia) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<Endereco> gravaEndereco(List<Endereco> enderecos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int gravaEndereco(Endereco endereco) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Pessoa recuperarPessoaPorId(int pessoaId) {
		return pessoas.get(pessoaId);
	}

	@Override
	public Pessoa recuperarPessoaPorLogin(String login) {
		for (Pessoa pessoa: pessoas.values())
			if (pessoa.getUsuario() != null && pessoa.getUsuario().getLogin() != null && pessoa.getUsuario().getLogin().equals(login))
				return pessoa;
		
		return null;
	}
	
	@Override
	public boolean editarPessoa(Pessoa pessoa) {
		pessoas.put(pessoa.getIdPessoa(), pessoa);
		return true;
	}

	@Override
	public boolean excluirPessoa(Pessoa pessoa) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Pessoa> listarPessoas() {		
		return new ArrayList<Pessoa>(pessoas.values());
	}

	@Override
	public boolean gravaPessoaBasico(Pessoa pessoa) {
		pessoa.setIdPessoa((int) UUID.randomUUID().getMostSignificantBits());
		pessoas.put(pessoa.getIdPessoa(), pessoa);
		return true;
	}

	@Override
	public List<Endereco> recuperarEnderecosPorPessoa(int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pessoa> recuperarMoradoresEndereco(int idEndereco) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario recuperarUsuarioPorPessoa(int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompetenciaExperiencia> recuperarExperienciaPorPessoa(
			int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean excluiEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluiEnderecoPessoa(Pessoa pessoa, Endereco endereco) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editarExperiencias(Pessoa pessoa,
			List<CompetenciaExperiencia> competenciasExperiencia)
			 {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editaExperiencias(Pessoa pessoa,
			CompetenciaExperiencia competenciaExperiencia)  {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editaUsuario(Pessoa pessoa, Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

}
