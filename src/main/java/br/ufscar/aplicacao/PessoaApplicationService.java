package br.ufscar.aplicacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.ufscar.consulta.EnderecoData;
import br.ufscar.consulta.LoginData;
import br.ufscar.consulta.PessoaData;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.UsuarioTipo;
import br.ufscar.dominio.interfaces.IPessoaRepository;


@Service
@Component
public class PessoaApplicationService {
	
	@Autowired
	private IPessoaRepository repositorio;

	Pessoa getPessoa(PessoaData pessoaData) {
		List<Endereco> enderecos = new ArrayList<Endereco>();
		
		if (pessoaData != null) {
			if (pessoaData.getEndereco() != null)
				for (EnderecoData endereco: pessoaData.getEndereco())
					enderecos.add(new Endereco(endereco.getIdEndereco(),
											   endereco.getRua(), 
											   endereco.getBairro(), 
											   endereco.getNumero(), 
											   endereco.getCidade(), 
											   endereco.getUf(), 
											   endereco.getPais(), 
											   endereco.getCep(), 
											   null));
			
			return new Pessoa(pessoaData.getIdPessoa(), 
							  pessoaData.getNome(), 
							  pessoaData.getSitCivil(), 
							  pessoaData.getSexo(), 
							  pessoaData.getDataNascimento(), 
							  pessoaData.getCpf(), 
							  pessoaData.getRg(), 
							  enderecos, 
							  pessoaData.getTelefone(), 
							  pessoaData.getCelular(), 
							  pessoaData.getEmail(), 
							  pessoaData.getPagPessoal(), 
							  pessoaData.getMsgInst(), 
							  new Usuario(pessoaData.getIdUsuario(), pessoaData.getLogin(), pessoaData.getSenha(), null, false, null, null, UsuarioTipo.COLABORADOR), 
							  pessoaData.isEstado(), 
							  pessoaData.getTs(), 
							  null);	
		}
		
		return null;
	}
	
	PessoaData getPessoaData(Pessoa pessoa) {
		List<EnderecoData> enderecos = new ArrayList<EnderecoData>();
		
		if (pessoa != null) {
			if (pessoa.getEndereco() != null)
				for (Endereco endereco: pessoa.getEndereco())
					enderecos.add(new EnderecoData(endereco.getIdEndereco(),
												   endereco.getRua(), 
												   endereco.getBairro(), 
												   endereco.getNumero(), 
												   endereco.getCidade(), 
												   endereco.getUf(), 
												   endereco.getPais(), 
												   endereco.getCep()));
			
			if (pessoa.getUsuario() != null)
			    return new PessoaData(pessoa.getIdPessoa(), 
			    					  pessoa.getNome(), 
			    					  pessoa.getSitCivil(), 
			    					  pessoa.getSexo(), 
			    					  pessoa.getDataNascimento(), 
			    					  pessoa.getCpf(), 
			    					  pessoa.getRg(), 
			    					  enderecos, 
			    					  pessoa.getTelefone(), 
			    					  pessoa.getCelular(), 
			    					  pessoa.getEmail(), 
			    					  pessoa.getPagPessoal(), 
			    					  pessoa.getMsgInst(), 
			    					  pessoa.getUsuario().getIdUsuario(), 
			    					  pessoa.getUsuario().getLogin(),
			    					  pessoa.getUsuario().getSenha(),
			    					  pessoa.isEstado(), 
			    					  pessoa.getTs());	
		}
		
		return null;
	}
	
	public PessoaData obterDataPeloId(int pessoaId) {
	    return getPessoaData(repositorio.recuperarPessoaPorId(pessoaId));
	} 

	public int inserir(PessoaData pessoaData) {
		
		Pessoa pessoa = getPessoa(pessoaData);
		
		repositorio.gravaPessoaBasico(pessoa);
		
		return pessoa.getIdPessoa();
	}

	public List<PessoaData> listar() {		
		List<PessoaData> result = new ArrayList<PessoaData>();
		
		for (Pessoa pessoa : repositorio.listarPessoas()) 
			result.add(getPessoaData(pessoa));		
		
		return result;			
	}

	public boolean excluir(int pessoaId) {
		return repositorio.excluirPessoa(repositorio.recuperarPessoaPorId(pessoaId));
	}

	public int editar(PessoaData pessoaData) {
		Pessoa pessoa = getPessoa(pessoaData);
		
		repositorio.editarPessoa(pessoa);
		
		return pessoa.getIdPessoa();
	}

	public boolean loginValido(LoginData login) {
		Pessoa pessoa = repositorio.recuperarPessoaPorLogin(login.getLogin());
		return (pessoa != null && pessoa.getUsuario() != null)?pessoa.getUsuario().validaLogin(login.getSenha()):false;
	}

}
