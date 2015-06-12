package br.ufscar.dominio;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import br.ufscar.persistencia.mySql.CompetenciaRepositoryMySQL;

public class CompetenciaTeste {

	CompetenciaRepositoryMySQL _repositorioCompetencias = new CompetenciaRepositoryMySQL();
	
	@Test
	public void cadastrarCompetencia() {
		
//		String nome;
//		boolean estado;
//		Date ts;
//		CompetenciaCategoria competenciaCategoria;
//		Responsavel  aprovadorPor;
//		
//		Competencia competencia = new Competencia(0, nome, estado, ts, competenciaCategoria, aprovadorPor);
//		
//		assertEquals(true, _repositorioCompetencias.gravaCompetencia(competencia ));
	}

}
