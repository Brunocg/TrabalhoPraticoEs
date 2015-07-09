package br.ufscar.aplicacao;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.ufscar.consulta.ProjetoData;
import br.ufscar.dominio.Projeto;
import br.ufscar.dominio.interfaces.IProjetoRepository;


@Service
@Component
public class ProjetoApplicationService {
	
	@Autowired
	private IProjetoRepository repositorio;
	private Mapper mapper = new DozerBeanMapper();

	public ProjetoData obterDataPeloId(int projetoId) {		    
	    return mapper.map(repositorio.recuperarProjetoPorId(projetoId), ProjetoData.class);
	} 

	public int inserir(ProjetoData projetoData) {
		
		Projeto projeto = mapper.map(projetoData, Projeto.class);
		
		repositorio.gravaProjeto(projeto);
		
		return projeto.getIdProjeto();
	}

	public List<ProjetoData> listar() {		
		List<ProjetoData> result = new ArrayList<ProjetoData>();
		
		for (Projeto projeto : repositorio.listarProjetos()) 
			result.add(mapper.map(projeto, ProjetoData.class));		
		
		return result;			
	}

	public int editar(ProjetoData projetoData) {
		Projeto projeto = mapper.map(projetoData, Projeto.class);
		
		repositorio.alterarProjeto(projeto);
		
		return projeto.getIdProjeto();
	}
}
