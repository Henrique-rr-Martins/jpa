package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {
	
	@SuppressWarnings("finally")
	public static Connection getConexao() throws Exception{
		
		String url, usuario, senha;
		
		url     = "jdbc:oracle:thin:@localhost:1521:xe";
		usuario = "CORONATEC";
		senha   = "CORONATEC";
		
		Connection conexao = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conexao = DriverManager.getConnection(url, usuario, senha);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			return conexao;
		}
	}
}
