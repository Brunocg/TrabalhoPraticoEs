package br.ufscar.dominio;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import br.ufscar.dominio.interfaces.IPessoaRepository;
import br.ufscar.persistencia.mySql.PessoaRepositoryMySQL;

public class PessoaTeste {

	IPessoaRepository _repositorioPessoa = new PessoaRepositoryMySQL();
	
	@Test
	public void gravaPessoaBasico() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Teste");
		pessoa.setSitCivil("S");
		pessoa.setSexo("M");
		pessoa.setDataNascimento(new Date());
		pessoa.setCpf("teste");
		pessoa.setRg("teste");
		pessoa.setTelefone("teste");
		pessoa.setCelular("teste");
		pessoa.setEmail("teste");
		pessoa.setPagPessoal("teste");
		pessoa.setMsgInst("teste");
		Usuario usuario = new Usuario(0, "login", "senha", null, true, null, null, UsuarioTipo.COLABORADOR);
		pessoa.setUsuario(usuario);

		assertEquals(true, _repositorioPessoa.gravaPessoaBasico(pessoa));
	}

}
