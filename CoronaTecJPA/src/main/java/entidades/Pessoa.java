package entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PESSOA")
public class Pessoa {
	
	@Id
	@Column(name="Cpf", nullable=false, length=14)
	private String cpf;
	
	@Column(name="Nome", nullable=false, length=200)
	private String nome;
	
	@Column(name="Sexo", nullable=false, length=10)
	private String sexo;
	
	@Column(name="Idade", nullable=false)
	private int idade;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="Id_Endereco", referencedColumnName="Id_Endereco", nullable=true)
	private Endereco endereco;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="Numero_Conta", referencedColumnName = "Numero", nullable=true)
	private Conta conta;
	
	public Pessoa() {}
	
	public Pessoa(String cpf) {this.cpf = cpf;}
	
	@Override
	public String toString() {
		String txt = "***Dados Pessoais***";
		txt += "\n • CPF:\t"  + this.cpf;
		txt += "\n • Nome:\t" + this.nome;
		txt += "\n • Sexo:\t" + this.sexo;
		txt += "\n • Idade:\t" + this.idade;
		
		if(this.endereco != null) {
			txt += '\n';
			txt += endereco.toString();
		}
		
		if(this.conta != null) {
			txt += '\n';
			txt += conta.toString();
		}
		return txt;		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		
		Pessoa p = (Pessoa)obj;
		return this.cpf.equals(p.getCpf()) 
				&& this.nome.equals(p.getNome()) 
				&& this.sexo.equals(p.getSexo()) 
				&& this.idade == p.getIdade()
				&& this.endereco.equals(p.getEndereco())
				&& this.conta.equals(p.getConta());
	}

	//Getters & Setters
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
