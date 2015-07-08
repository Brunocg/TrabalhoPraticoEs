package br.ufscar.aplicacao;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.ufscar.consulta.LoginData;
import br.ufscar.consulta.PessoaData;
import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.interfaces.IPessoaRepository;


@Service
@Component
public class PessoaApplicationService {
	
	@Autowired
	private IPessoaRepository repositorio;
	private Mapper mapper = new DozerBeanMapper();

	public PessoaData obterDataPeloId(int pessoaId) {		    
	    return mapper.map(repositorio.recuperarPessoaPorId(pessoaId), PessoaData.class);
	} 

	public int inserir(PessoaData pessoaData) {
		
		Pessoa pessoa = mapper.map(pessoaData, Pessoa.class);
		
		repositorio.gravaPessoaBasico(pessoa);
		
		return pessoa.getIdPessoa();
	}

	public List<PessoaData> listar() {		
		List<PessoaData> result = new ArrayList<PessoaData>();
		
		for (Pessoa pessoa : repositorio.listarPessoas()) 
			result.add(mapper.map(pessoa, PessoaData.class));		
		
		return result;			
	}

	public boolean excluir(int pessoaId) {
		return repositorio.excluirPessoa(repositorio.recuperarPessoaPorId(pessoaId));
	}

	public int editar(PessoaData pessoaData) {
		Pessoa pessoa = mapper.map(pessoaData, Pessoa.class);
		
		repositorio.editarPessoa(pessoa);
		
		return pessoa.getIdPessoa();
	}

	public boolean loginValido(LoginData login) {
		Pessoa pessoa = repositorio.recuperarPessoaPorLogin(login.getLogin());
		return (pessoa != null && pessoa.getUsuario() != null)?pessoa.getUsuario().validaLogin(login.getSenha()):false;
	}

}
