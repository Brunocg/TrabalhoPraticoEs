package br.ufscar.dominio.utilitarios;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufscar.dominio.UsuarioAcesso;
import br.ufscar.dominio.interfaces.IPessoaRepository;
import br.ufscar.persistencia.mySql.PessoaRepositoryMySQL;

public class UsuarioAcessoCadastro {

public static enum AcessosDoSistema {
		
		EXEMPLO 			("EXEMPLO", new int[]{1,2,3,4}),
		;
		
		private String nomeAcesso;
		private int[] niveisDeAcesso;

		AcessosDoSistema(String nomeAcesso, int[] niveisDeAcesso) {
			this.nomeAcesso = nomeAcesso;
			this.niveisDeAcesso = niveisDeAcesso;
		}

		public String getNomeAcesso(){
			return nomeAcesso;
		}

		public int[] getNiveisDeAcesso() {
			return niveisDeAcesso;
		}
	}
	
	@Autowired
	private static IPessoaRepository pessoaRepositorio = new PessoaRepositoryMySQL();

	public static void main(String[] args) {
		for (AcessosDoSistema acesso : AcessosDoSistema.values()) {
			UsuarioAcesso usuarioAcesso = transformaEnumObjeto(acesso);
			if(!pessoaRepositorio.verificarExistenciaAcesso(usuarioAcesso)){
				pessoaRepositorio.gravaUsuarioAcesso(usuarioAcesso);
			}
		}
	}

	private static UsuarioAcesso transformaEnumObjeto(AcessosDoSistema acesso) {
		String descricao = acesso.getNomeAcesso();
		int[] niveisDeAcesso = acesso.getNiveisDeAcesso();
		UsuarioAcesso usuarioAcesso = new UsuarioAcesso(descricao, niveisDeAcesso);
		return usuarioAcesso;
	}
	
}
