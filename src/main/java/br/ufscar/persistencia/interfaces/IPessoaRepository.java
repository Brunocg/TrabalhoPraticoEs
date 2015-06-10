package br.ufscar.persistencia.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ufscar.dominio.Usuario;

public interface IPessoaRepository {

	int novoId();
	Usuario buscarPorId(int usuarioId);
	Usuario buscarPorLogin(String login);
	void inserir(Usuario usuario);	
	void editar(Usuario usuario);
	boolean excluir(Usuario usuario);
	Page<Usuario> listar(Pageable pageable);
}
