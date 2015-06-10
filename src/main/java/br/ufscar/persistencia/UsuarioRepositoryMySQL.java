package br.ufscar.persistencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.UsuarioRepository;

public class UsuarioRepositoryMySQL implements UsuarioRepository {

	@Override
	public int novoId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Usuario buscarPorId(int usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inserir(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editar(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean excluir(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Page<Usuario> listar(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
