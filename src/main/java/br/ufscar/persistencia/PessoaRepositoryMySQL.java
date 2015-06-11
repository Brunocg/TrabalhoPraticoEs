package br.ufscar.persistencia;

import org.springframework.data.domain.Page;

import br.ufscar.dominio.Pessoa;
import br.ufscar.dominio.interfaces.IPessoaRepository;

public class PessoaRepositoryMySQL implements IPessoaRepository {

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
		return null;
	}

	@Override
	public void inserir(Pessoa pessoa) {
		// TODO Auto-generated method stub
		
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
	public Page<Pessoa> listar(org.springframework.data.domain.Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
