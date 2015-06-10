package br.ufscar.dominio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepository {

	int novoId();
	Usuario buscarPorId(int usuarioId);
	Usuario buscarPorLogin(String login);
	void inserir(Usuario usuario);	
	void editar(Usuario usuario);
	boolean excluir(Usuario usuario);
	Page<Usuario> listar(Pageable pageable);
}
