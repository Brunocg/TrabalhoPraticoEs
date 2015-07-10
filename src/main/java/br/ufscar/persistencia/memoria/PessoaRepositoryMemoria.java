package br.ufscar.persistencia.memoria;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.ufscar.dominio.CompetenciaExperiencia;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Responsavel;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.UsuarioAcesso;
import br.ufscar.dominio.UsuarioTipo;
import br.ufscar.dominio.interfaces.IPessoaRepository;


@Repository
public class PessoaRepositoryMemoria implements IPessoaRepository  {
	HashMap<Integer, Pessoa> pessoas = new HashMap<Integer, Pessoa>();
	HashMap<Integer, Endereco> enderecos = new HashMap<Integer, Endereco>();
	
	@Override
	public boolean gravaPessoa(Pessoa pessoa){
		pessoas.put(pessoa.getIdPessoa(), pessoa);
		return true;
	}

	@Override
	public boolean gravaUsuario(Pessoa pessoa, Usuario usuario) {
		pessoas.get(pessoa.getIdPessoa()).setUsuario(usuario);
		return true;
	}

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			List<CompetenciaExperiencia> competenciasExperiencia) {
			pessoas.get(pessoa.getIdPessoa()).setCompetenciasExperiencia(competenciasExperiencia);
		return true;
	}

	@Override
	public boolean gravaExperiencias(Pessoa pessoa,
			CompetenciaExperiencia competenciaExperiencia) {
		pessoas.get(pessoa.getIdPessoa()).adicionarCompetenciasExperiencia(competenciaExperiencia);
		return true;
	}

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos) {
		pessoas.get(pessoa.getIdPessoa()).setEndereco(enderecos);
		return true;
	}

	@Override
	public boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco) {
		pessoas.get(pessoa.getIdPessoa()).getEndereco().add(endereco);
		return true;
	}

	@Override
	public List<Endereco> gravaEndereco(List<Endereco> enderecos) {
		for (Endereco endereco: enderecos)
			this.enderecos.put(endereco.getIdEndereco(), endereco);
		
		return new ArrayList<Endereco>(this.enderecos.values());
	}

	@Override
	public int gravaEndereco(Endereco endereco) {
		enderecos.put(endereco.getIdEndereco(), endereco);
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
		pessoas.remove(pessoa.getIdPessoa());
		return true;
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
		return pessoas.get(idPessoa).getEndereco();
	}

	@Override
	public List<Pessoa> recuperarMoradoresEndereco(int idEndereco) {
		return enderecos.get(idEndereco).getMoradores();
	}

	@Override
	public Usuario recuperarUsuarioPorPessoa(int idPessoa) {
		return pessoas.get(idPessoa).getUsuario();
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
		pessoas.get(pessoa.getIdPessoa()).setCompetenciasExperiencia(competenciasExperiencia);
		return false;
	}

	@Override
	public boolean editaExperiencias(Pessoa pessoa,
			CompetenciaExperiencia competenciaExperiencia)  {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean editaUsuario(Pessoa pessoa, Usuario usuario) {
		pessoas.get(pessoa.getIdPessoa()).setUsuario(usuario);
		return true;
	}

	@Override
	public Responsavel recuperarResponsavelCompletoPorId(int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Responsavel recuperarResponsavelSimplesPorId(int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> recuperarUsuariosAprovadosPorResponsavel(int idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gravaUsuarioAcesso(UsuarioAcesso acesso) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean atualizaUltimoLoginUsuario(Usuario usuario, Date novaData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean trocarSenhaUsuario(String login, String senhaAntiga,
			String senhaNova) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean trocarSenhaUsuario(String login, String senhaNova) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean desativaUsuario(String login) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean trocaTipoUsuario(Usuario usuario, UsuarioTipo novoTipo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verificaLoginExiste(String login) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean aprovarUsuario(Usuario usuario, Responsavel aprovador) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UsuarioAcesso> recuperarUsuarioAcessosPorTipo(
			UsuarioTipo usuarioTipo) {
		// TODO Auto-generated method stub
		return null;
	}

}
