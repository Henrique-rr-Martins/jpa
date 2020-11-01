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

	@Override
	public Pessoa pesquisarPessoa(String cpf) {
		Pessoa pessoa;
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();

		pessoa = em.find(Pessoa.class, cpf);

		return pessoa;
	}

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