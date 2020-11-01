package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Pessoa;
import util.JpaUtil;

public class PessoaDaoImpl implements PessoaDAO {
	
	/**
	 * Busca entidade pessoa no banco realizando uma consulta pelo id da pessoa.
	 * @param String cpf.
	 * @return pessoa ou null se não achar nada.
	 */
	@Override
	public Pessoa pesquisarPessoa(String cpf) {
		Pessoa pessoa;
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();

		pessoa = em.find(Pessoa.class, cpf);

		return pessoa;
	}

	
	/**
	 * Busca mais completa podendo filtrar pela id da etidade ou mesmo pelos outros campos usando 1 ou mais de um filtro.
	 * @param Pessoa pessoa.
	 * @return pessoa ou null se não achar nada.
	 */
	@Override
	public List<Pessoa> pesquisarPessoa(Pessoa pessoa) {
		// lista de pessoas
		List<Pessoa> lista = new ArrayList<>();

		if (pessoa.getCpf() != null && pessoa.getCpf().trim().length() > 0) {
			lista.add(this.pesquisarPessoa(pessoa.getCpf()));
		} else {
			// Anagrama
			String anagrama = "";

			// JPA
			EntityManager em = JpaUtil.getEntityManager();

			// Dá suporte a query
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

			// Simula as clausulas (select, from, order, where, having, group, etc)
			CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);

			// simula o alias
			Root<Pessoa> root = criteriaQuery.from(Pessoa.class);
			// define a query
			criteriaQuery.select(root);
			if (pessoa.getNome() != null && pessoa.getNome().trim().length() > 0) {
				anagrama += "n";
			}
			if (pessoa.getSexo() != null && pessoa.getSexo().trim().length() > 0) {
				anagrama += "s";
			}
			if (pessoa.getIdade() > 0) {
				anagrama += "i";
			}

			if (anagrama.length() == 1) {
				if (anagrama.equals("n")) {
					criteriaQuery.where(criteriaBuilder.like(root.get("nome"), "%" + pessoa.getNome() + "%"));
				}
				if (anagrama.equals("s")) {
					criteriaQuery.where(criteriaBuilder.like(root.get("sexo"), "%" + pessoa.getSexo() + "%"));
				}
				if (anagrama.equals("i")) {
					criteriaQuery.where(criteriaBuilder.equal(root.get("idade"), pessoa.getIdade()));
				}
			}
			if (anagrama.length() == 2) {
				if (anagrama.equals("ns")) {
					criteriaQuery.where(criteriaBuilder.like(root.get("nome"), "%" + pessoa.getNome() + "%"),
							criteriaBuilder.like(root.get("sexo"), "%" + pessoa.getSexo() + "%"));
				}
				if (anagrama.equals("ni")) {
					criteriaQuery.where(criteriaBuilder.like(root.get("nome"), "%" + pessoa.getNome() + "%"),
							criteriaBuilder.equal(root.get("idade"), pessoa.getIdade()));
				}
				if (anagrama.equals("si")) {
					criteriaQuery.where(criteriaBuilder.like(root.get("sexo"), "%" + pessoa.getSexo() + "%"),
							criteriaBuilder.equal(root.get("idade"), pessoa.getIdade()));
				}
			}
			if (anagrama.length() == 3) {
				if (anagrama.equals("nsi")) {
					criteriaQuery.where(criteriaBuilder.like(root.get("nome"), "%" + pessoa.getNome() + "%"),
							criteriaBuilder.like(root.get("sexo"), "%" + pessoa.getSexo() + "%"),
							criteriaBuilder.equal(root.get("idade"), pessoa.getIdade()));
				}
			}

			TypedQuery<Pessoa> typedQuery = em.createQuery(criteriaQuery);
			lista = typedQuery.getResultList();
		}
		return lista;
	}

	/**
	 * Insere pessoa se a chave principal não existir no banco.
	 * @param Pessoa pessoa.
	 * @return true se inserido e false se a chave já existia.
	 */
	@Override
	public boolean inserirPessoa(Pessoa pessoa) {

		// Manipulador de entidade - Entity Manager é a JPA
		// Realiza os processos de DML
		// Por tras, também realiza o controle de conexão diretamente com o banco
		// atraves do obj Persistence.xml
		EntityManager em = JpaUtil.getEntityManager();
		// Realiza o controle da área de transação dos dados. Antes da persistência,
		// onde pode ser realizado o Rollback, o Save Point e o Commit
		EntityTransaction et = em.getTransaction();
		// Abre a área de transação
		et.begin();
		// Se pessoa diferente de nulo
		if (pesquisarPessoa(pessoa.getCpf()) != null)
			// retorna falso
			return false;
		// Se igual a nulo
		else
			// Realiza o insert
			em.persist(pessoa);
		// Commita as informações que estão na área de transação de dados
		et.commit();
		// Fecha o EntityManager
		em.close();

		return true;
	}

	/**
	 * Busca a pessoa existente no banco atraves dos atributos e 
	 */
	@Override
	public boolean alterarPessoa(Pessoa pessoa) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();

		// Retorno
		boolean retorno = true;

		et.begin();

		// Recupera a entidade pessoa do banco usando o mapeamento do JPA passando a
		// classe.class
		Pessoa existe = em.find(Pessoa.class, pessoa.getClass());

		// Se existir a pessoa ele retorna ela, senão null
		if (existe != null) {

			existe.setNome(pessoa.getNome());
			existe.setSexo(pessoa.getSexo());
			existe.setIdade(pessoa.getIdade());

			em.merge(pessoa);
		} else {
			retorno = false;
		}

		et.commit();
		em.close();

		return retorno;
	}

	@Override
	public boolean removerPessoa(Pessoa pessoa) {
		return false;
	}

}