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

import br.ufscar.aplicacao.UsuarioApplicationService;
import br.ufscar.consulta.LoginData;
import br.ufscar.consulta.UsuarioData;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioApplicationService servico;
	
	@RequestMapping(value = "/inserir", method = RequestMethod.POST)
	@ResponseBody 
	public UsuarioData inserir(@RequestBody UsuarioData usuario){
		int usuarioId = servico.inserir(usuario);
		return servico.obterDataPeloId(usuarioId);
	}
	
	@RequestMapping("/obter")
	@ResponseBody 
	public UsuarioData obter(@RequestParam("id") String usuarioId){
		return servico.obterDataPeloId(Integer.valueOf(usuarioId));
	}
	
	@RequestMapping(value = "/listar", method=RequestMethod.GET)
	@ResponseBody 
	public Page<UsuarioData> listar(Pageable pageable){
		return servico.listar(pageable);
	}
	
	@RequestMapping(value = "/excluir", method = RequestMethod.POST)
	@ResponseBody
	public boolean excluir(@RequestParam("id") String usuarioId){
		return servico.excluir(Integer.valueOf(usuarioId));
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	@ResponseBody
	public UsuarioData editar(@RequestBody UsuarioData usuario){
		int usuarioId = servico.editar(usuario);
		return servico.obterDataPeloId(usuarioId);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public boolean login(@RequestBody LoginData usuario){
		return servico.loginValido(usuario);
		
	}	

}
