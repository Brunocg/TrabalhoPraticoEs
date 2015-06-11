package br.ufscar.consulta;

import java.util.Date;
import java.util.List;

public class PessoaData {

	private int idPessoa;
	private String nome;
	private String sitCivil;
	private String sexo;
	private Date dataNascimento;
	private String cpf;
	private String rg;
	private List<EnderecoData> enderecos;
	private String telefone;
	private String celular;
	private String email;
	private String pagPessoal;
	private String msgInst;
	private UsuarioData usuario;
	private boolean estado;
	private Date ts;
	//private List<CompetenciaExperiencia> competenciasExperiencia = null;


	public PessoaData() {
		super();
	}

	public PessoaData(int idPessoa, String nome, String sitCivil, String sexo,
			Date dataNascimento, String cpf, String rg, List<EnderecoData> enderecos,
			String telefone, String celular, String email, String pagPessoal,
			String msgInst, UsuarioData usuario, boolean estado, Date ts) {
		super();
		this.idPessoa = idPessoa;
		this.nome = nome;
		this.sitCivil = sitCivil;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.rg = rg;
		this.enderecos = enderecos;
		this.telefone = telefone;
		this.celular = celular;
		this.email = email;
		this.pagPessoal = pagPessoal;
		this.msgInst = msgInst;
		this.usuario = usuario;
		this.estado = estado;
		this.ts = ts;
	}	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------
	
	
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	public int getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSitCivil() {
		return sitCivil;
	}

	public void setSitCivil(String sitCivil) {
		this.sitCivil = sitCivil;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public List<EnderecoData> getEndereco() {
		return enderecos;
	}

	public void setEndereco(List<EnderecoData> enderecos) {
		this.enderecos = enderecos;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPagPessoal() {
		return pagPessoal;
	}

	public void setPagPessoal(String pagPessoal) {
		this.pagPessoal = pagPessoal;
	}

	public String getMsgInst() {
		return msgInst;
	}

	public void setMsgInst(String msgInst) {
		this.msgInst = msgInst;
	}

	public UsuarioData getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioData usuario) {
		this.usuario = usuario;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

}
