package br.ufscar.dominio.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ufscar.dominio.Pessoa;

public interface IPessoaRepository {

	int novoId();
	Pessoa buscarPorId(int pessoaId);
	Pessoa buscarPorLogin(String login);
	void inserir(Pessoa pessoa);	
	void editar(Pessoa pessoa);
	boolean excluir(Pessoa pessoa);
	Page<Pessoa> listar(Pageable pageable);
}
