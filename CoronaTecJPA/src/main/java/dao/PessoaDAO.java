package dao;

import java.util.List;

import entidades.Pessoa;

public interface PessoaDAO {

	public boolean inserirPessoa(Pessoa pessoa);
	
	public boolean alterarPessoa(Pessoa pessoa);
	
	public boolean removerPessoa(Pessoa pessoa);
	
	public Pessoa pesquisarPessoa(String cpf);
	public List<Pessoa> pesquisarPessoa(Pessoa pessoa);
	
	
}
