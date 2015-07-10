package br.ufscar.dominio.interfaces;

import java.util.Date;
import java.util.List;

import br.ufscar.dominio.CompetenciaExperiencia;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Responsavel;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.UsuarioAcesso;
import br.ufscar.dominio.UsuarioTipo;

public interface IPessoaRepository {

	public abstract Pessoa recuperarPessoaPorId(int pessoaId);
	public abstract Pessoa recuperarPessoaPorLogin(String login);

	public abstract Usuario recuperarUsuarioPorPessoa(int idPessoa);
	
	public abstract List<Endereco> recuperarEnderecosPorPessoa(int idPessoa);
	public abstract List<Pessoa> recuperarMoradoresEndereco(int idEndereco);
	
	public abstract List<CompetenciaExperiencia> recuperarExperienciaPorPessoa(int idPessoa);

	public abstract Responsavel recuperarResponsavelCompletoPorId(int idPessoa);
	public abstract Responsavel recuperarResponsavelSimplesPorId(int idPessoa);
	
	public abstract List<Usuario> recuperarUsuariosAprovadosPorResponsavel(int idPessoa);

	public abstract boolean editarPessoa(Pessoa pessoa);
	
	public abstract boolean editarExperiencias(Pessoa pessoa, List<CompetenciaExperiencia> competenciasExperiencia);
	public abstract boolean editaExperiencias(Pessoa pessoa, CompetenciaExperiencia competenciaExperiencia);

	public abstract boolean editaUsuario(Pessoa pessoa, Usuario usuario) ;

	public abstract boolean excluirPessoa(Pessoa pessoa);
	
	public abstract boolean excluiEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos);
	public abstract boolean excluiEnderecoPessoa(Pessoa pessoa, Endereco endereco);
	
	public abstract List<Pessoa> listarPessoas();
	
	public abstract boolean gravaPessoa(Pessoa pessoa);
	public abstract boolean gravaPessoaBasico(Pessoa pessoa);
	
	public abstract boolean gravaUsuario(Pessoa pessoa, Usuario usuario);
	
	public abstract boolean gravaUsuarioAcesso(UsuarioAcesso acesso);
	
	public abstract boolean gravaExperiencias(Pessoa pessoa, List<CompetenciaExperiencia> competenciasExperiencia);
	public abstract boolean gravaExperiencias(Pessoa pessoa, CompetenciaExperiencia competenciaExperiencia);
	
	public abstract boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos);
	public abstract boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco);
	public abstract List<Endereco> gravaEndereco(List<Endereco> enderecos);
	public abstract int gravaEndereco(Endereco endereco);
	
	public abstract boolean atualizaUltimoLoginUsuario(Usuario usuario, Date novaData);

	public abstract boolean trocarSenhaUsuario(String login, String senhaAntiga, String senhaNova);
	public abstract boolean trocarSenhaUsuario(String login, String senhaNova);
	public abstract boolean trocarTipoUsuario(Usuario usuario, UsuarioTipo novoTipo);

	public abstract boolean desativaUsuario(String login);
	
	public abstract boolean aprovarUsuario(Usuario usuario, Responsavel aprovador);

	public abstract boolean verificaLoginExiste(String login);
	public abstract boolean verificarExistenciaAcesso(UsuarioAcesso usuarioAcesso);

	public abstract List<UsuarioAcesso> recuperarUsuarioAcessosPorTipo(int codigoTipoUsuario);
}
