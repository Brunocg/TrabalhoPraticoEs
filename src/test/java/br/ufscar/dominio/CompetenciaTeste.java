package br.ufscar.dominio;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import br.ufscar.dao.ConnectionManager;
import br.ufscar.dominio.interfaces.ICompetenciaRepository;
import br.ufscar.persistencia.mySql.CompetenciaRepositoryMySQL;

public class CompetenciaTeste {

	ICompetenciaRepository _repositorioCompetencias = new CompetenciaRepositoryMySQL();
	
	@Test
	public void cadastrarCompetencia() {
		
		int idCategoria = 0;
		String nome = "Categoria 1";
		boolean estado = false;
		Date ts = new Date();
		List<Competencia> competencias = null;
		List<CompetenciaCategoria> subCategorias = null;
		Responsavel aprovadoPor = null;
		
		CompetenciaCategoria competenciaCategoria = new CompetenciaCategoria(idCategoria, nome, estado, ts, competencias, subCategorias, aprovadoPor);
		
		assertEquals(true, _repositorioCompetencias.gravaCompetenciaCategoria(competenciaCategoria));
	
		ConnectionManager.closeConexao();
	}
	
	@Test
	public void cadastrarCompetenciaCategoriaSemSubSemComp() {
		
		int idCategoria = 0;
		String nome = "Categoria 2";
		boolean estado = false;
		Date ts = new Date();
		List<Competencia> competencias = null;
		List<CompetenciaCategoria> subCategorias = null;
		Responsavel aprovadoPor = null;
		
		CompetenciaCategoria competenciaCategoria = new CompetenciaCategoria(idCategoria, nome, estado, ts, competencias, subCategorias, aprovadoPor);
		
		assertEquals(true, _repositorioCompetencias.gravaCompetenciaCategoria(competenciaCategoria));
	
		ConnectionManager.closeConexao();
	}
	
	@Test
	public void cadastrarCompetenciaCategoriaSemSubCom1Comp() {
		
		int idCategoria = 0;
		String nome = "Categoria 3";
		boolean estado = false;
		Date ts = new Date();
		List<Competencia> competencias = new ArrayList<Competencia>();
		List<CompetenciaCategoria> subCategorias = null;
		Responsavel aprovadoPor = null;
		
		CompetenciaCategoria competenciaCategoria = new CompetenciaCategoria(idCategoria, nome, estado, ts, null, subCategorias, aprovadoPor);
		int idCompetencia = 0;
		String nomeComp = "Competencia 1";
		boolean estadoComp = false;
		Date tsComp = new Date();
		Responsavel aprovadorPor = null;
		competencias.add(new Competencia(idCompetencia, nomeComp, estadoComp, tsComp, competenciaCategoria, aprovadorPor));
		
		competenciaCategoria.setCompetencias(competencias);
		
		assertEquals(true, _repositorioCompetencias.gravaCompetenciaCategoria(competenciaCategoria));
		
		ConnectionManager.closeConexao();
	}
	
	@Test
	public void cadastrarCompetenciaCategoriaCom1SubSemComp() {
		
		int idCategoria = 0;
		String nome = "Categoria 4";
		boolean estado = false;
		Date ts = new Date();
		List<Competencia> competencias = null;
		List<CompetenciaCategoria> subCategorias = new ArrayList<CompetenciaCategoria>();
		Responsavel aprovadoPor = null;
		
		CompetenciaCategoria competenciaCategoria = new CompetenciaCategoria(idCategoria, nome, estado, ts, competencias, null, aprovadoPor);
		
		subCategorias.add(new CompetenciaCategoria(1, "Categoria 1", false, new Date(), null, null, null));
		
		competenciaCategoria.setSubCategorias(subCategorias);
		
		assertEquals(true, _repositorioCompetencias.gravaCompetenciaCategoria(competenciaCategoria));
		
		ConnectionManager.closeConexao();
	}
	
	@Test
	public void cadastrarCompetenciaCategoriaCom2SubCom2Comp() {
		
		int idCategoria = 0;
		String nome = "Categoria 5";
		boolean estado = false;
		Date ts = new Date();
		List<Competencia> competencias = new ArrayList<Competencia>();
		List<CompetenciaCategoria> subCategorias = new ArrayList<CompetenciaCategoria>();
		Responsavel aprovadoPor = null;
		
		CompetenciaCategoria competenciaCategoria = new CompetenciaCategoria(idCategoria, nome, estado, ts, null, null, aprovadoPor);
		
		subCategorias.add(new CompetenciaCategoria(2, "Categoria 2", false, new Date(), null, null, null));//existente
		subCategorias.add(new CompetenciaCategoria(0, "Categoria 6", false, new Date(), null, null, null));//nova
		
		competencias.add(new Competencia(5, "Competencia 1", false, new Date(), null, null));//existente
		competencias.add(new Competencia(0, "Competencia 2", false, new Date(), null, null));//nova
		
		competenciaCategoria.setSubCategorias(subCategorias);
		competenciaCategoria.setCompetencias(competencias);
		
		assertEquals(true, _repositorioCompetencias.gravaCompetenciaCategoria(competenciaCategoria));
		
		ConnectionManager.closeConexao();
	}
	
	

}
