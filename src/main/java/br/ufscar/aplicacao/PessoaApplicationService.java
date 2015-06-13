package br.ufscar.aplicacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.ufscar.consulta.EnderecoData;
import br.ufscar.consulta.LoginData;
import br.ufscar.consulta.PessoaData;
import br.ufscar.consulta.UsuarioData;
import br.ufscar.dominio.Endereco;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.interfaces.IPessoaRepository;


@Service
@Component
public class PessoaApplicationService {
	
	@Autowired
	private IPessoaRepository repositorio;

	Pessoa getPessoa(PessoaData pessoaData) {
		List<Endereco> enderecos = new ArrayList<Endereco>();
		
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
		
		Usuario usuario = new Usuario(pessoaData.getUsuario().getIdUsuario(), 
									  pessoaData.getUsuario().getLogin(), 
									  pessoaData.getUsuario().getSenha(), 
									  pessoaData.getUsuario().getUltimoLogin(), 
									  pessoaData.getUsuario().isEstado(), 
									  pessoaData.getUsuario().getTs(), 
									  null, 
									  null);
		
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
						  usuario, 
						  pessoaData.isEstado(), 
						  pessoaData.getTs(), 
						  null);	
	}
	
	PessoaData getPessoaData(Pessoa pessoa) {
		List<EnderecoData> enderecos = new ArrayList<EnderecoData>();
		
		for (Endereco endereco: pessoa.getEndereco())
			enderecos.add(new EnderecoData(endereco.getIdEndereco(),
										   endereco.getRua(), 
										   endereco.getBairro(), 
										   endereco.getNumero(), 
										   endereco.getCidade(), 
										   endereco.getUf(), 
										   endereco.getPais(), 
										   endereco.getCep()));
		
		UsuarioData usuario = new UsuarioData(pessoa.getUsuario().getIdUsuario(), 
											  pessoa.getUsuario().getLogin(), 
											  pessoa.getUsuario().getSenha(), 
											  pessoa.getUsuario().getUltimoLogin(), 
											  pessoa.getUsuario().isEstado(), 
											  pessoa.getUsuario().getTs());
		
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
	    					  usuario, 
	    					  pessoa.isEstado(), 
	    					  pessoa.getTs());	
	}
	
	public PessoaData obterDataPeloId(int pessoaId) {
	    return getPessoaData(repositorio.recuperarPessoaPorId(pessoaId));
	} 

	public int inserir(PessoaData pessoaData) {
		
		Pessoa pessoa = getPessoa(pessoaData);
		
		repositorio.gravaPessoa(pessoa);
		
		return pessoa.getIdPessoa();
	}

	public Page<PessoaData> listar(Pageable pageable) {		
		List<PessoaData> result = new ArrayList<PessoaData>();
		
		for (Pessoa pessoa : repositorio.listar(pageable)) {
			result.add(getPessoaData(pessoa));		
		}
		
		Page<PessoaData> pessoaPaginado = new PageImpl<PessoaData> (result, pageable, result.size());
		
		return pessoaPaginado;			
	}

	public boolean excluir(int pessoaId) {
		return repositorio.excluir(repositorio.recuperarPessoaPorId(pessoaId));
	}

	public int editar(PessoaData pessoaData) {
		Pessoa pessoa = getPessoa(pessoaData);
		
		repositorio.editar(pessoa);
		
		return pessoa.getIdPessoa();
	}

	public boolean loginValido(LoginData login) {
		Usuario usuario = repositorio.recuperarPessoaPorLogin(login.getLogin()).getUsuario();
		return (usuario != null)?usuario.validaLogin(login.getSenha()):false;
	}

}
