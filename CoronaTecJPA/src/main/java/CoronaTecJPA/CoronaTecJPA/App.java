package CoronaTecJPA.CoronaTecJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dao.PessoaDaoImpl;
import entidades.Conta;
import entidades.Endereco;
import entidades.Pessoa;
import util.JpaUtil;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {        
        Conta c = new Conta();
        c.setNumero_Conta(001);
        c.setLimite(6000);
        c.setSaldo(12000);
        
        Endereco e = new Endereco();
        e.setNumero(11971);
        e.setRua("Aldeia dos Camarás");
        e.setComplemento("CS");
        
        Pessoa p = new Pessoa();
        p.setCpf("0001");
        p.setIdade(31);
        p.setNome("Henrique Martins");
        p.setSexo("M");
        p.setConta(c);
        p.setEndereco(e);
        
        System.out.println(new PessoaDaoImpl().pesquisarPessoa(p));
        
        
        Pessoa p1 = new Pessoa();
        //p1.setNome("Henrique");
        p1.setSexo("M");
        p1.setIdade(31);
        
        //new PessoaDaoImpl().pesquisarPessoa(p1).forEach(pes -> System.out.println(pes));        
    }
}
