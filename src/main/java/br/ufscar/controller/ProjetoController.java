package br.ufscar.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.aplicacao.PessoaApplicationService;
import br.ufscar.consulta.LoginData;
import br.ufscar.consulta.PessoaData;
import br.ufscar.util.Response;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {
	
	@Autowired
	private PessoaApplicationService servico;
	
	@RequestMapping(value = "/inserir/basico", method = RequestMethod.POST)
	@ResponseBody 
	public Response inserir(@RequestBody PessoaData pessoa){
		PessoaData pessoaData = servico.obterDataPeloId(servico.inserir(pessoa));
		return new Response(pessoaData != null, pessoaData);
	}
	
	@RequestMapping("/obter")
	@ResponseBody 
	public Response obter(@RequestParam("id") String pessoaId){
		PessoaData pessoaData = servico.obterDataPeloId(Integer.valueOf(pessoaId));
		return new Response(pessoaData != null, pessoaData);
	}
	
	@RequestMapping(value = "/listar", method=RequestMethod.GET)
	@ResponseBody 
	public Response listar(){
		List<PessoaData> pessoaDataList = servico.listar();
		return new Response(pessoaDataList != null, pessoaDataList);
	}
	
	@RequestMapping(value = "/excluir")
	@ResponseBody
	public Response excluir(@RequestParam("id") String pessoaId){
		return new Response(servico.excluir(Integer.valueOf(pessoaId)), null);
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	@ResponseBody
	public Response editar(@RequestBody PessoaData pessoa){
		PessoaData pessoaData = servico.obterDataPeloId(servico.editar(pessoa));
		return new Response(pessoaData != null, pessoaData);
	}
	
	@RequestMapping(value = "/usuario/login", method = RequestMethod.POST)
	@ResponseBody
	public Response login(@RequestBody LoginData login, HttpServletRequest r){
		if (servico.loginValido(login)) {
			r.getSession().setAttribute("logado", servico.loginEfetuado(login));
			return new Response(true, null);
		} else {
			return new Response(false, null);
		}		
	}	
	
	@RequestMapping(value = "/usuario/logado", method = RequestMethod.GET)
	@ResponseBody
	public Response getSessaoLogado(HttpServletRequest r){
		return new Response(true, r.getSession().getAttribute("logado"));
	}
	
	@RequestMapping(value = "/usuario/logout", method = RequestMethod.GET)
	@ResponseBody
	public Response logout(HttpServletRequest r){
		r.getSession().setAttribute("logado", null);
		return new Response(true, null);		
	}	

}
