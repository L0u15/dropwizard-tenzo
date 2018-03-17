package fr.louis.dropwizard_tenzo.jdbi;

import java.util.List;

import org.hibernate.SessionFactory;

import fr.louis.dropwizard_tenzo.core.Product;
import fr.louis.dropwizard_tenzo.core.Unit;
import io.dropwizard.hibernate.AbstractDAO;

public class UnitDAO extends AbstractDAO<Unit> implements MyDAO<Unit> {

	public UnitDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Unit> getAll() {
		return super.list(namedQuery("Unit.findAll"));

	}

	public Unit findById(Integer id) {
		return super.get(id);
	}

	public void delete(Unit unit) {
		super.currentSession().delete(unit);
	}

	public Unit saveOrUpdate(Unit unit) {
		return super.persist(unit);
	}

	public Unit findByName(String name) {
		return super.uniqueResult(namedQuery("Unit.findByName").setParameter("unitName", name));

	}

	public boolean contains(String name) {
		return this.findByName(name) != null;
	}

}
