package br.ufscar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.aplicacao.ProjetoApplicationService;
import br.ufscar.consulta.ProjetoData;
import br.ufscar.util.Response;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {
	
	@Autowired
	private ProjetoApplicationService servico;
	
	@RequestMapping("/obter")
	@ResponseBody 
	public Response obter(@RequestParam("id") String projetoId){
		ProjetoData projetoData = servico.obterDataPeloId(Integer.valueOf(projetoId));
		return new Response(projetoData != null, projetoData);
	}
	
	@RequestMapping(value = "/listar", method=RequestMethod.GET)
	@ResponseBody 
	public Response listar(){
		List<ProjetoData> projetoDataList = servico.listar();
		return new Response(projetoDataList != null, projetoDataList);
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	@ResponseBody
	public Response editar(@RequestBody ProjetoData projeto){
		ProjetoData projetoData = servico.obterDataPeloId(servico.editar(projeto));
		return new Response(projetoData != null, projetoData);
	}
	
}
