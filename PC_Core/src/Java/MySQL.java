package Java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

	//com.mysql.jdbc.Driver
	private String host;
	private String port;
	private String user;
	private String password;
	private String database;
	private Connection connection;
	private String url;

	
	public MySQL(String host, String port, String user, String password, String database){
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
	}
	
	public MySQL(String host, String port, String user, String password){
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public void open(){
		try{
			if(database != null)
				url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?verifyServerCertificate=false&useSSL=true";
			else
				url  = "jdbc:mysql://" + this.host + ":" + this.port + "?verifyServerCertificate=false&useSSL=true";
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
		}catch(ClassNotFoundException e){
			System.out.println("--------------------------------");
			System.out.println("Erro: " + e.getMessage());
			System.out.println("Cause: " + e.getCause());
			System.out.println("From: " + e.getLocalizedMessage());
			System.out.println("--------------------------------");
		}catch (SQLException e){
			System.out.println("--------------------------------");
			System.out.println("Erro: " + e.getMessage());
			System.out.println("Cause: " + e.getCause());
			System.out.println("From: " + e.getLocalizedMessage());
			System.out.println("--------------------------------");
		}finally{
			if(connection != null){
				System.out.println("---------------[MySQL]---------------");
				System.out.println("Conexão realizada com sucesso!");
				System.out.println("Url:" + url);
				System.out.println("---------------[MySQL]---------------");
			}else{
				System.out.println("---------------[MySQL]---------------");
				System.out.println("Falha ao realizar conexão com banco de dados!");
				System.out.println("Url:" + url);
				System.out.println("---------------[MySQL]---------------");
			}
		}
	}
	
	public void close(){
		try{
			connection.close();
		}catch(SQLException e){
			System.out.println("--------------------------------");
			System.out.println("Erro: " + e.getMessage());
			System.out.println("Cause: " + e.getCause());
			System.out.println("From: " + e.getLocalizedMessage());
			System.out.println("--------------------------------");
		}
	}

}
