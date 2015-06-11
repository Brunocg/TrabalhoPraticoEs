package br.ufscar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.aplicacao.PessoaApplicationService;
import br.ufscar.consulta.LoginData;
import br.ufscar.consulta.PessoaData;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaApplicationService servico;
	
	@RequestMapping(value = "/inserir", method = RequestMethod.POST)
	@ResponseBody 
	public PessoaData inserir(@RequestBody PessoaData pessoa){
		int pessoaId = servico.inserir(pessoa);
		return servico.obterDataPeloId(pessoaId);
	}
	
	@RequestMapping("/obter")
	@ResponseBody 
	public PessoaData obter(@RequestParam("id") String pessoaId){
		return servico.obterDataPeloId(Integer.valueOf(pessoaId));
	}
	
	@RequestMapping(value = "/listar", method=RequestMethod.GET)
	@ResponseBody 
	public Page<PessoaData> listar(Pageable pageable){
		return servico.listar(pageable);
	}
	
	@RequestMapping(value = "/excluir", method = RequestMethod.POST)
	@ResponseBody
	public boolean excluir(@RequestParam("id") String pessoaId){
		return servico.excluir(Integer.valueOf(pessoaId));
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	@ResponseBody
	public PessoaData editar(@RequestBody PessoaData pessoa){
		int pessoaId = servico.editar(pessoa);
		return servico.obterDataPeloId(pessoaId);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public boolean login(@RequestBody LoginData pessoa){
		return servico.loginValido(pessoa);
		
	}	

}
