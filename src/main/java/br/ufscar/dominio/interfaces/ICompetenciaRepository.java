package br.ufscar.dominio.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.ufscar.dominio.Competencia;
import br.ufscar.dominio.CompetenciaCategoria;

public interface ICompetenciaRepository {

		public abstract boolean verificaExostenciaCompetencia(
				Competencia competencia);

		public abstract boolean gravaCompetencia(Competencia competencia);

		public abstract boolean gravaCompetenciaCategoria(
				CompetenciaCategoria competenciaCategoria);

		public abstract boolean gravaCompetencias(
				CompetenciaCategoria competenciaCategoria,
				List<Competencia> competencias) throws SQLException;

		public abstract boolean gravaRelacaoCategoriaCompetencia(
				Competencia competencia, CompetenciaCategoria competenciaCategoria)
				throws SQLException;

		public abstract boolean gravaSubCategorias(
				CompetenciaCategoria competenciaCategoria,
				List<CompetenciaCategoria> subCategorias) throws SQLException;

		public abstract boolean gravaRelacaoCategoriSubCategoria(
				CompetenciaCategoria competenciaCategoria,
				CompetenciaCategoria subCategoria) throws SQLException;

		public abstract CompetenciaCategoria recuperarCategoriaPeloNome(String nome);

		public abstract List<CompetenciaCategoria> recuperarSubCategoriasPorCategoria(
				CompetenciaCategoria categoria);

		public abstract List<Competencia> recuperarCompetenciasPorCategoria(
				CompetenciaCategoria categoria);

		public abstract boolean verificaExostenciaCompetenciaCategoria(
				CompetenciaCategoria competenciaCategoria);

		public abstract Competencia recuperarCompetenciaPeloNomeECategoria(
				String nome, CompetenciaCategoria competenciaCategoria);

}
