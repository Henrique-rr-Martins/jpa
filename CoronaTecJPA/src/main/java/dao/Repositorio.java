package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entidades.Conta;
import entidades.Endereco;
import entidades.Pessoa;
import util.JDBCUtil;

public class Repositorio {

	// METODOS ENDERECO

	/**
	 * Recuperar o proximo numero da sequence de Id do ENDERECO
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	public Integer recuperaIdEndereco() {
		String sql = "Select S_ID_ENDERECO.Nextval from dual";

		Integer id_retorno = null;

		Connection conexao = null;

		try {
			conexao = JDBCUtil.getConexao();
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next())
				id_retorno = rs.getInt(1);

			rs.close();
			ps.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return id_retorno;
		}
	}

	/**
	 * Isere o ENDERECO adicionando o Id atraves do metodo recuperaEndereco()
	 * 
	 * @param endereco
	 * @return
	 */
	@SuppressWarnings("finally")
	public int inserirEndereco(Endereco endereco) {
		int qtdLinhas = 0;

		String sql = "Insert Into ENDERECO(Id_Endereco, numero, rua, complemento) ";
		sql += " Values(?, ?, ?, ?)";

		Connection conexao;

		try {
			conexao = JDBCUtil.getConexao();

			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, this.recuperaIdEndereco());
			ps.setInt(2, endereco.getNumero());
			ps.setString(3, endereco.getRua());
			ps.setString(4, endereco.getComplemento());

			qtdLinhas = ps.executeUpdate();

			ps.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return qtdLinhas;
		}
	}

	/**
	 * Deleta o endereco atraves de seu id
	 * 
	 * @param endereco
	 * @return
	 */
	@SuppressWarnings("finally")
	public int[] removerPessoa(Pessoa pessoa) {
		int[] listaQtd = { 0, 0, 0 };

		String sqlPessoa = "Delete From PESSOA P Where P.Cpf = ? ";
		String sqlConta = "Delete From CONTA C Where C.Numero = ? ";
		String sqlEndereco = "Delete From ENDERECO E Where E.Id_Endereco = ? ";

		Connection conexao;

		try {
			conexao = JDBCUtil.getConexao();

			PreparedStatement ps = conexao.prepareStatement(sqlPessoa);
			ps.setString(1, pessoa.getCpf());

			listaQtd[0] = ps.executeUpdate();
			ps.close();

			if (pessoa.getEndereco() != null && pessoa.getEndereco().getId_Endereco() > 0) {
				ps = conexao.prepareStatement(sqlEndereco);
				ps.setInt(1, pessoa.getEndereco().getId_Endereco());

				listaQtd[1] = ps.executeUpdate();
				ps.close();
			}

			if (pessoa.getConta() != null && pessoa.getConta().getNumero_Conta() > 0) {
				ps = conexao.prepareStatement(sqlConta);
				ps.setInt(1, pessoa.getConta().getNumero_Conta());

				listaQtd[2] = ps.executeUpdate();
				ps.close();
			}

			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return listaQtd;
		}

	}

	/**
	 * Retorna pesquisa de Pessoa com todos seus atributos.
	 * Se Conta != null irá retornar também
	 * Se Endereco != null irá retornar também
	 * @param pessoa
	 * @return
	 */
	public List<Pessoa> pesquisarPessoa(Pessoa pessoa) {
		List<Pessoa> lista = new ArrayList<Pessoa>();

		String anagrama = "";

		String sql = "Select P.CpF         As Cpf\r\n" + "     , P.Nome        As Nome\r\n"
				+ "     , P.Sexo        As Sexo\r\n" + "     , P.Idade       As Idade\r\n"
				+ "     , C.Numero      As Numero_Conta\r\n" + "     , C.Saldo       As Saldo\r\n"
				+ "     , C.Limite      As Limite\r\n" + "     , E.Id_Endereco As Id_Endereco\r\n"
				+ "     , E.Rua         As Rua\r\n" + "     , E.Numero      As Numero_Endereco\r\n"
				+ "     , E.Complemento As Complemento\r\n" + "  From PESSOA        P\r\n"
				+ "  Left Join ENDERECO E On P.Id_Endereco  = E.Id_Endereco\r\n"
				+ "  Left Join CONTA    C On P.Numero_Conta = C.Numero" + " Where 1 = 1 ";

		if (pessoa.getCpf() != null) {
			sql += " And Cpf Like ? ";
			anagrama += "c";
		} else {
			if (pessoa.getNome() != null) {
				sql += " And Nome Like ? ";
				anagrama += "n";
			}
			if (pessoa.getSexo() != null) {
				sql += " And Sexo Like ? ";
				anagrama += "s";
			}
			if (pessoa.getIdade() > 0) {
				sql += " And Nome Like ? ";
				anagrama += "i";
			}
		}

		Connection conexao = null;

		try {
			conexao = JDBCUtil.getConexao();

			PreparedStatement ps = conexao.prepareStatement(sql);

			System.out.println("Anagrama " + anagrama + "length: " + anagrama.length());

			if (anagrama.contains("c")) {
				System.out.println("Cpf: Ok");
				ps.setString(1, pessoa.getCpf() + "%");
			}

			if (anagrama.length() == 1) {
				if (anagrama.contains("n")) {
					System.out.println("Nome: Ok");
					ps.setString(1, "%" + pessoa.getNome() + "%");
				}
				if (anagrama.contains("s")) {
					System.out.println("Sexo: Ok");
					ps.setString(1, pessoa.getSexo());
				}
				if (anagrama.contains("i")) {
					System.out.println("Idade: Ok");
					ps.setInt(1, pessoa.getIdade());
				}
			}
			if (anagrama.length() == 2) {
				if (anagrama.contains("ns")) {
					System.out.println("Nome e Sexo: Ok");
					ps.setString(1, pessoa.getNome());
					ps.setString(2, pessoa.getSexo());
				}
				if (anagrama.contains("ni")) {
					System.out.println("Nome e Idade: Ok");
					ps.setString(1, pessoa.getNome());
					ps.setInt(2, pessoa.getIdade());
				}
				if (anagrama.contains("si")) {
					System.out.println("Sexo e Idade: Ok");
					ps.setString(1, pessoa.getSexo());
					ps.setInt(2, pessoa.getIdade());
				}
			}
			if (anagrama.length() == 3) {
				System.out.println("Nome, Sexo e Idade: Ok");
				ps.setString(1, pessoa.getNome());
				ps.setString(2, pessoa.getSexo());
				ps.setInt(3, pessoa.getIdade());
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Pessoa p = new Pessoa();
				p.setCpf(rs.getString(rs.findColumn("Cpf")));
				p.setNome(rs.getString(rs.findColumn("Nome")));
				p.setSexo(rs.getString(rs.findColumn("Sexo")));
				p.setIdade(rs.getInt(rs.findColumn("Idade")));

				if (rs.getInt(rs.findColumn("Numero_Conta")) > 0) {
					Conta c = new Conta();
					c.setNumero_Conta(rs.getInt(rs.findColumn("Numero_Conta")));
					c.setSaldo(rs.getDouble(rs.findColumn("Saldo")));
					c.setLimite(rs.getInt(rs.findColumn("Limite")));
					
					p.setConta(c);
				}
				if(rs.getInt(rs.findColumn("Id_Endereco")) > 0) {
					Endereco e = new Endereco();
					e.setId_Endereco(rs.getInt(rs.findColumn("Id_Endereco")));
					e.setNumero(rs.getInt(rs.findColumn("Numero_Endereco")));
					e.setRua(rs.getString(rs.findColumn("Rua")));
					e.setComplemento(rs.getString(rs.findColumn("Complemento")));
					
					p.setEndereco(e);
				}
				
				lista.add(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

}
