package br.ufscar.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufscar.util.Configuracoes;

public class ConnectionManager 
{
	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//Conexao MySQL
	private static Connection conexaoMySQL;
	private static String     nomeBanco;
	private static String     host;
	private static int        porta;
	private static String     usuario;
	private static String     senha;

	private final static String		TITULO = "ConnectionManager";


	private ConnectionManager() {

	}

	//******************************CONEXAO MYSQL*********************************************\
	// * - Dados extraidos do configuracao.xml             									*

	/**
	 *  getConexaoMySQL
	 * @return Connection
	 * @throws BioHealthException
	 */
	public static Connection getConexao() {
		if(conexaoMySQL == null){
			try {
				inicializaConexao();
				System.out.println("Conectado - Connection Manager");
				System.out.println("Host: "+host+" Banco: "+nomeBanco);
			} catch (SQLException e) {
				System.out.println(TITULO + " - Erro ao obter a conexão do MySQL");
				e.printStackTrace();
			}
		}
		return conexaoMySQL;
	}

	/**
	 * inicializaConexao MySQL
	 * @throws SQLException
	 */
	private static void inicializaConexao() throws SQLException {
		//chama metodo para ler XML e preenche os atributos (dados banco)
		lerXML();
		//monta string conexao
		String STR_CON = "jdbc:mysql://" + host + ":"+porta+"/" + nomeBanco + "?user=" + usuario + "&password=" + senha;
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		conexaoMySQL = DriverManager.getConnection(STR_CON);
	}

	/**
	 * closeConexao
	 *
	 */
	public static void closeConexao() {
		try {
			if(conexaoMySQL != null){
				if (!conexaoMySQL.isClosed()) {
					conexaoMySQL.close();
					conexaoMySQL = null;
					System.out.println("Fechou conexao MySQL - ConnectionManager!!");
				}
			}
		} 
		catch (SQLException e){
			System.out.println(TITULO + " - Falha ao fechar a conexão com MySQL");
			e.printStackTrace();
		}
	}

	/**
	 * commit
	 */
	public static void commit() {
		try {
			if (conexaoMySQL != null) {
				conexaoMySQL.commit();
				conexaoMySQL.setAutoCommit(true);
			}
		}
		catch (SQLException e) 	{
			System.out.println(TITULO + " - Falha ao realizar o Commit.");
			e.printStackTrace();
		}
	}

	/**
	 * rollBack
	 */
	public static void rollBack() {
		try {
			if (conexaoMySQL != null){
				conexaoMySQL.rollback();
				System.out.println("rollback");
			}
		}
		catch (SQLException e) 	{
			System.out.println(TITULO + " - Falha ao realizar o RollBack.");
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * @param stmt
	 * @param rs
	 */
	public static void closeAll(Statement stmt, ResultSet rs) {
		try {
			if (stmt != null){
				closeAll(stmt);
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			System.out.println(TITULO + " - Falha ao fechar a conexão com MySQL");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param stmt
	 */
	public static void closeAll(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println(TITULO + " - Falha ao fechar a conexão com MySQL");
			e.printStackTrace();
		}
	}
	//******************************FIM CONEXAO MYSQL*********************************************//


	/**
	 * Leitura do arquivo XML com a configuracao para MySQL
	 */
	public static void lerXML() {
		final Document documento;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			/* Localizacao do arquivo de confuguracao padrao */
			//documento = builder.parse(new File("br/com/pradolux/principal/configuracao.xml"));
			//Localizacao do arquivo dentro do arquivo Configuracao.java
			documento = builder.parse(new File(Configuracoes.caminhoBancoXML+"configuracao.xml"));
			Element elemento = documento.getDocumentElement();
			// pega todos os elementos db do XML
			NodeList nl = elemento.getElementsByTagName( "db" );

			// percorre cada elemento db encontrado
			for( int i=0; i<nl.getLength(); i++ ) {
				Element tagDb = (Element) nl.item( i );

				// informacoes sobre o banco de dados
				host      = getChildTagValue( tagDb, "host" );
				nomeBanco = getChildTagValue( tagDb, "nomeBanco" );
				porta 	  =	Integer.parseInt(getChildTagValue( tagDb, "porta" ));
				usuario   = getChildTagValue( tagDb, "usuario" );
				senha 	  = getChildTagValue( tagDb, "senha" );
			}		
		}
		catch (SAXException sxe) {
			// Erro gerado durante a passagem
			sxe.printStackTrace();

		} 
		catch (ParserConfigurationException pce) {
			// Parser com opcoes especificas nao podem ser construidos
			pce.printStackTrace();

		} 
		catch (IOException ioe) {
			// Erro de E/S
			ioe.printStackTrace();
		}	
		catch (Exception e) {
			e.printStackTrace();
		}
	}//Fim de lerXML


	/** Este metodo le e retorna o contedo (texto) de uma tag (elemento)
	 *  filho da tag informada como parametro. A tag filho a ser pesquisada
	 *  a tag informada pelo nome (string)
	 */		
	private static String getChildTagValue( Element elem, String tagName ) 	throws Exception {
		NodeList children = elem.getElementsByTagName( tagName );
		if( children == null ) 
			return "";
		Element child = (Element) children.item(0);
		if( child == null ) 
			return "";

		if (child.getFirstChild() == null){
			return "";
		}
		return child.getFirstChild().getNodeValue();
	}

	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	//Getters and Setters
	public static String getNomeBanco() {
		return nomeBanco;
	}

	public static void setNomeBanco(String nomeBanco) {
		ConnectionManager.nomeBanco = nomeBanco;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		ConnectionManager.host = host;
	}


}
