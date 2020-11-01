package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author Andre
 *
 * A classe JPAUtil tem funcionalidade  de disponibilizar as EntityManger (conexoes com o banco de dados)
 * Tambem e uma classe sington, so vai existir uma instancia dessa classe no projeto todo.
 * 
 */

public class JpaUtil {
	
	private static EntityManagerFactory factory;
	
	static {
		factory = Persistence.createEntityManagerFactory("CORONATEC");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static void close() {
		factory.close();
	}
}
