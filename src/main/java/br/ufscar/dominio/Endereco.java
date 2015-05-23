package br.ufscar.dominio;

import java.util.List;


public class Endereco {

	private int idEndereco;
	private String rua;
	private String bairro;
	private int numero;
	private String cidade;
	private String uf;
	private String pais;
	private String cep;
	private List<Pessoa> moradores;


	public Endereco() {
		super();
	}

	public Endereco(int idEndereco, String rua, String bairro, int numero,
			String cidade, String uf, String pais, String cep, List<Pessoa> moradores) {
		super();
		this.idEndereco = idEndereco;
		this.rua = rua;
		this.bairro = bairro;
		this.numero = numero;
		this.cidade = cidade;
		this.uf = uf;
		this.pais = pais;
		this.cep = cep;
		this.moradores = moradores;
	}
	
	//-------------------------------------------------------------------------Metodos-------------------------------------------------------------------------


	
	
	//-------------------------------------------------------------------------Getters and Setters-------------------------------------------------------------------------
	
	public int getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}

	public List<Pessoa> getMoradores() {
		return moradores;
	}

	public void setMoradores(List<Pessoa> moradores) {
		this.moradores = moradores;
	}

}
