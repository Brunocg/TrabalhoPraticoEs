package br.ufscar.aplicacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufscar.consulta.LoginData;
import br.ufscar.consulta.UsuarioData;
import br.ufscar.dominio.Usuario;
import br.ufscar.dominio.UsuarioRepository;


@Service
public class UsuarioApplicationService {
	
	@Autowired
	private UsuarioRepository repositorio;

	public UsuarioData obterDataPeloId(int usuarioId) {
		
		Usuario usuario = repositorio.buscarPorId(usuarioId);
		
	    return new UsuarioData(usuario.getIdUsuario(), usuario.getLogin(), usuario.getSenha(), usuario.getUltimoLogin(), usuario.isEstado(), usuario.getTs());
	} 

	public int inserir(UsuarioData usuarioData) {
		Usuario usuario = new Usuario(repositorio.novoId(), usuarioData.getLogin(), usuarioData.getSenha(), usuarioData.getUltimoLogin(), usuarioData.isEstado(), usuarioData.getTs(), null, null);
		
		repositorio.inserir(usuario);
		
		return usuario.getIdUsuario();
	}

	public Page<UsuarioData> listar(Pageable pageable) {
		
		List<UsuarioData> result = new ArrayList<UsuarioData>();
		for (Usuario usuario : repositorio.listar(pageable)) {
			result.add(new UsuarioData(usuario.getIdUsuario(), usuario.getLogin(), usuario.getSenha(), usuario.getUltimoLogin(), usuario.isEstado(), usuario.getTs()));		
		}
		
		Page<UsuarioData> usuarioPaginado = new PageImpl<UsuarioData> (result, pageable, result.size());
		
		return usuarioPaginado;			
	}

	public boolean excluir(int usuarioId) {
		Usuario usuario = repositorio.buscarPorId(usuarioId);
		return repositorio.excluir(usuario);
	}

	public int editar(UsuarioData usuarioData) {
		
		Usuario usuario = new Usuario(usuarioData.getIdUsuario(), usuarioData.getLogin(), usuarioData.getSenha(), usuarioData.getUltimoLogin(), usuarioData.isEstado(), usuarioData.getTs(), null, null);
		
		repositorio.editar(usuario);
		
		return usuario.getIdUsuario();
	}

	public boolean loginValido(LoginData usuario) {
		return repositorio.buscarPorLogin(usuario.getLogin()).validaLogin(usuario.getSenha());
	}

}
