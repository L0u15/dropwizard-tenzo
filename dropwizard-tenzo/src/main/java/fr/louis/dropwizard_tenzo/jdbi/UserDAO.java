package fr.louis.dropwizard_tenzo.jdbi;

import java.util.List;

import org.hibernate.SessionFactory;

import fr.louis.dropwizard_tenzo.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;

public class UserDAO extends AbstractDAO<User> implements MyDAO<User> {

	public UserDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<User> getAll() {
		return super.list(namedQuery("User.findAll"));

	}

	public User findById(Integer id) {
		return super.get(id);
	}

	public User findByNameAndPassword(String name, String password) {
		return super.uniqueResult(
				namedQuery("User.findByNameAndPassword").setParameter("name", name).setParameter("password", password));
	}

	public void delete(User user) {
		super.currentSession().delete(user);

	}

	public User saveOrUpdate(User user) {
		return super.persist(user);
	}

	public User findByName(String name) {
		return super.uniqueResult(namedQuery("User.findByName").setParameter("name", name));

	}

	public boolean contains(String name) {
		return this.findByName(name) != null;
	}

}
