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

	public int novoId();
	public Pessoa buscarPorId(int pessoaId);
	public Pessoa buscarPorLogin(String login);
	public void editar(Pessoa pessoa);
	public boolean excluir(Pessoa pessoa);
	public Page<Pessoa> listar(Pageable pageable);
	
	public boolean gravaPessoa(Pessoa pessoa);
	public boolean gravaUsuario(Pessoa pessoa, Usuario usuario) throws SQLException;
	public boolean gravaExperiencias(Pessoa pessoa,	List<CompetenciaExperiencia> competenciasExperiencia) throws SQLException;
	public boolean gravaExperiencias(Pessoa pessoa, CompetenciaExperiencia competenciaExperiencia) throws SQLException;
	public boolean gravaEnderecosPessoa(Pessoa pessoa, List<Endereco> enderecos) throws SQLException;
	public boolean gravaEnderecosPessoa(Pessoa pessoa, Endereco endereco) throws SQLException;
	public List<Endereco> gravaEndereco(List<Endereco> enderecos) throws SQLException;
	public int gravaEndereco(Endereco endereco) throws SQLException;
}
