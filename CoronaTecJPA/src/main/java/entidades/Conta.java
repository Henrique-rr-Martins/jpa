package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="CONTA")
@Table(name="CONTA")
public class Conta {
	
	@Id
	@Column(name="Numero", nullable=false)
	private int numero_conta;
	
	@Column(name="Saldo", nullable=false)
	private double saldo;
	
	@Column(name="Limite", nullable=false)
	private double limite;

	public Conta() {}
	
	@Override
	public String toString() {
		String txt = "***Conta***";
		txt += "\n • Número: " + this.numero_conta;
		txt += "\n • Saldo: " + this.saldo;
		txt += "\n • Limite: " + this.limite;
		
		return txt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		
		Conta c = (Conta)obj;
		return this.numero_conta == c.getNumero_Conta() 
				&& this.saldo 	 == c.getSaldo()
				&& this.limite   == c.getLimite();
	}
	
	//Getters & Setters
	public int getNumero_Conta() {
		return numero_conta;
	}

	public void setNumero_Conta(int numero_conta) {
		this.numero_conta = numero_conta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}	
}
