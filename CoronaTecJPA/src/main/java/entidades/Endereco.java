package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ENDERECO")
public class Endereco {

	@Id
	@Column(name="Id_Endereco", nullable=false)
	@GeneratedValue(generator="S_ID_ENDERECO")
	@SequenceGenerator(name="S_ID_ENDERECO", sequenceName="S_ID_ENDERECO", allocationSize = 1)
	private int id_endereco;
	
	@Column(name="Rua", nullable=false, length=200)
	private String rua;
	
	@Column(name="Numero", nullable=false)
	private int numero;
	
	@Column(name="Complemento", nullable=false)
	private String complemento;

	public Endereco() {}
	
	@Override
	public String toString() {
		String txt = "***Endereço***";
		txt += "\n • " + this.rua;
		txt += ", "    + this.numero;
		txt += ", "    + this.complemento;
		
		return txt;
	}
	
	public boolean equals(Object obj) {
		
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		
		Endereco e = (Endereco)obj;
		return this.id_endereco == e.getId_Endereco()
				&& this.rua.equals(e.getRua())
				&& this.numero == e.getNumero()
				&& this.complemento.equals(e.getComplemento());
	}
	
	//Getters & Setters
	public int getId_Endereco() {
		return id_endereco;
	}

	public void setId_Endereco(int id_endereco) {
		this.id_endereco = id_endereco;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
}
