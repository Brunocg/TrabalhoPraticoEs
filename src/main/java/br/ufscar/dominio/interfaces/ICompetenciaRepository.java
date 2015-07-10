package br.ufscar.dominio.interfaces;

import java.util.List;

import br.ufscar.dominio.Competencia;
import br.ufscar.dominio.CompetenciaCategoria;
import br.ufscar.dominio.Responsavel;

public interface ICompetenciaRepository {

		public abstract boolean verificaExostenciaCompetencia(Competencia competencia);
		public abstract boolean verificaExostenciaCompetenciaCategoria(CompetenciaCategoria competenciaCategoria);

		public abstract boolean gravaCompetencia(Competencia competencia);
		public abstract boolean gravaCompetencias(CompetenciaCategoria competenciaCategoria, List<Competencia> competencias);
		public abstract boolean gravaRelacaoCategoriaCompetencia(Competencia competencia, CompetenciaCategoria competenciaCategoria);

		public abstract boolean gravaCompetenciaCategoria(CompetenciaCategoria competenciaCategoria);
		public abstract boolean gravaSubCategorias(CompetenciaCategoria competenciaCategoria, List<CompetenciaCategoria> subCategorias);
		public abstract boolean gravaRelacaoCategoriSubCategoria(CompetenciaCategoria competenciaCategoria, CompetenciaCategoria subCategoria);

		public abstract CompetenciaCategoria recuperarCategoriaPeloNome(String nome);
		public abstract List<CompetenciaCategoria> recuperarSubCategoriasPorCategoria(CompetenciaCategoria categoria);

		public abstract List<Competencia> recuperarCompetenciasPorCategoria(CompetenciaCategoria categoria);
		public abstract Competencia recuperarCompetenciaPeloNome(String nome);
		public abstract Competencia recuperarCompetenciaPeloId(int idCompetencia);
		public abstract List<Competencia> recuperarCompetenciasAprovadasPorResponsavel(int idPessoa);
		public abstract List<CompetenciaCategoria> recuperarCompetenciaCategoriasAprovadasPorResponsavel(int idPessoa);

		public abstract boolean aprovarCompetencia(Competencia competencia, Responsavel aprovador);
		public abstract boolean aprovarCompetenciaCategoria(CompetenciaCategoria competenciaCategoria, Responsavel aprovador);
}
