package testes;

import java.sql.Connection;
import java.sql.PreparedStatement;

import util.JDBCUtil;

public class TesteConexaoJDBC {
	public static void main(String [] args) {
		
		boolean retorno = false;
		
		String sql = "select * from dual";
		
		try {
			Connection conexao = JDBCUtil.getConexao();
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.execute();
			ps.close();
			conexao.close();
			
			retorno = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println(retorno);
		}
	}
}
