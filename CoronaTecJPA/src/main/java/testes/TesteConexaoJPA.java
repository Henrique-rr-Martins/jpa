package testes;

import javax.persistence.EntityManager;

import util.JpaUtil;

public class TesteConexaoJPA {

	public static void main(String[] args) {
		
		EntityManager ent = JpaUtil.getEntityManager();
		System.out.println(ent.isOpen());

	}

}
